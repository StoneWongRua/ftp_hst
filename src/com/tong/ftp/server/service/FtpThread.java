package com.tong.ftp.server.service;

import com.tong.common.constant.ServerConstant;
import com.tong.ftp.server.command.FtpCommand;
import com.tong.ftp.server.command.FtpCommandFactory;
import com.tong.ftp.server.command.PASS;
import com.tong.ftp.server.command.USER;

import java.io.*;
import java.net.Socket;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class FtpThread extends Thread {
    private String username;
    private Socket controlSocket;
    private Socket dataSocket;
    private String dataHost;
    private int dataPort;
    private boolean isLogin = false;
    private boolean firstAccess = true;
    private String mode;

//    public FtpThread(Socket controlSocket) {
//        this.controlSocket = controlSocket;
//    }

    public FtpThread(Socket controlSocket, String mode) {
        this.controlSocket = controlSocket;
        this.mode = mode;
    }

    @Override
    public void run() {
        System.out.println("[ftp] client (" + controlSocket.getRemoteSocketAddress() + ")" + " connected");
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(controlSocket.getInputStream()));
            Writer writer = new OutputStreamWriter(controlSocket.getOutputStream());

            while (true) {
                if (firstAccess && ServerConstant.getThreadModeControl().equals(mode)) {
                    writer.write("220 welcome\r\n");
                    writer.flush();
                    firstAccess = false;
                } else {
                    if (!controlSocket.isClosed()) {
                        String command = bufferedReader.readLine();
                        if (command != null && command.trim().length() > 0) {
                            String[] input = command.split(" ");
                            FtpCommand ftpCommand = FtpCommandFactory.createFtpCommand(input[0]);

                            if (checkLogin(ftpCommand)) {
                                if (ftpCommand == null) {
                                    writer.write("502 command doesn't exist");
                                    writer.flush();
                                } else {

                                    if (input.length >= 2) {
                                        ftpCommand.process(input[1], writer, this);
                                    } else {
                                        ftpCommand.process("", writer, this);
                                    }
                                }
                            } else {
                                writer.write("532 please login");
                                writer.flush();
                            }
                        }
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void interrupt() {
        try {
            if (controlSocket != null) {
                System.out.println("[ftp] client (" + controlSocket.getRemoteSocketAddress() + ")" + " disconnect");
                controlSocket.close();
            }
        } catch (IOException ignore) {

        } finally {
            try {
                if (dataSocket != null) {
                    dataSocket.close();
                }
            } catch (IOException ignored) {

            } finally {
                super.interrupt();
            }
        }
    }

    private boolean checkLogin(FtpCommand command) {
        return isLogin || command instanceof USER || command instanceof PASS;
    }

    public Socket getControlSocket() {
        return controlSocket;
    }

    public void setControlSocket(Socket controlSocket) {
        this.controlSocket = controlSocket;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public boolean isFirstAccess() {
        return firstAccess;
    }

    public void setFirstAccess(boolean firstAccess) {
        this.firstAccess = firstAccess;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Socket getDataSocket() {
        return dataSocket;
    }

    public void setDataSocket(Socket dataSocket) {
        this.dataSocket = dataSocket;
    }

    public String getDataHost() {
        return dataHost;
    }

    public void setDataHost(String dataHost) {
        this.dataHost = dataHost;
    }

    public int getDataPort() {
        return dataPort;
    }

    public void setDataPort(int dataPort) {
        this.dataPort = dataPort;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
