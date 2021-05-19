package com.lyl.jvm.py.O002;

import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.concurrent.locks.LockSupport;

public class MyLock1 {
    //status代表是否锁定，0代表未锁定，1代表锁定
    private volatile int state = 0;
    private Unsafe unsafe;
    private long statusOffset;
    private long tailOffset; //队尾元素偏移量
    private long headOffset; //对头元素偏移量
    private long waitStatusOffset;
 
    private transient volatile Node head;
    private transient volatile Node tail;
 
    //当前占有锁的对象
    private Thread exclusiveOwnerThread;
 
    public MyLock1() throws NoSuchFieldException, IllegalAccessException {
        Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
        theUnsafe.setAccessible(true);
        unsafe = (Unsafe) theUnsafe.get(null);
        statusOffset = unsafe.objectFieldOffset(MyLock1.class.getDeclaredField("state"));
        tailOffset = unsafe.objectFieldOffset(MyLock1.class.getDeclaredField("tail"));
        headOffset = unsafe.objectFieldOffset(MyLock1.class.getDeclaredField("head"));
        waitStatusOffset = unsafe.objectFieldOffset(Node.class.getDeclaredField("waitStatus"));
    }
 
    /**
     * 加锁方法
     */
    public void lock() {
        /**
         * 1. 如果tryAcquire返回true，说明已经获取到锁，则不在执行后面的程序（创建node，排队）
         * 2. 如果tryAcquire返回
         *
         * 获取锁的几种情况
         * 1. 第一个线程，不需要排队，不需要等待，不需要队列，直接获取锁
         * 2. 锁被占用，需要排队，但是当前没有队列，需要新建队列并入队
         * 3. 锁被占用，需要排队，而且队列中已经有其他线程，则直接入队等待
         *
         */
        if (!tryAcquire(1) &&
                acquireQueued(addWaiter(), 1)) {
            Thread.currentThread().interrupt();
        }
    }
 
    /**
     * 判断传入的node是否要排队，如果需要，则开始排队，如果不需要直接获取锁
     * 执行这个方法说明node肯定已经入队，但未必是等待状态，这个方法就是要让线程等待或者获取锁
     * 判断当前线程的上一个元素是否是head元素，如果是，就尝试获取锁，如果获取锁成功，就把当前元素置为head元素，并返回true
     * 如果上一个元素不是head元素，或者获取锁失败就判断当前元素是否需要等待，如果需要则等待，如果不需要则自旋
     *
     * @param node
     * @param arg
     * @return 如果排队成功（线程开始等待）返回true，没有等待就返回false
     */
    private boolean acquireQueued(Node node, int arg) {
 
        while (true) {
            //判断当前node的pre是不是head，如果是head不需要排队 尝试获取锁
            if (node.pre == head && tryAcquire(1)) {
                setHead(node);//因为已经获取到锁，将当前node设置成头元素
                compareAndSetNodeStatus(node, node.waitStatus, Node.DEFAULT);
                return false;
            }
            //判断是否需要排队等待
            if (shouldParkAfterFailedAcquire(node) && parkAndCheckInterrupt(node)) {
                return true;
            }
        }
    }
 
    /**
     * 判断当前线程是否需要排队
     *
     * @param node
     * @return
     */
    private boolean shouldParkAfterFailedAcquire(Node node) {
        Node pre = node.pre;
        //如果上一个元素是头元素是不需要等待,
        //如果上一个元素的状态是等待状态，当前的线程必须等待
        //如果上一个元素不是头元素，也不是等待状态，也没有获取锁，当前线程就等待
        if (pre != null && pre != head && pre.waitStatus != Node.CANCELLED) {
            return true;
        }
 
        //判断如果上一个元素是否被取消，如被取消将上一个元素从当前的队列中移除
        if (pre != null && pre.waitStatus == Node.CANCELLED) {
            do {
                node.pre = pre = pre.pre;
            } while (pre.waitStatus == Node.CANCELLED);
            pre.next = node;
        }
 
        return false;
    }
 
    /**
     * 中断当前线程，排队等待
     *
     * @param node
     * @return
     */
    private boolean parkAndCheckInterrupt(Node node) {
        compareAndSetNodeStatus(node, node.waitStatus, Node.WAIT);
        LockSupport.park(this);
        return Thread.interrupted();
    }
 
