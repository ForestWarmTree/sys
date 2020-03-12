package com.zt.sys;

import com.alibaba.fastjson.JSON;
import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.service.ISysUserRoleService;
import com.zt.sys.authority.service.ISysUsersService;
import com.zt.sys.authority.service.ISysUsersgroupService;
import com.zt.sys.authority.utils.MD5Util;
import com.zt.sys.authority.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SysApplicationTests {

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ISysUsersgroupService sysUsersgroupService;

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private ISysUsersService sysUsersService;

    String password = "";

//    @Test
//    public void testRedis() {
//        SysUsers sysUsers = new SysUsers();
//        SysUserinfo sysUserinfo = new SysUserinfo();
//        sysUserinfo.setUserCardNo("工号001");
//        sysUserinfo.setName("张三");
//        sysUserinfo.setPosition("岗位001");
//        sysUserinfo.setOrgId("1230");
//        sysUsers.setSysUserinfo(sysUserinfo);
//        List<SysRoleinfo> roleinfoList = new ArrayList<>();
//        for(int i=0;i<2;i++) {
//            SysRoleinfo roleinfo = new SysRoleinfo();
//            roleinfo.setRoleId("角色ID00"+i);
//            roleinfo.setRoleName("超级管理员"+i);
//            roleinfoList.add(roleinfo);
//        }
//        sysUsers.setRole(new HashMap<>());
//        redisService.setObj("admin",sysUsers);
//        System.out.println(sysUsers.toString());
//    }
//
//    @Test
//    public void testRedis1() {
//        SysUsers sysUsers = redisService.getObj("admin");
//        System.out.println(JSON.toJSONString(sysUsers));
//    }


    @Test
    public void password() {
        System.out.println("第一次密码:" + password);

        String pwd = "funfun1qaz";
        try {
            password = MD5Util.getEncryptedPwd(pwd);
            System.out.println("加密后" + password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        testpwd();
    }

    public void testpwd() {
        String pwd1 = "funfun1qaz";
        boolean flag;
        try {
           flag = MD5Util.validPassword(pwd1,password);
           System.out.println("未加密验证结果：" + flag);
           pwd1 = MD5Util.getEncryptedPwd(pwd1);
           flag = MD5Util.validPassword(pwd1,password);
           System.out.println("加密验证结果：" + flag);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 测试新增用户组与用户对应关系
     */
    @Test
    public void test1() {
        SysUsersgroup sysUsersgroup = new SysUsersgroup();
        sysUsersgroup.setGroupId("group001");
        sysUsersgroup.setCreateUser("普通管理员");
        sysUsersgroup.setCreateTime(new Date());
        List<String> userids = new ArrayList<>();
        for(int i=0;i<10;i++) {
            userids.add("funfun" + i);
        }
        sysUsersgroup.setUserIds(userids);
        sysUsersgroupService.saveUsersGroup(sysUsersgroup);
    }

    @Test
    public void test2() {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setUserId("user001");
        sysUserRole.setStartDate(new Date().toString());
        sysUserRole.setEndDate(new Date().toString());
        sysUserRole.setCreateUser("普通管理员");
        sysUserRole.setCreateTime(new Date());
        List<String> roleids = new ArrayList<>();
        for(int i=0;i<10;i++) {
            roleids.add("roleid" + i);
        }
        sysUserRole.setRoleIds(roleids);
        sysUserRoleService.saveUserRole(sysUserRole);

    }

    @Test
    public void test3() {
        SysUserRole sysUserRole = new SysUserRole();
        sysUserRole.setRoleId("role001");
        sysUserRole.setStartDate(new Date().toString());
        sysUserRole.setEndDate(new Date().toString());
        sysUserRole.setCreateUser("普通管理员");
        sysUserRole.setCreateTime(new Date());
        List<String> userids = new ArrayList<>();
        for(int i=0;i<10;i++) {
            userids.add("user" + i);
        }
        sysUserRole.setUserIds(userids);
        sysUserRoleService.saveRoleUser(sysUserRole);

    }


    @Test
    public void test4() {
        String orgId = "1230";
        List<SysUsers> users = sysUsersService.selectUserByOrgId(orgId);
        for(SysUsers sysUsers:users) {
            System.out.println(sysUsers.toString());
        }
    }

    @Test
    public void test5() {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId("user0");
        sysUsersService.selectUsersResource(sysUsers);
    }


}
