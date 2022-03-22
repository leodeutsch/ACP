package com.acpchallenge.product.controller;

import com.acpchallenge.product.model.Product;
import com.acpchallenge.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.findAllProducts();

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/find={id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") Long id) {
        Product product = productService.getProductById(id);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @GetMapping("/find/{name}")
    public ResponseEntity<?> getProductByName(@RequestParam("name") String name) {
        Product product = productService.getProductByName(name);

        System.out.println(product);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.addProduct(product);

        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @PutMapping("/edit={id}")
    public ResponseEntity<Product> editProduct(
            @PathVariable("id") Long id, @RequestBody Product updatedProduct
    ) {
        Product product = productService.updateProduct(id, updatedProduct);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/delete={id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long id) {
        productService.deleteProduct(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/delete-a-few/{ids}")
    public ResponseEntity<?> deleteProducts(@RequestParam("ids") String ids) {
        productService.deleteProducts(ids);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
