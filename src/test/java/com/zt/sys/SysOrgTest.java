package com.zt.sys;

import com.zt.sys.authority.entity.SysDeptinfo;
import com.zt.sys.authority.entity.SysOrginfo;
import com.zt.sys.authority.service.ISysDeptinfoService;
import com.zt.sys.authority.service.ISysOrginfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/2/25
 * Time: 16:15
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysOrgTest {
    @Resource
    private ISysOrginfoService orginfoService;

    @Resource
    private ISysDeptinfoService sysDeptinfoService;

    @Test
    public void saveOrg() {
        SysOrginfo sysOrginfo = new SysOrginfo();
        sysOrginfo.setOrgId("asdasda");
        sysOrginfo.setOrgName("测试公司");
        sysOrginfo.setOrgType("集团");
        sysOrginfo.setCreateTime(new Date());//创建时间
        sysOrginfo.setCreateUser("12345");//创建人
        orginfoService.saveOrgInfo(sysOrginfo);

//        SysOrginfo sysOrginfo1 = new SysOrginfo();
//        sysOrginfo1.setOrgId("zt101010001");
//        sysOrginfo.setParentId("zt1010100");
//        sysOrginfo1.setOrgName("青岛中唐科技子公司1");
//        sysOrginfo1.setOrgType("公司");
//        sysOrginfo1.setCreateTime(new Date());//创建时间
//        sysOrginfo1.setCreateUser("12345");//创建人
//        orginfoService.saveOrgInfo(sysOrginfo1);
//
//        SysOrginfo sysOrginfo2 = new SysOrginfo();
//        sysOrginfo2.setOrgId("zt101010002");
//        sysOrginfo.setParentId("zt1010100");
//        sysOrginfo2.setOrgName("青岛中唐科技子公司2");
//        sysOrginfo2.setOrgType("公司");
//        sysOrginfo2.setCreateTime(new Date());//创建时间
//        sysOrginfo2.setCreateUser("12345");//创建人
//        orginfoService.saveOrgInfo(sysOrginfo2);
    }


    @Test
    public void updateOrg() {
        SysOrginfo sysOrginfo = new SysOrginfo();
        sysOrginfo.setOrgId("asdasda");
        sysOrginfo.setOrgName("修改测试公司");
        sysOrginfo.setOrgType("集团");
        sysOrginfo.setUpdateTime(new Date());//修改时间
        sysOrginfo.setUpdateUser("987654");//修改人
        orginfoService.editOrgInfo(sysOrginfo);
    }



    @Test
    public void saveDept() {
        SysDeptinfo deptinfo = new SysDeptinfo();
        deptinfo.setDeptId("dept001");
        deptinfo.setDeptName("开发部");
        deptinfo.setCreateUser("12345");
        deptinfo.setCreateTime(new Date());
        deptinfo.setOrgId("zt1010100");
        sysDeptinfoService.saveDeptInfo(deptinfo);


        SysDeptinfo deptinfo1 = new SysDeptinfo();
        deptinfo1.setDeptId("dept002");
        deptinfo1.setDeptName("运营部");
        deptinfo1.setCreateUser("12345");
        deptinfo1.setCreateTime(new Date());
        deptinfo1.setOrgId("zt1010100");
        sysDeptinfoService.saveDeptInfo(deptinfo1);


        SysDeptinfo deptinfo2 = new SysDeptinfo();
        deptinfo2.setDeptId("dept001003");
        deptinfo2.setDeptName("测试部");
        deptinfo2.setCreateUser("12345");
        deptinfo2.setCreateTime(new Date());
        deptinfo2.setOrgId("zt101010001");
        sysDeptinfoService.saveDeptInfo(deptinfo2);

        SysDeptinfo deptinfo3 = new SysDeptinfo();
        deptinfo3.setDeptId("dept001004");
        deptinfo3.setDeptName("后勤部");
        deptinfo3.setCreateUser("12345");
        deptinfo3.setCreateTime(new Date());
        deptinfo3.setOrgId("zt101010002");
        sysDeptinfoService.saveDeptInfo(deptinfo3);
    }
}
