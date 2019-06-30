package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.IOException;
import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:37
 * @Param
 * @return
 **/
public abstract class AbstractFtpCommand implements FtpCommand {
    @Override
    public abstract void process(String data, Writer writer, FtpThread t);

    public void sendResponse(String response, Writer writer) {
        try {
            writer.write(response);
            writer.write("\r\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
