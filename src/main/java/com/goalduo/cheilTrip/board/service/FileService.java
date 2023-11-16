package com.goalduo.cheilTrip.board.service;

import com.goalduo.cheilTrip.board.dto.FileInfoDto;
import org.imgscalr.Scalr;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileService {

    @Transactional
    public FileInfoDto uploadFile(String realPath, MultipartFile image) throws IOException {
        if (!image.isEmpty()) {
            String today = new SimpleDateFormat("yyMMdd").format(new Date());
            String uploadDir = realPath + File.separator + today;
            File folder = new File(uploadDir);
            if (!folder.exists())
                folder.mkdirs();
            String originalFileName = image.getOriginalFilename();
            String extension = StringUtils.getFilenameExtension(originalFileName);
            String saveFileName = UUID.randomUUID().toString()
                    + originalFileName.substring(originalFileName.lastIndexOf('.'));
            try(InputStream inputStream = image.getInputStream()){
                BufferedImage bi = ImageIO.read(inputStream);
                bi=resizeImage(bi,450,450);
                System.out.println(bi);
                System.out.println(saveFileName);
                System.out.println(extension);
                ImageIO.write(bi, extension, new File(folder, saveFileName));
            }
            FileInfoDto fileInfoDto = FileInfoDto.builder()
                    .saveFolder(today)
                    .originalFile(originalFileName)
                    .saveFile(saveFileName).build();
            return fileInfoDto;
        }
        return null;

    }

    BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight){
        // resize에 들어가는 속성을 변경해서 여러 모드로 변경해줄수있다
        return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_EXACT, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
    }

}
