package com.leyou.service;

import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.mapper.CategoryMapper;
import com.leyou.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.cci.connection.ConnectionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2018/12/28-8:46
 */
@Service
public class CategoryService {

	@Autowired
	private  CategoryMapper categoryMapper;

	public List<Category> getListByPid(Long pid) {
		Category category = new Category();
		category.setParent_id(pid);
		List<Category> categoryList = categoryMapper.select(category);
		if (CollectionUtils.isEmpty(categoryList)) {
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
		}
		return categoryList;
	}
	public List<Category> getListByPids(List<Long> ids) {
		List<Category> categories = categoryMapper.selectByIdList(ids);
		if (CollectionUtils.isEmpty(categories)) {
			throw new LyException(ExceptionEnum.CATEGORY_NOT_FOUND);
		}
		return categories;
	}

	public List<String> queryCategoryAllName() {
		List<Category> categories = categoryMapper.selectAll();
		List<String> list = new ArrayList<>();
		for (Category category : categories) {
			list.add(category.getName());
		}
	return list;
	}
}
