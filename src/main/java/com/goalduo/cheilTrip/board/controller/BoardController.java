package com.goalduo.cheilTrip.board.controller;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.service.BoardService;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/list")
    public ResponseEntity<?> list() {
        List<Board> boards = boardService.searchListAll();
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<?> getArticle(@PathVariable int articleNo){
        Board board = boardService.viewArticle(articleNo);
        return new ResponseEntity<>(board,HttpStatus.OK);
    }

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody Board board, HttpSession session) {
        MemberDto loginMember = (MemberDto) session.getAttribute("userinfo");
        board.setUserId(loginMember.getUserId());
        boardService.writeArticle(board);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/search")
    public ResponseEntity<?> searchListBySubject(@RequestParam String subject){
        List<Board> boards = boardService.searchListBySubject(subject);
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }

}
