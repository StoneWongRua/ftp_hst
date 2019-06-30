package com.tong.ftp.client.service;

import com.tong.common.constant.Directory;
import com.tong.common.constant.ServerConstant;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.StringTokenizer;


/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 16:44
 * @Param
 * @return
 **/
public class FtpClient {
    private Socket socket; // 控制连接
    private BufferedReader controlReader; // 字符读取流
    private BufferedWriter controlWriter; // 字符写入流
    private String username; // 用户名
    private String password; // 密码
    private String dataHost; // 服务端数据连接的IP
    private int dataPort; // 服务端数据连接的端口
    private static Directory currentDir = new Directory();

    public FtpClient() {

    }

    public void connectServer(String serverHost, String username, String password) throws IOException {
        this.socket = new Socket(serverHost, ServerConstant.getServerControlPort());
        this.username = username;
        this.password = password;

        controlReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        controlWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        loginFtp();
    }



    private void loginFtp() throws IOException {
        if (socket == null) {
            return;
        }
        String response;
        do {
            response = controlReader.readLine();
            System.out.println(response);
        } while (!response.startsWith("220 "));

        // 验证用户名
        sendCommand("USER " + username);
        response = controlReader.readLine();
        System.out.println(response);

        // 验证密码
        if (response.startsWith("331 ")) {
//            controlPrintWriter.println("PASS " + password);
            sendCommand("PASS " + password);
            response = controlReader.readLine();
            System.out.println(response);

            if (response.startsWith("230 ")) {
                return;
            }

        }
        throw new IOException();
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

    public void showFilesInPath(String ftpPath) throws Exception {
        // 进入被动模式
        switchToPassMode();

        sendCommand("LIST " + ftpPath);

        Socket dataSocket = getDataSocket();

        String response = controlReader.readLine();
        if (!response.startsWith("210 ")) { // 文件夹存在
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataSocket.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            reader.close();

            response = controlReader.readLine();
            System.out.println(response);
        }

        dataSocket.close();
    }

    public void uploadFile(String localPath, String ftpPath) throws Exception {
        // 进入被动模式
        switchToPassMode();

        // 建立数据端口的连接
        Socket dataSocket = getDataSocket();
//        controlPrintWriter.println("STOR" + ftpPath);
        sendCommand("STOR " + ftpPath);
        String response = controlReader.readLine();
        System.out.println(response);

        // 上传文件
        File localFile = new File(localPath);
        OutputStream outputStream = dataSocket.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(localFile);
        int offset;
        byte[] bytes = new byte[1024];
        while ((offset = fileInputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, offset);
        }
        outputStream.flush();
        outputStream.close();
        fileInputStream.close();

        response = controlReader.readLine();
        System.out.println(response);
    }

    public void downloadFile(String localPath, String ftpPath) throws Exception {
        // 进入被动模式
        switchToPassMode();

        // 建立数据端口的连接
        Socket dataSocket = getDataSocket();
//        controlPrintWriter.println("RETR" + ftpPath);
        sendCommand("RETR " + ftpPath);
        String response = controlReader.readLine();
        System.out.println(response);

        // 下载文件
        File localFile = new File(localPath);
        InputStream inputStream = dataSocket.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(localFile);
        int offset;
        byte[] bytes = new byte[1024];
        while ((offset = inputStream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, offset);
        }

        fileOutputStream.close();
        inputStream.close();
//        dataSocket.close();

        response = controlReader.readLine();
        System.out.println(response);
    }

    public void deleteServerFile(String ftpPath) throws Exception {
        sendCommand("DELE " + ftpPath);
        String response = controlReader.readLine();
        System.out.println(response);

        response = controlReader.readLine();
        System.out.println(response);
    }

    public void disconnectServer() {
        if (socket != null && socket.isConnected()) {
            try {
                sendCommand("QUIT");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Socket getDataSocket() throws IOException {
        System.out.println("client open data socket: " + dataHost + ":" + dataPort);
        return new Socket(dataHost, dataPort);
    }

    private void switchToPassMode() throws Exception {
        sendCommand("PASV");
        String response = controlReader.readLine();
        System.out.println(response);
        if (!response.startsWith("2271 ")) {
            throw new IOException("server not allow passive mode");
        }

        // 解析服务器被动模式下传回的数据线程 ip 和 port
        int opening = response.indexOf('(');
        int closing = response.indexOf(')', opening + 1);
        if (closing > 0) {
            String dataLink = response.substring(opening + 1, closing);
            StringTokenizer tokenizer = new StringTokenizer(dataLink, ",");
            try {
                dataHost = tokenizer.nextToken() + "." + tokenizer.nextToken() + "."
                        + tokenizer.nextToken() + "." + tokenizer.nextToken();
                dataPort = Integer.parseInt(tokenizer.nextToken()) * 256
                        + Integer.parseInt(tokenizer.nextToken());
            } catch (Exception e) {
                throw new IOException("FTPClient received bad data link information: " + response);
            }
        }
    }

    public void mkdir(String dirname) {
        if (currentDir.getDirectories().containsKey(dirname)) {
            System.out.println("同名目录已存在");
            return;
        }
        Directory dir = new Directory();
        dir.setDirname(dirname);
        dir.setParent(currentDir);
        currentDir.getDirectories().put(dirname, dir);
        System.out.println("目录创建成功");
    }

    public void cd(String dirname) {
        if ("..".equals(dirname)) {
            Directory parent = currentDir.getParent();
            if (parent != null) {
                currentDir = parent;
            }
        } else {
            if (!currentDir.getDirectories().containsKey(dirname)) {
                return;
            }
            currentDir = currentDir.getDirectories().get(dirname);
        }
    }

    public void ls() {
        Directory directory = currentDir;
        Map<String, Directory> directories = directory.getDirectories();
        Map<String, File> files = directory.getFiles();
        System.out.println("此目录包含" + directories.size() + "个子目录," + files.size() + "个文件");
        if (directories.size() != 0) {
            System.out.println("目录：");
            directories.forEach((dirname, directory1) -> System.out.println(dirname + "/"));
        }
        if (files.size() != 0) {
            System.out.println("\n文件：");
            files.forEach((filename, file) -> System.out.println(filename));
        }
    }

}
