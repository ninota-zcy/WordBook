package com.example.wordbook;

import java.util.UUID;

public class GUID {
    public static String getUID(){
        UUID uuid = UUID.randomUUID();

        String a = uuid.toString();

         a = a.toUpperCase();

         a = a.replaceAll("-","");

         return a;
    }
}
