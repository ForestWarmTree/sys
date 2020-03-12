package com.zt.sys;

import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.service.ISysRoleinfoService;
import com.zt.sys.authority.service.ISysUserRoleService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/2/25
 * Time: 16:55
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysRoleTest {

    @Resource
    private ISysRoleinfoService roleinfoService;

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Test
    public void saveRole() {
        SysRoleinfo sysRoleinfo = new SysRoleinfo();
        sysRoleinfo.setRoleId("1001001");
        sysRoleinfo.setRoleName("集团业务管理员");
        sysRoleinfo.setOrgId("zt1010100");
        sysRoleinfo.setCreateUser("12345");
        sysRoleinfo.setCreateTime(new Date());
        sysRoleinfo.setStartDate(new Date().toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH,7);
        sysRoleinfo.setEndDate(calendar.getTime().toString());
        roleinfoService.saveRole(sysRoleinfo);

        SysRoleinfo sysRoleinfo1 = new SysRoleinfo();
        sysRoleinfo1.setRoleId("1001002");
        sysRoleinfo1.setRoleName("集团权限管理员");
        sysRoleinfo1.setOrgId("zt1010100");
        sysRoleinfo1.setCreateUser("12345");
        sysRoleinfo1.setCreateTime(new Date());
        sysRoleinfo1.setStartDate(new Date().toString());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar1.add(calendar1.DAY_OF_MONTH,7);
        sysRoleinfo1.setEndDate(calendar1.getTime().toString());
        roleinfoService.saveRole(sysRoleinfo1);

        SysRoleinfo sysRoleinfo2 = new SysRoleinfo();
        sysRoleinfo2.setRoleId("1001003");
        sysRoleinfo2.setRoleName("集团人事管理员");
        sysRoleinfo2.setOrgId("zt1010100");
        sysRoleinfo2.setCreateUser("12345");
        sysRoleinfo2.setCreateTime(new Date());
        sysRoleinfo2.setStartDate(new Date().toString());
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(new Date());
        calendar2.add(calendar2.DAY_OF_MONTH,7);
        sysRoleinfo2.setEndDate(calendar2.getTime().toString());
        roleinfoService.saveRole(sysRoleinfo2);

        SysRoleinfo sysRoleinfo3 = new SysRoleinfo();
        sysRoleinfo3.setRoleId("1001004");
        sysRoleinfo3.setRoleName("公司人事管理员");
        sysRoleinfo3.setOrgId("zt101010001");
        sysRoleinfo3.setCreateUser("12345");
        sysRoleinfo3.setCreateTime(new Date());
        sysRoleinfo3.setStartDate(new Date().toString());
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(new Date());
        calendar3.add(calendar3.DAY_OF_MONTH,7);
        sysRoleinfo3.setEndDate(calendar3.getTime().toString());
        roleinfoService.saveRole(sysRoleinfo3);

        SysRoleinfo sysRoleinfo4 = new SysRoleinfo();
        sysRoleinfo4.setRoleId("1001005");
        sysRoleinfo4.setRoleName("公司后勤管理员");
        sysRoleinfo4.setOrgId("zt101010002");
        sysRoleinfo4.setCreateUser("12345");
        sysRoleinfo4.setCreateTime(new Date());
        sysRoleinfo4.setStartDate(new Date().toString());
        Calendar calendar4 = Calendar.getInstance();
        calendar4.setTime(new Date());
        calendar4.add(calendar4.DAY_OF_MONTH,7);
        sysRoleinfo4.setEndDate(calendar4.getTime().toString());
        roleinfoService.saveRole(sysRoleinfo4);
    }


    @Test
    public void saveUserRole() {
//        SysUserRole sysUserRole = new SysUserRole();
//        sysUserRole.setUserId("12345");
//        List<String> roleIds = new ArrayList<>();
//        roleIds.add("1001001");
//        roleIds.add("1001002");
//        roleIds.add("1001003");
//        roleIds.add("1001004");
//        roleIds.add("1001005");
//        sysUserRole.setRoleIds(roleIds);
//        sysUserRole.setCreateTime(new Date());
//        sysUserRole.setCreateUser("sys");
//        sysUserRole.setStartDate(new Date());
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTime(new Date());
//        calendar.add(Calendar.DAY_OF_MONTH,7);
//        sysUserRole.setEndDate(calendar.getTime());
//        sysUserRoleService.saveUserRole(sysUserRole);


        SysUserRole sysUserRole1 = new SysUserRole();
        sysUserRole1.setUserId("9999999");
        List<String> roleIds1 = new ArrayList<>();
        roleIds1.add("1001001");
        roleIds1.add("1111111");
        roleIds1.add("0000000");
        sysUserRole1.setRoleIds(roleIds1);
        sysUserRole1.setCreateTime(new Date());
        sysUserRole1.setCreateUser("sys");
        sysUserRole1.setStartDate(new Date().toString());
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(new Date());
        calendar1.add(Calendar.DAY_OF_MONTH,7);
        sysUserRole1.setEndDate(calendar1.getTime().toString());
        sysUserRoleService.saveUserRole(sysUserRole1);


    }
}
