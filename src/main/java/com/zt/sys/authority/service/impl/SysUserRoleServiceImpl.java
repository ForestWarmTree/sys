package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUserRole;
import com.zt.sys.authority.entity.SysUsers;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.mapper.SysUserRoleMapper;
import com.zt.sys.authority.mapper.SysUserinfoMapper;
import com.zt.sys.authority.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * <p>
 * 用户基础信息表 服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Resource
    private SysUserRoleMapper sysUserRoleMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 新增用户角色关系-按用户分配
     * @param sysUserRoles
     * @param sessionUser
     */
    @Transactional
    @Override
    public void saveUserRole(List<SysUserRole> sysUserRoles, SysUsers sessionUser) {
        List<SysRolelog> sysRolelogList = new ArrayList<>();//保存日志集合

        //从关系表中，根据用户ID获取 该用户ID下的所有角色ID
        List<SysUserRole> RoleIds = sysUserRoleMapper.selectAllByUserId(sysUserRoles.get(0).getUserId());

        Map<String, String> logmap = new HashMap<>(); //已有关系Map
        for(SysUserRole userrole:RoleIds) {
            logmap.put(userrole.getRoleId(),userrole.getRoleId());
        }

        Map<String, String> saveMap = new HashMap<>(); //本次需要保存的角色Map
        //循环本次需要保存的角色ID集合
        for(SysUserRole sysUserRole:sysUserRoles) {
            saveMap.put(sysUserRole.getRoleId(),sysUserRole.getRoleId());
            /**
             * 用本次需要保存的集合与 已有权限的集合，进行匹配。
             * 找出本次需要保存集合中 与已有权限集合的不同。
             * 进行log记录
             */
            String flag = logmap.get(sysUserRole.getRoleId());
            //如果本次保存的第N个角色ID，不存在已有角色集合中
            //则-->说明是新增权限
            if(flag == null || "".equals(flag)) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(sysUserRole.getUserId());//用户ID
                sysRolelog.setRoleId(sysUserRole.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型描述
                sysRolelog.setSysUser(sessionUser.getCreateUser());//创建人
                sysRolelog.setSysTime(sessionUser.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sessionUser.getName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }
        /**
         * 循环已有权限集合，与本次新增权限集合进行匹配
         */
        for(SysUserRole userrole:RoleIds) {
            String flag = saveMap.get(userrole.getRoleId());
            /**
             * 如果在本次需要保存的权限集合中，未找到已有的权限
             * 则说明当前循环的权限已被删除。
             * 进行log记录
             */
            if(flag == null || "".equals(flag)) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(userrole.getUserId());//用户ID
                sysRolelog.setRoleId(userrole.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型描述
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型描述
                sysRolelog.setSysUser(sessionUser.getCreateUser());//创建人
                sysRolelog.setSysTime(sessionUser.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sessionUser.getName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        if(sysRolelogList!=null && sysRolelogList.size()>0) {
            //保存log记录
            logMapper.saveLogList(sysRolelogList);
        }
        // 根据用户ID 删除对应关系
        sysUserRoleMapper.deleteUserRoleByUserId(sysUserRoles.get(0).getUserId());
        // 保存
        for(SysUserRole sysUserRole:sysUserRoles) {
            if(sysUserRole.getRoleId()!=null && !sysUserRole.getRoleId().equals("")) {
                sysUserRoleMapper.saveOne(sysUserRole);
            }
        }
    }

    /**
     * 用户管理——复制用户角色功能，数据保存
     * @param sysUserRole
     */
    @Override
    public void copyUserRoleSave(SysUserRole sysUserRole) {
        //根据被复制的人员ID,查询出已有的角色集合
        List<SysUserRole> result = sysUserRoleMapper.selectAllByUserId(sysUserRole.getUserId());

        //循环要分配的人员ID
        for(SysUserRole userRole: result) {
            for(String userId:sysUserRole.getUserIds()) {
                //根据人员ID,查询出已有的角色集合
                List<SysUserRole> result1 = sysUserRoleMapper.selectAllByUserId(userId);
                if(result1!=null && result1.size()>0) {
                    for(SysUserRole data: result1) {
                        if(userRole.getRoleId().equals(data.getRoleId())) {
                            continue;
                        } else {
                            //保存日志
                            SysRolelog sysRolelog = new SysRolelog();
                            sysRolelog.setUserId(userId);//用户ID
                            sysRolelog.setRoleId(userRole.getRoleId());//角色ID
                            sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                            sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型描述
                            sysRolelog.setSysUser(sysUserRole.getCreateUser());//创建人
                            sysRolelog.setSysTime(sysUserRole.getCreateTime());//创建时间
                            sysRolelog.setSysUserName(sysUserRole.getCreateUserName());//创建人姓名
                            logMapper.saveLog(sysRolelog);

                            //保存
                            userRole.setUserId(userId);
                            sysUserRoleMapper.saveOne(userRole);
                        }
                    }
                } else {
                    //保存日志
                    SysRolelog sysRolelog = new SysRolelog();
                    sysRolelog.setUserId(userId);//用户ID
                    sysRolelog.setRoleId(userRole.getRoleId());//角色ID
                    sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型描述
                    sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型描述
                    sysRolelog.setSysUser(sysUserRole.getCreateUser());//创建人
                    sysRolelog.setSysTime(sysUserRole.getCreateTime());//创建时间
                    sysRolelog.setSysUserName(sysUserRole.getCreateUserName());//创建人姓名
                    logMapper.saveLog(sysRolelog);

                    //保存
                    userRole.setUserId(userId);
                    sysUserRoleMapper.saveOne(userRole);
                }

            }
        }
    }

    /**
     * 新增用户角色关系-按角色分配
     * @param sysUserRole
     */
    @Transactional
    @Override
    public void saveRoleUser(SysUserRole sysUserRole) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysUserRole",sysUserRole);
        map.put("userIds",sysUserRole.getUserIds());
        List<SysRolelog> sysRolelogList = new ArrayList<>();//log记录集合

        //从关系表中，根据角色ID查询所有用户ID集合。
        List<SysUserRole> userRoleList = sysUserRoleMapper.selectAllByRoleId(sysUserRole.getRoleId());
        Map<String, String> logmap = new HashMap<>(); //该角色已有用户集合
        for(SysUserRole userRole:userRoleList) {
            logmap.put(userRole.getUserId(), userRole.getUserId());
        }

        Map<String, String> savemap = new HashMap<>(); //本次需要保存的用户集合
        for(String userid:sysUserRole.getUserIds()) {
            savemap.put(userid, userid);
        }

        /**
         * 循环这次要保存的用户ID，与已拥有的用户ID进行匹配
         * 如果未匹配上，则说明本次循环的用户是新增用户，
         * 生成log记录
         */
        for(String userid:sysUserRole.getUserIds()) {
            String flag = logmap.get(userid);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(userid);//用户ID
                sysRolelog.setRoleId(sysUserRole.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型业务描述
                sysRolelog.setSysUser(sysUserRole.getCreateUser());//创建人
                sysRolelog.setSysTime(sysUserRole.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysUserRole.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        /**
         * 循环已有用户的ID集合，与本次需要保存的用户ID集合进行匹配
         * 如果未匹配上，则说明本次循环的用户是删除用户，
         * 生成log记录
         */
        for(SysUserRole userRole:userRoleList) {
            String flag = savemap.get(userRole.getUserId());
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(userRole.getUserId());//用户ID
                sysRolelog.setRoleId(sysUserRole.getRoleId());//角色ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserRole);//变更类型业务描述
                sysRolelog.setSysUser(sysUserRole.getCreateUser());//创建人
                sysRolelog.setSysTime(sysUserRole.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysUserRole.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        if(sysRolelogList!=null && sysRolelogList.size()>0) {
            //保存log记录
            logMapper.saveLogList(sysRolelogList);
        }
        // 根据角色ID 删除对应关系
        sysUserRoleMapper.deleteUserRoleByRoleId(sysUserRole.getRoleId());
        if(sysUserRole.getUserIds()!=null && sysUserRole.getUserIds().size()>0) {
            // 保存
            sysUserRoleMapper.saveRoleUser(map);
        }

    }

}
