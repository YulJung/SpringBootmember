package com.icia.member.controller;

import com.icia.member.dto.MemberDetailDTO;
import com.icia.member.dto.MemberLoginDTO;
import com.icia.member.dto.MemberSaveDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/member/*")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService ms;
    @GetMapping("save")
    public String saveForm(Model model){
        model.addAttribute("login",new MemberLoginDTO());
        return "member/save";
    }

    @PostMapping("save")
    public String save(@ModelAttribute MemberSaveDTO memberSaveDTO, BindingResult bindingResult){

        if(bindingResult.hasErrors()) return "member/save";
        ms.save(memberSaveDTO);
        return "redirect:/member/findAll";
    }
    @GetMapping("login")
    public String memberLogin(Model model){
        model.addAttribute("login",new MemberLoginDTO());
        return "member/login";
    }
    @PostMapping("login")
    public String doLogin(@Validated @ModelAttribute("login") MemberLoginDTO memberLoginDTO, BindingResult bindingResult, HttpSession session){
        System.out.println(memberLoginDTO);
        if(bindingResult.hasErrors()) return "member/login";
        // boolean result = ms.login(login);
        if(ms.login(memberLoginDTO)){
            session.setAttribute("loginEmail",memberLoginDTO.getMemberEmail());
            return "/member/mypage";
        }else{
            bindingResult.reject("loginfail","이메일또는 비밀번호가 틀립니다.");
            return "member/login";
        }

    }
    @GetMapping("{memberId}")
    public String findById(@PathVariable("memberId") Long memberId, Model model, @ModelAttribute("member")
            MemberSaveDTO memberSaveDTO, BindingResult bindingResult){//pathVariable 경로상에 있는변수가져오는거
        System.out.println(memberId);

        MemberDetailDTO member =  ms.findById(memberId);
        model.addAttribute("member",member);
        return "member/findById";
//
//
//        try {
//            ms.save(memberSaveDTO);
//        }catch (IllegalStateException e){
//            bindingResult.reject("emailcheck",e.getMessage());
//            return "member/save";
//        }
//
//        return "redirect:/member/login";
    }
    //목록출력 (/member)
    @GetMapping("findAll")
    public String findAll(Model model){
        List<MemberDetailDTO> memberList= ms.findAll();
        model.addAttribute("memberList",memberList);
        return "member/findAll";
    }

    @PostMapping("{memberId}")
    public @ResponseBody MemberDetailDTO detail(@PathVariable("memberId") Long memberId){
        MemberDetailDTO member = ms.findById(memberId);
        return member;
    }
    //delete
    @DeleteMapping("{memberId}")
    public ResponseEntity deleteById(@PathVariable Long memberId){
        ms.deleteById(memberId);
        return new ResponseEntity(HttpStatus.OK);
    }
    //update
    @GetMapping("update")
    public String update(Model model,HttpSession httpSession){

        MemberDetailDTO memberDetailDTO = ms.findByEmail((String) httpSession.getAttribute("loginEmail"));

        model.addAttribute("member",memberDetailDTO);
        return "member/update";
    }
    //do update
    @PostMapping("update")
    public String doUpdate (@ModelAttribute MemberDetailDTO memberDetailDTO){
        System.out.println("post update");

        Long memberId =   ms.update(memberDetailDTO);
        System.out.println(memberDetailDTO.toString());
        return "redirect:/member/"+memberDetailDTO.getMemberId();
    }
    //수정처리 [put
    @PutMapping("{memberId}") //json으로 날라오면 requestBody로 받아줘야됨
    public ResponseEntity update(@RequestBody MemberDetailDTO memberDetailDTO){
        Long memberId = ms.update(memberDetailDTO);
        return new ResponseEntity(HttpStatus.OK);

    }
}
