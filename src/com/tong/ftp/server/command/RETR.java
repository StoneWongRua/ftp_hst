package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.*;
import java.net.Socket;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class RETR extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        try {
//            writer.write("150 open ASCII mode\r\n");
//            writer.flush();
            sendResponse("150 open ASCII mode", writer);

            // 获取数据连接
//            Socket dataSocket = t.getDataThread().getControlSocket();
            Socket dataSocket = t.getDataSocket();

            // 发送文件
            File localFile = new File(data);
            if (!localFile.exists()) {
                sendResponse("220 file not exist", writer);
                return;
            }
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
//            t.getDataThread().interrupt();
            dataSocket.close();

//            writer.write("226 transfer complete\r\n");
//            writer.flush();
            sendResponse("226 transfer complete", writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
