package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.File;
import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 22:51
 * @Param
 * @return
 **/
public class CD extends AbstractFtpCommand{

    private File currentPath;
    @Override
    public void process(String data, Writer writer, FtpThread t) {

    }

    /**
     * 回退父目录
     */
    public void toParentPath(File filePath){
        currentPath = currentPath.getParentFile();
    }

    public File getCurrentPath() {
        return currentPath;
    }
}
