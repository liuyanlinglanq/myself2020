package com.lyl.myself.mytest;

import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;


public class JsonViewDemo {

    @Data
    private static class User {

        private long id;

        @JsonView({FilterView.Output.class})
        private String name;

        @JsonView({FilterView.Output.class})
        private String avator240;

        private String avator160;

        private String address;


    }

    private static class FilterView {

        static class Output {}

    }

    public static void main(String[] args) throws Exception {

        User user = new User();

        user.id = 1000L;

        user.name = "test 1name";

        user.avator240 = "2410.jpg";

        user.avator160 = "160.jpg";

        user.address = "some address";

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
        System.out.println(mapper.writerWithView(FilterView.Output.class).writeValueAsString(user));

    }

}
