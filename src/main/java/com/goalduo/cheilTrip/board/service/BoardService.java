package com.goalduo.cheilTrip.board.service;

import com.goalduo.cheilTrip.board.dto.Board;

import java.util.List;

public interface BoardService {

    void writeArticle(Board boardDto);

    List<Board> searchListAll();

    List<Board> searchListBySubject(String subject);

    Board viewArticle(int no);

    void modifyArticle(Board boardDto);

    void deleteArticle(int no);

    List<Board> searchListByMostRecentN(int count);

    List<Board> searchListByTopN(int count);
}
