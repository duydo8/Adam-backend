package com.example.adambackend.controller.admin;

import net.bytebuddy.utility.RandomString;

import java.util.Random;

public class test {
    public static void main(String[] args) {
        for (int i = 0; i <13 ; i++) {
            String code = RandomString.make(64);
            System.out.println(code);
        }
    }
}
