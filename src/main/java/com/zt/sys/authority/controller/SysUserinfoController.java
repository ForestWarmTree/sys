package com.zt.sys.authority.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.service.ISysUserinfoService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 * 用户基础信息表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/userInfo")
public class SysUserinfoController {
    private final Logger logger=LoggerFactory.getLogger(SysUserinfoController.class);

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysUserinfoService sysUserinfoService;

    /**
     * 新增用户基础信息
     * @param userinfo
     * @return
     */
    @PostMapping("/saveUserInfo")
    @ResponseBody
    public RetResult<Map> saveUserInfo(@RequestBody SysUserinfo userinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            userinfo.setCreateUser(sessionUser.getUserId());//创建人
            userinfo.setCreateTime(new Date());// 创建时间
            userinfo.setCreateUserName(sessionUser.getName());//创建人姓名
            userinfo.setUserId(UUID.UU32());
            // 保存
            sysUserinfoService.saveUserInfo(userinfo);
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }


    /**
     * 修改用户基础信息
     * @param userinfo
     * @return
     */
    @PostMapping("/updateUserInfo")
    @ResponseBody
    public RetResult<Map> updateUserInfo(@RequestBody SysUserinfo userinfo, HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            userinfo.setUpdateUser(sessionUser.getUserId());//创建人
            userinfo.setUpdateTime(new Date());// 创建时间
            // 修改
            sysUserinfoService.updateUserInfo(userinfo);
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp();
    }

    /**
     * 根据用户ID获取用户基础信息
     * @return
     */
    @PostMapping("/getUserInfo")
    @ResponseBody
    public RetResult<Map> getUserInfoByUserId(@RequestBody SysUserinfo userinfo,
                                              HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                SysUserinfo sysUserinfo = sysUserinfoService.getUserInfoByUserId(userinfo.getUserId());

                result.put("data",sysUserinfo);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            logger.error(e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }




    /**
     * 人员列表查询
     * @param sysUserinfo
     * @param request
     * @return
     */
    @PostMapping("/selectUserList")
    @ResponseBody
    public Map<String, Object> selectOrgList(@RequestBody SysUserinfo sysUserinfo,
                                             HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysUserinfo> sysUserinfoList = null;
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {

                //获取当前登陆人所属组织
                sysUserinfo.setOrgId(sessionUser.getSysUserinfo().getOrgId());
                //查询列表
                sysUserinfoList = sysUserinfoService.selectUserInfoList(sysUserinfo);

                //返回结果集
                PageInfo<SysUserinfo> pageInfo = new PageInfo<>(sysUserinfoList);
                result.put("size",sysUserinfo.getPageSize());
                result.put("current",sysUserinfo.getCurrent());
                result.put("total",pageInfo.getTotal());
                result.put("pages",pageInfo.getPages());
                result.put("records",sysUserinfoList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 删除人员信息
     * @param userinfoList
     * @param request
     * @return
     */
    @PostMapping("/deleteUser")
    @ResponseBody
    public RetResult<Map> deleteUser(@RequestBody List<SysUserinfo> userinfoList,
                                     HttpServletRequest request) {
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId()!=null &&
                    !sessionUser.getUserId().equals("")) {

                //删除
                sysUserinfoService.deleteUserInfo(userinfoList, sessionUser);
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
     * 根据组ID查询该组下所有用户
     * @param sysGroupinfo
     * @param request
     * @return
     */
    @PostMapping("/selectUserByGroupId")
    @ResponseBody
    public Map<String, Object> selectUserByGroupId(@RequestBody SysGroupinfo sysGroupinfo,
                                                   HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysUserinfo> resultUserList = new ArrayList<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getSysUserinfo()!=null ) {
                if(sessionUser.getSysUserinfo().getOrgId()!=null &&
                        !"".equals(sessionUser.getSysUserinfo().getOrgId())) {

                    SysUserinfo userinfo = new SysUserinfo();
                    userinfo.setOrgId(sessionUser.getSysUserinfo().getOrgId());
                    userinfo.setGroupId(sysGroupinfo.getGroupId());
                    //根据当前登陆人的组织编码，查询该组织下所有用户
                    List<SysUserinfo> userinfoList = sysUserinfoService.selectUserInfoListByAuth(userinfo);
                    if(userinfoList!=null && userinfoList.size()>0) {
                        for(SysUserinfo usr:userinfoList) {
                            usr.setIsChoose("0");
                            resultUserList.add(usr);
                        }
                    }

                    //根据用户组ID查询该用户组下的人员
                    List<SysUserinfo> groupUserList = sysUserinfoService.selectUserInfoListByGroupId(sysGroupinfo.getGroupId());
                    if(groupUserList!=null && groupUserList.size()>0) {
                        for(SysUserinfo usr:groupUserList) {
                            usr.setIsChoose("1");
                            resultUserList.add(usr);
                        }
                    }

                    //返回结果集
                    PageInfo<SysUserinfo> pageInfo = new PageInfo<>(resultUserList);
                    result.put("size",sysGroupinfo.getPageSize());
                    result.put("current",sysGroupinfo.getCurrent());
                    result.put("total",pageInfo.getTotal());
                    result.put("pages",pageInfo.getPages());
                    result.put("records",resultUserList);
                }
            }
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }




    /**
     * 根据角色编码查询用户
     * @param sysRoleinfo
     * @return
     */
    @PostMapping("/selectUserByRoleId")
    @ResponseBody
    public Map<String, Object> selectUserByRoleId(@RequestBody SysRoleinfo sysRoleinfo,
                                                  HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        List<SysUserinfo> resultUserList = new ArrayList<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getSysUserinfo()!=null ) {
                if (sessionUser.getSysUserinfo().getOrgId() != null &&
                        !"".equals(sessionUser.getSysUserinfo().getOrgId())) {
                    //开启分页支持
                    PageHelper.startPage(sysRoleinfo.getCurrent(), sysRoleinfo.getPageSize());
                    //根据角色编码 查询用户
                    List<SysUserinfo> usersList = sysUserinfoService.selectUserInfoListByRoleId(sysRoleinfo.getRoleId());
                    if (usersList != null && usersList.size() > 0) {
                        for (SysUserinfo usr : usersList) {
                            usr.setIsChoose("1");
                            resultUserList.add(usr);
                        }
                    }

                    //根据当前登陆人的组织编码，查询该组织下所有用户
                    SysUserinfo info = new SysUserinfo();
                    info.setOrgId(sessionUser.getSysUserinfo().getOrgId());
                    info.setRoleId(sysRoleinfo.getRoleId());
                    List<SysUserinfo> userinfoList = sysUserinfoService.selectUserInfoListByAuth(info);
                    if (userinfoList != null && userinfoList.size() > 0) {
                        for (SysUserinfo usr : userinfoList) {
                            usr.setIsChoose("0");
                            resultUserList.add(usr);
                        }
                    }

                }
            }
            //返回结果集
            PageInfo<SysUserinfo> pageInfo = new PageInfo<>(resultUserList);
            result.put("size",sysRoleinfo.getPageSize());
            result.put("current",sysRoleinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",resultUserList);
        } catch (Exception e) {
            logger.error(e.toString());
        }
        return result;
    }



}
