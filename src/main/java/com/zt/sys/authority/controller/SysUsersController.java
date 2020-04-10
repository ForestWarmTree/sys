package com.zt.sys.authority.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zt.sys.authority.core.RetResponse;
import com.zt.sys.authority.core.RetResult;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.logutil.BaseLogger;
import com.zt.sys.authority.service.ISysUsersService;
import com.zt.sys.authority.utils.HttpSessionValue;
import com.zt.sys.authority.utils.MD5Util;
import com.zt.sys.authority.utils.Tool;
import com.zt.sys.authority.utils.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@RestController
@RequestMapping("/")
public class SysUsersController extends BaseLogger {

    @Resource
    private HttpSessionValue sessionValue;

    @Resource
    private ISysUsersService sysUsersService;

    /**
     * 模拟/auth/2step-code
     * @return
     */
    @RequestMapping("/auth/2step-code")
    @ResponseBody
    public Map<String, Object> twoStepCode(){
        Map<String, String> res = new HashMap<String, String>();
        res.put("stepCode", "0");

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("code", 0);
        result.put("message", "");
        result.put("result", res);
        result.put("timestemp", "");

        return result;
    }

    /**
     * 登陆
     * @param sysUsers
     * @return
     */
    @PostMapping("/user/login")
    @ResponseBody
    public Map<String, Object> userLogin(@RequestBody SysUsers sysUsers, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
           SysUsers user = sysUsersService.selectPassWordByUserName(sysUsers.getUsername());
           if(user!=null) {
               String password = user.getPassword();
               if(password!=null && !"".equals(password)) {
                   boolean flag = MD5Util.validPassword(sysUsers.getPassword(),password);
                   if(flag) {
                       user.setToken(UUID.UU32());
                       //用户名和密码验证通过，将用户信息缓存到redis中
                       sessionValue.setSessionUserAuth(user);
                       user.setPassword("");
                       if(user != null) {
                           result.put("code",200);
                           result.put("message", "");
                           result.put("timestemp", "");
                           result.put("result",user);
                       } else {
                           result.put("code",401);
                           result.put("message", "用户名或密码错误!");
                           result.put("timestemp", "");
                           result.put("result",user);
                       }
                   } else {
                       result.put("code",400);
                       return result;
                   }
               } else {
                   result.put("code",400);
                   return result;
               }
           } else {
               result.put("code",401);
               result.put("message", "用户名或密码错误!");
               result.put("timestemp", "");
               result.put("result",user);
           }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping("/user/logout")
    @ResponseBody
    public RetResult<Map> logout(HttpServletRequest request){
        Map<String, Object> result = new HashMap<String, Object>();

        try {
            sessionValue.logout(request);
            result.put("code", 200);
            result.put("message", "");
            result.put("result", new SysUsers());
            result.put("timestemp", "");
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }

        return RetResponse.makeOKRsp(result);
    }



    /**
     * 获取用户信息
     * @return
     */
    @RequestMapping("/user/info")
    @ResponseBody
    public Map<String, Object> userinfo(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            SysUsers user = sessionValue.getSessionUserAuth(request);
            user.setPassword("");
            result.put("code", 200);
            result.put("message", "");
            result.put("result", user);
            result.put("timestemp", "");
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 新增用户
     * @param sysUsers
     * @return
     */
    @PostMapping("/user/saveUser")
    @ResponseBody
    public RetResult<Map> saveUser(@RequestBody SysUsers sysUsers, HttpServletRequest request) {
        if(sysUsers != null) {
            //判断用户名是否重复
            String userId = sysUsersService.validataUserName(sysUsers.getUsername());
            if(userId!=null && !userId.equals("") && !userId.equals(sysUsers.getUserId())) {
                return  RetResponse.makeErrRsp("用户名重复");
            } else {
                try {
                    // 获取当前登陆人信息
                    SysUsers sessionUser = sessionValue.getSessionUserAuth(request);

                    if(sessionUser!=null && sessionUser.getUserId()!=null &&
                            !sessionUser.getUserId().equals("")) {
                        //加密密码
                        String pwd = MD5Util.getEncryptedPwd(sysUsers.getPassword());
                        sysUsers.setPassword(pwd);

                        if(sysUsers.getIndate() == null) {
                            sysUsers.setIndate(Tool.parseDateShort("9999-01-01"));
                        }
                        sysUsers.setCreateUser(sessionUser.getUserId());// 创建人
                        sysUsers.setCreateTime(new Date());// 创建时间
                        sysUsers.setName(sessionUser.getName());// 创建人姓名
                        sysUsersService.saveUser(sysUsers); // 新增用户
                    } else {
                        return RetResponse.makeErrRsp("登陆已过期!请重新登陆");
                    }
                } catch (Exception e) {
                    logerror("错误信息："+e.toString());
                    return  RetResponse.makeSysErrRsp();
                }
            }
        } else {
            return  RetResponse.makeErrRsp("用户名或密码不可为空");
        }
        return RetResponse.makeRsp(200,"操作成功!");
    }

    /**
     * 根据用户编码 查询用户账号
     * @param sysUsers
     * @param request
     * @return
     */
    @PostMapping("/user/selectUserNameAndPassword")
    @ResponseBody
    public RetResult<Map> getUserInfoByUserId(@RequestBody SysUsers sysUsers,
                                              HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getUserId() != null &&
                    !sessionUser.getUserId().equals("")) {
                SysUsers user = sysUsersService.selectUserNameAndPassword(sysUsers);

                result.put("data",user);
            } else {
                return RetResponse.makeErrRsp("登陆时间过期!请重新登陆");
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
            return RetResponse.makeSysErrRsp();
        }
        return RetResponse.makeOKRsp(result);
    }



    /**
     * 根据组织编码查询用户
     * @param sysUsers
     * @param request
     * @return
     */
    @PostMapping("/user/selectUserByOrgId")
    @ResponseBody
    public Map<String, Object> selectUserByOrgId(@RequestBody SysUsers sysUsers, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getSysUserinfo()!=null ) {
                if(sessionUser.getSysUserinfo().getOrgId()!=null && !"".equals(sessionUser.getSysUserinfo().getOrgId())) {
                    //开启分页支持
                    PageHelper.startPage(sysUsers.getCurrent(),sysUsers.getPageSize());
                    List<SysUsers> usersList = sysUsersService.selectUserByOrgId(sessionUser.getSysUserinfo().getOrgId());
                    //返回结果集
                    PageInfo<SysUsers> pageInfo = new PageInfo<>(usersList);
                    result.put("size",sysUsers.getPageSize());
                    result.put("current",sysUsers.getCurrent());
                    result.put("total",pageInfo.getTotal());
                    result.put("pages",pageInfo.getPages());
                    result.put("records",usersList);
                }
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }
        return result;
    }



