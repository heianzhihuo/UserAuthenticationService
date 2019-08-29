package com.gtja.user.pojo;

import java.io.Serializable;
import java.util.List;

public class Order implements Serializable {
    private Long shopId;
    private List<Member> members;

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "Order{" +
                "shopId=" + shopId +
                ", members=" + members.toString() +
                '}';
    }
}
