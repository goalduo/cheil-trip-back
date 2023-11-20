package com.goalduo.cheilTrip.board.service;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.dto.BoardDto;
import com.goalduo.cheilTrip.util.PageNavigation;

import java.util.List;
import java.util.Map;

public interface BoardService {

    void writeArticle(Board boardDto, String token);

    List<BoardDto> searchArticles(Map<String, String> map) throws Exception;

    List<Board> searchListBySubject(String subject);

    Board viewArticle(int no);

    PageNavigation makePageNavigation(Map<String, String> map) throws Exception;

    void modifyArticle(Board boardDto);

    void deleteArticle(int no);

    List<Board> searchListByMostRecentN(int count);

    List<Board> searchListByTopN(int count);

    List<Board> findArticlesByUserId(Map<String, String> map, String userId);

    int getArticleCountByUserId(String userId);
}
