package com.tong.common.constant;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/30 21:22
 * @Param
 * @return
 **/
public class Directory {

    /**
     * 文件名
     */
    private String dirname;

    /**
     * 上层文件夹
     */
    private Directory parent;

    /**
     * 子文件夹
     */
    private Map<String, Directory> directories = new HashMap<>();

    /**
     * 文件列表
     */
    private Map<String, File> files = new HashMap<>();

    public String getDirname() {
        return dirname;
    }

    public void setDirname(String dirname) {
        this.dirname = dirname;
    }

    public Directory getParent() {
        return parent;
    }

    public void setParent(Directory parent) {
        this.parent = parent;
    }

    public Map<String, Directory> getDirectories() {
        return directories;
    }

    public void setDirectories(Map<String, Directory> directories) {
        this.directories = directories;
    }

    public Map<String, File> getFiles() {
        return files;
    }

    public void setFiles(Map<String, File> files) {
        this.files = files;
    }
}