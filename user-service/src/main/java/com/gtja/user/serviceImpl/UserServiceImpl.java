package com.gtja.user.serviceImpl;

import com.gtja.productapi.utils.Result;
import com.gtja.productapi.utils.ResultGenerator;
import com.gtja.user.mapper.AccountFlowMapper;
import com.gtja.user.mapper.AccountMapper;
import com.gtja.user.mapper.MemberMapper;
import com.gtja.user.pojo.AccountFlow;
import com.gtja.user.pojo.Member;
import com.gtja.user.service.UserService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.DigestUtils;
import com.google.gson.Gson;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.gtja.user.constant.CodeMsg.*;

@Service(interfaceClass = UserService.class)
@Component
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    AccountMapper accountMapper;

    @Autowired
    AccountFlowMapper accountFlowMapper;


    @Resource(name = "MemberRedisTemplate")
    RedisTemplate<String,Member> redisTemplate;

    private static final int SESSION_EXPIRE = 600;//token过期时间10分钟
    private static final String SESSION = "SESSION:";//session前缀

    //匹配帐号是否合法(字母开头，允许5-16个字符，允许字母数字下划线)
    private static final String USERNAME_REGEX = "^[a-zA-Z][a-zA-Z0-9_]{0,30}$";
    //匹配密码是否合法(字母数字下划线，6-17个字符)
    private static final String PWD_REGEX = "^[a-zA-Z0-9]{5,17}$";
    //匹配邮箱是否合法
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
    //匹配手机号是否合法
    private static final String PHONE_REGEX = "^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$";

    //json转字符串
    private static final Gson gson = new Gson();

    @Override
    public String sayHello(String name) {
        System.out.println(logger.getName());
        logger.info("Hello "+name+", request from consumer: "+ RpcContext.getContext().getRemoteAddress());
        logger.info("Hello "+name+", response from provider: "+ RpcContext.getContext().getLocalAddress());
        return "您好,"+name+"!";
    }

    @Override
    public Result<String> userLogin(long logId, String username, String pwd, String ip) {
        long start = System.currentTimeMillis();

        Member member = memberMapper.selectByName(username);
        Result ret;
        //用户是否存在，密码是否为空，md5加密
        if(member==null || pwd==null ||
                !DigestUtils.md5DigestAsHex(pwd.getBytes()).equals(member.getPwd())){
            ret = NO_USER_OR_ERROR_PWD;
        } else{
            //生成token
            String token = UUID.randomUUID().toString();

            //设置用户状态
            member.setToken(token);
            member.setIp(ip);

            ret = ResultGenerator.genSuccessResult(token,LOGIIN_SUCESS_MESSAGE);

            redisTemplate.opsForValue().set(SESSION+token,member,SESSION_EXPIRE,TimeUnit.SECONDS);
        }
        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} userLogin(username:{}, pwd:{}, ip:{}). return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,username,pwd,ip,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());

        return ret;
    }

    @Override
    public Result<Member> queryPersonalInfo(long logId, String token, String ip) {
        long start = System.currentTimeMillis();

        Result ret;
        Member member = redisTemplate.opsForValue().get(SESSION+token);

        if(member==null || ip==null || !ip.equals(member.getIp())){
//            System.out.println(member);
            ret = UN_REGISTERED;//用户未登录
        }else{
            redisTemplate.expire(SESSION+token,SESSION_EXPIRE, TimeUnit.SECONDS);//重置过期时间
            member.setPwd(null);
            member.setToken(null);
            ret = ResultGenerator.genSuccessResult(member);//返回用户信息
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} queryPersonalInfo(token:{}, ip:{}). return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ip,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());

        return ret;
    }

    @Override
    public Result<Long> queryUserId(long logId, String token, String ip) {
        long start = System.currentTimeMillis();

        Result ret;
        Member member = redisTemplate.opsForValue().get(SESSION+token);
        if(member==null || ip==null || !ip.equals(member.getIp())){
            ret = UN_REGISTERED;//用户未登录
        }else{
            redisTemplate.expire(SESSION+token,SESSION_EXPIRE, TimeUnit.SECONDS);//重置过期时间
            ret = ResultGenerator.genSuccessResult(member.getId());//返回用户ID
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} queryUserId(token:{}, ip:{}). return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ip,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());

        return ret;

    }

    @Override
    public Result logout(long logId, String token) {
        long start = System.currentTimeMillis();
        redisTemplate.delete(SESSION+token);//删除cookie
        Result ret = ResultGenerator.genSuccessResult(LOGOUT_SUCESS_MESSAGE);//登出成功

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} logout(token:{}). return code:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());

        return ret;
    }

    private boolean check(Member member){
        return !member.getUsername().isEmpty() && !member.getPwd().isEmpty();
    }

    /**
     * 检查用户名合法性
     * */
    private boolean checkUsername(String username){
        return username.matches(USERNAME_REGEX);
    }

    /**
     * 检查密码合法性
     * */
    private boolean checkPwd(String pwd){
        return pwd.matches(PWD_REGEX);
    }

    /**
     * 检查邮箱合法性
     * */
    private boolean checkEmail(String email){
        return email.matches(EMAIL_REGEX);
    }

    /**
     * 检查手机号合法性
     * */
    private boolean checkPhone(String phone){
        return phone.matches(PHONE_REGEX);
    }

    @Override
    public Result register(long logId, Member member) {
        long start = System.currentTimeMillis();

        Result ret;
        if(!check(member)){
            ret = USERNAME_OR_PWD_EMPTY;//用户名和密码不能为空
        }else if(!checkUsername(member.getUsername())) {
            ret = ILLEGAL_USERNAME;//非法用户名
        }else if(!checkPwd(member.getPwd())){
            ret = ILLEGAL_PWD;//非法密码
        }else if(member.getEmail()!=null && !checkEmail(member.getEmail())){
            ret = ILLEGAL_EMAIL;//非法邮箱
        }else if(member.getPhone()!=null && !checkPhone(member.getPhone())){
            ret = ILLEGAL_PHONE;//非法手机号
        }else{
            Member member1 = memberMapper.selectByName(member.getUsername());
            if(member1!=null){
                ret = USERNAME_ALREADY_EXISTS;//用户名已被注册
            }else {
                //md5加密
                String md5pwd = DigestUtils.md5DigestAsHex(member.getPwd().getBytes());
                member.setPwd(md5pwd);
                if(memberMapper.insert(member)!=1){
                    ret = REGISTER_FAILURE;
                }else{
                    //result = REGISTER_SUCESS;//注册成功
                    ret = ResultGenerator.genSuccessResult(REGISTER_SUCESS_MESSAGE);
                }
            }
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} register(member:{}). return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,member,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());
        return ret;
    }

    @Override
    public Result<Integer> queryUserLevel(long logId, String token, String ip) {
        long start = System.currentTimeMillis();

        Result ret;
        Member member = redisTemplate.opsForValue().get(SESSION+token);
        if(member==null || ip==null || !ip.equals(member.getIp())){
            ret = UN_REGISTERED;//未登录
        }else{
            redisTemplate.expire(SESSION+token,SESSION_EXPIRE, TimeUnit.SECONDS);//重置过期时间
            ret = ResultGenerator.genSuccessResult(member.getLevel());//用户等级
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} queryUserLevel(token:{}, ip:{}). return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ip,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());

        return ret;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result accountTransfer(long logId, long fromId, long toId, BigDecimal money, String serviceType, long serviceId) {
        long start = System.currentTimeMillis();

        Result ret;
        int flag1, flag2, flag3, flag4;
        if(money.compareTo(BigDecimal.ZERO)==0){
            //转移资金为0，需要生成流水，但不需要对余额操作
            flag1 = 1;
            flag2 = accountFlowMapper.createFlow(fromId,money,serviceType,serviceId);
            flag3 = 1;
            flag4 = accountFlowMapper.createFlow(toId,money,serviceType,serviceId);
        }else{
            //转移资金不为0
            flag1 = accountMapper.moveOut(fromId,money);
            flag2 = accountFlowMapper.createFlow(fromId,money.negate(),serviceType,serviceId);
            flag3 = accountMapper.moveIn(toId,money);
            flag4 = accountFlowMapper.createFlow(toId,money,serviceType,serviceId);

        }
        if(flag1==1 && flag2==1 && flag3==1 && flag4==1){
            ret = ResultGenerator.genSuccessResult(TRANSACTION_SUCESS_MESSAGE);//支付成功
        }else if(flag1==0 || flag3==0){
            ret = BALANCE_IS_NOT_ENOUGH;//余额不足，扣款失败
        }else if(flag2==0 || flag4==0){
            ret = REPEAT_PAY;//重复支付，扣款失败
        }else{
            ret = TRANSACTION_ERROR;//未知支付失败
        }

        if(ret.getResultCode()!=200){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//支付失败回滚
        }

        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} accountTransfer(fromId:{}, toId:{}, money{}, serviceType:{}, serviceId:{}) return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,fromId,toId,money,serviceType,serviceId,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());
        return ret;
    }


    @Override
    public Result<List<AccountFlow>> queryPersonalFlow(long logId, String token, String ip, Date start, Date end) {
        long begin = System.currentTimeMillis();

        Result ret;
        Member member = redisTemplate.opsForValue().get(SESSION+token);
        if(member==null || ip==null || !ip.equals(member.getIp())){
            ret = UN_REGISTERED;
        }else{
            //重置过期时间
            redisTemplate.expire(SESSION+token,SESSION_EXPIRE, TimeUnit.SECONDS);

            List<AccountFlow> accountFlows = accountFlowMapper.queryAccountFlow(member.getId(),start,end);
            ret = ResultGenerator.genSuccessResult(accountFlows);
        }
        long last = System.currentTimeMillis();
        logger.info("[GTJA]:{} queryPersonalFlow(token:{}, ip:{}, start:{}, end:{}) return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ip,start,end,ret.getMessage(),(last-begin),RpcContext.getContext().getRemoteAddress());
        return ret;
    }

    @Override
    public Result<BigDecimal> queryAccountBalance(long logId, String token, String ip) {
        long start = System.currentTimeMillis();

        Result ret;
        Member member = redisTemplate.opsForValue().get(SESSION+token);
        if(member==null || ip==null || !ip.equals(member.getIp())){
            ret = UN_REGISTERED;
        }else{
            redisTemplate.expire(SESSION+token,SESSION_EXPIRE, TimeUnit.SECONDS);//重置过期时间
            BigDecimal balance = accountMapper.queryAccountBalance(member.getId());//账户余额
            ret = ResultGenerator.genSuccessResult(balance);
        }
        long end = System.currentTimeMillis();
        logger.info("[GTJA]:{} queryAccountBalance(token:{}, ip:{}) return:{}. Processing time:{}ms. request from consumer:{}."
                ,logId,token,ip,ret.getMessage(),(end-start),RpcContext.getContext().getRemoteAddress());
        return ret;
    }

}
