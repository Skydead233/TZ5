package com.yandex.app.service;

public class IdGenerate {
    private static long currentId = 0;

    public static long getNextId(){
        return currentId++;
    }
}
