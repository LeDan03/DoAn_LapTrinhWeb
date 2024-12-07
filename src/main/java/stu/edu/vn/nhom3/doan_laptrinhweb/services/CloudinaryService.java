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

    public String uploadFile(MultipartFile file) throws IOException {
        try {

            Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String url = uploadResult.get("url") != null ? uploadResult.get("url").toString() : null;
            if (url == null) {
                throw new RuntimeException("Failed to retrieve file URL from Cloudinary response");
            }
            return url;
        } catch (IOException e) {
            System.err.println("Error uploading file to Cloudinary: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to upload file to Cloudinary", e);
        }
    }
}
