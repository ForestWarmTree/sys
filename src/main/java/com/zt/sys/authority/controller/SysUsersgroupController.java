package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.entity.SysUsersgroup;
import com.zt.sys.authority.service.ISysUsersService;
import com.zt.sys.authority.service.ISysUsersgroupService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.MD5Util;
import com.zt.sys.authority.utils.UUID;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/authority/sys-usersgroup")
public class SysUsersgroupController {


    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysUsersgroupService sysUsersgroupService;
    /**
     * 新增用户组
     * @param sysUsersgroup
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public RetResult<Map> save(@RequestBody SysUsersgroup sysUsersgroup,
                                   HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                sysUsersgroup.setCreateUser(sessionUser.getUserId());// 创建人
                sysUsersgroup.setCreateTime(new Date()); // 创建时间
                sysUsersgroup.setCreateUserName(sessionUser.getName());

                sysUsersgroupService.saveUsersGroup(sysUsersgroup);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }

}
