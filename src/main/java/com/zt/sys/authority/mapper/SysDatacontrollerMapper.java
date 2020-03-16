package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysDataModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.sys.authority.entity.SysRoleinfo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-17
 */
public interface SysDatacontrollerMapper extends BaseMapper<SysDataModel> {
    /**
     * 保存
     * @param sysDatacontroller
     */
    void saveData(SysDataModel sysDatacontroller);

    /**
     * 根据角色编码与资源编码查询数据权限信息
     * @param map
     * @return
     */
    List<SysDataModel> selectByRoleIdAndResourceId(Map<String, String> map);
}
