package com.tong.ftp.server.command;

import com.tong.ftp.server.dao.UserData;
import com.tong.ftp.server.service.FtpThread;

import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class USER extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        String response = "";
        if (UserData.ifUserExist(data)) {
            t.setUsername(data);
            response = "331 server needs password";
        } else {
            response = "501 user is not available";
        }

        sendResponse(response, writer);
    }
}
