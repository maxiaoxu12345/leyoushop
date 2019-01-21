package com.leyou.mq;

import com.leyou.client.SpuClient;
import com.leyou.pojo.Spu;
import com.leyou.service.PageService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class PageListener {

    @Autowired
    private PageService pageService;


    /**
     * 处理insert和update的消息
     *
     * @param id
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
                        value = @Queue(value = "leyou.page.listenCreateOrUpdate.queue", durable = "true"),
                       exchange = @Exchange(
                                       value = "leyou.item.exchange",
                                        ignoreDeclarationExceptions = "true",
                                       type = ExchangeTypes.TOPIC),
            key = {"item.insert", "item.update"}))
    public void listenCreateOrUpdate(Long id) throws Exception {
        if (id == null) {
            return;
        }

		pageService.creatHtml(id);

	}

    /**
     * 处理delete的消息
     *
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "leyou.page.delete.queue", durable = "true"),
            exchange = @Exchange(
                    value = "leyou.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = "item.delete"))
    public void listenDelete(Long id) {
        if (id == null) {
            return;
        }
		File file=new File("C:\\nginx-1.8.0\\nginx-1.8.0\\html\\item",id+".html");
		if (file.exists()){
			file.delete();
		}
    }
}