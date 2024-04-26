package com.example.adambackend.controller.website;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductTop10Create;
import com.example.adambackend.payload.product.ProductWebsiteDTO;
import com.example.adambackend.payload.productWebsiteDTO.*;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.FavoriteRepository;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/product")
public class ProductWebsiteController {
	@Autowired
	private DetailOrderService detailOrderService;
	@Autowired
	private ProductSevice productSevice;
	@Autowired
	private ModelMapper modelMapper;
	@Autowired
	private DetailProductService detailProductService;
	@Autowired
	private TagService tagService;
	@Autowired
	private ColorService colorService;
	@Autowired
	private SizeService sizeService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private MaterialService materialService;
	@Autowired
	private TagProductService tagProductService;
	@Autowired
	private MaterialProductService materialProductService;
	@Autowired
	private FavoriteService favoriteService;

	@GetMapping("findAllByPageble")
	public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
		try {
			Page<Product> products = productSevice.findPage(page, size);
			return ResponseEntity.ok().body(new IGenericResponse(products, 200, "Page product"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findTop10productByCreateDate")
	public ResponseEntity<?> findTop10productByCreateDate() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(productSevice.findTop10productByCreateDate(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("findByOpionalArrayValue")
	public ResponseEntity<?> findByOpionalArrayValue(@RequestBody ProductWebstieFilterDTO productWebstieFilterDTO) {
		try {

			Integer page = productWebstieFilterDTO.getPage();
			Integer size = productWebstieFilterDTO.getSize();
			Pageable pageable = PageRequest.of(page, size);
			Page<CustomProductFilterRequest> customProductFilterRequests = productSevice.findPageableByOption(productWebstieFilterDTO,pageable);

			return ResponseEntity.ok().body(new IGenericResponse<>(customProductFilterRequests, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findProductByTagName")
	public ResponseEntity<?> findProductByTag(@RequestParam("tag_name") String tagName) {
		try {
			Optional<Tag> tagOptional = tagService.findByTagName(tagName);
			if (tagOptional.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findAllByTagName(tagName), 200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findTop10ProductBestSale")
	public ResponseEntity<?> findTop10ProductBestSale() {
		try {
			List<Product> products = productSevice.findTop10ProductBestSale();
			List<ProductWebsiteDTO> productDTOS = new ArrayList<>();
			for (Product product : products) {
				ProductWebsiteDTO productWebsiteDTO = new ProductWebsiteDTO();
				productWebsiteDTO.setId(product.getId());
				productWebsiteDTO.setProductName(product.getProductName());
				productWebsiteDTO.setCreateDate(product.getCreateDate());
				productWebsiteDTO.setDescription(product.getDescription());
				productWebsiteDTO.setImage(product.getImage());
				productWebsiteDTO.setStatus(product.getStatus());
				productWebsiteDTO.setVoteAverage(product.getVoteAverage());
				List<DetailProduct> detailProducts = product.getDetailProducts();
				List<Double> price = detailProducts.stream().map(e -> e.getPriceExport()).collect(Collectors.toList());
				Collections.sort(price);
				Double minPrice = price.get(0);
				Double maxPrice = price.get(price.size() - 1);
				productWebsiteDTO.setMaxPrice(maxPrice);
				productWebsiteDTO.setMinPrice(minPrice);
				productDTOS.add(productWebsiteDTO);
			}
			return ResponseEntity.ok().body(new IGenericResponse<>(productDTOS, 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}

	}

	@GetMapping("findTop10ProductByCountQuantityInOrderDetail")
	public ResponseEntity<?> findTop10ProductByCountQuantityInOrderDetail() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(detailOrderService.findTop10ProductByCountQuantityInOrderDetail(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findOptionProductById")
	public ResponseEntity<?> findOptionProductById(@RequestParam("product_id") Integer productId,
												   @RequestParam(value = "account_id", required = false) Integer accountId) {
		try {
			Optional<Favorite> favorite = favoriteService.findByAccountIdAndProductId(accountId, productId);
			Optional<Product> productOptional = productSevice.findById(productId);
			ProductOptionalDTO productOptionalDTO = null;
			if (productOptional.isPresent()) {

				Optional<ProductHandleValue> productHandleValue = productSevice.findOptionWebsiteByProductId(productId);
				if (productHandleValue.isEmpty()) {
					return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "that bai"));
				}
				if (!favorite.isPresent()) {
					productOptionalDTO = new ProductOptionalDTO(productHandleValue.get().getId(),
							productHandleValue.get().getDescription(), productHandleValue.get().getStatus(),
							productHandleValue.get().getMaxPrice(), productHandleValue.get().getMinPrice()
							, productHandleValue.get().getProductName(), productHandleValue.get().getVoteAverage(), false, null);
				} else {
					productOptionalDTO = new ProductOptionalDTO(productHandleValue.get().getId(),
							productHandleValue.get().getDescription(), productHandleValue.get().getStatus(),
							productHandleValue.get().getMaxPrice(), productHandleValue.get().getMinPrice()
							, productHandleValue.get().getProductName(), productHandleValue.get().getVoteAverage(), true, null);
				}
				List<DetailProduct> detailProducts = detailProductService.findAllByProductId(productId);
				Set<Integer> colorIdList = detailProducts.stream().map(e -> e.getColor().getId()).collect(Collectors.toSet());
				Set<Integer> sizeIdList = detailProducts.stream().map(e -> e.getSize().getId()).collect(Collectors.toSet());
				List<ValueOption> colorOptionList = new ArrayList<>();
				for (Integer x : colorIdList) {
					Optional<Color> color = colorService.findById(x);
					ValueOption colorOption = new ValueOption();
					colorOption.setId(color.get().getId());
					colorOption.setName(color.get().getColorName());
					colorOptionList.add(colorOption);
				}
				OptionProduct optionColorProduct = new OptionProduct("Color", colorOptionList);
				List<ValueOption> sizeOptionList = new ArrayList<>();
				for (Integer x : sizeIdList) {
					Optional<Size> sizeOptional = sizeService.findById(x);
					ValueOption sizeOption = new ValueOption();
					sizeOption.setId(sizeOptional.get().getId());
					sizeOption.setName(sizeOptional.get().getSizeName());
					sizeOptionList.add(sizeOption);
				}
				OptionProduct optionSizeProduct = new OptionProduct("Size", sizeOptionList);
				List<OptionProduct> optionProducts = new ArrayList<>();
				optionProducts.add(optionSizeProduct);
				optionProducts.add(optionColorProduct);
				productOptionalDTO.setOptions(optionProducts);
				return ResponseEntity.ok().body(new IGenericResponse<>(productOptionalDTO, 200, "successfully"));
			} else {
				return ResponseEntity.ok().body(new IGenericResponse<>("", 400, "not found"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
