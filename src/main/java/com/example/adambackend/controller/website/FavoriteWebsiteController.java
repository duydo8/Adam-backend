package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.FavoriteId;
import com.example.adambackend.entities.Product;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.response.ProductDto;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.FavoriteService;
import com.example.adambackend.service.ProductSevice;
import org.apache.tomcat.jni.Local;
import org.dom4j.rule.Mode;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController("favorite")
public class FavoriteWebsiteController {
    @Autowired
    FavoriteService favoriteService;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    AccountService accountService;
    @GetMapping("findProductFavoriteByAccountId")
    public ResponseEntity<?> findProductFavoriteByAccountId(@RequestParam("account_id")Long accountId){
        Optional<Account> account= accountService.findById(accountId);
        if(account.isPresent()){
            ProductDto productDto = modelMapper.map(favoriteService.findProductFavoriteByAccountId(accountId),ProductDto.class);
            return ResponseEntity.ok().body(new IGenericResponse<ProductDto>(productDto,200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found Account"));
    }
    @GetMapping("findTop10FavoriteProduct")
    public  ResponseEntity<?> findTop10FavoriteProduct(){
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(favoriteService.findTop10FavoriteProduct(),200,""));
    }
    @PostMapping("create")
    public ResponseEntity<?> createFavorite(@RequestParam("product_id")Long productId,@RequestParam("account_id")Long accountId){
        Favorite favorite = favoriteService.findByAccountIdAndProductId(accountId,productId);
        if(favorite==null){
            FavoriteId favoriteId= new FavoriteId(accountId,productId);
            return ResponseEntity.ok().body(new IGenericResponse<Favorite>(favoriteService.save(new Favorite(favoriteId,LocalDateTime.now(),
                    accountService.findById(accountId).get(),productSevice.findById(productId).get())),200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"Exist"));

    }
    @DeleteMapping("delete")
    public ResponseEntity<?> deleteFavorite(@RequestParam("product_id")Long productId,@RequestParam("account_id")Long accountId){
        Favorite favorite = favoriteService.findByAccountIdAndProductId(accountId,productId);
        if(favorite==null){
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"Not Exist"));

        }else{
            favoriteService.deleteFavoriteByAccountIdAndProductId(accountId,productId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200,""));
        }

    }




}
