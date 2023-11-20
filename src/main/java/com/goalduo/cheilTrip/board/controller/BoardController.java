package com.goalduo.cheilTrip.board.controller;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.dto.BoardDto;
import com.goalduo.cheilTrip.board.dto.FileInfoDto;
import com.goalduo.cheilTrip.board.service.BoardService;
import com.goalduo.cheilTrip.board.service.FileService;
import com.goalduo.cheilTrip.member.dto.MemberDto;
import com.goalduo.cheilTrip.util.error.CommonErrorCode;
import com.goalduo.cheilTrip.util.error.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;
    private final FileService fileService;
    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
        List<BoardDto> boards = boardService.searchArticles(map);
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> writeArticle(@RequestBody Board board,
                                          @RequestHeader("Authorization") String token) {
        System.out.println(board);
        boardService.writeArticle(board,token);
        return new ResponseEntity<>(1, HttpStatus.OK);
    }

    @GetMapping("/{articleNo}")
    public ResponseEntity<?> getArticle(@PathVariable int articleNo){
        Board board = boardService.viewArticle(articleNo);
        return new ResponseEntity<>(board,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchListBySubject(@RequestParam String subject){
        List<Board> boards = boardService.searchListBySubject(subject);
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }

    @PostMapping(value = "/image-upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadEditorImage(ServletRequest request, @RequestPart("image") MultipartFile image) throws IOException {
        String realPath = request.getServletContext().getRealPath("/upload");
        FileInfoDto fileInfoDto = fileService.uploadFile(realPath, image);
        if (fileInfoDto != null) return new ResponseEntity<>(fileInfoDto,HttpStatus.CREATED);
        else return new ResponseEntity<>(new ErrorResponse(CommonErrorCode.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> printEditorImage(ServletRequest request,@RequestParam String savedFolder,  @RequestParam final String filename) {
        // 업로드된 파일의 전체 경로
        String realPath = request.getServletContext().getRealPath("/upload");
        String fullPath = realPath + File.separator + savedFolder + File.separator + filename;
        System.out.println(fullPath);
        // 파일이 없는 경우 예외 throw
        File uploadedFile = new File(fullPath);
        if (uploadedFile.exists() == false) {
            throw new RuntimeException();
        }
        try {
            // 이미지 파일을 byte[]로 변환 후 반환
            byte[] imageBytes = Files.readAllBytes(uploadedFile.toPath());
            return new ResponseEntity<>(imageBytes,HttpStatus.OK);
        } catch (IOException e) {
            // 예외 처리는 따로 해주는 게 좋습니다.
            throw new RuntimeException(e);
        }
    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<?> findArticlesByUserId(@RequestParam Map<String, String> map , @PathVariable String userId){
        map.put("userId", userId);
        List<Board> articlesByUserId = boardService.findArticlesByUserId(map, userId);
        return new ResponseEntity<>(articlesByUserId, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/count")
    public ResponseEntity<?> getArticleCountByUserId(@PathVariable String userId){
        int count = boardService.getArticleCountByUserId(userId);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @DeleteMapping("/{articleNo}")
    public ResponseEntity<?> deleteArticleByArticleId(@PathVariable int articleNo){
        boardService.deleteArticle(articleNo);
        return new ResponseEntity<>("삭제완료", HttpStatus.OK);
    }
}
