package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class PORT extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        String response = "200 the port an ip have been get";
        String[] iAp = data.split(",");
        String ip = iAp[0];
        String port = Integer.toString(Integer.parseInt(iAp[1]));
        System.out.println("ip is " + ip);
        System.out.println("port is " + port);
        t.setDataHost(ip);
        t.setDataPort(Integer.parseInt(port));
        sendResponse(response, writer);
//            writer.write(response);
//            writer.write("\r\n");
//            writer.flush();
    }
}
