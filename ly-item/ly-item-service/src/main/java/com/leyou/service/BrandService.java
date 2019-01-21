package com.leyou.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.mapper.BrandMapper;
import com.leyou.mapper.CategoryMapper;
import com.leyou.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @date 2019/1/4-10:46
 */
@Service
public class BrandService {
	@Autowired
	private BrandMapper brandMapper;

	public PageResult<Brand> getCurrBrand(Boolean desc, String key, Integer page, Integer rows, String sortBy) {
		//分页
		PageHelper.startPage(page, rows);

		//创造条件
		Example example =new Example(Brand.class);

		//过滤关键字
		if (StringUtils.isNotBlank(key)) {
			example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key.toUpperCase());
		}
		//排序
		example.setOrderByClause(sortBy+" "+(desc?"asc":"desc"));
		List<Brand> brands = brandMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(brands)){
			throw new LyException(ExceptionEnum.BRANDS_NOT_FOUND);
		}
		//解析封装结果
		PageInfo<Brand> info=new PageInfo<>(brands);
		return new PageResult<Brand>(info.getTotal(),brands);
	}

	public void addBrand(Brand brand, List<Long> cids) {
		int insert=brandMapper.insert(brand);
		if (1!=insert){
			throw new LyException(ExceptionEnum.CREATED_DEFAULT);
		}
		for (Long cid : cids) {
			int i = brandMapper.addInTbCategoryBrand(cid,brand.getId());
			if (1!=i){
				throw new LyException(ExceptionEnum.CREATED_DEFAULT);
			}
		}
	}
	public Brand queryById(Long id){
		Brand brand = brandMapper.selectByPrimaryKey(id);
		if (brand==null){
			throw new LyException(ExceptionEnum.BRAND_NOT_FOUND);
		}
		return brand;
	}

	public List<Brand> queryBrandListByCid(Long cid) {
		return 	brandMapper.queryBrandListByCid(cid);
	}

	public List<Brand> queryBrandByIds(List<Long> ids) {
		return brandMapper.selectByIdList(ids);
	}
}
