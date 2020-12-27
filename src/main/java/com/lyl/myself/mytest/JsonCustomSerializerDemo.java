package com.lyl.myself.mytest;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

/**
 * 如果需要在串行化时修改值，要怎么办呢？ 只要实现自己的JsonSerializer就可以了,下面这个例子就会输出id的md5值
 */
public class JsonCustomSerializerDemo {
    static class User {
        @JsonSerialize(using = Md5IdSerializer.class)
        private long id;
        private String name;
        private String address;

        public long getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

    }

    public static class Md5IdSerializer extends JsonSerializer {

        @Override
        public void serialize(Object value, JsonGenerator generator, SerializerProvider provider)
                throws IOException {
            generator.writeString(md5(Long.parseLong(value.toString())));
        }

        private String md5(Long value) {
            return value + "-md5-mock";
        }


    }

    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        User user = new User();
        user.id = 1234567L;
        user.name = "test name";
        user.address = "some address";

        String json = mapper.writeValueAsString(user);
        System.out.println(json);

    }
}
   
