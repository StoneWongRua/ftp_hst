package com.tong.common.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author tong
 * @create 2018-06-13 12:39 AM
 **/
public class MyUtils {
    public static StringBuilder getStringBuilder(InputStream inputStream) throws IOException {
        byte[] bytes = new byte[1024];
        int len;
        StringBuilder sb = new StringBuilder();
        while ((len = inputStream.read(bytes)) != -1) {
            sb.append(new String(bytes, 0, len, "UTF-8"));
        }
        return sb;
    }

    public static void getCurrentClientInfo(Socket socket) {
        System.out.println("-------------------------------------------------");
        System.out.println("inet address: " + socket.getInetAddress());
        System.out.println("local address: " + socket.getLocalAddress());
        System.out.println("local port: " + socket.getLocalPort());
        System.out.println("local socket address: " + socket.getLocalSocketAddress());
        System.out.println("port: " + socket.getPort());
        System.out.println("-------------------------------------------------");
    }


}
