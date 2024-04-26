package com.example.adambackend.controller.website;

import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Product;
import com.example.adambackend.payload.response.DetailProductDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("productDetail")
public class DetailProductWebsiteController {

	@Autowired
	private DetailProductService detailProductService;

	@Autowired
	private ProductSevice productSevice;

	@GetMapping("findByProductId")
	public ResponseEntity<?> findByProductId(@RequestParam("product_id") Integer productId) {
		try {
			Optional<Product> product = productSevice.findById(productId);
			if (product.isPresent()) {
				List<DetailProduct> detailProducts = detailProductService.findAllByProductId(productId);
				List<DetailProductDto> detailProductDtos = detailProducts.stream().map(e -> new DetailProductDto(e.getId(), e.getQuantity(), e.getPriceImport(),
								e.getPriceExport(), e.getStatus(), e.getImageProduct(), e.getProduct().getProductName(), e.getColor(), e.getSize())).
						collect(Collectors.toList());
				return ResponseEntity.ok().body(new IGenericResponse(detailProductDtos, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found product"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
