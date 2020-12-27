package com.lyl.myself.mytest;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

/**
 * MixIn Annotation应该能满足几乎所有需要对属性进行自定义的情况了
 */
public class JsonMixInDemo {

    @Data
    static class User {

        private long id;

        private String name;

        private String avator240;

        private String avator160;

        private String address;

 


    }

 

    abstract class MixIn {

        @JsonIgnore
        abstract int getAddress();

 
        @JsonIgnore   long id;


        @JsonProperty("custom_name") abstract String getName();


        @JsonProperty("avator") String avator240;

    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.id = 1234567L;
        user.name = "test name";
        user.avator240 = "240.jpg";
        user.avator160 = "160.jpg";
        user.address = "some address";

        mapper.addMixIn(User.class, MixIn.class);
        String json = mapper.writeValueAsString(user);
        System.out.println(json);

    }
}
