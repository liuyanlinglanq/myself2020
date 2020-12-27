package com.lyl.myself.concurrency.c005;

/**
 * 破坏循环等待-->你占了我的球拍,你占了我的羽毛球-->哎哎,按规矩来,先小后大,先拿羽毛球,再拿球拍
 * 资源排个序
 */
public class AccountCirculation {

    private Integer index;
    private Integer balance = 200;

    public AccountCirculation() {
    }

    public AccountCirculation(Integer index) {
        this.index = index;
    }

    /**
     * 从小到大加锁
     */
    public void transfor(AccountCirculation target, Integer amt) {
        AccountCirculation left = this;
        AccountCirculation right = target;

        //left要是index小的那个
        if (left.index > right.index) {
            left = target;
            right = this;
        }

        synchronized (left) {
            synchronized (right) {
                if (this.balance > amt) {
                    this.balance -= amt;
                    target.balance += amt;
                    System.out.println(this.balance + "   " + target.balance);
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AccountCirculation from = new AccountCirculation(1);
        AccountCirculation to = new AccountCirculation(2);

        for (int i = 0; i < 1000; i++) {
            Thread t1 = new Thread(() -> {
                from.transfor(to, 100);
            });

            Thread t2 = new Thread(() -> {
                to.transfor(from, 100);
            });

            t1.start();
            t2.start();

            t1.join();
            t2.join();
        }
    }
}
