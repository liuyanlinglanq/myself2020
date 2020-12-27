package com.lyl.myself.concurrency.c004;

public class Account {

    //不同的锁保护不同的资源,也可以用一把互斥锁来保护多个资源(this锁,加同步关键字 synchronized),但是操作都是串行,性能太差;
    //细粒度锁:用不同的锁对受 保护资源进行精细化管理，能够提升性能。
    //锁:保护账户余额
    private final Object balLock = new Object();
    //账户余额
    private Integer balance = 200;
    //锁:保护账户密码
    private final Object pwLock = new Object();
    //账户密码
    private String password = "pwd";

    //取款
    private void withdraw(Integer amt) {
        synchronized (new Object()) {
            if (this.balance > amt) {
                this.balance -= amt;
            }
        }
    }

    //查看余额
    private Integer getBalance() {
        synchronized (balLock) {
            return this.balance;
        }
    }

    //更改密码
    private void modifyPassword(String newPassword) {
        synchronized (new Object()) {
            this.password = newPassword;
        }
    }

    //查看密码
    private String getPassword() {
        synchronized (pwLock) {
            return this.password;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Account account = new Account();

        Thread thread = new Thread(() -> {
            account.withdraw(100);
        });

        Thread thread1 = new Thread(() -> {
            account.getBalance();
        });

        Thread thread2 = new Thread(() -> {
            account.modifyPassword("password");
        });

        Thread thread3 = new Thread(() -> {
            account.getPassword();

        });

        thread.start();
        thread1.start();
        thread2.start();
        thread3.start();

        thread.join();
        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println(account.getBalance());
        System.out.println(account.getPassword());
    }
}
