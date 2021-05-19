package com.lyl.study.mytest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class JsonApiViewDemo {


    @Data
    private static class User {

        private long id;

 

//        @JsonView({ApiView.Default.class})
        private String name;

 

//        @JsonView({ApiView.Ios.class})
        private String avator240;

 

        @JsonView({ApiView.Android.class})
        private String avator160;

 

        private String address;


        private List<Person> personList;

        private Human human;
    }

    /**
     * description Person
     *
     * @author liuyanling
     * @date 2020-09-04 17:01
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class Human {
        private Person person;
    }

    /**
     * description Person
     *
     * @author liuyanling
     * @date 2020-09-04 17:01
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonView({ApiView.Android.class})
    private static class Person {
        private String name;
    }






    public static void main(String[] args) throws Exception {

        ObjectMapper mapper = new ObjectMapper();

//        mapper.configure(SerializationConfig.Feature.DEFAULT_VIEW_INCLUSION, false);
//        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
 

        User user = new User();

        user.id = 10000L;

        user.name = "test name";

        user.avator240 = "240.jpg";

        user.avator160 = "160.jpg";

        user.address = "some address";
        user.personList = new ArrayList<>();
        user.personList.add(new Person("l1"));

        user.human = new Human();
        user.human.person = new Person("l2");
 

//        String apiViewJson = mapper.writerWithView(ApiView.Default.class).writeValueAsString(user);
//
//        String iosViewJson = mapper.writerWithView(ApiView.Ios.class).writeValueAsString(user);

        String androidViewJson = mapper.writerWithView(ApiView.Ios.class).writeValueAsString(user);


//        System.out.println(apiViewJson);
//
//        System.out.println(iosViewJson);

        System.out.println(androidViewJson);

 

    }

 

}

