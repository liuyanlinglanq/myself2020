package com.lyl.study.mytest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties({"id","avator160","address"})
class User {

    //如果不想输出id，最简单的方法，就是给该属性加上注解JsonIgnore:
    @JsonIgnore
    private long id;

    private String name;

    private String avator240;

    private String avator160;

    private String address;
}

