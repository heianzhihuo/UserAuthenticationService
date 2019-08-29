package com.gtja.user.service;


import com.gtja.productapi.utils.Result;
import com.gtja.user.pojo.AccountFlow;
import com.gtja.user.pojo.Member;

import java.math.BigDecimal;
import java.sql.Date;

import java.util.List;

public interface UserService {
    public String sayHello(String name);

    /**
     * 登陆
     * @param logId     日志id
     * @param username  用户名
     * @param pwd       密码
     * @param ip        登录ip地址
     * @return          用户登陆token
     * */
    public Result<String> userLogin(long logId,String username,String pwd,String ip);

    /**
     * 查询用户个人信息，同时验证登录
     * @param logId 日志id
     * @param token 用户token
     * @param ip    用户ip
     * @return      用户信息
     * */
    public Result<Member> queryPersonalInfo(long logId,String token,String ip);

    /**
     * 查询用户id
     * @param logId 日志id
     * @param token 用户token
     * @param ip    用户ip
     * @return      用户id
     * */
    public Result<Long> queryUserId(long logId,String token,String ip);

    /**
     * 退出登陆
     * @param logId 日志id
     * @param token 用户token
     * @return      退出登陆结果
     * */
    public Result logout(long logId,String token);

    /**
     * 用户注册
     * @param logId     日志id
     * @param member    注册信息
     * @return          注册结果
     * */
    public Result register(long logId,Member member);

    /**
     * 获取用户等级
     * @param logId     日志id
     * @param token     用户id
     * @param ip        ip
     * @return          用户等级
     * */
    public Result<Integer> queryUserLevel(long logId, String token,String ip);

    /**
     * 资金转移
     * @param logId         日志ID
     * @param fromId       扣款账户
     * @param toId         目的账户
     * @param money         金额
     * @param serviceType   业务类型
     * @param serviceId     业务ID
     * */
    public Result accountTransfer(long logId, long fromId, long toId, BigDecimal money, String serviceType, long serviceId);

    /**
     * 查询账户流水
     * @param logId     日志id
     * @param token     用户token
     * @param ip        ip地址
     * @param start     开始时间
     * @param end       结束时间
     * */
    public Result<List<AccountFlow>> queryPersonalFlow(long logId, String token, String ip, Date start, Date end);

    /**
     * 查询账户余额
     * @param logId 日志id
     * @param token 用户token
     * @param ip    用户ip
     * */
    public Result<BigDecimal> queryAccountBalance(long logId, String token, String ip);
}
