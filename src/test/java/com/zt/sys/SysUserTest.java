package com.zt.sys;

import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUserinfo;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysUserinfoService;
import com.zt.sys.authority.service.ISysUsersService;
import com.zt.sys.authority.utils.MD5Util;
import com.zt.sys.authority.utils.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/2/25
 * Time: 15:45
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysUserTest {
    @Resource
    private ISysUsersService sysUsersService;

    @Resource
    private ISysUserinfoService sysUserinfoService;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    @Test
    public void saveUser() throws UnsupportedEncodingException, NoSuchAlgorithmException {
        SysUsers sysUsers = new SysUsers();
        sysUsers.setUserId(UUID.UU32());
        sysUsers.setIsSupper("0");
        sysUsers.setCreateUser("12345");
        sysUsers.setCreateTime(new Date());
        sysUsers.setIndate(new Date());
        sysUsers.setType(1);
        sysUsers.setUsername("caozijian");
        //加密密码
        String pwd = MD5Util.getEncryptedPwd("123456");
        sysUsers.setPassword(pwd);
        sysUsersService.saveUser(sysUsers); // 新增用户

        SysUsers sysUsers1 = new SysUsers();
        sysUsers1.setUserId(UUID.UU32());
        sysUsers1.setIsSupper("0");
        sysUsers1.setCreateUser("12345");
        sysUsers1.setCreateTime(new Date());
        sysUsers1.setIndate(new Date());
        sysUsers1.setType(1);
        sysUsers1.setUsername("zhangjunqiao");
        //加密密码
        String pwd1 = MD5Util.getEncryptedPwd("123456");
        sysUsers1.setPassword(pwd1);
        sysUsersService.saveUser(sysUsers1); // 新增用户

        SysUsers sysUsers2 = new SysUsers();
        sysUsers2.setUserId(UUID.UU32());
        sysUsers2.setIsSupper("0");
        sysUsers2.setCreateUser("12345");
        sysUsers2.setCreateTime(new Date());
        sysUsers2.setIndate(new Date());
        sysUsers2.setType(1);
        sysUsers2.setUsername("gaoyongzhe");
        //加密密码
        String pwd2 = MD5Util.getEncryptedPwd("123456");
        sysUsers2.setPassword(pwd2);
        sysUsersService.saveUser(sysUsers2); // 新增用户

        SysUsers sysUsers3 = new SysUsers();
        sysUsers3.setUserId(UUID.UU32());
        sysUsers3.setIsSupper("0");
        sysUsers3.setCreateUser("12345");
        sysUsers3.setCreateTime(new Date());
        sysUsers3.setIndate(new Date());
        sysUsers3.setType(1);
        sysUsers3.setUsername("wangchuanwei");
        //加密密码
        String pwd3 = MD5Util.getEncryptedPwd("123456");
        sysUsers3.setPassword(pwd3);
        sysUsersService.saveUser(sysUsers3); // 新增用户

        SysUsers sysUsers4 = new SysUsers();
        sysUsers4.setUserId(UUID.UU32());
        sysUsers4.setIsSupper("0");
        sysUsers4.setCreateUser("12345");
        sysUsers4.setCreateTime(new Date());
        sysUsers4.setIndate(new Date());
        sysUsers4.setType(1);
        sysUsers4.setUsername("wangchuanwei");
        //加密密码
        String pwd4 = MD5Util.getEncryptedPwd("123456");
        sysUsers4.setPassword(pwd4);
        sysUsersService.saveUser(sysUsers4); // 新增用户
    }


    @Test
    public void editUserInfo() {
        SysUserinfo sysUserinfo = new SysUserinfo();
        sysUserinfo.setUserId("12345"); // 用户ID
        sysUserinfo.setUserCardNo("超管001"); // 工号
        sysUserinfo.setName("徐"); // 姓名
        sysUserinfo.setPosition("zt001"); //岗位编码
        sysUserinfo.setOrgId("zt1010100"); // 组织编码
        sysUserinfo.setDeptId("dept001"); // 部门编码
        sysUserinfo.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo);

        SysUserinfo sysUserinfo1 = new SysUserinfo();
        sysUserinfo1.setUserId("vlgs2ehji8i1uo8a39nopcfm12"); // 用户ID
        sysUserinfo1.setUserCardNo("员工001"); // 工号
        sysUserinfo1.setName("樊久铭"); // 姓名
        sysUserinfo1.setPosition("zt002"); //岗位编码
        sysUserinfo1.setOrgId("zt1010100"); // 组织编码
        sysUserinfo1.setDeptId("dept001"); // 部门编码
        sysUserinfo1.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo1);

        SysUserinfo sysUserinfo2 = new SysUserinfo();
        sysUserinfo2.setUserId("sj2hm6gb98ic7o510ekv864cap"); // 用户ID
        sysUserinfo2.setUserCardNo("员工002"); // 工号
        sysUserinfo2.setName("曹子建"); // 姓名
        sysUserinfo2.setPosition("zt002"); //岗位编码
        sysUserinfo2.setOrgId("zt1010100"); // 组织编码
        sysUserinfo2.setDeptId("dept001"); // 部门编码
        sysUserinfo2.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo2);

        SysUserinfo sysUserinfo3 = new SysUserinfo();
        sysUserinfo3.setUserId("vaabrkisgkiumoduqbrvj57vlq"); // 用户ID
        sysUserinfo3.setUserCardNo("员工003"); // 工号
        sysUserinfo3.setName("张俊侨"); // 姓名
        sysUserinfo3.setPosition("zt002"); //岗位编码
        sysUserinfo3.setOrgId("zt1010100"); // 组织编码
        sysUserinfo3.setDeptId("dept001"); // 部门编码
        sysUserinfo3.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo3);

        SysUserinfo sysUserinfo4 = new SysUserinfo();
        sysUserinfo4.setUserId("q8tfehi15iif6ro9ok670942c8"); // 用户ID
        sysUserinfo4.setUserCardNo("员工004"); // 工号
        sysUserinfo4.setName("高勇喆"); // 姓名
        sysUserinfo4.setPosition("zt002"); //岗位编码
        sysUserinfo4.setOrgId("zt1010100"); // 组织编码
        sysUserinfo4.setDeptId("dept001"); // 部门编码
        sysUserinfo4.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo4);

        SysUserinfo sysUserinfo5 = new SysUserinfo();
        sysUserinfo5.setUserId("0lgcd9oqikh1vo03dnbluralkc"); // 用户ID
        sysUserinfo5.setUserCardNo("员工005"); // 工号
        sysUserinfo5.setName("王传威"); // 姓名
        sysUserinfo5.setPosition("zt002"); //岗位编码
        sysUserinfo5.setOrgId("zt1010100"); // 组织编码
        sysUserinfo5.setDeptId("dept001"); // 部门编码
        sysUserinfo5.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo5);

        SysUserinfo sysUserinfo6 = new SysUserinfo();
        sysUserinfo6.setUserId("553c0abtneg06ou866i68gae23"); // 用户ID
        sysUserinfo6.setUserCardNo("员工006"); // 工号
        sysUserinfo6.setName("徐家颂"); // 姓名
        sysUserinfo6.setPosition("zt002"); //岗位编码
        sysUserinfo6.setOrgId("zt1010100"); // 组织编码
        sysUserinfo6.setDeptId("dept001"); // 部门编码
        sysUserinfo6.setAssumptionFlag(1); //就职状态
        sysUserinfoService.saveUserInfo(sysUserinfo6);
    }

    @Test
    public void testSaveLog() {
        List<SysRolelog> sysRolelogs = new ArrayList<>();
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setUserId("123");
        sysRolelog.setRoleId("01");
        sysRolelog.setUpdateType("新增");
        sysRolelog.setSysUser("sys");
        sysRolelog.setSysTime(new Date());

        SysRolelog sysRolelog1 = new SysRolelog();
        sysRolelog1.setUserId("123");
        sysRolelog1.setRoleId("02");
        sysRolelog1.setUpdateType("新增");
        sysRolelog1.setSysUser("sys");
        sysRolelog1.setSysTime(new Date());

        sysRolelogs.add(sysRolelog);
        sysRolelogs.add(sysRolelog1);

        sysRolelogMapper.saveLogList(sysRolelogs);
    }
}
