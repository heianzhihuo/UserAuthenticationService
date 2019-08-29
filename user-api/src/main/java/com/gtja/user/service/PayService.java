package com.gtja.user.service;

import java.math.BigDecimal;

public interface PayService {

    /**
     * 资金转移
     * @param logId         日志ID
     * @param fromId       扣款账户
     * @param toId         目的账户
     * @param money         金额
     * @param serviceType   业务类型
     * @param serviceId     业务ID
     * */
    public boolean rollBack(long logId, long fromId, long toId, BigDecimal money, String serviceType, long serviceId);
}
