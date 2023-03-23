package com.example.TestSpring.restImpl;

import com.example.TestSpring.POJO.Product;
import com.example.TestSpring.rest.ProductRest;
import com.example.TestSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class ProductRestImpl implements ProductRest {

    @Autowired
    ProductService productService;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            return productService.addNewProduct(requestMap);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":"+"Something went wrong. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        try{
            return productService.getAllProduct();
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            return productService.updateProduct(requestMap);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":"+"Something went wrong at product service impl. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(int productId) {
        try{
            return productService.deleteProduct(productId);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":"+"Something went wrong at product service impl. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
