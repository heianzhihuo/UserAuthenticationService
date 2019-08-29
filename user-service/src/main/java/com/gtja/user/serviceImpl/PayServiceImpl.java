package com.gtja.user.serviceImpl;

import com.gtja.user.mapper.AccountFlowMapper;
import com.gtja.user.mapper.AccountMapper;
import com.gtja.user.service.PayService;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.math.BigDecimal;

@Component
public class PayServiceImpl implements PayService {

    private static final Logger logger = LoggerFactory.getLogger(PayServiceImpl.class);

    @Autowired
    AccountFlowMapper accountFlowMapper;

    @Autowired
    AccountMapper accountMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollBack(long logId, long fromId, long toId, BigDecimal money, String serviceType, long serviceId) {
        long start = System.currentTimeMillis();

        int flag1, flag2, flag3, flag4;
        boolean ret = true;
        if(money.compareTo(BigDecimal.ZERO)==0){
            //转移资金为0，需要生成流水，但不需要对余额操作
            flag1 = 1;
            flag2 = accountFlowMapper.deleteFlow(fromId,money,serviceType,serviceId);
            flag3 = 1;
            flag4 = accountFlowMapper.deleteFlow(toId,money,serviceType,serviceId);
        }else{
            //转移资金不为0
            flag1 = accountMapper.moveIn(fromId,money);
            flag2 = accountFlowMapper.deleteFlow(fromId,money.negate(),serviceType,serviceId);
            flag3 = accountMapper.moveOut(toId,money);
            flag4 = accountFlowMapper.deleteFlow(toId,money,serviceType,serviceId);
        }
        if(flag1==1 && flag2==1 && flag3==1 && flag4==1){
            ret = true;
        }else{
            ret = false;
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//支付失败回滚
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} accountTransfer(fromId:{}, toId:{}, money{}, serviceType:{}, serviceId:{}) return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,fromId,toId,money,serviceType,serviceId,ret,(end-start), RpcContext.getContext().getRemoteAddress());

        return ret;
    }
}
