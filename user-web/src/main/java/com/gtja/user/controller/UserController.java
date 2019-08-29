package com.gtja.user.controller;

import com.gtja.productapi.utils.Result;
import com.gtja.productapi.utils.ResultGenerator;
import com.gtja.user.pojo.AccountFlow;
import com.gtja.user.pojo.Member;
import com.gtja.user.pojo.Order;
import com.gtja.user.service.UserService;
import org.apache.dubbo.config.annotation.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

import static com.gtja.user.constant.CodeMsg.UN_REGISTERED;

@RestController
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Reference
    UserService userService;

    private static final String COOKIE_NAME = "gtjacookie";
    private static final String EMPTY_COOKIE = "";
    private static final String PAY_ORDER = "PAY_ORDER";
    private static final String PAY_ID = "-1";

    @PostMapping("/hello")
    public Result hello(){
        return ResultGenerator.genSuccessResult("hello");
    }

    @GetMapping("/sayHello")
    public String sayHello(HttpServletRequest request, String username){
        return userService.sayHello(username);
    }

    @PostMapping("/login")
    public Result login(HttpServletRequest request, HttpServletResponse response, String username, String pwd){

        Result<String> result = userService.userLogin(System.currentTimeMillis(),username,pwd,request.getRemoteHost());
        if(result.getResultCode()==200){
            Cookie cookie = new Cookie(COOKIE_NAME,result.getData());
            response.addCookie(cookie);
            result.setData(null);
        }
        return result;
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request, HttpServletResponse response,
                         @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token){
        Result result = userService.logout(System.currentTimeMillis(),token);
        Cookie cookie = new Cookie(COOKIE_NAME,null);
        response.addCookie(cookie);
        return result;
    }

    @PostMapping("/register")
    public Result register(Member member){
        return userService.register(System.currentTimeMillis(),member);
    }

    //查询余额
    @GetMapping(value = "/queryAccount")
    public Result queryAccount(HttpServletRequest request,
                               @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token){
        return userService.queryAccountBalance(System.currentTimeMillis(),token,request.getRemoteHost());
    }

    //查询流水
    @GetMapping(value = "/queryFlow")
    public Result queryFlow(HttpServletRequest request,
                            @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token, Date start, Date end){
        Result<List<AccountFlow>> ret = userService.queryPersonalFlow(System.currentTimeMillis(),token,request.getRemoteHost(),start,end);
        System.out.println(ret);
        return ret;
    }

    //查询个人信息
    @GetMapping(value = "/queryInfo")
    public Result<Member> queryInfo(HttpServletRequest request,
                                    @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token){
        return userService.queryPersonalInfo(System.currentTimeMillis(),token,request.getRemoteHost());
    }

    //扣款
    @PostMapping(value = "/pay")
    public Result pay(HttpServletRequest request, @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token,
                      Long fromId, Long toId, BigDecimal money,@RequestParam(defaultValue=PAY_ORDER) String serviceType,
                      @RequestParam(defaultValue = PAY_ID) Long serviceId){
        Result<Long> ret = userService.queryUserId(System.currentTimeMillis(),token,request.getRemoteHost());
        if(ret.getResultCode()==200 && ret.getData().equals(fromId)){
            return userService.accountTransfer(System.currentTimeMillis(),fromId,toId,money,serviceType,serviceId);
        }
        return UN_REGISTERED;
    }

    //下单
    @PostMapping(value = "buy")
    public Result buy(HttpServletRequest request, @CookieValue(value = COOKIE_NAME,defaultValue = EMPTY_COOKIE) String token,
                      @RequestBody Order order){
        //shopId
        //,@RequestParam(value = "shopId")Long shopId
        System.out.println(order.getShopId());
        return ResultGenerator.genSuccessResult(order.getMembers());
    }

}
