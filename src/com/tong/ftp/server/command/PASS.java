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
public class PASS extends AbstractFtpCommand {

    @Override
    public void process(String data, Writer writer, FtpThread t) {
        String response = "";
        if (UserData.verifyUser(t.getUsername(), data)) {
            response = "230 login success";
            t.setLogin(true);
        } else {
            response = "530 login fail";
        }

        sendResponse(response, writer);
    }
}