    private boolean compareAndSetNodeStatus(Node node, int waitStatus, int aDefault) {
        return unsafe.compareAndSwapInt(node, waitStatusOffset, waitStatus, aDefault);
    }
 
    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.pre = null;
    }
 
    /**
     * 自旋完成入队操作
     * 如果是一个未创建的队列，则
     * 第一次循环：创建了队头元素
     * 第二次循环：创建新元素，并将队尾指向新元素
     *
     * @param node
     */
    private Node enq(Node node) {
        while (true) {
            //获取队尾元素
            Node t = tail;
            if (t == null) { //队列没有初始化。或者正在初始化
                //如果队列不存在，创建新的队列
                //原子操作创建对头元素
                if (compareAndSetHead(new Node())) {
                    //如果创建成功，将head和tail指向同一个元素
                    tail = head;
                }
 
            } else {
                if (compareAndSetTail(t, node)) {
                    node.pre = t;
                    t.next = node;
                    return node;
                }
            }
        }
    }
 
    private boolean compareAndSetHead(Node node) {
        return unsafe.compareAndSwapObject(this, headOffset, null, node);
    }
 
    /**
     * 原子操作替换队尾元素
     *
     * @param pred
     * @param node
     * @return
     */
    private boolean compareAndSetTail(Node pred, Node node) {
        return unsafe.compareAndSwapObject(this, tailOffset, pred, node);
    }
 
    /**
     * 根据当前线程，创建一个node对象，并将该对象放入队列
     * <p>
     * 实现步骤：
     * 1. 使用当前线程创建一个新的Node对象
     * 2. 判断尾元素是否存在，如果存在就将当前元素加入队列，并且将当前元素修改为尾元素
     * 3. 如果尾元素不存在则说明队列本身就不存在，但是又没有获取到锁，说明目前有一个线程在占用锁，没有其他线程等待
     * 4.
     *
     * @return
     */
    private Node addWaiter() {
        //根据当前线程创建一个Node
        Node node = new Node(Thread.currentThread());
        //取出队列尾元素
        Node pred = tail;
        if (pred != null) {
            node.pre = pred;  //新元素的上一个元素就是队尾元素
            //原子操作替换队尾元素
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
 
        }
        //如果队尾元素不存在，入队
        enq(node);
        return node;
    }
 
    /**
     * 尝试获取锁
     *
     * @param
     * @return 如果获取到锁，就返回true, 如果没有就返回false
     * <p>
     * 实现步骤：
     * 1. 判断锁是否是空闲状态------判断state的值
     * 1）如果是空闲状态，就开始判断自己是否需要排队，如果不需要排队就直接获取锁（CAS），如果需要排队就返回false
     * 2) 如果不是空闲状态，则判断自己是否重入，如果是重入，则 status+1 ,并且返回true
     * 3）其他情况全部返回false
     */
    private boolean tryAcquire(int arg) {
 
        //获取当前线程对象
        Thread thread = Thread.currentThread();
        //获取当前锁的状态
        int state = getState();
        if (0 == state) {  //当前锁处于释放状态
            //判断自己是否需要排队
            if (!hasQueuedPredecessors() && compareAndSet(0, arg)) {
                //获取锁成功,设置占有锁的线程为当前线程
                setExclusiveOwnerThread(thread);
                System.out.println(thread.getName() + ": 获取锁成功");
                return true;
            }
 
            //当前尝试获取锁的线程就是占有锁的线程----锁的重入
        } else if (thread == getExclusiveOwnerThread()) {
//            compareAndSet(state,getState() + arg);
            setState(state + arg);
        }
        return false;
    }
 
    /**
     * 判断自己是否需要排队
     *
     * @return 如果返回true说明要排队，如果返回false，可以直接获取锁
     * h == t
     * 1) h==null , t==null 队列还没有创建
     * 2）h==t ，队列中只有一个元素，尝试获取锁
     * h != t
     * 1) h!=null  t==null 队列正在创建
     * 2）h!=null t!=null
     * <p>
     * h.next == null 代码能够走到这里，证明h肯定 != null, 队列正在初始化，不能获取锁
     * <p>
     * s.thread != Thread.currentThread() 判断是否排队到了自己
     */
    private boolean hasQueuedPredecessors() {
        Node t = tail;
        Node h = head;
        Node s;
        return h != t &&
                ((s = h.next) == null || s.thread != Thread.currentThread());
    }
 
    /**
     * 解锁方法
     * 1. 设置state=0
     * 2. unpark 唤醒下一个线程
     */
    public void unlock(int arg) {
 
        if (tryUnLock(arg)) {
 
            unparkSuccessor(head);
        }
    }
 
    /**
     * 唤醒head的下一个元素
     *
     * @param head
     */
    private void unparkSuccessor(Node head) {
        setState(0);
 
        //找到下一个要唤醒的元素
        final Node h = head;
        Node next = null;
        if (h != null) {
            next = h.next;
        }
        if (next == null || next.waitStatus != Node.WAIT) {
            for (Node t = tail; t != null && t != h; t = t.pre) {
                if (t.waitStatus != Node.CANCELLED) {
                    next = t;
                }
            }
        }
 
        //唤醒下一个
        if (next != null) {
            LockSupport.unpark(next.thread);
            compareAndSetNodeStatus(next, next.waitStatus, Node.DEFAULT);
        }
 
    }
 
    /**
     * 尝试释放锁
     *
     * @param arg
     * @return
     */
    private boolean tryUnLock(int arg) {
        //计算状态
        int c = getState() - arg;
        //判断当前释放锁的线程是否是当前占有锁的线程
        if (Thread.currentThread() != getExclusiveOwnerThread()) {
            throw new RuntimeException();
        }
        if (c == 0) {
            //释放锁
            setExclusiveOwnerThread(null);
            return true;
        }
        setState(c);
        return false;
    }
 
    /**
     * CAS操作，修改state的值
     *
     * @param exceptedValue
     * @param newValue
     * @return
     */
    private boolean compareAndSet(int exceptedValue, int newValue) {
        return unsafe.compareAndSwapInt(this, statusOffset, exceptedValue, newValue);
    }
 
    protected final int getState() {
        return state;
    }
 
    protected final void setState(int newState) {
        state = newState;
    }
 
    public Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
 
    public void setExclusiveOwnerThread(Thread exclusiveOwnerThread) {
        this.exclusiveOwnerThread = exclusiveOwnerThread;
    }
 
    static final class Node {
 
        //前一个节点
        volatile Node pre;
        //后一个节点
        volatile Node next;
        //当前线程
        volatile Thread thread;
 
        volatile int waitStatus;
 
        static final int DEFAULT = 0;
        static final int WAIT = 1;
        static final int CANCELLED = -1;
 
        public Node(Thread thread) {
            this.thread = thread;
        }
 
        public Node() {
        }
 
        public Node(Node pre, int waitStatus) {
            this.pre = pre;
            this.waitStatus = waitStatus;
        }
    }
}