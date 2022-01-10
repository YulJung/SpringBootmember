package com.icia.member.service;


import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
    private final MemberRepository mr;
    @Override
    public Long save(MemberSaveDTO memberSaveDTO) {
        MemberEntity memberEntity = MemberEntity.saveMember(memberSaveDTO);

        MemberEntity emailCheckResult = mr.findByMemberEmail(memberSaveDTO.getMemberEmail());
        //null이면 예외발생
        if(emailCheckResult !=null){
            throw new IllegalStateException("중복된 이메일입니다.");
        }
        return mr.save(memberEntity).getMemberId();
    }



    @Override
    public boolean login(MemberLoginDTO memberLoginDTO) {
        // 사용자가 입력한 이메일을 조건으로 데이터베이스 조회
        MemberEntity memberEntity = mr.findByMemberEmail(memberLoginDTO.getMemberEmail());
        if(memberLoginDTO.getMemberPassword().equals(memberEntity.getMemberPassword())){
            return true;
        }else {
            return false;
        }
    }


    @Override
    public MemberDetailDTO findById(Long memberId) {
        Optional<MemberEntity> memberEntityOptional = mr.findById(memberId); //옵셔널 널포인트 방지
        MemberEntity memberEntity = memberEntityOptional.get();
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDto(memberEntity);
        return memberDetailDTO;

//        MemberEntity member = mr.findById(memberId).get();
//        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDto(member);
//        System.out.println("memberDetailDTO.toString() = " + memberDetailDTO.toString());


    }

    @Override
    public List<MemberDetailDTO> findAll() {
        return null;
    }

    @Override
    public void deleteById(Long memberId) {
        mr.deleteById(memberId);
    }

    @Override
    public MemberDetailDTO findByEmail(String memberEmail) {
        MemberEntity memberEntity = mr.findByMemberEmail(memberEmail);
        MemberDetailDTO memberDetailDTO = MemberDetailDTO.toMemberDetailDtos(memberEntity);
        return memberDetailDTO;
    }

    @Override
    public Long update(MemberDetailDTO memberDetailDTO) {
        //update 처리시 save 메서드 호출 따로 update는 없음
        MemberEntity memberEntity = MemberEntity.toUpdateMember(memberDetailDTO);
        Long memberId = mr.save(memberEntity).getMemberId();
        return memberId;
    }
}
