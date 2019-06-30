package com.tong.ftp.client.view;

import com.tong.common.constant.ServerConstant;
import com.tong.ftp.client.service.FtpClient;
import com.tong.ftp.client.service.UserClient;

import java.io.IOException;
import java.util.Scanner;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 16:43
 * @Param
 * @return
 **/
public class Main {
    private FtpClient ftpClient = new FtpClient();
    private String interval = "--------------------------------------------";

    public static void main(String[] args) throws Exception {
        System.out.println("-------------------------------------------");
        System.out.println("----------- 欢迎连接黄斯彤的ftp服务器 -----------");
        new Main().beforeLoginView();
    }

    private void operation() throws Exception {
        Scanner in = new Scanner(System.in);
        String root = System.getProperty("user.dir");
        System.out.print(root + ">");
        int choice = in.nextInt();
        switch (choice) {
            case -1:
                System.out.print("username: ");
                String username2 = in.next();
                System.out.print("password: ");
                String password2 = in.next();
                UserClient userClient = new UserClient();
                userClient.connectServer(ServerConstant.getServerHost());
                userClient.register(username2, password2);
                userClient.close();
                beforeLoginView();
            case 0:
                System.out.println("see you");
                ftpClient.disconnectServer();
                System.exit(-1);
                break;
            case 1:
                System.out.print("username: ");
                String username = in.next();
                System.out.print("password: ");
                String password = in.next();
                try {
                    ftpClient.connectServer(ServerConstant.getServerHost(), username, password);
                } catch (IOException ignore) {
                    System.out.println("error username or password");
                    beforeLoginView();
                    return;
                }
                mainView();
                break;
            case 2:
                System.out.print("local path: ");
                String localPath = in.next();
                System.out.print("ftp path: ");
                String ftpPath = in.next();
                ftpClient.uploadFile(localPath, ftpPath);
                mainView();
                break;
            case 3:
                System.out.print("local path: ");
                String localPath2 = in.next();
                System.out.print("ftp path: ");
                String ftpPath2 = in.next();
                ftpClient.downloadFile(localPath2, ftpPath2);
                mainView();
                break;
            case 4:
                System.out.print("ftp path: ");
                String ftpPath4 = in.next();
                ftpClient.deleteServerFile(ftpPath4);
                mainView();
                break;
            case 5:
                System.out.print("ftp path: ");
                String ftpPath3 = in.next();
                ftpClient.showFilesInPath(ftpPath3);
                mainView();
                break;
            case 6:
                ftpClient.showFilesInPath(System.getProperty("user.dir"));
                mainView();
                break;
            case 7:
                System.out.print("new password: ");
                String psw = in.next();
                UserClient userClient2 = new UserClient();
                userClient2.connectServer("127.0.0.1");
                userClient2.changePassword(psw);
                userClient2.close();
                mainView();
                break;
            case 8:
                System.out.println("输入相应的选项并根据提示输入即可");
                System.out.println("" +
                        "  ┏┓　　┏┓\n" +
                        " ┏┛┻━━━┛┻┓\n" +
                        " ┃       ┃\n" +
                        " ┃   ━   ┃\n" +
                        "████━████┃\n" +
                        " ┃       ┃\n" +
                        " ┃   ┻   ┃\n" +
                        " ┃       ┃\n" +
                        " ┗━┓   ┏━┛\n" +
                        "   ┃   ┃\n" +
                        "   ┃   ┃\n" +
                        "   ┃   ┗━━━┓\n" +
                        "   ┃made by┣┓" +
                        "   ┃        ┗━━┓\n" +
                        "   ┃stonewong━━┛\n" +
                        "   ┗┓┓┏━┳┓┏┛\n" +
                        "    ┃┫┫ ┃┫┫\n" +
                        "    ┗┻┛ ┗┻┛");
                mainView();
                break;
            case 9:
                //fileHandler.toParentPath(fileHandler.getCurrentPath());
                System.out.println(System.getProperty("user.dir"));
                mainView();
                break;
            case  10:
                System.out.print("new dir: ");
                String dirPath = in.next();
                ftpClient.mkdir(dirPath);
                mainView();
            case 11:
                System.out.println("test cd");
                String cdPath = in.next();
                ftpClient.cd(cdPath);
                mainView();
            default:
                break;
        }
    }

    private void beforeLoginView() throws Exception {
        System.out.println(interval);
        System.out.println(" 1) login");
        System.out.println("-1) register");
        System.out.println(" 0) quit");
        operation();
    }

    private void mainView() throws Exception {
        System.out.println(interval);

        System.out.println("2) upload file");
        System.out.println("3) download file");
        System.out.println("4) delete file");
        System.out.println("5) show files in specified path");
        System.out.println("6) show all files");
        System.out.println("7) change password");
        System.out.println("8) help");
        System.out.println("9) pwd");
        System.out.println("0) quit");
        operation();
    }
}
