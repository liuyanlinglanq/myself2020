package com.lyl.study.mytest;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.Data;

/**
 * MixIn Annotation的配置是静态的，不能在运行时修改。 结合JSON Filter和Mixin就可以实现动态的过滤属性了
 */
public class JsonFilterDemo {

    @Data
    private static class User {
        private long id;
        private String name;
        private String avator240;
        private String avator160;
        private String address;
    }

    @JsonFilter("userFilter")
    private static interface UserFilterMixIn {

    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.id = 1000L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";


        FilterProvider idFilterProvider = new SimpleFilterProvider().addFilter("userFilter", SimpleBeanPropertyFilter.filterOutAllExcept(new String[]{"name", "avator240"}));
        mapper.setFilterProvider(idFilterProvider);
        mapper.addMixIn(User.class, UserFilterMixIn.class);
        String userFilterJson = mapper.writeValueAsString(user);

        System.out.println(userFilterJson);
    }
}
