package utils;

import clover.com.google.gson.Gson;
import java.util.LinkedHashMap;

public class StringUtils {

    /**
     * Prints provided data in standard output.
     * @param s
     */
    public static void print(String s) {
        System.out.println(s);
    }

    /**
     * <p>Takes a {@link java.util.Map<String,String>} and returns a JSON as String</p>
     * @param input
     * @return JSON representation of provided map as String.
     */
    public static String convertToJson(LinkedHashMap<String, String> input) {
        return new Gson().toJson(input);
    }
}
