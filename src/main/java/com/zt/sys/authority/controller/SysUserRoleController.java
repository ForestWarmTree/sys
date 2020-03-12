package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.entity.SysUsersgroup;
import com.zt.sys.authority.service.ISysUserRoleService;
import com.zt.sys.authority.service.ISysUsersgroupService;
import com.zt.sys.authority.utils.HttpSessionValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 用户基础信息表 前端控制器
 * </p>
 *  用户角色关系
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/authority/sys-user-role")
public class SysUserRoleController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysUserRoleService sysUserRoleService;
    /**
     * 新增用户角色关系 - 按用户分配
     * @param sysUserRole
     * @return
     */
    @PostMapping("/saveUserRole")
    @ResponseBody
    public RetResult<Map> saveUserRole(@RequestBody SysUserRole sysUserRole,
                               HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                sysUserRole.setCreateUser(sessionUser.getUserId());// 创建人
                sysUserRole.setCreateTime(new Date()); // 创建时间
                sysUserRole.setCreateUserName(sessionUser.getName());// 创建人姓名
                sysUserRoleService.saveUserRole(sysUserRole);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }

    /**
     * 新增用户角色关系 -按角色分配
     * @param sysUserRole
     * @param request
     * @return
     */
    @PostMapping("/saveRoleUser")
    @ResponseBody
    public RetResult<Map> saveRoleUser(@RequestBody SysUserRole sysUserRole,
                                       HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                sysUserRole.setCreateUser(sessionUser.getUserId());// 创建人
                sysUserRole.setCreateTime(new Date()); // 创建时间
                sysUserRoleService.saveRoleUser(sysUserRole);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }


    /**
     * 把A用户的权限复制B用户
     * @param sysUserRole
     * @param request
     * @return
     */
    @PostMapping("/saveUserRoleByAB")
    @ResponseBody
    public RetResult<Map> saveUserRoleByAB(@RequestBody SysUserRole sysUserRole,
                                       HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                sysUserRole.setCreateUser(sessionUser.getUserId());// 创建人
                sysUserRole.setCreateTime(new Date()); // 创建时间
                sysUserRoleService.saveUserRoleByAB(sysUserRole);
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