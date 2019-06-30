/*package com.zkyyo.test;

import com.zkyyo.common.constant.SocketConstant;
import com.zkyyo.common.util.MyUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

*//**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **//*
public class SocketServer {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(SocketConstant.getServerControlPort());
        System.out.println("waiting...");

        ExecutorService threadPool = Executors.newFixedThreadPool(2);

        while (true) {
            Socket socket = server.accept();

            Runnable runnable = () -> {
                try {
                    // 从 client 接受信息
                    InputStream inputStream = socket.getInputStream();
                    StringBuilder sb = MyUtils.getStringBuilder(inputStream);
                    System.out.println("get message from client: " + sb);

                    // 向 client 发送信息
                    OutputStream outputStream = socket.getOutputStream();
                    String message = "666";
                    outputStream.write(message.getBytes("UTF-8"));

                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            };
            threadPool.submit(runnable);
        }

*//*        InputStream inputStream = socket.getInputStream();
        StringBuilder sb = MyUtils.getStringBuilder(inputStream);
        System.out.println("get message from client: " + sb);

        OutputStream outputStream = socket.getOutputStream();
        String message = "666";
        outputStream.write(message.getBytes("UTF-8"));

        outputStream.close();
        inputStream.close();
        socket.close();
        server.close();*//*
    }
}*/
