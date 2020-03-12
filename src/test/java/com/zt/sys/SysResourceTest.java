package com.zt.sys;

import com.zt.sys.authority.entity.SysResourceinfo;
import com.zt.sys.authority.entity.SysRoleResource;
import com.zt.sys.authority.service.ISysResourceinfoService;
import com.zt.sys.authority.service.ISysRoleResourceService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/2/25
 * Time: 17:30
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class SysResourceTest {
    @Resource
    private ISysResourceinfoService resourceinfoService;

    @Resource
    private ISysRoleResourceService sysRoleResourceService;

    @Test
    public void saveResource() {
        SysResourceinfo sysResourceinfo = new SysResourceinfo();
        sysResourceinfo.setPermissionId("1");
        sysResourceinfo.setPermissionName("列表查询");
        sysResourceinfo.setResourceType("menu");
        sysResourceinfo.setLevel("1");
        sysResourceinfo.setResourceUrl("/list");
        resourceinfoService.saveResource(sysResourceinfo);

        SysResourceinfo sysResourceinfo1 = new SysResourceinfo();
        sysResourceinfo1.setPermissionId("2");
        sysResourceinfo1.setPermissionName("用户查询");
        sysResourceinfo1.setResourceType("menu");
        sysResourceinfo1.setLevel("2");
        sysResourceinfo1.setResourceUrl("/list");
        resourceinfoService.saveResource(sysResourceinfo1);

        SysResourceinfo sysResourceinfo2 = new SysResourceinfo();
        sysResourceinfo2.setPermissionId("001");
        sysResourceinfo2.setPermissionName("新增");
        sysResourceinfo2.setResourceType("btn");
        sysResourceinfo2.setLevel("1");
        sysResourceinfo2.setResourceUrl("/add");
        sysResourceinfo2.setParentId("2");
        sysResourceinfo2.setPermissionId("add");
        resourceinfoService.saveResource(sysResourceinfo2);

        SysResourceinfo sysResourceinfo3 = new SysResourceinfo();
        sysResourceinfo3.setPermissionId("002");
        sysResourceinfo3.setPermissionName("删除");
        sysResourceinfo3.setResourceType("btn");
        sysResourceinfo3.setLevel("2");
        sysResourceinfo3.setResourceUrl("/delete");
        sysResourceinfo3.setParentId("2");
        sysResourceinfo3.setPermissionId("delete");
        resourceinfoService.saveResource(sysResourceinfo3);
    }


    @Test
    public void saveRoleResource() {
        SysRoleResource sysRoleResource = new SysRoleResource();
        sysRoleResource.setRoleId("1001001");
        List<String> resourceIds = new ArrayList<>();
        resourceIds.add("1");
        resourceIds.add("2");
        resourceIds.add("001");
        resourceIds.add("002");
        sysRoleResource.setResourceIds(resourceIds);
        sysRoleResource.setCreateUser("12345");
        sysRoleResource.setCreateTime(new Date());
        sysRoleResourceService.saveRoleResource(sysRoleResource);


        SysRoleResource sysRoleResource1 = new SysRoleResource();
        sysRoleResource1.setRoleId("1001002");
        List<String> resourceIds1 = new ArrayList<>();
        resourceIds1.add("2");
        resourceIds1.add("001");
        resourceIds1.add("002");
        sysRoleResource1.setResourceIds(resourceIds1);
        sysRoleResource1.setCreateUser("12345");
        sysRoleResource1.setCreateTime(new Date());
        sysRoleResourceService.saveRoleResource(sysRoleResource1);


        SysRoleResource sysRoleResource2 = new SysRoleResource();
        sysRoleResource2.setRoleId("1001003");
        List<String> resourceIds2 = new ArrayList<>();
        resourceIds2.add("1");
        sysRoleResource2.setResourceIds(resourceIds2);
        sysRoleResource2.setCreateUser("12345");
        sysRoleResource2.setCreateTime(new Date());
        sysRoleResourceService.saveRoleResource(sysRoleResource2);
    }
}
