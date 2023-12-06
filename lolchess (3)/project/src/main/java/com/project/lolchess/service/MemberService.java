package com.project.lolchess.service;

import com.project.lolchess.dao.MemberDao;
import com.project.lolchess.dto.MemberDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Service
@Slf4j
public class MemberService {
    @Autowired
    private MemberDao mDao;
    private BCryptPasswordEncoder pEncoder = new BCryptPasswordEncoder();
    public String idCheck(String mid) {
        log.info("idCheck()");
        String result = null;
        int mcnt = mDao.selectId(mid);
        if(mcnt == 0){
            result = "ok";
        } else {
            result = "fail";
        }
        return result;
    }
    public String memberJoin(MemberDto member, RedirectAttributes rttr) {
        log.info("memberJoin()");
        String view = null;
        String msg = null;
        String encPwd = pEncoder.encode(member.getM_pwd());
        member.setM_pwd(encPwd);
        try {
            mDao.insertMember(member);
            msg = "가입 성공";
            view = "redirect:/";
        } catch (Exception e){
            e.printStackTrace();
            msg = "가입 실패";
            view = "redirect:joinForm";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
    public String loginProc(MemberDto member,
                            HttpSession session,
                            RedirectAttributes rttr) {
        log.info("loginProc()");
        String view = null;
        String msg = null;
        String encPwd = mDao.selectPassword(member.getM_id());
        if(encPwd != null){
            if(pEncoder.matches(member.getM_pwd(), encPwd)){
                member = mDao.selectMember(member.getM_id());
                session.setAttribute("member", member);
                view = "redirect:boardList";
                msg = "로그인 성공";
            } else {
                view = "redirect:loginForm";
                msg = "비밀번호가 틀립니다";
            }
        } else {
            view = "redirect:loginForm";
            msg = "존재하지 않는 아이디입니다";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }
    public String logout(HttpSession session) {
        log.info("logout()");
        session.invalidate();
        return "redirect:/";
    }
}
