package com.goalduo.cheilTrip.board.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfoDto {
    private String saveFolder;
    private String originalFile;
    private String saveFile;
}