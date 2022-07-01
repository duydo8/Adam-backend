package com.example.adambackend.config;

import net.bytebuddy.utility.RandomString;

public class Test {
    public static void main(String[] args) {
        for(int i=0;i<100;i++){
            System.out.println(RandomString.make(64));
        }
    }
}
