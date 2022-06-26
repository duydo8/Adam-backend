package com.example.adambackend.controller.admin;

import com.example.adambackend.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/favorite")
public class FavoriteController {
    @Autowired
    FavoriteService favoriteService;

//    @GetMapping("countFavoriteByAccountIdAndProductId")
//    public ResponseEntity<IGenericResponse> countCommentByAccountIdAndProductId(@RequestParam("account_id") int idAccount, @RequestParam("product_id") int idProduct) {
//        return ResponseEntity.ok().body(new IGenericResponse<Integer>(favoriteService.countFavoriteByAccountIdAndProductId(idAccount, idProduct), 200, "countFavoriteByAccountIdAndProductId"));
//    }


}
