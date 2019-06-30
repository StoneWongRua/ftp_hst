package com.tong.ftp.server.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class UserData {
    private static Map<String, String> users = new HashMap<>();

    public static void init() {
        users.put("test", "123123");
        users.put("user", "123123");
    }

    public static void addUser(String username, String password) {
        if (username == null || password == null) {
            return;
        }
        users.put(username, password);
    }

    public static boolean verifyUser(String username, String password) {
        return username != null && password != null && password.equals(users.get(username));
    }

    public static boolean ifUserExist(String username) {
        return users.containsKey(username);
    }

    public static void print() {
        System.out.println(users);
    }
}
