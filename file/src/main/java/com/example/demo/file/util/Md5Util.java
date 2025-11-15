package com.example.demo.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5Util {
    // MD5 算法名称
    private static final String MD5_ALGORITHM = "MD5";
    // 字节转十六进制的字符数组（优化性能，避免频繁字符串拼接）
    private static final char[] HEX_CHARS = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};

    /**
     * 计算输入流的 MD5 值（核心方法，适配文件上传场景）
     * @param inputStream 待计算的输入流（如文件流、字节流）
     * @return 32位小写 MD5 字符串
     * @throws IOException 流读取异常
     */
    public static String calculateMd5(InputStream inputStream) throws IOException {
        try (inputStream) {
            // 初始化 MD5 消息摘要
            MessageDigest md5Digest = MessageDigest.getInstance(MD5_ALGORITHM);

            // 缓冲区（1024*8 = 8KB，平衡性能和内存占用）
            byte[] buffer = new byte[8192];
            int readLength;

            // 逐段读取流并更新摘要
            while ((readLength = inputStream.read(buffer)) != -1) {
                md5Digest.update(buffer, 0, readLength);
            }

            // 计算最终摘要并转为 32 位小写字符串
            byte[] digestBytes = md5Digest.digest();
            return bytesToHex(digestBytes);
        } catch (NoSuchAlgorithmException e) {
            // MD5 算法是 Java 标准算法，不会抛出此异常，此处仅做兼容处理
            throw new RuntimeException("MD5 算法不支持", e);
        }
        // 关闭输入流（避免资源泄露）
    }

    /**
     * 计算字节数组的 MD5 值（适配内存中的数据，如处理后的图片字节）
     * @param data 待计算的字节数组
     * @return 32位小写 MD5 字符串
     */
    public static String calculateMd5(byte[] data) {
        try {
            MessageDigest md5Digest = MessageDigest.getInstance(MD5_ALGORITHM);
            md5Digest.update(data);
            return bytesToHex(md5Digest.digest());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 算法不支持", e);
        }
    }

    /**
     * 字节数组转 32 位小写十六进制字符串（高效实现）
     * @param bytes 摘要字节数组（长度 16）
     * @return 32位小写 MD5 字符串
     */
    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            // 取字节的高4位和低4位，映射到十六进制字符
            int value = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_CHARS[value >>> 4];
            hexChars[i * 2 + 1] = HEX_CHARS[value & 0x0F];
        }
        return new String(hexChars);
    }
}
