package com.github.daniel.resumob3.utils;

import java.io.InputStream;

public class Utils {

    public InputStream getInputStream(String resourceName) {
        return getClass().getClassLoader().getResourceAsStream(resourceName);
    }
}
