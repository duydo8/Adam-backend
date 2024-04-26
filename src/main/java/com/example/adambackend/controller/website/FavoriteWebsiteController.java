package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Favorite;
import com.example.adambackend.entities.FavoriteId;
import com.example.adambackend.entities.Product;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.FavoriteService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("favorite")
public class FavoriteWebsiteController {

	@Autowired
	private FavoriteService favoriteService;

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private AccountService accountService;

	@GetMapping("findProductFavoriteByAccountId")
	public ResponseEntity<?> findProductFavoriteByAccountId(@RequestParam("account_id") Integer accountId) {
		try {
			Optional<Account> account = accountService.findById(accountId);
			if (account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(favoriteService.findProductFavoriteByAccountId(accountId),
						200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Account"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findTop10FavoriteProduct")
	public ResponseEntity<?> findTop10FavoriteProduct() {
		try {
			List<Integer> list = favoriteService.findTop10FavoriteProduct();
			List<ProductHandleWebsite> productHandleWebsites = list.stream().map(e -> favoriteService.findProductById(e)).collect(Collectors.toList());
			return ResponseEntity.ok().body(new IGenericResponse<>(productHandleWebsites, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("create")
	public ResponseEntity<?> createFavorite(@RequestParam("product_id") Integer productId, @RequestParam("account_id") Integer accountId) {
		try {
			Optional<Favorite> favorite = favoriteService.findByAccountIdAndProductId(accountId, productId);
			Optional<Account> accountOptional = accountService.findById(accountId);
			Optional<Product> productOptional = productSevice.findById(productId);
			if (productOptional.isPresent() && accountOptional.isPresent()) {
				if (favorite.isPresent()) {
					favoriteService.deleteByIdAccountAndProduct(accountId, productId);
				} else {
					FavoriteId favoriteId = new FavoriteId(accountId, productId);
					favoriteService.save(new Favorite(favoriteId, LocalDateTime.now(), 1, accountOptional.get(), productOptional.get()));
				}
				return ResponseEntity.ok().body(new IGenericResponse<Favorite>(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
