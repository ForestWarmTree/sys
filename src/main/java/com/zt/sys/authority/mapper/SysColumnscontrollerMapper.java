package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.DataCommonModel;
import com.zt.sys.authority.entity.SysColumnsModel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zt.sys.authority.entity.SysRoleinfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 数据权限控制表 Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2020-02-14
 */
@Mapper
public interface SysColumnscontrollerMapper extends BaseMapper<SysColumnsModel> {
    /**
     * 保存
     * @param sysColumnscontroller
     */
    void saveColumn(SysColumnsModel sysColumnscontroller);

    void deleteColumn(DataCommonModel dataCommonModel);

    /**
     * 根据角色编码与资源编码查询对应的字段权限信息
     * @param map
     * @return
     */
    List<SysColumnsModel> selectByRoleIdAndResourceId(Map<String, String> map);
}
