package com.project.lolchess.controller;

import com.project.lolchess.dto.BoardDto;
import com.project.lolchess.dto.ReplyDto;
import com.project.lolchess.dto.SearchDto;
import com.project.lolchess.service.BoardService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Slf4j
public class BoardController {
    @Autowired
    private BoardService bServ;

    @GetMapping("boardList")
    public ModelAndView boardList(SearchDto sdto, HttpSession session){
        log.info("boardList()");
        ModelAndView mv = bServ.getBoardList(sdto, session);
        return mv;
    }
    @GetMapping("writeForm")
    public String writeForm(){
        log.info("writeForm()");
        return "writeForm";
    }
    @PostMapping("writeProc")
    public String writeProc(BoardDto board, RedirectAttributes rttr){
        log.info("writeProc()");
        String view = bServ.boardWrite(board, rttr);
        return view;
    }
    @GetMapping("boardDetail")
    public ModelAndView boardDetail(int b_num){
        log.info("boardDetail() : {}", b_num);
        ModelAndView mv = bServ.getBoard(b_num);
        return mv;
    }

    @GetMapping("boardDelete")
    public String boardDelete(int b_num, RedirectAttributes rttr){
        log.info("boardDelete()");
        String view = bServ.deleteBoard(b_num, rttr);
        return view;
    }
    @GetMapping("updateForm")
    public ModelAndView updateForm(int b_num){
        log.info("updateForm()");
        ModelAndView mv = bServ.updateBoard(b_num);
        return mv;
    }
    @PostMapping("updateProc")
    public String updateProc(BoardDto board, RedirectAttributes rttr){
        log.info("updateProc()");
        String view = bServ.updateBoard(board, rttr);
        return view;
    }
    @GetMapping("replyDelete")
    public String replyDelete(ReplyDto reply, RedirectAttributes rttr){
        log.info("replyDelete()");
        String view = bServ.deletereply(reply, rttr);
        return view;
    }
}