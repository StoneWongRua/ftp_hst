package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.File;
import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/30 21:02
 * @Param
 * @return
 **/
public class mkdir  extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
/*        File dirFile = new File(data);
        if(!dirFile.exists()){
            if(!dirFile.mkdir()){
                try {
                    sendResponse("220 file not exist", writer);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
                sendResponse("dir exist", writer);
        }
        }*/

    }
}
