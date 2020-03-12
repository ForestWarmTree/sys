package com.zt.sys.authority.utils;

import com.alibaba.fastjson.JSONObject;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.service.ISysUsersService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2018/7/5
 * Time: 15:47
 */
@Component
public class HttpSessionValue {
    /**
     * 注入redis缓存工具类
     */
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISysUsersService sysUsersService;

    /**
     *将用户信息存入 Session
     * @param sysUsers SysUsers
     */
    public void setSessionUserAuth(SysUsers sysUsers) {
        redisUtil.set(sysUsers.getToken(), sysUsers);
    }

    /**
     *从Session中取出当前用户信息
     * @param request HttpServletRequest
     */
    public SysUsers getSessionUserAuth(HttpServletRequest request) {
        String token = request.getHeader("Access-Token");
        SysUsers users = null;
        if (token != null && !"".equals(token)) {
            // 验证key 是否存在
            boolean flag = redisUtil.hasKey(token);
            if(flag) {
                //key 存在，从redis中获取用户信息
                Object obj = redisUtil.get(token);
                JSONObject jsonObject = (JSONObject)JSONObject.toJSON(obj);
                users = JSONObject.parseObject(String.valueOf(jsonObject),SysUsers.class);
                if (users.getRole()==null || users.getRole().size()==0){
                    users = sysUsersService.selectUsersResource(users);
                }
                redisUtil.set(token, users, 7200L);
            }
        }
        return users;
    }


    /**
     *从Session中取出当前用户信息
     * @param request HttpServletRequest
     */
    public SysUsers getSessionUser(HttpServletRequest request) {
//        String token = request.getHeader("Access-Token");
//        if (token != null && !"".equals(token)) {
//            Object obj = redisUtil.get(token);
//            JSONObject jsonObject = (JSONObject)JSONObject.toJSON(obj);
//            SysUsers sysUsers = JSONObject.parseObject(String.valueOf(jsonObject),SysUsers.class);
//            if (null != sysUsers) {
//                return sysUsers;
//            }
//        }
//        return null;

        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId("12345");
        sysUsers.setUsername("supper");
        sysUsers.setType(1);
        sysUsers.setIsSupper("1");
        sysUsers.setName("超级管理");
        SysUserinfo sysUserinfo = new SysUserinfo();
        sysUserinfo.setUserId("12345");
        sysUserinfo.setOrgId("zt1010100");
        sysUserinfo.setDeptId("dept001");
        sysUsers.setSysUserinfo(sysUserinfo);
        return sysUsers;
    }

    /**
     * 删除redis中的key
     * @param request
     */
    public void logout(HttpServletRequest request) {
        String token = request.getHeader("Access-Token");
        redisUtil.del(token);
    }


    /**
     * 虚拟用户
     * @return
     */
    public SysUsers xnSessionUser() {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId("12345");
        sysUsers.setUsername("supper");
        sysUsers.setType(1);
        sysUsers.setIsSupper("1");
        sysUsers.setName("超级管理");
        SysUserinfo sysUserinfo = new SysUserinfo();
        sysUserinfo.setUserId("12345");
        sysUserinfo.setOrgId("zt1010100");
        sysUserinfo.setDeptId("dept001");
        sysUsers.setSysUserinfo(sysUserinfo);
        return sysUsers;
    }


}
