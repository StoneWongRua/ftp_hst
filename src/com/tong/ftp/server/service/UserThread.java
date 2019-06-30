package com.tong.ftp.server.service;

import com.tong.ftp.server.dao.UserData;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class UserThread extends Thread {
    private Socket socket;

    public UserThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("[user system] client (" + socket.getRemoteSocketAddress() + ")" + " connected");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Writer writer = new OutputStreamWriter(socket.getOutputStream());

            if (!socket.isClosed()) {
                String command = bufferedReader.readLine();
                if (command != null && command.trim().length() > 0) {
                    String[] input = command.split(" ");
                    if("my_register".equals(input[0])) {
                        UserData.addUser(input[1], input[2]);
                        writer.write("my_register_ok");
                        writer.write("\r\n");
                        writer.flush();
                    }
                }
            }
            interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void interrupt() {
        try {
            if (socket != null) {
                socket.close();
            }
        } catch (Exception ignore) {

        } finally {
            super.interrupt();
        }
    }
}
