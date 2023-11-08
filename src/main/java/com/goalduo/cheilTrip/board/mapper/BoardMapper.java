package com.goalduo.cheilTrip.board.mapper;

import com.goalduo.cheilTrip.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    void writeArticle(Board board);

    List<Board> searchListAll();

    List<Board> searchListBySubject(String subject);

    Board viewArticle(int no);

    void modifyArticle(Board board);

    void deleteArticle(int no);

    List<Board> searchListByMostRecentN(int count);

    List<Board> searchListByTopN(int count);
}
