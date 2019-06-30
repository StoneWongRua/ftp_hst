/*package com.zkyyo.test;

import com.zkyyo.common.constant.SocketConstant;
import com.zkyyo.common.util.MyUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Random;

*//**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **//*
public class SocketClient {
    public static void main(String[] args) throws Exception {
        String host = SocketConstant.getServerHost();
        int port = SocketConstant.getServerControlPort();
        Socket socket = new Socket(host, port);
        OutputStream outputStream = socket.getOutputStream();
        String message = "" + new Random().nextInt();
        socket.getOutputStream().write(message.getBytes("UTF-8"));
        socket.shutdownOutput();

        InputStream inputStream = socket.getInputStream();
        StringBuilder sb = MyUtils.getStringBuilder(inputStream);
        System.out.println("get message from server: " + sb);

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}*/
