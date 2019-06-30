package com.tong.ftp.server.service;

import com.tong.common.constant.ServerConstant;

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
public class UserServer extends Thread {
    @Override
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(ServerConstant.getServerUserSystemPort());
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("user server waiting...");


        if (server != null) {
            while (true) {
                Socket socket = null;
                try {
                    socket = server.accept();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                UserThread userThread = new UserThread(socket);
                userThread.start();
            }
        }
    }
}
