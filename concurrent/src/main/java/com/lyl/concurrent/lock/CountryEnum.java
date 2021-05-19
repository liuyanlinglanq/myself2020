package com.lyl.concurrent.lock;

import lombok.Getter;

/**
 * 枚举
 * 就是数据版的mysql数据库
 */
public enum CountryEnum {

    ONE(1, "齐"),
    TWO(2, "燕"),
    THREE(3, "楚"),
    FOUR(4, "魏"),
    FIVE(5, "赵"),
    SIX(6, "韩");

    //属性
    @Getter
    private Integer retCode;
    @Getter
    private String retMessage;

    CountryEnum(Integer retCode, String retMessage) {
        this.retCode = retCode;
        this.retMessage = retMessage;
    }

    //根据下标
    public static CountryEnum forEacheGetEnum(int index) {

        CountryEnum[] myArray = CountryEnum.values();

        for (CountryEnum element : myArray) {
            if (index == element.getRetCode()) {
                return element;
            }
        }

        return null;

    }

}
