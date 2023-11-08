package com.goalduo.cheilTrip.board.service;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.mapper.BoardMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;

    @Override
    @Transactional
    public void writeArticle(Board board) {
        board.setHit(0);
        boardMapper.writeArticle(board);
    }

    @Override
    public List<Board> searchListAll() {
        return boardMapper.searchListAll();
    }

    @Override
    public List<Board> searchListBySubject(String subject) {
        return boardMapper.searchListBySubject(subject);
    }

    @Override
    public Board viewArticle(int articleNo) {
       return boardMapper.viewArticle(articleNo);
    }

    @Override
    public void modifyArticle(Board boardDto) {

    }

    @Override
    public void deleteArticle(int no) {

    }

    @Override
    public List<Board> searchListByMostRecentN(int count) {
        return null;
    }

    @Override
    public List<Board> searchListByTopN(int count) {
        return null;
    }
}
