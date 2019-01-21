package com.leyou.mapper;

import com.leyou.pojo.Brand;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @date 2019/1/4-10:46
 */
public interface BrandMapper extends Mapper<Brand>,IdListMapper<Brand,Long> {


	@Insert("insert into tb_category_brand values(#{cid},#{bid})")
	int addInTbCategoryBrand(@Param("cid") Long id1, @Param("bid") Long id2);
	@Select("SELECT b.id,b.name FROM tb_brand b LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id\n" +
			" WHERE cb.category_id =#{cid} ")
	List<Brand> queryBrandListByCid(Long cid);
}
