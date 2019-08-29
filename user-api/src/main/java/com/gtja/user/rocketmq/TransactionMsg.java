package com.gtja.user.rocketmq;

import java.io.Serializable;
import java.math.BigDecimal;

public class TransactionMsg implements Serializable {

    public static final Integer TRANSACTION_CANCELED = -1;
    public static final Integer TRANSACTION_COMMIT = 1;

    private Long fromId;
    private Long toId;
    private BigDecimal money;
    private String serviceType;
    private Long serviceId;
    private Integer state = -1;

    public TransactionMsg(Long fromId, Long toId, BigDecimal money, String serviceType, Long serviceId, Integer state) {
        this.fromId = fromId;
        this.toId = toId;
        this.money = money;
        this.serviceType = serviceType;
        this.serviceId = serviceId;
        this.state = state;
    }

    public Long getFromId() {
        return fromId;
    }

    public void setFromId(Long fromId) {
        this.fromId = fromId;
    }

    public Long getToId() {
        return toId;
    }

    public void setToId(Long toId) {
        this.toId = toId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "TransactionMsg{" +
                "fromId=" + fromId +
                ", toId=" + toId +
                ", money=" + money +
                ", serviceType='" + serviceType + '\'' +
                ", serviceId=" + serviceId +
                ", state=" + state +
                '}';
    }
}
