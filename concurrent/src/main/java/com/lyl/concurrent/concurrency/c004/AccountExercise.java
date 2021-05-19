package com.lyl.concurrent.concurrency.c004;

public class AccountExercise {

    //不同的锁保护不同的资源,也可以用一把互斥锁来保护多个资源(this锁,加同步关键字 synchronized),但是操作都是串行,性能太差;
    //细粒度锁:用不同的锁对受 保护资源进行精细化管理，能够提升性能。
    //锁:保护账户余额
    private final Object balLock = new Object();
    //账户余额
    //账户余额
    private Integer balance = 1000;
    //锁:保护账户密码
    private final Object pwLock = new Object();
    //账户密码
    private String password = "pwd";

    //取款
    private void withdraw(Integer amt) {
        synchronized (this.balance) {
            if (this.balance > amt) {
                this.balance -= amt;
            }
        }
    }

    //查看余额
    private Integer getBalance() {
        synchronized (this.balance) {
            return this.balance;
        }
    }

    //更改密码
    private void modifyPassword(String newPassword) {
        synchronized (this.pwLock) {
            this.password = newPassword;
        }
    }

    //查看密码
    private String getPassword() {
        synchronized (this.pwLock) {
            return this.password;
        }
    }

    public static void main(String[] args) throws InterruptedException {


        for (int i = 0; i < 1000; i++) {
            AccountExercise account = new AccountExercise();
            Thread thread = new Thread(() -> {
                account.withdraw(100);
                account.getBalance();
                account.modifyPassword("password");
                account.getPassword();
            });

            Thread thread1 = new Thread(() -> {
                account.withdraw(100);
                account.getBalance();
                account.modifyPassword("password");
                account.getPassword();

            });


            thread.start();
            thread1.start();


            thread.join();
            thread1.join();

            System.out.println(account.getBalance());
            System.out.println(account.getPassword());
        }
    }
}
