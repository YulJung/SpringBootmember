package com.icia.member.member;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.lang.reflect.Member;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class MemberTest {

    @Autowired
    private MemberService ms;

    @Test
    @Transactional
    @Rollback
    @DisplayName("회원가입 테스트")
    public void memberSave(){
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트이메일");
        memberSaveDTO.setMemberName("testname");
        memberSaveDTO.setMemberPassword("testpw");
        ms.save(memberSaveDTO);
    }

    @Test
    @Transactional
    @Rollback
    public void memberDetailTest(){
        //given :테스트 조건 설정
        //1.새로운 회원을 등록하고 해당회원의 번호를 가져옴
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트이메일1");
        memberSaveDTO.setMemberName("testname");
        memberSaveDTO.setMemberPassword("testpw");
        ms.save(memberSaveDTO);
        //2.테스트용 데이터를 디비에 저장하고 아이디를 가져옴
         MemberDetailDTO memberDetailDTO= ms.findByEmail(memberSaveDTO.getMemberEmail());
        Long memberId = memberDetailDTO.getMemberId();
        //when: 테스트 수행
        //위에서 가져온 회원번호로 조회
        MemberDetailDTO findMember = ms.findById(memberId);
        //then : 테스트 결과 검증
        //1번에서 가입한 회원의 정보와 2에서 조회한 정보가 일치하면 테스트 성공 아니면 실패
        assertThat(memberSaveDTO.getMemberEmail()).isEqualTo(findMember.getMemberEmail());

    }

    @Test
    @DisplayName("회원 삭제 테스트")
    @Transactional
    @Rollback
    public void memberDeleteTest(){
        //새로운 회원등록
        MemberSaveDTO memberSaveDTO = new MemberSaveDTO();
        memberSaveDTO.setMemberEmail("테스트이메일2");
        memberSaveDTO.setMemberName("testname");
        memberSaveDTO.setMemberPassword("testpw");
//        ms.save(memberSaveDTO);
        //2.테스트용 데이터를 디비에 저장하고 아이디를 가져옴
//        MemberDetailDTO memberDetailDTO= ms.findByEmail(memberSaveDTO.getMemberEmail());
        Long memberId = ms.save(memberSaveDTO);
        System.out.println(ms.findById(memberId));
        //삭제 실행
        ms.deleteById(memberId);
        //삭제된 내용으로 select  > null이뜨면 테스트 성공
        //exception 을 무시하고 테스트 진행
        assertThrows(NoSuchElementException.class,()->{
            assertThat(ms.findById(memberId)).isNull(); // null 이면 테스트 통과
        });
    }
    @Test
    @DisplayName("회원 수정 테스트")
    @Transactional
    @Rollback
    public void updateTest(){
        //가입
        //수정
        //아이디로 조회



    }
}
