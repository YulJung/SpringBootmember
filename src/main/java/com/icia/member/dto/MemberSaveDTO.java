package com.icia.member.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSaveDTO {
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private Long memberId;

    public MemberSaveDTO(String 조회용이름, String 조회용_비밀번호, String 조회용_이메일) {
    }
}
