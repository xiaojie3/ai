package com.example.demo.file.controller;

import com.example.demo.common.model.ApiResult;
import com.example.demo.common.util.JwtTokenUtil;
import com.example.demo.file.model.dto.FileDTO;
import com.example.demo.file.service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    private final FileService fileService;
    private final MessageSource messageSource;
    private final JwtTokenUtil jwtTokenUtil;

    /**
     * 文件上传（需登录）
     */
    @PostMapping("/upload")
    public ApiResult<FileDTO> uploadFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("directoryId") String directoryId,
            @RequestHeader("Authorization") String token) {
        // 上传文件并保存元数据
        FileDTO fileDTO;
        try {
            fileDTO = fileService.upload(file, directoryId, jwtTokenUtil.getUserIdFromToken(token));
        } catch (IOException e) {
            return ApiResult.error(messageSource.getMessage("file.upload.fail", null, LocaleContextHolder.getLocale()));
        }
        return ApiResult.success(fileDTO, messageSource.getMessage("file.upload.success", null, LocaleContextHolder.getLocale()));
    }

    /**
     * 文件下载（双重权限控制）
     */
    @GetMapping("/download/{fileId}")
    public ApiResult<String> downloadFile(
            @PathVariable("fileId") String fileId,
            @RequestHeader("Authorization") String token,
            HttpServletResponse response) {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        // ToDo: 角色获取
        List<String> userRoleCodes = List.of("admin");

        // 权限校验（核心）
        boolean hasPermission = fileService.checkDownloadPermission(fileId, userId, userRoleCodes);
        if (!hasPermission) {
            return ApiResult.error(HttpServletResponse.SC_FORBIDDEN, messageSource.getMessage("file.download.notPermission", null, LocaleContextHolder.getLocale()));
        }

        // 下载文件
        try {
            fileService.download(fileId, userId, response);
        } catch (IOException e) {
            return ApiResult.error(HttpServletResponse.SC_FORBIDDEN, messageSource.getMessage("file.download.fail", null, LocaleContextHolder.getLocale()));
        }
        return ApiResult.success(messageSource.getMessage("file.download.success", null, LocaleContextHolder.getLocale()));
    }

    @PostMapping("/delete/{fileId}")
    public void deleteFile(@PathVariable("fileId") String fileId,@RequestHeader("Authorization") String token) throws IOException {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        fileService.delete(fileId, userId);
    }

    @GetMapping("/preview/{fileId}")
    public void previewFile(@PathVariable("fileId") String fileId,
                            @RequestHeader("Authorization") String token,
                            HttpServletResponse response) throws IOException {
        String userId = jwtTokenUtil.getUserIdFromToken(token);
        fileService.download(fileId, userId, response);
    }
}
