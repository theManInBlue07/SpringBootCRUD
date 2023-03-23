package com.example.TestSpring.serviceImpl;

import com.example.TestSpring.POJO.Product;
import com.example.TestSpring.dao.ProductDao;
import com.example.TestSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service

public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductDao productDao;
    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try{
            if(validateAddNewProduct(requestMap,false)){
                productDao.save(getProductFromMap(requestMap,false));
                return new ResponseEntity<String>("{\"message\":"+"Product added successfully."+"\"}", HttpStatus.OK);
            }else{
                return new ResponseEntity<String>("{\"message\":"+"Invalid Request."+"\"}", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
        }


        return new ResponseEntity<String>("{\"message\":"+"Something went wrong at product service impl. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateAddNewProduct(Map<String,String> requestMap,Boolean validateId){
        if(requestMap.containsKey("name")&&requestMap.containsKey("price")&&requestMap.containsKey("description")){
            if(validateId && requestMap.containsKey("productId")){
                return true;
            } else if (!validateId) {
                return true;
            }

        }
        return false;
    }

    private Product getProductFromMap(Map<String,String> requestMap,Boolean isAdd){
        Product product = new Product();

        if(isAdd){
            product.setId(Integer.parseInt(requestMap.get("productId")));
        }
        product.setName(requestMap.get("name"));
        product.setPrice(Float.parseFloat(requestMap.get("price")));
        product.setDescription(requestMap.get("description"));

        return product;
    }

    @Override
    public ResponseEntity<List<Product>> getAllProduct() {
        try{
            return new ResponseEntity<List<Product>>(productDao.findAll(), HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<List<Product>>(new ArrayList<Product>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try{
            if(validateAddNewProduct(requestMap,true)){
                Optional optional = productDao.findById(Integer.parseInt(requestMap.get("productId")));
                if(!optional.isEmpty()){
                    productDao.save(getProductFromMap(requestMap,true));
                    return new ResponseEntity<String>("{\"message\":"+"Product updated successfully. "+"\"}", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("{\"message\":"+"You are trying to update the product which doesn't exist. "+"\"}", HttpStatus.BAD_REQUEST);
                }
            }else{
                return new ResponseEntity<String>("{\"message\":"+"Invalid Request."+"\"}", HttpStatus.BAD_REQUEST);
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":"+"Something went wrong at product service impl. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> deleteProduct(int productId) {
        try{
            if(productId >= 0){
                productDao.deleteById(productId);
                return new ResponseEntity<String>("{\"message\":"+"Product deleted successfully "+"\"}", HttpStatus.OK);

            }else{
                return new ResponseEntity<String>("{\"message\":"+"Invalid Product Id "+"\"}", HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":"+"Something went wrong at product service impl. "+"\"}", HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
