package com.tong.ftp.client.service;

import com.tong.common.constant.ServerConstant;

import java.io.*;
import java.net.Socket;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 16:44
 * @Param
 * @return
 **/
public class UserClient {

    private Socket socket;
    private BufferedReader controlReader;
    private BufferedWriter controlWriter;

    public void connectServer(String serverHost) {
        try {
            this.socket = new Socket(serverHost, ServerConstant.getServerUserSystemPort());

            controlReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            controlWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void register(String username, String password) throws IOException {
        if (socket == null) {
            return;
        }
        sendCommand("my_register " + username + " " + password);
        String response = controlReader.readLine();
        System.out.println(response);
    }

    public void close() throws IOException {
        socket.close();
    }

    private void sendCommand(String command) throws IOException {
        if (command == null || command.trim().length() == 0) {
            return;
        }
        if (socket == null) {
            return;
        }
        controlWriter.write(command + "\r\n");
        controlWriter.flush();
    }

    public void changePassword(String password) throws IOException {
                if (socket == null) {
            return;
        }
        sendCommand("my_change_password " + password);
//        String response = controlReader.readLine();
    }

}
