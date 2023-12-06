package com.project.lolchess.controller;

import com.project.lolchess.dto.ReplyDto;
import com.project.lolchess.service.BoardService;
import com.project.lolchess.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BoardRestController {
    @Autowired
    private MemberService mServ;
    @Autowired
    private BoardService bServ;

    @GetMapping("idCheck")
    public String idCheck(String mid) {
        log.info("idCheck()");
        String result = mServ.idCheck(mid);
        return result;
    }

    @PostMapping("replyInsert")
    public ReplyDto replyInsert(ReplyDto reply) {
        log.info("replyInsert()");
        reply = bServ.replyInsert(reply);
        return reply;
    }
}