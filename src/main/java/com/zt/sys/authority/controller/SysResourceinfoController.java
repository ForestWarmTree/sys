package com.zt.sys.authority.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.service.ISysResourceinfoService;
import com.zt.sys.authority.service.ISysRolelogService;
import com.zt.sys.authority.service.impl.SysRolelogServiceImpl;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.ParamUtil;
import com.zt.sys.authority.utils.UUID;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 资源基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/resource")
public class SysResourceinfoController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysResourceinfoService resourceinfoService;

    /**
     * 根据资源ID查询资源详情
     * @param sysResourceinfo
     * @return
     */
    @PostMapping("/selectResourceInfoByResourceId")
    @ResponseBody
    public Map<String, Object> selectResourceInfoByResourceId(@RequestBody SysResourceinfo sysResourceinfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            SysResourceinfo resourceinfo = resourceinfoService.selectResourceInfoByResourceId(sysResourceinfo);
            result.put("code",200);
            result.put("message", "");
            result.put("timestemp", "");
            result.put("result",resourceinfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",400);
            return result;
        }
        return result;
    }


    /**
     * 删除资源信息
     * @param resourceinfos
     * @param request
     * @return
     */
    @PostMapping("/deleteResources")
    @ResponseBody
    public RetResult<Map> deleteResources(@RequestBody List<SysResourceinfo> resourceinfos,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                resourceinfoService.deleteResources(resourceinfos,sessionUser);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }


    /**
     * 新增资源信息
     * @return
     */
    @PostMapping("/saveResourceInfo")
    @ResponseBody
    public RetResult<Map> saveResourceInfo(@RequestBody SysResourceinfo resourceinfo,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                resourceinfo.setResourceId(ParamUtil.RESOURCE+resourceinfo.getResourceId());
                int flag =  0;
                if(flag<=0) {
                    resourceinfo.setCreateTime(new Date());//创建时间
                    resourceinfo.setCreateUser(sessionUser.getUserId());//创建人
                    if(resourceinfo.getResourceType().equals("1")) {
                        resourceinfo.setResourceType("menu");
                    } else {
                        resourceinfo.setResourceType("btn");
                    }
                    resourceinfoService.saveResource(resourceinfo);
                } else {
                    return RetResponse.makeErrRsp("资源编码重复!");
                }
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }

    /**
     * 编辑资源信息
     * @param resourceinfo
     * @param request
     * @return
     */
    @PostMapping("/editResourceInfo")
    @ResponseBody
    public RetResult<Map> editResourceInfo(@RequestBody SysResourceinfo resourceinfo,
                                           HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                if(resourceinfo.getResourceId().equals(resourceinfo.getParentId())) {
                    return RetResponse.makeErrRsp("父级资源不能是当前资源");
                }

                resourceinfo.setUpdateTime(new Date());//修改时间
                resourceinfo.setUpdateUser(sessionUser.getUserId());//修改人

                resourceinfoService.editResource(resourceinfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }

    /**
     * 根据用户ID 查询资源列表
     * @param sysResourceinfo
     * @return
     */
    @PostMapping("/selectAll")
    @ResponseBody
    public Map<String, Object> selectAll(@RequestBody SysResourceinfo sysResourceinfo,
                                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //模拟登陆
            //SysUsers sessionUser = sessionValue.xnSessionUser();
            // 获取当前登陆人信息
            //SysUsers sessionUser = sessionValue.getSessionUser(request);
            //开启分页支持
            PageHelper.startPage(sysResourceinfo.getCurrent(),sysResourceinfo.getPageSize());
            List<SysResourceinfo> resourceinfos = resourceinfoService.selectAll(sysResourceinfo);
            //返回结果集
            PageInfo<SysResourceinfo> pageInfo = new PageInfo<>(resourceinfos);

            result.put("size",sysResourceinfo.getPageSize());
            result.put("current",sysResourceinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",resourceinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }



    /**
     * 根据用户Id 获取所有资源
     * @param sysResourceinfo
     * @return
     */
    @PostMapping("/selectResourceByUserId")
    @ResponseBody
    public Map<String, Object> selectResourceByUserId(@RequestBody SysResourceinfo sysResourceinfo,
                                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {

            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            //开启分页支持
            PageHelper.startPage(sysResourceinfo.getCurrent(),sysResourceinfo.getPageSize());
            sessionUser.setResourceId(sysResourceinfo.getResourceId());
            sessionUser.setPermissionName(sysResourceinfo.getPermissionName());
            List<SysResourceinfo> resourceinfos = resourceinfoService.selectResourceByUserId(sessionUser);
            //返回结果集
            PageInfo<SysResourceinfo> pageInfo = new PageInfo<>(resourceinfos);

            result.put("size",sysResourceinfo.getPageSize());
            result.put("current",sysResourceinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",resourceinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据角色Id 获取所有资源
     * @param sysRoleinfo
     * @return
     */
    @PostMapping("/selectResourceByRoleId")
    @ResponseBody
    public Map<String, Object> selectResourceByRoleId(@RequestBody SysRoleinfo sysRoleinfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            //开启分页支持
            PageHelper.startPage(sysRoleinfo.getCurrent(),sysRoleinfo.getPageSize());
            List<SysResourceinfo> resourceinfos = resourceinfoService.selectResourceByRoleId(sysRoleinfo);
            //返回结果集
            PageInfo<SysResourceinfo> pageInfo = new PageInfo<>(resourceinfos);

            result.put("size",sysRoleinfo.getPageSize());
            result.put("current",sysRoleinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",resourceinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
