package com.zt.sys.authority.mapper;

import com.zt.sys.authority.entity.SysDeptPosition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

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
public interface SysDeptPositionMapper extends BaseMapper<SysDeptPosition> {

    /**
     * 根据部门编码 删除部门岗位对应关系
     * @param deptId
     */
    void deleteDeptPosition(String deptId);

    /**
     * 新增部门编码 删除部门岗位对应关系
     * @param map
     */
    void saveDeptPosition(Map<String,Object> map);

    /**
     * 根据部门编码查询岗位编码
     * @param deptId
     */
    List<String> selectPositionIdByDeptId(String deptId);
}
