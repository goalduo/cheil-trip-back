package com.goalduo.cheilTrip.board.service;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.dto.BoardDto;
import com.goalduo.cheilTrip.board.mapper.BoardMapper;
import com.goalduo.cheilTrip.jwt.JwtProvider;
import com.goalduo.cheilTrip.util.PageNavigation;
import com.goalduo.cheilTrip.util.SizeConstant;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService{

    private final BoardMapper boardMapper;
    private final JwtProvider jwtProvider;

    @Override
    @Transactional
    public void writeArticle(Board board, String token) {
        Claims claims = jwtProvider.getClaims(token);
        String userId = String.valueOf(claims.get("userId"));
        board.setUserId(userId);
        board.setHit(0);
        boardMapper.writeArticle(board);
    }

    @Override
    public List<BoardDto> searchArticles(Map<String, String> map){
        Map<String, Object> param = new HashMap<>();
        String key = map.get("key");
        if("userid".equals(key) || "subject".equals(key)) param.put("key",key);
        else param.put("key", null);
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
        param.put("start", start);
        param.put("listsize", SizeConstant.LIST_SIZE);

        return boardMapper.searchArticles(param);
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
    public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
        PageNavigation pageNavigation = new PageNavigation();

        int naviSize = SizeConstant.NAVIGATION_SIZE;
        int sizePerPage = SizeConstant.LIST_SIZE;
        int currentPage = Integer.parseInt(map.get("pgno"));

        pageNavigation.setCurrentPage(currentPage);
        pageNavigation.setNaviSize(naviSize);
        Map<String, Object> param = new HashMap<>();
        String key = map.get("key");
        if("userid".equals(key) || "subject".equals(key)) param.put("key",key);
        else param.put("key", null);
        param.put("word", map.get("word") == null ? "" : map.get("word"));
        int totalCount = boardMapper.getTotalArticleCount(param);
        pageNavigation.setTotalCount(totalCount);
        int totalPageCount = (totalCount - 1) / sizePerPage + 1;
        pageNavigation.setTotalPageCount(totalPageCount);
        boolean startRange = currentPage <= naviSize;
        pageNavigation.setStartRange(startRange);
        boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
        pageNavigation.setEndRange(endRange);

        return pageNavigation;
    }

    @Override
    public void modifyArticle(Board boardDto) {

    }

    @Override
    public void deleteArticle(int no) {
        boardMapper.deleteArticle(no);
    }

    @Override
    public List<Board> searchListByMostRecentN(int count) {
        return null;
    }

    @Override
    public List<Board> searchListByTopN(int count) {
        return null;
    }

    @Override
    public List<Board> findArticlesByUserId(Map<String, String> map, String userId) {
        Map<String, Object> param = new HashMap<>();
        int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
        int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
        param.put("start", start);
        param.put("listsize", SizeConstant.LIST_SIZE);
        param.put("userId",userId);
        return boardMapper.findArticlesByUserId(param);
    }

    @Override
    public int getArticleCountByUserId(String userId) {
        return boardMapper.getArticleCountByUserId(userId);
    }
}
