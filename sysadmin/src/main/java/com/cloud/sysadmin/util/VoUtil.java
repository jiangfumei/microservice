package com.cloud.sysadmin.util;

import cn.hutool.core.bean.BeanUtil;
import com.cloud.sysadmin.entity.Permission;
import com.cloud.sysadmin.vo.MenuVo;

public class VoUtil {

    public static MenuVo permissionToMenuVo(Permission p){

        MenuVo menuVo = new MenuVo();
        BeanUtil.copyProperties(p, menuVo);
        return menuVo;
    }
}
