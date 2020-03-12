package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysResourceinfo;
import com.zt.sys.authority.entity.SysRoleinfo;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.mapper.SysResourceinfoMapper;
import com.zt.sys.authority.mapper.SysRoleinfoMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.mapper.SysUsersMapper;
import com.zt.sys.authority.service.ISysUsersService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysUsersServiceImpl extends ServiceImpl<SysUsersMapper, SysUsers> implements ISysUsersService {

    @Resource
    private SysUsersMapper sysUsersMapper;

    @Resource
    private SysRoleinfoMapper sysRoleinfoMapper;

    @Resource
    private SysResourceinfoMapper resourceinfoMapper;

    @Resource
    private SysRolelogMapper sysRolelogMapper;

    /**
     * 根据用户名查询密码
     * @param userName
     * @return
     */
    @Override
    public SysUsers selectPassWordByUserName(String userName) {
        return sysUsersMapper.selectPassWordByUserName(userName);
    }

    /**
     * 验证用户名是否重复
     * @param userName
     * @return
     */
    @Override
    public int validataUserName(String userName) {
        return sysUsersMapper.validataUserName(userName);
    }

    /**
     * 新增用户
     * @param sysUsers
     */
    @Transactional
    @Override
    public void saveUser(SysUsers sysUsers) {
        SysRolelog sysRolelog = new SysRolelog();
        sysRolelog.setUserId(sysUsers.getUserId());//被新增人的ID
        sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
        sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUser);//变更类型业务描述
        sysRolelog.setSysUser(sysUsers.getCreateUser());//创建人
        sysRolelog.setSysTime(new Date());//创建时间
        sysRolelog.setSysUserName(sysUsers.getName());//创建人姓名
        //保存日志
        sysRolelogMapper.saveLog(sysRolelog);
        //保存用户
        sysUsersMapper.saveUser(sysUsers);
    }

    /**
     * 根据组织编码查询用户
     * @param orgId
     * @return
     */
    @Override
    public List<SysUsers> selectUserByOrgId(String orgId) {
        return sysUsersMapper.selectUserByOrgId(orgId);
    }

    /**
     * 根据部门编码查询用户
     * @param deptId
     * @return
     */
    @Override
    public List<SysUsers> selectUserByDeptId(String deptId) {
        return sysUsersMapper.selectUserByDeptId(deptId);
    }

    /**
     * 根据角色编码查询用户
     * @param roleId
     * @return
     */
    @Override
    public List<SysUsers> selectUserByRoleId(String roleId) {
        return sysUsersMapper.selectUserByRoleId(roleId);
    }

    /**
     * 根据资源编码查询用户
     * @param resourceId
     * @return
     */
    @Override
    public List<SysUsers> selectUserByResourceId(String resourceId) {
        return sysUsersMapper.selectUserByResourceId(resourceId);
    }

    /**
     * 根据用户ID获取用户权限
     * @param sysUsers
     * @return
     */
    @Override
    public SysUsers selectUsersResource(SysUsers sysUsers) {
        //获取用户组织、部门基础信息
        SysUsers users = sysUsersMapper.selectUsersResource(sysUsers);
        users.setName(users.getSysUserinfo().getName());
//        Map<String, Object> map = new HashMap<>();
//        map.put("userId",users.getUserId());
//        map.put("orgId",users.getSysUserinfo().getOrgId());
//        // 获取用户可用角色基础信息
//        List<SysRoleinfo> roleinfoList = sysRoleinfoMapper.selectRoleByUserId(map);
        //users.setRole(roleinfoList);
        Map<String, Object> role = new HashMap<String, Object>();
        role.put("id", "user");
        role.put("name", "用户");
        role.put("describe", "拥有所有权限");
        role.put("status", "1");
        role.put("creatorId", "system");
        role.put("createTime", "");
        role.put("deleted", "0");

        //获取用户可用资源菜单
        List<SysResourceinfo> menus = resourceinfoMapper.selectResourceByUserId(users);
        //users.setPermissions(menus);

        //循环用户角色，同步循环用户资源，将该角色下的对应资源，放入角色中的Permissions集合里
//        for(SysRoleinfo role:roleinfoList) {
//            List<SysResourceinfo> permissions = new ArrayList<>();
//            for(SysResourceinfo resourceinfo:menus) {
//                if(resourceinfo.getRoleId().equals(role.getRoleId())) {
//                    permissions.add(resourceinfo);
//                }
//            }
//            role.setPermissions(permissions);
//        }

        List<String> menuIds = new ArrayList<>();
        for(SysResourceinfo resourceinfo:menus) {
            menuIds.add(resourceinfo.getResourceId());
        }

        // 根据菜单ID查询所有按钮
        List<SysResourceinfo> btnList = resourceinfoMapper.selectBtn(menuIds);

        /**
         * 循环菜单与按钮，将当前循环的菜单下的按钮找出来。
         * 放进actions集合中
         */
        for(SysResourceinfo menu:menus) {
            List<SysResourceinfo> actions = new ArrayList<>();
            for(SysResourceinfo btn:btnList) {
                if(btn.getParentId().equals(menu.getResourceId())) {
                    actions.add(btn);
                }
            }
            menu.setActions(actions);
        }

        role.put("permissions", menus);
        users.setRole(role);

        return users;
    }


}
