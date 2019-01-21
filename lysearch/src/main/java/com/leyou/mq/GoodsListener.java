package com.leyou.mq;

import com.leyou.client.SpuClient;
import com.leyou.pojo.Goods;
import com.leyou.pojo.Spu;
import com.leyou.respority.GoodsRepository;
import com.leyou.service.GoodsService;
import com.leyou.service.SearchService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @Autowired
    private SpuClient spuClient;
    @Autowired
    private GoodsService goodsService;
	@Autowired
	private GoodsRepository goodsRepository ;

    /**
     * 处理insert和update的消息
     *
     * @param id
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
                        value = @Queue(value = "leyou.goods.listenCreateOrUpdate.queue", durable = "true"),
                       exchange = @Exchange(
                                       value = "leyou.item.exchange",
                                        ignoreDeclarationExceptions = "true",
                                       type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}))
    public void listenCreateOrUpdate(Long id) throws Exception {
        if (id == null) {
            return;
        }
        // 创建或更新索引
        //1.查询spu数据
        Spu spu = spuClient.querySpuById(id);
        //2.改成索引库的pojo类
		Goods goods = goodsService.ChangeTo(spu);
			//3.修改索引库
		goodsRepository.save(goods);
	}

    /**
     * 处理delete的消息
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.goods.delete.queue", durable = "true"),
            exchange = @Exchange(
                    value = "leyou.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = "item.delete"))
    public void listenDelete(Long id) {
        if (id == null) {
            return;
        }
       goodsRepository.deleteById(id);
    }
}