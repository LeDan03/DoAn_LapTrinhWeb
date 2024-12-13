package stu.edu.vn.nhom3.doan_laptrinhweb.services;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import stu.edu.vn.nhom3.doan_laptrinhweb.dto.ProductDTO;
import stu.edu.vn.nhom3.doan_laptrinhweb.model.Product;
import stu.edu.vn.nhom3.doan_laptrinhweb.repository.ProductRepository;
import stu.edu.vn.nhom3.doan_laptrinhweb.response.Response;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Product not found"));
    }
    public void save(Product product) {
        productRepository.save(product);
    }
    public Product addProduct(Product product) {
        if(!isExistedProduct(product.getName()))
        {
            return productRepository.save(product);
        }
        return null;
    }


    public boolean isExistedProduct(String name)
    {
        for(Product p : productRepository.findAll())
        {
            if(p.getName().equals(name))
                return true;
        }
        return false;
    }

    public Response deleteProduct(int id) {


            try{
                Product product = productRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("Product not found"));
                if (product.getId() > 0){
                    productRepository.deleteById(id);
                    return new Response(true, "Xóa thành công");
                }else{
                    return new Response(false, "Xóa lỗi");
                }
            }catch (Exception e) {
                return new Response(false, "Không tìm thấy sản phẩm cần xóa với id = "+ id);
            }
    }
    public ResponseEntity<Product> updateProduct(int id, ProductDTO productDTO) {
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.info("DA VAO UPDATE PRODUCT SERVICE ");
        Product product = findById(id);
        product.setName(productDTO.getName());
        product.setTheme(productDTO.getTheme());
        product.setUnit(productDTO.getUnit());
        product.setCate_id(productDTO.getCategory_id());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        return ResponseEntity.ok(productRepository.save(product));
    }
}
