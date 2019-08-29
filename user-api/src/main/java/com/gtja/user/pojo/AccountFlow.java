package com.gtja.user.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class AccountFlow implements Serializable {
    private long id;
    private long accountId;
    private BigDecimal amount;
    private Timestamp transactionTime;
    private String serviceType;
    private long serviceId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Timestamp getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Timestamp transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public long getServiceId() {
        return serviceId;
    }

    public void setServiceId(long serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String toString() {
        return "AccountFlow{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", amount=" + amount +
                ", transactionTime=" + transactionTime +
                ", serviceType='" + serviceType + '\'' +
                ", serviceId=" + serviceId +
                '}';
    }
}
