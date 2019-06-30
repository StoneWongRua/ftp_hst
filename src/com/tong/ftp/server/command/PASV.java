package com.tong.ftp.server.command;

import com.tong.common.constant.ServerConstant;
import com.tong.ftp.server.service.FtpThread;

import javax.sql.rowset.serial.SerialClob;
import java.io.IOException;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class PASV extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        int port = -1;
        ServerSocket serverSocket = null;
        String response = "";
        while (serverSocket == null) {
            // 服务器生成随机数据线程端口
            port = ThreadLocalRandom.current().nextInt(49152, 65535);
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 将服务器准备开启监听的 ip 和 端口传给客户端
        if (port != -1) {
            int p1 = port / 256;
            int p2 = port - p1 * 256;
            response = "2271 Entering Passive Mode (127,0,0,1," + p1 + "," + p2 + ")";
//            response = "2271 Entering Passive Mode (172,20,10,2," + p1 + "," + p2 + ")";
        }
        sendResponse(response, writer);

        // 服务器开启监听数据端口, 准备开启数据传输socket
        try {
            Socket dataSocket = serverSocket.accept();
            t.setDataSocket(dataSocket);
//            FtpThread thread = new FtpThread(dataSocket, ServerConstant.getThreadModeData());
//            t.setDataThread(thread);
//            thread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
