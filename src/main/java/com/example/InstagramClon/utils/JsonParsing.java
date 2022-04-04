package com.example.InstagramClon.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public class JsonParsing {
    public static String toString(MultipartFile multipartFile) throws IOException {
        return new String(multipartFile.getBytes());
    }
}
