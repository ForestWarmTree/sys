package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysRoleResource;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.logutil.BaseLogger;
import com.zt.sys.authority.service.ISysRoleResourceService;
import com.zt.sys.authority.utils.HttpSessionValue;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 角色资源关系对应表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-21
 */
@RestController
@RequestMapping("/authority/sys-role-resource")
public class SysRoleResourceController extends BaseLogger {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysRoleResourceService sysRoleResourceService;
    /**
     * 新增角色资源关系 - 按角色分配
     * @param roleResource
     * @return
     */
    @PostMapping("/saveRoleResource")
    @ResponseBody
    public RetResult<Map> saveRoleResource(@RequestBody SysRoleResource roleResource,
                                       HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                roleResource.setCreateUser(sessionUser.getUserId());// 创建人
                roleResource.setCreateTime(new Date()); // 创建时间
                roleResource.setCreateUserName(sessionUser.getName());//创建人姓名
                sysRoleResourceService.saveRoleResource(roleResource);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }

    /**
     * 新增角色资源关系 - 按资源分配
     * @param roleResource
     * @param request
     * @return
     */
    @PostMapping("/saveResourceRole")
    @ResponseBody
    public RetResult<Map> saveResourceRole(@RequestBody SysRoleResource roleResource,
                                       HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                roleResource.setCreateUser(sessionUser.getUserId());// 创建人
                roleResource.setCreateTime(new Date()); // 创建时间
                sysRoleResourceService.saveResourceRole(roleResource);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }


    /**
     * 复制功能数据保存
     * @param roleinfo
     * @param request
     * @return
     */
    @PostMapping("/copyRoleSave")
    @ResponseBody
    public RetResult<Map> copyRoleSave(@RequestBody SysRoleinfo roleinfo,
                                       HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                roleinfo.setCreateTime(new Date());//修改时间
                roleinfo.setCreateUser(sessionUser.getUserId());//修改人

                //保存
                sysRoleResourceService.copyRoleSave(roleinfo);

            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }

}
