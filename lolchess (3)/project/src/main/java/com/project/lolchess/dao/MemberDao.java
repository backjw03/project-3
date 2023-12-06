package com.project.lolchess.dao;

import com.project.lolchess.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDao {
    String selectPassword(String mId);
    void insertMember(MemberDto member);
    MemberDto selectMember(String mId);
    int selectId(String mid);
}
