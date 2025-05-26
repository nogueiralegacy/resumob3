package com.github.daniel.resumob3.utils;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {

    public InputStream getInputStream(String resourceName) {
        return getClass().getClassLoader().getResourceAsStream(resourceName);
    }

    public static Date convertStringToDate(String date) throws ParseException {
        return new SimpleDateFormat("dd/MM/yyyy").parse(date);
    }
}
