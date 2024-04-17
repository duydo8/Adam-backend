package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.payload.detailProduct.CustomDetailProductResponse;
import com.example.adambackend.payload.detailProduct.DetailProductDTO;
import com.example.adambackend.payload.detailProduct.DetailProductRequest;
import com.example.adambackend.payload.detailProduct.DetailProductUpdateAdmin;
import com.example.adambackend.payload.detailProduct.ListDetailProductId;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(value = "*", maxAge = 36000000)
@RequestMapping("admin/detailProduct")
public class DetailProductController {

	@Autowired
	private DetailProductService detailProductService;

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private ColorService colorService;

	@Autowired
	private SizeService sizeService;

	@PostMapping("create")
	public ResponseEntity<?> createDetailProduct(@RequestBody DetailProductDTO detailProductDTO) {
		try {
			DetailProduct detailProduct = detailProductService.createDetailProduct(detailProductDTO);
			if (CommonUtil.isNotNull(detailProduct)) {
				return ResponseEntity.ok().body(new IGenericResponse(detailProduct, 200, "success"));
			}
			return ResponseEntity.ok().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> updateDetailProduct(@RequestBody DetailProductUpdateAdmin detailProductUpdateAdmin) {
		try {
			DetailProduct detailProduct = detailProductService.updateDetailProduct(detailProductUpdateAdmin);
			if (CommonUtil.isNotNull(detailProduct)) {
				return ResponseEntity.ok().body(new IGenericResponse(detailProduct, 200, "success"));
			}
			return ResponseEntity.ok().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteDetailProduct(@RequestParam("product_id") Integer productId, @RequestParam("detail_product_id") Integer detailProductId) {
		try {
			boolean isDeleted = detailProductService.deleteDetailProduct(productId, detailProductId);
			if (isDeleted) {
				return ResponseEntity.badRequest().body(new IGenericResponse(200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("createArrayOptionValueDetailProduct")
	public ResponseEntity<?> createArrayOptionValueDetailProduct(@RequestBody DetailProductRequest detailProductRequest) {
		try {
			List<DetailProduct> detailProductList = detailProductService.createListDetailProductByOption(detailProductRequest);
			if (!detailProductList.isEmpty()) {
				return ResponseEntity.badRequest().body(new IGenericResponse(detailProductList, 200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}


	@PutMapping("updateArrayOptionValueDetailProduct")
	public ResponseEntity<?> updateArrayOptionValueDetailProduct(@RequestBody DetailProductRequest detailProductRequest) {
		try {
			List<DetailProduct> detailProducts = detailProductService.updateArrayOptionValueDetailProduct(detailProductRequest);
			if (!detailProducts.isEmpty()) {
				return ResponseEntity.badRequest().body(new IGenericResponse(detailProducts, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateListDetailProductAfterCreate")
	public ResponseEntity<?> updateListDetailProductAfterCreate(@RequestBody CustomDetailProductResponse customDetailProductResponse) {
		try {
			List<DetailProduct> detailProducts = detailProductService.updateListDetailProductAfterCreate(customDetailProductResponse
					.getNewDetailProductDTOList());
			if (!detailProducts.isEmpty()) {
				return ResponseEntity.badRequest().body(new IGenericResponse(detailProducts, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "nothing updated"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListDetailProductId listDetailProductId) {
		try {
			String message = detailProductService.deleteByListId(listDetailProductId.getListDetailProductId());
			return ResponseEntity.ok().body(new IGenericResponse(200, message));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
