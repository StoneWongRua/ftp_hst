package com.tong.ftp.server.service;

import com.tong.ftp.server.dao.UserData;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class Server {

    public static void main(String[] args) {
        UserData.init();
        // 启动 ftp服务器
        FtpServer ftpServer = new FtpServer();
        ftpServer.start();
        // 启动 user服务器
        UserServer userServer = new UserServer();
        userServer.start();
    }
}
