package com.icia.member.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberLoginDTO {
    @NotBlank(message = "아이디를 입력해주세요")
    private String memberEmail;
    @NotBlank(message = "비밀번호를 입력해주세요")
    private String memberPassword;

}
