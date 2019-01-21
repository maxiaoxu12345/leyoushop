package com.leyou.common.baseMapper;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;
import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;
/**
 * @date 2019/1/9-8:53
 */
@RegisterMapper
public interface BaseMapper<T>
		extends IdListMapper<T,Long>,Mapper<T>, InsertListMapper<T>{}
