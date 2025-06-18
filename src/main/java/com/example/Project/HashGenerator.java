package com.example.Project;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class HashGenerator {
    public static void main(String[] args) {
        String raw = "XLR8*xlr8&";
        String hash = new BCryptPasswordEncoder().encode(raw);
        System.out.println(hash);
    }
}
