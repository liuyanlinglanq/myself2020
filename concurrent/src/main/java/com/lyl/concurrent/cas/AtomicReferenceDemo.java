package com.lyl.concurrent.cas;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicReference;

/**
 * description AtomicReferenceDemo
 *
 * @author liuyanling
 * @date 2020-11-25 23:14
 */
public class AtomicReferenceDemo {

    public static void main(String[] args) {
        User z3 = new User("z3", 18);
        User li4 = new User("li4", 25);

        AtomicReference<User> atomicReference = new AtomicReference<>();
        atomicReference.set(z3);

        //结果:
        //true 	 current user:User(userName=li4, age=25)
        //false 	 current user:User(userName=li4, age=25)
        System.out.println(atomicReference.compareAndSet(z3, li4) + " \t current user:" + atomicReference.get());
        System.out.println(atomicReference.compareAndSet(z3, li4) + " \t current user:" + atomicReference.get());

    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class User {
    private String userName;

    private Integer age;
}