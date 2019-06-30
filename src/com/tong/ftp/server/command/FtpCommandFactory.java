package com.tong.ftp.server.command;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:30
 * @Param
 * @return
 **/
public class FtpCommandFactory {
    public static FtpCommand createFtpCommand(String type) {
        switch (type.toUpperCase()) {
            case "USER":
                return new USER();
            case "PASS":
                return new PASS();
            case "QUIT":
                return new QUIT();
            case "PASV":
                return new PASV();
            case "RETR":
                return new RETR();
            case "STOR":
                return new STOR();
            case "LIST":
                return new LIST();
            case "DELE":
                return new DELE();
            case "CD":
                return new CD();
            case "mkdir":
                return new mkdir();
            default:
                return null;
        }
    }
}
