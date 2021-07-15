package com.lyl.study.ms.xiaomi;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * description Client
 *
 * @author liuyanling
 * @date 2021-06-18 10:50
 */
public class Client {

    public static void main(String[] args) {
        List<Animal> animalList = new ArrayList<>();
        List<Cat> catList = new ArrayList<>();

        petsGeneric(animalList);
        petsGeneric(catList);


        Animal[] animalArray = new Animal[]{};
        Cat[] catArray = new Cat[]{};
        petsArray(animalArray);
        petsArray(catArray);


//        pets(animalList);
//        pets(catList);


    }


    private static void pets(List<Animal> pets) {
        pets.forEach(System.out::println);
    }

    private static void petsArray(Animal[] pets) {
        Arrays.stream(pets).forEach(System.out::println);
    }


    private static <T extends Animal> void petsGeneric(List<T> pets) {
        pets.forEach(System.out::print);
    }
}
