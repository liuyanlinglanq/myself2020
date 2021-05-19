package com.lyl.study.mytest;


/**
 * description JacksonUtilsTest
 *
 * @author liuyanling
 * @date 2020-09-03 10:40
 */

public class JacksonUtilsTest {

    public static void main(String[] args) {
        User user = new User();
        user.setId(0L);
        user.setName("1");
        user.setAvator240("2");
        user.setAvator160("3");
        user.setAddress("4");

        System.out.println(user);
        System.out.println(JacksonUtils.toJson(user));
    }
}
