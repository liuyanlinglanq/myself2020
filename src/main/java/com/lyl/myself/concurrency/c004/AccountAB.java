package com.lyl.myself.concurrency.c004;

public class AccountAB {

    //账户余额
    private Integer balance = 200;

    //转账 synchronized(this)
    private synchronized void transfer(AccountAB target, Integer amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
            System.out.println("101:" + this.balance); //101+100
            System.out.println("102:" + target.balance);//102+300
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountAB accountA = new AccountAB();
        AccountAB accountB = new AccountAB();
        AccountAB accountC = new AccountAB();

        //101:100
        //51:50
        //102:350
        //52:350
        //50
        //350
        Thread thread = new Thread(() -> {
            accountA.transfer(accountB, 100);

        });
        Thread thread1 = new Thread(() -> {
            accountA.transfer(accountC, 30);
//            System.out.println("51:" + accountB.balance);//51+50
//            System.out.println("52:" + accountC.balance);//52+350
        });


        thread.start();
        thread1.start();

        thread.join();
        thread1.join();

//        System.out.println(accountA.balance);//50
//        System.out.println(accountB.balance);//350
//        System.out.println(accountC.balance);

    }
}
