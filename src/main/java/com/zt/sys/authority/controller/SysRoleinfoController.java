package com.zt.sys.authority.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.service.ISysResourceinfoService;
import com.zt.sys.authority.service.ISysRoleResourceService;
import com.zt.sys.authority.service.ISysRoleinfoService;
import com.zt.sys.authority.utils.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 角色基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/role")
public class SysRoleinfoController {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysRoleinfoService roleinfoService;

    @Resource
    private ISysResourceinfoService sysResourceinfoService;

    @Resource
    private ISysRoleResourceService sysRoleResourceService;

    @Resource
    private CommonUtil<SysRoleinfo> commonUtil;
    /**
     * 根据角色ID查询角色详情
     * @param sysRoleinfo
     * @return
     */
    @PostMapping("/selectRoleInfo")
    @ResponseBody
    public Map<String, Object> selectRoleInfo(@RequestBody SysRoleinfo sysRoleinfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            SysRoleinfo roleinfo = roleinfoService.selectRoleInfoByRoleId(sysRoleinfo);
            result.put("code",200);
            result.put("message", "");
            result.put("timestemp", "");
            result.put("result",roleinfo);
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",400);
            return result;
        }
        return result;
    }


    /**
     * 初始化编辑页面数据
     * @param roleinfo
     * @return
     */
    @PostMapping("/editInit")
    @ResponseBody
    public Map<String, Object> editInit(@RequestBody SysRoleinfo roleinfo, HttpServletRequest request){
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            SysResourceinfo sysResourceinfo = new SysResourceinfo();
            sysResourceinfo.setUserId(sessionUser.getUserId());
            //根据用户ID获取当前用户可操作的权限树
            List<TreeModel> treeModelList = commonUtil.editInitTree(sysResourceinfo);
            //根据当前编辑的角色编码，查询出该角色已有的资源信息
            List<SysResourceinfo> resList = sysResourceinfoService.selectResourceByRoleId(roleinfo);
            List<String> selectKeys = new ArrayList<>();
            for(SysResourceinfo info:resList) {
                selectKeys.add(info.getResourceId());
            }
            roleinfo.setTreeData(treeModelList);
            roleinfo.setSelectKeys(selectKeys);

            result.put("code",200);
            result.put("message", "");
            result.put("timestemp", "");
            result.put("result",roleinfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除角色信息-物理删除
     * @param roleIds
     * @param request
     * @return
     */
    @PostMapping("/deleteRoles")
    @ResponseBody
    public RetResult<Map> deleteRoles(@RequestBody List<String> roleIds, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                SysRoleinfo sysRoleinfo = new SysRoleinfo();
                sysRoleinfo.setCreateUser(sessionUser.getUserId());//操作人
                sysRoleinfo.setCreateTime(new Date());//操作时间
                sysRoleinfo.setCreateUserName(sessionUser.getName());//操作人姓名
                sysRoleinfo.setRoleIds(roleIds);
                roleinfoService.deleteRoles(sysRoleinfo);
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeRsp(200,"操作成功!");
    }


    /**
     * 新增角色基础信息
     * @param roleinfo
     * @return
     */
    @PostMapping("/saveRoleInfo")
    @ResponseBody
    public RetResult<Map> saveRoleInfo(@RequestBody SysRoleinfo roleinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null && !sessionUser.getUserId().equals("")) {
                //判断用户名是否重复
                roleinfo.setRoleId(ParamUtil.ROLE + roleinfo.getRoleId());
                int flag = roleinfoService.validataRoleId(roleinfo.getRoleId());
                if (flag<=0) {

                    // 设置有效期
                    commonUtil.validate(roleinfo);

                    roleinfo.setCreateUser(sessionUser.getUserId());
                    roleinfo.setCreateTime(new Date());
                    roleinfo.setCreateUserName(sessionUser.getName());
                    roleinfo.setOrgId(sessionUser.getSysUserinfo().getOrgId());
                    roleinfoService.saveRole(roleinfo);
                } else {
                    return RetResponse.makeErrRsp("角色ID重复!");
                }
            } else {
                return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return  RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeRsp(200,"操作成功!");
    }


    /**
     * 编辑角色信息
     * @param roleinfo
     * @param request
     * @return
     */
    @PostMapping("/editRoleInfo")
    @ResponseBody
    public RetResult<Map> editRoleInfo(@RequestBody SysRoleinfo roleinfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                roleinfo.setUpdateTime(new Date());//修改时间
                roleinfo.setUpdateUser(sessionUser.getUserId());//修改人

                //保存角色资源对应关系
                SysRoleResource sysRoleResource = new SysRoleResource();
                sysRoleResource.setCreateUser(sessionUser.getUserId());// 创建人
                sysRoleResource.setCreateTime(new Date()); // 创建时间
                sysRoleResource.setCreateUserName(sessionUser.getName());//创建人姓名
                sysRoleResource.setResourceIds(roleinfo.getResourceIds());//资源ID集合
                sysRoleResource.setRoleId(roleinfo.getRoleId());//角色ID
                sysRoleResourceService.saveRoleResource(sysRoleResource);

                // 设置有效期
                commonUtil.validate(roleinfo);
                roleinfoService.editRole(roleinfo);
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
     * 用户管理——给用户分配角色，查询已分配与待分配角色
     * @param request
     * @param sysUsers
     * @return
     */
    @PostMapping("/selectRoleByUserId")
    @ResponseBody
    public Map<String, Object> selectRoleByUserId(@RequestBody SysUsers sysUsers,
                                             HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysRoleinfo> roleinfoList = null;
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                //根据当前登陆人查询当前登陆人可操作得角色
                sessionUser.setParamId(sysUsers.getUserId());
                roleinfoList = roleinfoService.selectAuthRoleByUser(sessionUser);

                // 根据前台传入用户ID 查询该用户角色信息权限
                List<SysRoleinfo> chooseList = roleinfoService.selectChooseRoleList(sysUsers);

                // 根据用户ID 查询已分配角色
                for(SysRoleinfo roleinfo:chooseList) {
                    roleinfo.setIsChoose("1");
                    roleinfoList.add(roleinfo);
                }
                //返回结果集
                PageInfo<SysRoleinfo> pageInfo = new PageInfo<>(roleinfoList);
                result.put("size",sysUsers.getPageSize());
                result.put("current",sysUsers.getCurrent());
                result.put("total",pageInfo.getTotal());
                result.put("pages",pageInfo.getPages());
                result.put("records",roleinfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }




    /**
     * 根据组织编码查询角色
     * @param roleinfo
     * @param request
     * @return
     */
    @PostMapping("/selectRoleByOrgId")
    @ResponseBody
    public Map<String, Object> selectRoleByOrgId(@RequestBody SysRoleinfo roleinfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUser(request);
            if(sessionUser!=null && sessionUser.getSysUserinfo()!=null ) {
                if(sessionUser.getSysUserinfo().getOrgId()!=null && !"".equals(sessionUser.getSysUserinfo().getOrgId())) {
                    //开启分页支持
                    PageHelper.startPage(roleinfo.getCurrent(),roleinfo.getPageSize());
                    // 查询条件时间段
                    commonUtil.commonSetTime(roleinfo.getCreateTimes(), roleinfo);
                    roleinfo.setOrgId(sessionUser.getSysUserinfo().getOrgId());//设置组织编码
                    List<SysRoleinfo> sysRoleinfos = roleinfoService.selectRoleByOrgId(roleinfo);

                    //返回结果集
                    PageInfo<SysRoleinfo> pageInfo = new PageInfo<>(sysRoleinfos);
                    result.put("size",roleinfo.getPageSize());
                    result.put("current",roleinfo.getCurrent());
                    result.put("total",pageInfo.getTotal());
                    result.put("pages",pageInfo.getPages());
                    result.put("records",sysRoleinfos);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据资源编码查询角色
     * @param sysResourceinfo
     * @param request
     * @return
     */
    @PostMapping("/selectRoleByResourceId")
    @ResponseBody
    public Map<String, Object> selectRoleByResourceId(@RequestBody SysResourceinfo sysResourceinfo, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            //开启分页支持
            PageHelper.startPage(sysResourceinfo.getCurrent(),sysResourceinfo.getPageSize());
            List<SysRoleinfo> sysRoleinfos = roleinfoService.selectRoleByResourceId(sysResourceinfo.getPermissionId());

            //返回结果集
            PageInfo<SysRoleinfo> pageInfo = new PageInfo<>(sysRoleinfos);
            result.put("size",sysResourceinfo.getPageSize());
            result.put("current",sysResourceinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",sysRoleinfos);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


}
