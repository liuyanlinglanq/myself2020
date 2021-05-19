package com.lyl.concurrent.concurrency.c004;

/**
 * 锁同一个对象lock,构造时传入
 */
public class AccountAll {

    //账户余额
    private Integer balance = 200;

    //锁
    private Object lock;

    private AccountAll(Object lock) {
        this.lock = lock;
    }

    //转账 synchronized(lock)
    private void transfer(AccountAll target, Integer amt) {
        synchronized (lock) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
                System.out.println("101:" + this.balance); //101+100
                System.out.println("102:" + target.balance);//102+300
            }
        }
    }

    //转账 synchronized(class)
    private void transferClass(AccountAll target, Integer amt) {
        synchronized (AccountAll.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
                System.out.println("101:" + this.balance); //101+100
                System.out.println("102:" + target.balance);//102+300
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        //锁不同的对象
        Object lock1 = new Object();
        AccountAll accountA = new AccountAll(lock);
        AccountAll accountB = new AccountAll(lock);
        AccountAll accountC = new AccountAll(lock);


        Thread thread = new Thread(() -> {
            accountA.transfer(accountB, 100);

        });
        Thread thread1 = new Thread(() -> {
            accountB.transfer(accountC, 30);
        });


//        Thread thread = new Thread(() -> {
//            accountA.transferClass(accountB, 100);
//
//        });
//        Thread thread1 = new Thread(() -> {
//            accountB.transferClass(accountC, 30);
//        });


        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

//        System.out.println(accountA.balance);//50
//        System.out.println(accountB.balance);//350
//        System.out.println(accountC.balance);

    }
}
