package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file){
        try {
            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("url") != null ? uploadResult.get("url").toString() : null;
            if (url == null) {
                return "URL is null, upload failed";
            }
            return url;
        } catch (IOException e) {
            return "Can not upload file";
        }
    }

    public void deleteFile(String url){
        try {
            String publicId = extractPublicIdFromUrl(url);
            Map result = cloudinary.uploader().destroy(publicId,ObjectUtils.emptyMap());
            result.get("result");
        }catch (Exception ignored){
        }
    }

    private String extractPublicIdFromUrl(String url) {
        // Cắt phần "https://res.cloudinary.com/xxx/image/upload/" khỏi URL
        String[] parts = url.split("/image/upload/");
        if (parts.length > 1) {
            String publicIdWithExtension = parts[1].split("\\?")[0]; // lấy phần trước dấu "?" nếu có
            publicIdWithExtension = publicIdWithExtension.split("\\.")[0]; // lấy phần trước dấu "."

            // Cắt bỏ phiên bản (version) nếu có (phần bắt đầu bằng "v" theo sau là số)
            if (publicIdWithExtension.startsWith("v")) {
                // Nếu có version, public_id sẽ là phần sau version
                String[] versionAndPublicId = publicIdWithExtension.split("/", 2);
                if (versionAndPublicId.length > 1) {
                    return versionAndPublicId[1];  // trả về phần public_id
                }
            } else {
                return publicIdWithExtension;
            }
        }
        return null; // nếu không tìm thấy public_id
    }

}
