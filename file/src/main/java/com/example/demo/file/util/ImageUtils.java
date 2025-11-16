package com.example.demo.file.util;

import com.example.demo.file.exception.BusinessException;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Component
public class ImageUtils {
    // 支持的图片格式（避免恶意文件）
    private static final Set<String> SUPPORTED_IMAGE_FORMATS = Set.of("png", "jpg", "jpeg", "webp", "gif");
    private static final Tika TIKA = new Tika();

    /**
     * 校验是否为合法图片（文件头校验+格式校验）
     */
    public void validateImage(MultipartFile file) throws BusinessException {
        // 1. 校验文件大小（头像建议不超过5MB）
        long maxSize = 5 * 1024 * 1024; // 5MB
        if (file.getSize() > maxSize) {
            throw new BusinessException("图片大小不能超过5MB");
        }

        // 2. 文件头校验（避免后缀名伪装）
        String mimeType;
        try {
            mimeType = TIKA.detect(file.getInputStream());
        } catch (IOException e) {
            throw new BusinessException("图片类型校验失败");
        }
        if (!mimeType.startsWith("image/")) {
            throw new BusinessException("请上传合法的图片文件");
        }

        // 3. 格式校验（仅支持指定格式）
        String fileExt = Objects.requireNonNull(FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();
        if (!SUPPORTED_IMAGE_FORMATS.contains(fileExt)) {
            throw new BusinessException("支持的图片格式：" + String.join(",", SUPPORTED_IMAGE_FORMATS));
        }
    }

    /**
     * 图片处理：裁剪为固定尺寸 + 压缩 + 格式转换
     * @param inputStream 原图输入流
     * @param targetWidth 目标宽度
     * @param targetHeight 目标高度
     * @param targetFormat 目标格式（png/jpg/webp）
     * @param quality 压缩质量（0.1-1.0，1.0为无损）
     * @return 处理后的图片字节数组
     */
    public byte[] processImage(InputStream inputStream, int targetWidth, int targetHeight, String targetFormat, float quality) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // 核心处理：裁剪（居中裁剪）+ 压缩 + 格式转换
        Thumbnails.of(inputStream)
                .size(targetWidth, targetHeight)
                .crop(Positions.CENTER) // 居中裁剪，避免拉伸
                .outputFormat(targetFormat)
                .outputQuality(quality)
                .toOutputStream(outputStream);

        return outputStream.toByteArray();
    }

    /**
     * 生成多种尺寸的头像（如200x200、400x400、原图）
     */
    public Map<String, byte[]> generateAvatarSizes(InputStream originalStream) throws IOException {
        Map<String, byte[]> avatarMap = new HashMap<>();

        // 1. 200x200（列表展示用，低质量压缩）
        byte[] smallAvatar = processImage(originalStream, 200, 200, "webp", 0.7f);
        avatarMap.put("200x200", smallAvatar);

        // 2. 400x400（个人中心展示用，中质量压缩）
        originalStream.reset(); // 重置流指针（需确保输入流支持reset）
        byte[] mediumAvatar = processImage(originalStream, 400, 400, "webp", 0.85f);
        avatarMap.put("400x400", mediumAvatar);

        // 3. 原图（保留，用于下载）
        originalStream.reset();
        byte[] originalAvatar = IOUtils.toByteArray(originalStream);
        avatarMap.put("original", originalAvatar);

        return avatarMap;
    }
}
