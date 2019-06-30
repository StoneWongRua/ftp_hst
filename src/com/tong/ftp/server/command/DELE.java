package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.File;
import java.io.Writer;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class DELE extends AbstractFtpCommand {
    @Override
    public void process(String data, Writer writer, FtpThread t) {
        sendResponse("150 open ASCII mode", writer);

        // 删除文件
        File deleteFile = new File(data);
        if (!deleteFile.exists()) {
            sendResponse("220 file not exist", writer);
            return;
        }

        boolean delete = deleteDirectory(deleteFile);
        if (delete) {
            sendResponse("226 transfer complete", writer);
        } else {
            sendResponse("553 delete failure", writer);
        }
    }

    private boolean deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        return directoryToBeDeleted.delete();
    }
}
