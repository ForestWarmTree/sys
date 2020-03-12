package com.zt.sys.authority.utils;

import com.zt.sys.authority.entity.BaseModel;
import org.springframework.stereotype.Component;

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

    public void validate(T obj) {
        if(obj.getStartDate() == null || obj.getStartDate().equals("")) {
            obj.setStartDate(Tool.today());
        }
        if(obj.getEndDate() == null || obj.getEndDate().equals("")) {
            obj.setEndDate("9999-01-01");
        }
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
