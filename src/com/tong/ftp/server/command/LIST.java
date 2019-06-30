package com.tong.ftp.server.command;

import com.tong.ftp.server.service.FtpThread;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class LIST extends AbstractFtpCommand {
    private List<String> allFiles = new ArrayList<>();

    @Override
    public void process(String data, Writer writer, FtpThread t) {
        File dir = new File(data);
        if (!dir.exists()) {
            sendResponse("210 dir doesn't exist", writer);
        } else {
            sendResponse("125 show files", writer);
            try {
                find(data, 1);

                Socket dataSocket = t.getDataSocket();
                Writer dataWriter = new OutputStreamWriter(dataSocket.getOutputStream());
                for (String file : allFiles) {
                    dataWriter.write(file);
                    dataWriter.flush();
                }

                dataWriter.close();
                dataSocket.close();
                sendResponse("226 transfer complete", writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void find(String pathName, int depth) throws IOException {
        //获取pathName的File对象
        File dirFile = new File(pathName);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!dirFile.exists()) {
            return;
        }
        //判断如果不是一个目录，就判断是不是一个文件，时文件则输出文件路径
        if (!dirFile.isDirectory()) {
            if (dirFile.isFile()) {
//                System.out.println(dirFile.getCanonicalFile());
                allFiles.add(dirFile.getCanonicalFile().toString());
                allFiles.add("\r\n");
            }
            return;
        }

        for (int j = 0; j < depth; j++) {
//            System.out.print("  ");
            allFiles.add("  ");
        }
//        System.out.print("|--");
//        System.out.println(dirFile.getName());
        allFiles.add("|--");
        allFiles.add(dirFile.getName());
        allFiles.add("\r\n");
        //获取此目录下的所有文件名与目录名
        String[] fileList = dirFile.list();
        if (fileList != null) {
            int currentDepth = depth + 1;
            for (String string : fileList) {
                //遍历文件目录
                //File("documentName","fileName")是File的另一个构造器
                File file = new File(dirFile.getPath(), string);
                String name = file.getName();
                //如果是一个目录，搜索深度depth++，输出目录名后，进行递归
                if (file.isDirectory()) {
                    //递归
                    find(file.getCanonicalPath(), currentDepth);
                } else {
                    //如果是文件，则直接输出文件名
                    for (int j = 0; j < currentDepth; j++) {
//                    System.out.print("   ");
                        allFiles.add("  ");
                    }
//                System.out.print("|--");
//                System.out.println(name);
                    allFiles.add("|--");
                    allFiles.add(name);
                    allFiles.add("\r\n");
                }
            }
        }
    }
}
