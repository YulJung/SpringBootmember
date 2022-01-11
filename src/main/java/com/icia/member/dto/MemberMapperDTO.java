package com.icia.member.dto;

import lombok.Data;

@Data
public class MemberMapperDTO {
    private Long member_id;
    private String member_email;
    private String member_password;
    private String member_name;

    public MemberMapperDTO(String tsetEmail, String testpassword, String testname) {

    }
}
