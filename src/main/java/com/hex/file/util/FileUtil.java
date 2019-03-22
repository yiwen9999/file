package com.hex.file.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

/**
 * User: hexuan
 * Date: 2017/8/24
 * Time: 下午3:05
 */
public class FileUtil {
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void uploadImgFile(MultipartFile multipartFile, String filePath, String fileName, Long zipFileLimit) throws Exception {
        if (multipartFile.getSize() / zipFileLimit >= 2) { // 大于2m，进行图片压缩
            ImageZipUtil.zipImageFile(multipartFile.getInputStream(), new File(filePath + fileName), 0, 0, 0.25f);
        } else {
            FileUtil.uploadFile(multipartFile.getBytes(), filePath, fileName);
        }
    }

    public static String uploadImgFileNew(MultipartFile multipartFile, String oldFileName, String defaultFileName, String path, Long zipFileLimit) throws Exception {
        String fileName = multipartFile.getOriginalFilename();
        String suffixName = fileName.substring(fileName.lastIndexOf("."));
        fileName = UUID.randomUUID() + suffixName;

        if (StringUtils.isNotBlank(oldFileName) && !oldFileName.equals(defaultFileName)) {
            File deleteFile = new File(path + oldFileName);
            if (deleteFile.exists()) {
                deleteFile.delete();
            }
        }

        if (multipartFile.getSize() / zipFileLimit >= 2) { // 大于2m，进行图片压缩
            ImageZipUtil.zipImageFile(multipartFile.getInputStream(), new File(path + fileName), 0, 0, 0.25f);
        } else {
            FileUtil.uploadFile(multipartFile.getBytes(), path, fileName);
        }

        return fileName;
    }

    public static void deleteFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    public static void deleteFile(String path, String fileName) {
        File file = new File(path + fileName);
        if (file.exists()) {
            file.delete();
        }
    }
}
