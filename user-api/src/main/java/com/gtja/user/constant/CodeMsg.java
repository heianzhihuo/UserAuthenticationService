package com.gtja.user.constant;

import com.gtja.productapi.utils.Result;

public class CodeMsg extends Result {

    public static final CodeMsg NO_USER_OR_ERROR_PWD = new CodeMsg(701,"用户名不存在或密码错误");
    public static final CodeMsg USERNAME_OR_PWD_EMPTY = new CodeMsg(702,"用户名或密码为空");
    //public static final CodeMsg LOGIN_SUCESS = new CodeMsg(703,"登录成功");
    public static final String LOGIIN_SUCESS_MESSAGE = "登录成功";
    public static final CodeMsg USERNAME_ALREADY_EXISTS = new CodeMsg(704,"用户名已被注册");
    public static final CodeMsg REGISTER_FAILURE = new CodeMsg(705,"注册失败");
    //public static final CodeMsg REGISTER_SUCESS = new CodeMsg(706,"注册成功");
    public static final String REGISTER_SUCESS_MESSAGE = "注册成功";
    public static final CodeMsg UN_REGISTERED = new CodeMsg(707,"未登录");
    //public static final CodeMsg LOGOUT_SUCESS = new CodeMsg(708,"退出成功");
    public static final String LOGOUT_SUCESS_MESSAGE = "退出成功";

    public static final CodeMsg NO_USER = new CodeMsg(709,"用户不存在");

    public static final CodeMsg BALANCE_IS_NOT_ENOUGH = new CodeMsg(711,"账户余额不足");
    public static final CodeMsg REPEAT_PAY = new CodeMsg(712,"重复支付");
    public static final CodeMsg TRANSACTION_ERROR = new CodeMsg(713,"支付异常");
    //public static final CodeMsg TRANSACTION_SUCESS = new CodeMsg(714,"支付成功");
    public static String TRANSACTION_SUCESS_MESSAGE = "支付成功";

    public static final CodeMsg ILLEGAL_USERNAME = new CodeMsg(715,"非法用户名(仅允许字母数字下划线，第一个字符必须是字母)");
    public static final CodeMsg ILLEGAL_PWD = new CodeMsg(716,"非法密码(仅允许字母数字6-18位)");
    public static final CodeMsg ILLEGAL_EMAIL = new CodeMsg(717,"非法邮箱");
    public static final CodeMsg ILLEGAL_PHONE = new CodeMsg(718,"非法手机号");

    public CodeMsg(int code, String msg) {
        this.setResultCode(code);
        this.setMessage(msg);
    }
}
