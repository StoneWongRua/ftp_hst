package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:30
 * @Param
 * @return
 **/
public interface FtpCommand {
    void process(String data, Writer writer, FtpThread t);
}
