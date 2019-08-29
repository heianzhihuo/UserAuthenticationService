package com.gtja.user.mapper;

import com.gtja.user.pojo.AccountFlow;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

@Repository
public interface AccountFlowMapper {

    /**
     * 创建流水
     * @param accountId    账户id
     * @param money         流水金额
     * @param serviceType   服务类型
     * @param serviceId     服务id
     * */
    public int createFlow(long accountId,BigDecimal money, String serviceType, long serviceId);

    /**
     * 删除流水
     * @param accountId    账户id
     * @param money         流水金额
     * @param serviceType   服务类型
     * @param serviceId     服务id
     * */
    public int deleteFlow(long accountId,BigDecimal money, String serviceType, long serviceId);

    /**
     * 查询流水
     * @param accountId    账户id
     * @param start        起始时间
     * @param end          结束时间
     * */
    public List<AccountFlow> queryAccountFlow(long accountId, Date start, Date end);

}
