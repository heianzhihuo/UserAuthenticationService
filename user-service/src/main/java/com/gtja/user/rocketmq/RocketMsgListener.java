package com.gtja.user.rocketmq;

import com.gtja.user.service.PayService;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Component
public class RocketMsgListener implements MessageListenerConcurrently {

    private static final Logger logger = LoggerFactory.getLogger(RocketMsgListener.class);

    @Resource
    PayService payService;

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
        if (CollectionUtils.isEmpty(msgs)){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }

        MessageExt messageExt = msgs.get(0);

        logger.info("[GTJA]: receive message: {}",new String(messageExt.getBody()));

        int reConsume = messageExt.getReconsumeTimes();
        // 消息已经重试了3次，如果不需要再次消费，则返回成功
        if(reConsume == 1){
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        try{
            String param = new String(messageExt.getBody());
            String[] params = param.split(",");
            Long fromId = Long.valueOf(params[0]);
            Long toId = Long.valueOf(params[1]);
            BigDecimal money = new BigDecimal(params[2]);
            String serviceType = params[3];
            Long serviceId = Long.valueOf(params[4]);
            boolean ret = payService.rollBack(messageExt.getStoreTimestamp(),fromId,toId,money,serviceType,serviceId);
            if(ret){
                // 消息消费成功
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }else{
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
    }
}
