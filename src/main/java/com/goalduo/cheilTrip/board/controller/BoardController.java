package com.goalduo.cheilTrip.board.controller;

import com.goalduo.cheilTrip.board.dto.Board;
import com.goalduo.cheilTrip.board.dto.BoardDto;
import com.goalduo.cheilTrip.board.dto.FileInfoDto;
import com.goalduo.cheilTrip.board.service.BoardService;
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

    @GetMapping
    public ResponseEntity<?> list(@RequestParam Map<String, String> map) throws Exception {
        List<BoardDto> boards = boardService.searchArticles(map);
        return new ResponseEntity<>(boards,HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<?> writeArticle(@RequestBody Board board,
                                          @RequestHeader("Authorization") String token) {

        boardService.writeArticle(board,token);
        return new ResponseEntity<>(HttpStatus.OK);
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

    @PostMapping("/image-upload")
    public ResponseEntity<?> uploadEditorImage(ServletRequest request, @RequestParam("upload") final MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String realPath = request.getServletContext().getRealPath("/upload");
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String uploadDir = realPath + File.separator + today;
            log.debug("저장 폴더 : {}", uploadDir);
            File folder = new File(uploadDir);
            if (!folder.exists())
                folder.mkdirs();
            String originalFileName = image.getOriginalFilename();                                         // 원본 파일명
            String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
            image.transferTo(new File(folder, saveFileName));
            FileInfoDto fileInfoDto = FileInfoDto.builder()
                    .saveFolder(today)
                    .originalFile(originalFileName)
                    .saveFile(saveFileName).build();
            return new ResponseEntity<>(fileInfoDto,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(new ErrorResponse(CommonErrorCode.RESOURCE_NOT_FOUND), HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "/image-print", produces = { MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    public ResponseEntity<byte[]> printEditorImage(ServletRequest request,@RequestParam String savedFolder,  @RequestParam final String filename) {
        // 업로드된 파일의 전체 경로
        String realPath = request.getServletContext().getRealPath("/upload");
        String fullPath = realPath + File.separator + savedFolder + File.separator + filename;
//        System.out.println(fullPath);
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
}
