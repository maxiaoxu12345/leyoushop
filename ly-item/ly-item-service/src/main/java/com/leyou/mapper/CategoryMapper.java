package com.leyou.mapper;

import com.leyou.pojo.Category;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @date 2018/12/28-8:47
 * IdListMapper<T,PK>可以根据多个id的集合查询
 */
public interface CategoryMapper extends Mapper<Category>,IdListMapper<Category,Long> {
}
