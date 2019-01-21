package com.leyou.respority;

import com.leyou.pojo.Goods;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * @date 2019/1/11-9:47
 */
public interface GoodsRepository extends ElasticsearchRepository<Goods,Long> {

}
