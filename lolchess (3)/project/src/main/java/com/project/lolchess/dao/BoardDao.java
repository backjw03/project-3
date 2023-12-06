package com.project.lolchess.dao;

import com.project.lolchess.dto.BoardDto;
import com.project.lolchess.dto.ReplyDto;
import com.project.lolchess.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardDao {
    List<BoardDto> selectBoardList(SearchDto sdto);
    int selectBoardCnt(SearchDto sdto);
    void insertBoard(BoardDto board);
    BoardDto selectBoard(int b_num);
    List<ReplyDto> selectReplyList(int b_num);
    void deleteBoard(int b_num);
    void deleteReplies(int b_num);
    void deleteReply(ReplyDto reply);
    void updateBoard(BoardDto board);
    void insertReply(ReplyDto rDto);
    ReplyDto selectLastReply(int r_num);
    void updateViews(int b_num);
}
