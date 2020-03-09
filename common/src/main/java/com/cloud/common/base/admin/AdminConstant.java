package com.cloud.common.base.admin;

public class AdminConstant {
    //状态：1正常,启用 0删除,禁用
    public static final Integer STATUS_NORMAL = 1;
    public static final Integer STATUS_DEL = 0;

    //用户是否锁定
    public static final Integer USER_STATUS_LOCK = 0;//锁定

    //数据权限： 0全部数据权限 1自定义数据权限 2部门及以下 3本部门
    public static final Integer DATA_TYPE_ALL = 0;
    public static final Integer DATA_TYPE_CUSTOM = 1;
    public static final Integer DATA_TYPE_UNDER = 2;
    public static final Integer DATA_TYPE_SAME = 3;
    /**
     * 顶部菜单类型权限
     */
    public static final Integer PERMISSION_NAV = -1;

    /**
     * 页面类型权限
     */
    public static final Integer PERMISSION_PAGE = 0;

    /**
     * 操作类型权限
     */
    public static final Integer PERMISSION_OPERATION = 1;



    /**
     * 1级菜单父id
     */
    public static final long PARENT_ID = 0;
    /**
     * 0级菜单
     */
    public static final Integer LEVEL_ZERO = 0;

    /**
     * 1级菜单
     */
    public static final Integer LEVEL_ONE = 1;

    /**
     * 2级菜单
     */
    public static final Integer LEVEL_TWO = 2;

    /**
     * 3级菜单
     */
    public static final Integer LEVEL_THREE = 3;

    /**
     * 部门负责人类型 主负责人
     */
    public static final Integer HEADER_TYPE_MAIN = 0;

    /**
     * 部门负责人类型 副负责人
     */
    public static final Integer HEADER_TYPE_VICE = 1;
}
