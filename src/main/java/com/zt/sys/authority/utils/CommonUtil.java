package com.zt.sys.authority.utils;

import com.zt.sys.authority.entity.*;
import com.zt.sys.authority.mapper.SysResourceinfoMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ  IDEA
 * User: 王传威
 * Date: 2020/3/3
 * Time: 15:48
 */
@Component
public class CommonUtil<T extends BaseModel> {

    @Resource
    private SysResourceinfoMapper sysResourceinfoMapper;

    //资源树集合
    List<SysResourceinfo> treeMenus = null;

    public void validate(T obj) {
        if(obj.getStartDate() == null || obj.getStartDate().equals("")) {
            obj.setStartDate(Tool.today());
        }
        if(obj.getEndDate() == null || obj.getEndDate().equals("")) {
            obj.setEndDate("9999-01-01");
        }
    }


    /**
     * 创建资源树节点对象集合
     * @param sysResourceinfo
     * @return
     */
    public List<TreeModel> editInitTree(SysResourceinfo sysResourceinfo) {
        //根据用户ID获取当前用户可操作的权限数据
        treeMenus = sysResourceinfoMapper.selectResourceList(sysResourceinfo);

        List<TreeModel> list = new ArrayList<TreeModel>();
        if (treeMenus!=null && treeMenus.size()>0){
            for(SysResourceinfo resource : treeMenus){
                if(resource.getLevel().equals("1")) {
                    List<TreeModel> children = getChildren(resource.getResourceId());
                    if (children == null || children.size() == 0) {
                        TreeModel treeModel = new TreeModel();
                        treeModel.setKey(resource.getResourceId());
                        treeModel.setTitle(resource.getPermissionName());
                        treeModel.setLevel(resource.getLevel());
                        list.add(treeModel);
                    } else {
                        TreeModel treeModel = new TreeModel();
                        treeModel.setKey(resource.getResourceId());
                        treeModel.setTitle(resource.getPermissionName());
                        treeModel.setLevel(resource.getLevel());
                        treeModel.setChildren(children);
                        list.add(treeModel);
                    }
                }
            }
        }
        return list;
    }

    /**
     * 获取子节点信息
     * @param pid
     * @return
     */
    private List<TreeModel> getChildren(String pid){
        List<TreeModel> list = new ArrayList<>();
        if (treeMenus!=null){
            for(SysResourceinfo resource : treeMenus){
                if(pid!=null && pid.equals(resource.getParentId())){
                    List<TreeModel> children = getChildren(resource.getResourceId());
                    if(children == null || children.size() == 0){
                        TreeModel tree = new TreeModel();
                        tree.setKey(resource.getResourceId());
                        tree.setTitle(resource.getPermissionName());
                        tree.setLevel(resource.getLevel());
                        list.add(tree);
                    }else {
                        TreeModel tree = new TreeModel();
                        tree.setKey(resource.getResourceId());
                        tree.setTitle(resource.getPermissionName());
                        tree.setChildren(children);
                        tree.setLevel(resource.getLevel());
                        list.add(tree);
                    }
                }
            }
        }
        return list;
    }



    /**
     * 转换前台传入的时间集合
     * @param times
     * @return
     */
    public void commonSetTime(List<String> times, T obj) {
        // 查询条件时间段
        if(times!=null){
            int i = 0;
            for(String time : times){
                if(i == 0){
                    obj.setStartDate(time);
                }else {
                    obj.setEndDate(time);
                }
                i++;
            }
        }
    }

    /**
     * 有效期转换成list
     * @param obj
     */
    public void commonSetCreatesTime(T obj) {
        List<String> times = new ArrayList<>();
        times.add(obj.getStartDate().toString());
        times.add(obj.getEndDate().toString());
        obj.setCreateTimes(times);
    }

}
