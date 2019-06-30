package com.tong.ftp.server.service;

import com.tong.common.constant.ServerConstant;
import com.tong.ftp.server.dao.UserData;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class FtpServer extends Thread {

    @Override
    public void run(){
        ServerSocket server = null;
        try {
            server = new ServerSocket(ServerConstant.getServerControlPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("ftp server waiting...");


        if (server != null) {
            while (true) {
                Socket socket = null;
                try {
                    socket = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                FtpThread thread = new FtpThread(socket, ServerConstant.getThreadModeControl());
                thread.start();
            }
        }
    }

/*        ServerSocket server = new ServerSocket(ServerConstant.getServerControlPort());
        System.out.println("waiting...");

        ExecutorService threadPool = Executors.newFixedThreadPool(100);

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
        }*/
}
