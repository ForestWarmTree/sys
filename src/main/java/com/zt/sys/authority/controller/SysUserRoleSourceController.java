package com.zt.sys.authority.controller;


import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.entity.SysUserRoleSource;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysUserRoleSourceService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户角色资源关系关联表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/authority/sys-user-role-source")
public class SysUserRoleSourceController {

    @Resource
    private ISysUserRoleSourceService sysUserRoleSourceService;


    @Resource
    private HttpSessionValue sessionValue;


    /**
     * 新增用户角色权限关系信息
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public RetResult<Map> save(@RequestBody SysUserRoleSource sysUserRoleSource,
                               HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                sysUserRoleSource.setCreateUser(sessionUser.getUserId());
                sysUserRoleSource.setCreateTime(new Date());
                sysUserRoleSourceService.saveUserRoleResource(sysUserRoleSource);

            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }


}
