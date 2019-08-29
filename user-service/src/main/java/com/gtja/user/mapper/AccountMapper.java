package com.gtja.user.mapper;

import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountMapper {

    /**
     * 转入
     * @param id    账户id
     * @param money 转入金额
     * @return      返回成功或失败
     * */
    public int moveIn(long id, BigDecimal money);

    /**
     * 转出
     * @param id    账户id
     * @param money 转出金额
     * @return      返回成功或失败
     * */
    public int moveOut(long id,BigDecimal money);

    /**
     * 查询账户余额
     * @param id    账户id
     * @return      账户余额
     * */
    public BigDecimal queryAccountBalance(long id);

    //其它
}
