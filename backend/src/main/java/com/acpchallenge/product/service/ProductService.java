package com.acpchallenge.product.service;

import com.acpchallenge.product.exception.ProductNotFoundException;
import com.acpchallenge.product.model.Product;
import com.acpchallenge.product.repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public List<Product> findAllProducts() {
        return productRepo.findAll();
    }

    public Product getProductById(Long id) {
        return productRepo
                .findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto com id " + id + " não encontrado"
                ));
    }

    public Product getProductByName(String name) {
        return productRepo
                .findProductByName(name)
                .orElseThrow(() -> new ProductNotFoundException(
                        "Produto " + name + " não encontrado"
                ));
    }

    public Product addProduct(Product product) {
        product.setCode(UUID.randomUUID().toString());
        return productRepo.save(product);
    }

    public Product updateProduct(Long id, Product updatedProduct) {
        productRepo.findProductById(id).map((product) -> {
            product.setName(updatedProduct.getName());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setStatus(updatedProduct.getStatus());

            return productRepo.save(product);
        }).orElseGet(() -> {
            updatedProduct.setId(id);

            return productRepo.save(updatedProduct);
        });

        return productRepo.save(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepo.deleteProductById(id);
    }

    private Long[] convertMethod(String idsToChange) {
        String[] stringIds = idsToChange.split(",");
        Long[] ids = new Long[stringIds.length];
        for (int i = 0; i < stringIds.length; i++) {
            ids[i] = Long.parseLong(stringIds[i]);
        }

        return ids;
    }

    public void deleteProducts(String idsResponse) {
        Long[] ids = this.convertMethod(idsResponse);

        for (Long id : ids) {
            productRepo.deleteProductById(id);
        }
    }
}
