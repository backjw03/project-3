package com.project.lolchess.service;

import com.project.lolchess.dao.BoardDao;
import com.project.lolchess.dao.MemberDao;
import com.project.lolchess.dto.BoardDto;
import com.project.lolchess.dto.MemberDto;
import com.project.lolchess.dto.ReplyDto;
import com.project.lolchess.dto.SearchDto;
import com.project.lolchess.util.PagingUtil;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Service
@Slf4j
public class BoardService {
    @Autowired
    private BoardDao bDao;

    private int lcnt = 5;

    public ModelAndView getBoardList(SearchDto sdto, HttpSession session) {
        log.info("getBoardList()");
        ModelAndView mv = new ModelAndView();
        if (sdto.getPageNum() == 0) {
            sdto.setPageNum(1);
        }
        int num = sdto.getPageNum();
        if (sdto.getListCnt() == 0) {
            sdto.setListCnt(lcnt);
        }
        sdto.setPageNum((num - 1) * sdto.getListCnt());
        List<BoardDto> bList = bDao.selectBoardList(sdto);
        mv.addObject("bList", bList);
        sdto.setPageNum(num);
        String pageHtml = getPaging(sdto);
        mv.addObject("paging", pageHtml);
        if (sdto.getColname() != null) {
            session.setAttribute("sdto", sdto);
        } else {
            session.removeAttribute("sdto");
        }
        session.setAttribute("pageNum", num);
        mv.setViewName("boardList");
        return mv;
    }

    private String getPaging(SearchDto sdto) {
        String pageHtml = null;
        int maxNum = bDao.selectBoardCnt(sdto);
        int pageCnt = 5;
        String listName = "boardList?";
        PagingUtil paging = new PagingUtil(maxNum, sdto.getPageNum(), sdto.getListCnt(), pageCnt, listName);
        pageHtml = paging.makePaging();
        return pageHtml;
    }

    public String boardWrite(BoardDto board, RedirectAttributes rttr) {
        log.info("boardWrite()");
        String view = null;
        String msg = null;
        try {
            bDao.insertBoard(board);
            log.info("게시글 번호 : " + board.getB_num());

            view = "redirect:boardList?pageNum=1";
            msg = "작성 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:writeForm";
            msg = "작성 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public ModelAndView getBoard(int b_num) {
        log.info("getBoard");
        ModelAndView mv = new ModelAndView();
        bDao.updateViews(b_num);
        BoardDto board = bDao.selectBoard(b_num);
        mv.addObject("board", board);
        List<ReplyDto> rList = bDao.selectReplyList(b_num);
        mv.addObject("rList", rList);
        mv.setViewName("boardDetail");
        return mv;
    }

    public ReplyDto replyInsert(ReplyDto reply) {
        log.info("replyInsert()");
        try {
            bDao.insertReply(reply);
            reply = bDao.selectLastReply(reply.getR_num());
        } catch (Exception e) {
            e.printStackTrace();
            reply = null;
        }
        return reply;
    }

    public String deleteBoard(int b_num, RedirectAttributes rttr) {
        log.info("deleteBoard()");
        String view = null;
        String msg = null;
        try {
            bDao.deleteReplies(b_num);
            bDao.deleteBoard(b_num);
            view = "redirect:boardList?pageNum=1";
            msg = "삭제 성공";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:boardDetail?b_num=" + b_num;
            msg = "삭제 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }


    public ModelAndView updateBoard(int b_num) {
        log.info("updateBoard()");
        ModelAndView mv = new ModelAndView();
        BoardDto board = bDao.selectBoard(b_num);
        mv.addObject("board", board);
        mv.setViewName("updateForm");
        return mv;
    }

    public String updateBoard(BoardDto board, RedirectAttributes rttr) {
        log.info("updateBoard()");
        String view = null;
        String msg = null;
        try {
            bDao.updateBoard(board);
            view = "redirect:boardDetail?b_num=" + board.getB_num();
            msg = "수정 완료.";
        } catch (Exception e) {
            e.printStackTrace();
            view = "redirect:updateForm?b_num=" + board.getB_num();
            msg = "수정 실패";
        }
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

    public String deletereply(ReplyDto reply, RedirectAttributes rttr) {
        log.info("deletereply()");
        String view = null;
        String msg = null;
        try {
            bDao.deleteReply(reply);
            msg = "삭제 성공";
        } catch (Exception e) {
            e.printStackTrace();
            msg = "삭제 실패";
        }
        view = "redirect:boardDetail?b_num=" + reply.getR_bnum();
        rttr.addFlashAttribute("msg", msg);
        return view;
    }

//    public ModelAndView getBoard(BoardDto board, HttpSession session) {
//        log.info("getBoard()");
//        ModelAndView mv = new ModelAndView();
//
//        MemberDto member = (MemberDto) session.getAttribute("member");
//        if (member.getM_id().equals(board.getB_id())) {
//            int views = board.getB_views() + 1;
//            board.setB_views(views);
//            bDao.updateViews(board);
//            board = bDao.selectBoard(board.getB_num());
//            session.setAttribute("board", board);
//        }
//        return mv;
//    }

}
//    public ModelAndView getBoard(int b_num, BoardDto bdto, HttpSession session){
//    log.info("getBoard()");
//    ModelAndView mv = new ModelAndView();
//
//    MemberDto member = (MemberDto) session.getAttribute("memeber");
//    int views = bdto.getB_views();
//    if (member.getM_id().equals(bdto.getB_id())) {
//        views += 1;
//        bdto.setB_views(views);
//        bDao.updateViews(bdto);
//    }else {
//        views ++;
//        bdto.setB_views(views);
//        bDao.updateViews(bdto);
//    }
//
//    return mv;
//}


