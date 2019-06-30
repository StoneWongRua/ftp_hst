package com.tong.test;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 15:36
 * @Param
 * @return
 **/
public class Father {
    class Data {
        List<String> stringList;
        List<Father> fathers;
    }

    public Data func1() {
        Data data = new Data();
        data.stringList = new ArrayList<>();
        data.fathers = new ArrayList<>();
        return data;
    }
}
