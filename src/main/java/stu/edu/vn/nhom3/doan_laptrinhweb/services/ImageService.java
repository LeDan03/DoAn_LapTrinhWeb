package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Image;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ImageRepository;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public void addNewImage(int product_id,String fileUrl) {
        Image image = new Image();
        image.setProduct_id(product_id);
        image.setUrl(fileUrl);
        imageRepository.save(image);
    }
}
