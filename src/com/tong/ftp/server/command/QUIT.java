package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.IOException;
import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class QUIT extends AbstractFtpCommand{
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        try {
//            writer.write("221 see you\r\n");
//            writer.flush();
            sendResponse("221 see you", writer);
            writer.close();
            t.interrupt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
