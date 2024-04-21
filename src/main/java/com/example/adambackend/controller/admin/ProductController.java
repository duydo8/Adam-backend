package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.payload.product.ListProductIdDTO;
import com.example.adambackend.payload.product.ProductDTO;
import com.example.adambackend.payload.product.ProductUpdateDTO;
import com.example.adambackend.payload.product.ProductUpdateIsActive;
import com.example.adambackend.payload.productWebsiteDTO.OptionalProduct;
import com.example.adambackend.payload.request.ProductRequest;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CategoryService;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.MaterialProductService;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.SizeService;
import com.example.adambackend.service.TagProductService;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("admin/product")
public class ProductController {

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private TagService tagService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private MaterialProductService materialProductService;

	@Autowired
	private TagProductService tagProductService;

	@Autowired
	private SizeService sizeService;

	@Autowired
	private ColorService colorService;

	@Autowired
	private DetailProductService detailProductService;

	@GetMapping("findAllByPageble")
	public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page,
	                                          @RequestParam("size") int size
			, @RequestParam(value = "name", required = false) String name) {
		try {
			Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
			return ResponseEntity.ok().body(new IGenericResponse<>(productSevice.findAll(name, pageable), 200, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("create")
	public ResponseEntity<?> create(@RequestBody ProductDTO productDTO) {
		try {
			if (categoryService.findById(productDTO.getCategoryId()).isPresent()) {

				return ResponseEntity.ok().body(new IGenericResponse(productSevice.createProduct(productDTO), 200, "success"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody ProductUpdateDTO productUpdateDTO) {
		try {
			Optional<Product> product = productSevice.findById(productUpdateDTO.getId());
			if (product.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse(productSevice.updateProduct(productUpdateDTO), 200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("product_id") Integer productId) {
		try {
			Optional<Product> product1 = productSevice.findById(productId);
			if (product1.isPresent()) {
				productSevice.deleteById(productId);
				return ResponseEntity.ok().body(new IGenericResponse(200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("updateStatus")
	public ResponseEntity updateIsActive(@RequestBody ProductUpdateIsActive productUpdateIsActive) {
		try {
			System.out.println(productUpdateIsActive.getStatus() + " " + productUpdateIsActive.getId());
			Optional<Product> product1 = productSevice.findById(productUpdateIsActive.getId());
			if (product1.isPresent()) {
				productSevice.updateStatusProductById(productUpdateIsActive.getStatus(), productUpdateIsActive.getId());
				return ResponseEntity.ok().body(new IGenericResponse<>(200, "Thành công"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("createArrayOptionValueProduct")
	public ResponseEntity<?> createArrayOptionValueProduct(@RequestBody ProductRequest productRequest) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(productSevice.createArrayOptionValueProduct(productRequest), 200, ""));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}

	}

	@GetMapping("findOptionProductById")
	public ResponseEntity<?> findOptionProductById(@RequestParam("id") Integer id) {
		try {
			Optional<Product> productOptional = productSevice.findById(id);
			if (productOptional.isPresent()) {
				List<Integer> listTagId = tagProductService.findTagIdByProductId(id);
				List<Integer> listMaterialId = materialProductService.findMaterialIdByProductId(id);
				List<DetailProduct> detailProductList = detailProductService.findAllByProductId(id);
				Set<Tag> tagList = new HashSet<>();
				Set<Material> materialList = new HashSet<>();
				Set<Color> colorList = new HashSet<>();
				Set<Size> sizeList = new HashSet<>();
				for (DetailProduct dp : detailProductList
				) {
					Optional<Color> c = colorService.findByDetailProductId(dp.getId());
					Optional<Size> s = sizeService.findByDetailProductId(dp.getId());
					if (c.isPresent()) {
						colorList.add(c.get());
					}
					if (s.isPresent()) {
						sizeList.add(s.get());
					}
				}
				for (Integer x : listTagId) {
					Optional<Tag> tagOptional = tagService.findById(x);
					tagList.add(tagOptional.get());
				}
				for (Integer x : listMaterialId) {
					Optional<Material> materialOptional = materialService.findById(x);
					materialList.add(materialOptional.get());
				}
				System.out.println(colorList.size());
				if (colorList.size() == 0) {
					colorList = Collections.<Color>emptySet();
				}
				if (sizeList.size() == 0) {
					sizeList = Collections.<Size>emptySet();
				}
				OptionalProduct optionalProduct = new OptionalProduct(tagList, materialList, colorList, sizeList);
				return ResponseEntity.ok().body(new IGenericResponse<>(optionalProduct, 200, ""));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListProductIdDTO listProductIdDTO) {
		try {
			List<Integer> list = listProductIdDTO.getListProductId();
			if (!list.isEmpty()) {
				for (Integer id : list) {
					Optional<Product> productOptional = productSevice.findById(id);
					if (productOptional.isPresent()) {
						productSevice.updateStatusProductById(0, id);
					}
				}
				return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
