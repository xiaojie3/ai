package com.example.demo.file.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.util.JwtTokenUtil;
import com.example.demo.file.model.dto.FileDTO;
import com.example.demo.file.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;

    /**
     * 文件上传（需登录）
     */
    @PostMapping("/upload")
    public ApiResult<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("directoryId") String directoryId,
            @RequestHeader("Authorization") String token) {
        // 解析JWT获取当前用户account
        String account = JwtTokenUtil.getAccountFromToken(token.replace("Bearer ", ""));
        // 上传文件并保存元数据
        FileDTO fileDTO;
        try {
            fileDTO = fileService.upload(file, directoryId, account);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ApiResult.success(fileDTO);
    }

    /**
     * 文件下载（双重权限控制）
     */
    @GetMapping("/download/{fileId}")
    public void downloadFile(
            @PathVariable("fileId") String fileId,
            @RequestHeader("Authorization") String token,
            HttpServletResponse response) throws IOException {
        String account = JwtTokenUtil.getAccountFromToken(token.replace("Bearer ", ""));
        // ToDo: 角色校验
        List<String> userRoleCodes = List.of("admin");

        // 权限校验（核心）
        boolean hasPermission = fileService.checkDownloadPermission(fileId, account, userRoleCodes);
        if (!hasPermission) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "无下载权限");
            return;
        }

        // 下载文件
        fileService.download(fileId, response);
    }
}
