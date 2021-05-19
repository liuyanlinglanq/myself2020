package com.lyl.concurrent.concurrency.c005;

public class Account {

    //账户余额
    private Integer balance = 200;

    //转账 synchronized(this)
    private void transfer(Account target, Integer amt) {
        //锁定转入
        synchronized (this) {
            //锁定转出
            synchronized (target) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                    System.out.println(this.balance + "     " + target.balance);
                }
            }
        }
    }

//    public static void main(String[] args) throws InterruptedException {
//        Account accountA = new Account();
//        Account accountB = new Account();
//        Account accountC = new Account();
//        Account accountD = new Account();
//
//        Thread thread = new Thread(() -> {
//            accountA.transfer(accountB, 100);
//
//        });
//        Thread thread1 = new Thread(() -> {
//            accountC.transfer(accountD, 30);
//        });
//
//
//        thread.start();
//        thread1.start();
//
//        thread.join();
//        thread1.join();
//
//    }

    /**
     * 死锁,执行多次,总有执行不下去的时候
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Account accountA = new Account();
        Account accountB = new Account();

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
            Thread thread = new Thread(() -> {
                accountA.transfer(accountB, 100);

            });
            Thread thread1 = new Thread(() -> {
                accountB.transfer(accountA, 100);
            });


            thread.start();
            thread1.start();

            thread.join();
            thread1.join();
        }

    }

}
