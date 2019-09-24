package com.yf.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yf.entity.DomainPage;

import java.util.HashMap;
import java.util.Map;

/**
 * MyBatis分页
 *
 * @author ouyangyufeng
 * @date 2019/9/24
 */
public class MybatisPage {

    /**
     * 通用类型条件分页查询
     *
     * @param t:         Mapper对象
     * @param pageIndex: 页面索引
     * @param pageSize:  页面大小
     * @param map:  查询的条件
     * @param <T>
     * @return 分页对象
     */
    public static  <T extends BaseMapper> DomainPage getDomainPageByParams(T t, long pageIndex, long pageSize, Map map) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.allEq(map);
        wrapper.orderByDesc("created_time");
        IPage page = t.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return new DomainPage(page.getSize(), page.getCurrent(), page.getTotal(),
                page.getRecords().size(), page.getRecords());
    }



    /**
     * 通用无条件分页查询
     *
     * @param t:         Mapper对象
     * @param pageIndex: 页面索引
     * @param pageSize:  页面大小
     * @param <T>
     * @return 分页对象
     */
    public static <T extends BaseMapper> DomainPage getDomainPage(T t, long pageIndex, long pageSize) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.orderByDesc("created_time");
        IPage page = t.selectPage(new Page<>(pageIndex, pageSize), wrapper);
        return new DomainPage(page.getSize(), page.getCurrent(), page.getTotal(),
                page.getRecords().size(), page.getRecords());
    }

}
