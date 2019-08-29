package com.gtja.user.mapper;

import com.gtja.user.pojo.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMapper {

    long countMember();

    Member selectByName(String name);

    Member selectById(long id);

    /**
     * 添加用户，同时添加账号*/
    int insert(Member member);
}
