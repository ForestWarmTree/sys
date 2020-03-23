package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.entity.SysUsersgroup;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.mapper.SysUsersgroupMapper;
import com.zt.sys.authority.service.ISysUsersgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.alibaba.druid.sql.ast.SQLPartitionValue.Operator.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysUsersgroupServiceImpl extends ServiceImpl<SysUsersgroupMapper, SysUsersgroup> implements ISysUsersgroupService {

    @Resource
    private SysUsersgroupMapper sysUsersgroupMapper;

    @Resource
    private SysRolelogMapper logMapper;

    /**
     * 新增用户组与用户对应关系
     * @param sysUsersgroup
     */
    @Transactional
    @Override
    public void saveUsersGroup(SysUsersgroup sysUsersgroup) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysUsersgroup",sysUsersgroup);
        map.put("userIds",sysUsersgroup.getUserIds());
        List<SysRolelog> sysRolelogList = new ArrayList<>();//log记录集合
        //根据组ID从用户组与用户关系表中查询已有用户ID
        List<String> logUserIdList = sysUsersgroupMapper.selectUserIdsByGroupId(sysUsersgroup.getGroupId());
        Map<String, String> logMap = new HashMap<>();//组中已有用户map
        for(String loguserid:logUserIdList) {
            logMap.put(loguserid, loguserid);
        }

        Map<String, String> saveMap = new HashMap<>();//本次需要保存的用户map
        for(String saveUserId:sysUsersgroup.getUserIds()) {
            saveMap.put(saveUserId, saveUserId);
        }

        /**
         * 循环已有的用户集合，与本次需要保存的用户进行匹配，
         * 如果未匹配到，说明本次循环的这个用户已被删除。
         * 进行log记录-->delete
         */
        for(String userid: logUserIdList) {
            String flag = saveMap.get(userid);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(userid);//用户ID
                sysRolelog.setRoleId(sysUsersgroup.getGroupId());//组ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserGroup);//变更类型业务描述
                sysRolelog.setSysUser(sysUsersgroup.getCreateUser());//创建人
                sysRolelog.setSysTime(sysUsersgroup.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysUsersgroup.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        /**
         * 循环本次需要保存的用户集合，与已有的用户集合进行匹配
         * 如果未匹配到，说明本次循环的这个用户是新增用户。
         * 进行log记录-->insert
         */
        for(String userid: sysUsersgroup.getUserIds()) {
            String flag = logMap.get(userid);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(userid);//用户ID
                sysRolelog.setRoleId(sysUsersgroup.getGroupId());//组ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveUserGroup);//变更类型业务描述
                sysRolelog.setSysUser(sysUsersgroup.getCreateUser());//创建人
                sysRolelog.setSysTime(sysUsersgroup.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysUsersgroup.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }
        if(sysRolelogList!=null && sysRolelogList.size()>0) {
            //保存log记录
            logMapper.saveLogList(sysRolelogList);
        }
        //根据组ID删除对应关系
        sysUsersgroupMapper.delete(sysUsersgroup);
        if (sysUsersgroup.getUserIds() !=null && sysUsersgroup.getUserIds().size()>0) {
            //保存
            sysUsersgroupMapper.saveUsersGroup(map);
        }
    }
}
