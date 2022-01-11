package com.icia.member.repository;


import com.icia.member.dto.MemberMapperDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface MemberMapperRepository {
    //회원목록출력
    List<MemberMapperDTO> memberList();

    void save(MemberMapperDTO memberMapperDTO);

    //mapper없이 xml에서 쿼리까지 수행
    @Select("select * from member_table")
    List<MemberMapperDTO> memberList2();

}
