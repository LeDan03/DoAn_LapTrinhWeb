package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Image;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ImageRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    CloudinaryService cloudinaryService = new CloudinaryService();

    public void addNewImage(int product_id,String fileUrl) {
        Image image = new Image();
        image.setProduct_id(product_id);
        image.setUrl(fileUrl);
        imageRepository.save(image);
    }
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }
    public Image getFirstImage(int product_id) {
        for(Image image : getAllImages()) {
            if(image.getProduct_id() == product_id) {
                return image;
            }
        }
        return null;
    }
    public List<Image> getImagesByProductId(int product_id) {
        List<Image> images = new ArrayList<>();
        for(Image image : getAllImages()) {
            if(image.getProduct_id() == product_id) {
                images.add(image);
            }
        }
        return images;
    }
    public void updateImages(int product_id, List<String> urls) {
        int i = 0;
        List<Image> images = getImagesByProductId(product_id);
        if(images.size() == urls.size()) {
            for(Image image : images) {
                image.setUrl(urls.get(i));
                imageRepository.save(image);
            }
        }
        else if(images.size() > urls.size()) {
            for (int j = 0; j < images.size()-urls.size(); j++) {
                cloudinaryService.deleteFile(images.get(j).getUrl());
                imageRepository.delete(images.get(j));
                images = getImagesByProductId(product_id);
            }
            for(int j = 0; j<urls.size(); j++)
            {
                Image img = images.get(j);
                img.setUrl(urls.get(j));
                imageRepository.save(img);
            }

        }
        else
        {
            for(int j = 0; j<images.size();j++)
            {
                Image img = images.get(j);
                img.setUrl(urls.get(j));
                imageRepository.save(img);
            }
            for (int j = 0; j < urls.size()-images.size(); j++) {
                addNewImage(product_id, urls.get(j));
            }
        }
    }

    public void deleteAllImageFromProduct(int product_id) {
        List<Image> images = getImagesByProductId(product_id);
        imageRepository.deleteAll(images);
    }

    public List<String> getProductImagesLink(List<Image> images) {
        List<String> links = new ArrayList<>();
        for(Image image : images)
            links.add(image.getUrl());
        return links;
    }
}
