package com.tong.common.constant;
/**
 * @Author stonewong
 * @Description TODO
 * @Date 2019/6/27 14:47
 * @Param
 * @return
 **/
public class ServerConstant {
    private static final String SERVER_HOST = "127.0.0.1";
    private static final Integer SERVER_USER_SYSTEM_PORT = 55554;
    private static final Integer SERVER_CONTROL_PORT = 55555;

    private static final String SERVER_ROOT_PATH = "/home/xu/ftp_test/server";

    private static final String THREAD_MODE_CONTROL = "control";
    private static final String THREAD_MODE_DATA = "data";

    public static String getServerHost() {
        return SERVER_HOST;
    }

    public static Integer getServerControlPort() {
        return SERVER_CONTROL_PORT;
    }

    public static String getThreadModeControl() {
        return THREAD_MODE_CONTROL;
    }

    public static String getThreadModeData() {
        return THREAD_MODE_DATA;
    }

    public static Integer getServerUserSystemPort() {
        return SERVER_USER_SYSTEM_PORT;
    }

    public static String getServerRootPath() {
        return SERVER_ROOT_PATH;
    }
}