    /**
     * 根据部门编码查询用户
     * @param sysUsers
     * @param request
     * @return
     */
    @PostMapping("/user/selectUserBydeptId")
    @ResponseBody
    public Map<String, Object> selectUserBydeptId(@RequestBody SysUsers sysUsers, HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            // 获取当前登陆人信息
            SysUsers sessionUser = sessionValue.getSessionUserAuth(request);
            if(sessionUser!=null && sessionUser.getSysUserinfo()!=null ) {
                if(sessionUser.getSysUserinfo().getDeptId()!=null && !"".equals(sessionUser.getSysUserinfo().getDeptId())) {
                    //开启分页支持
                    PageHelper.startPage(sysUsers.getCurrent(),sysUsers.getPageSize());
                    List<SysUsers> usersList = sysUsersService.selectUserByDeptId(sessionUser.getSysUserinfo().getDeptId());
                    //返回结果集
                    PageInfo<SysUsers> pageInfo = new PageInfo<>(usersList);
                    result.put("size",sysUsers.getPageSize());
                    result.put("current",sysUsers.getCurrent());
                    result.put("total",pageInfo.getTotal());
                    result.put("pages",pageInfo.getPages());
                    result.put("records",usersList);
                }
            }
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }
        return result;
    }


    /**
     * 根据资源编码查询用户
     * @param sysResourceinfo
     * @return
     */
    @PostMapping("/user/selectUserByResourceId")
    @ResponseBody
    public Map<String, Object> selectUserByResourceId(@RequestBody SysResourceinfo sysResourceinfo) {
        Map<String, Object> result = new HashMap<>();
        try {
            //开启分页支持
            PageHelper.startPage(sysResourceinfo.getCurrent(),sysResourceinfo.getPageSize());
            List<SysUsers> usersList = sysUsersService.selectUserByResourceId(sysResourceinfo.getPermissionId());
            //返回结果集
            PageInfo<SysUsers> pageInfo = new PageInfo<>(usersList);

            result.put("size",sysResourceinfo.getPageSize());
            result.put("current",sysResourceinfo.getCurrent());
            result.put("total",pageInfo.getTotal());
            result.put("pages",pageInfo.getPages());
            result.put("records",usersList);
        } catch (Exception e) {
            logerror("错误信息："+e.toString());
        }
        return result;
    }


}
