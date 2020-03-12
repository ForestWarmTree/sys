package com.zt.sys.authority.service.impl;

import com.zt.sys.authority.entity.SysDeptPosition;
import com.zt.sys.authority.entity.SysRolelog;
import com.zt.sys.authority.mapper.SysDeptPositionMapper;
import com.zt.sys.authority.mapper.SysRolelogMapper;
import com.zt.sys.authority.service.ISysDeptPositionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zt.sys.authority.utils.ParamUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
@Service
public class SysDeptPositionServiceImpl extends ServiceImpl<SysDeptPositionMapper, SysDeptPosition> implements ISysDeptPositionService {

    @Resource
    private SysDeptPositionMapper deptPositionMapper;

    @Resource
    private SysRolelogMapper logMapper;
    /**
     * 新增部门岗位对应关系
     * @param sysDeptPosition
     */
    @Transactional
    @Override
    public void saveDeptPosition(SysDeptPosition sysDeptPosition) {
        Map<String, Object> map = new HashMap<>();
        map.put("sysDeptPosition",sysDeptPosition);
        map.put("positionIds",sysDeptPosition.getPositontIds());
        List<SysRolelog> sysRolelogList = new ArrayList<>();//log记录集合

        //根据部门编码从部门岗位关系表总查询岗位编码
        List<String> logPositionIds = deptPositionMapper.selectPositionIdByDeptId(sysDeptPosition.getDeptId());
        Map<String, String> logMap = new HashMap<>();//已有关系map
        for(String logpositionId:logPositionIds) {
            logMap.put(logpositionId, logpositionId);
        }

        Map<String, String> saveMap = new HashMap<>();//本次需要保存的map
        for(String positionId:sysDeptPosition.getPositontIds()) {
            saveMap.put(positionId,positionId);
        }

        /**
         * 循环本次需要保存的岗位编码，并与已有关系的岗位编码进行匹配
         * 如果未匹配到，说明本次循环的岗位是新增的岗位
         * 进行log记录-->insert
         */
        for(String positionId:sysDeptPosition.getPositontIds()) {
            String flag = logMap.get(positionId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(sysDeptPosition.getDeptId());//部门ID
                sysRolelog.setRoleId(positionId);//岗位ID
                sysRolelog.setUpdateType(ParamUtil.INSERT);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveDeptPosition);//变更类型业务描述
                sysRolelog.setSysUser(sysDeptPosition.getCreateUser());//创建人
                sysRolelog.setSysTime(sysDeptPosition.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysDeptPosition.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        /**
         * 循环已有岗位关系的集合，并与本次需要保存的岗位进行匹配
         * 如果未匹配上，说明本次循环的岗位已被删除。
         * 进行log记录-->delete
         */
        for(String positionId: logPositionIds) {
            String flag = logMap.get(positionId);
            if(flag == null || flag.equals("")) {
                SysRolelog sysRolelog = new SysRolelog();
                sysRolelog.setUserId(sysDeptPosition.getDeptId());//部门ID
                sysRolelog.setRoleId(positionId);//岗位ID
                sysRolelog.setUpdateType(ParamUtil.DELETE);//变更类型
                sysRolelog.setUpdateTypeTips(ParamUtil.LogSaveDeptPosition);//变更类型业务描述
                sysRolelog.setSysUser(sysDeptPosition.getCreateUser());//创建人
                sysRolelog.setSysTime(sysDeptPosition.getCreateTime());//创建时间
                sysRolelog.setSysUserName(sysDeptPosition.getCreateUserName());//创建人姓名
                sysRolelogList.add(sysRolelog);
            }
        }

        //保存log记录
        logMapper.saveLogList(sysRolelogList);
        //删除部门岗位对应关系
        deptPositionMapper.deleteDeptPosition(sysDeptPosition.getDeptId());
        //保存
        deptPositionMapper.saveDeptPosition(map);
    }
}
