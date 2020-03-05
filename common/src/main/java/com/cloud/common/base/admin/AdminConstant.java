package com.cloud.common.base.admin;

public class AdminConstant {
    //状态：1正常,启用 0删除,禁用
    public static final Integer STATUS_NORMAL = 1;
    public static final Integer STATUS_DEL = 0;

    //数据权限： 0全部数据权限 1自定义数据权限 2部门及以下 3本部门
    public static final Integer DATA_TYPE_ALL = 0;
    public static final Integer DATA_TYPE_CUSTOM = 1;
    public static final Integer DATA_TYPE_UNDER = 2;
    public static final Integer DATA_TYPE_SAME = 3;
}
