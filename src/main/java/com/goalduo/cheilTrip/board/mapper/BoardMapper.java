package com.goalduo.cheilTrip.board.mapper;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.dto.BoardDto;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface BoardMapper {

    void writeArticle(Board board);

    List<BoardDto> searchArticles(Map<String, Object> map);

    List<Board> searchListBySubject(String subject);

    Board viewArticle(int no);

    int getTotalArticleCount(Map<String, Object> param) throws SQLException;

    void modifyArticle(Board board);

    void deleteArticle(int no);

    List<Board> searchListByMostRecentN(int count);

    List<Board> searchListByTopN(int count);
}
