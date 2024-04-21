package com.example.adambackend.service.impl;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.MaterialProductPK;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.entities.TagProductPK;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductDTO;
import com.example.adambackend.payload.product.ProductResponse;
import com.example.adambackend.payload.product.ProductTop10Create;
import com.example.adambackend.payload.product.ProductUpdateDTO;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleValue;
import com.example.adambackend.payload.productWebsiteDTO.ProductHandleWebsite;
import com.example.adambackend.payload.request.ProductRequest;
import com.example.adambackend.repository.ProductRepository;
import com.example.adambackend.service.CategoryService;
import com.example.adambackend.service.MaterialProductService;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagProductService;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductSevice {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private TagService tagService;

	@Autowired
	private TagProductService tagProductService;

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialProductService materialProductService;

	@Override
	public Page<Product> findAll(String name, Pageable pageable) {
		return productRepository.findAll(name, pageable);
	}

	@Override
	public Page<Product> findPage(int page, int size) {
		return productRepository.findAll(PageRequest.of(page, size, Sort.by("createDate").descending()));
	}

	@Override
	public Boolean checkFavorite(Integer productId, Integer accountId) {
		List<Integer> listProductId = productRepository.checkFavorite(productId, accountId);
		if (CommonUtil.isNotNull(listProductId) && !listProductId.isEmpty()) {
			return false;
		}
		return true;
	}

	@Override
	public Product save(Product Product) {
		return productRepository.save(Product);
	}

	@Override
	public void deleteById(Integer id) {
		productRepository.deleteById(id);
	}

	@Override
	public Optional<Product> findById(Integer id) {
		return productRepository.findById(id);
	}

	@Override
	public List<ProductTop10Create> findTop10productByCreateDate() {
		return productRepository.findTop10productByCreateDate();
	}

	@Override
	public List<Product> findAllByTagName(String tagName) {
		return productRepository.findAllByTagName(tagName);
	}

	@Override
	public Page<CustomProductFilterRequest> findPageableByOption(Integer categoryId, Integer sizeId, Integer colorId, Integer materialId, Integer tagId,
																 Double bottomPrice, Double topPrice, Pageable pageable) {
		return productRepository.findPageableByOption(categoryId, sizeId,
				colorId, materialId, tagId, bottomPrice, topPrice, pageable);
	}

	@Override
	public List<Product> findTop10ProductBestSale() {
		return productRepository.findTop10ProductBestSale();
	}

	@Override
	public Product findByDetailProductId(Integer detalId) {
		return productRepository.findByDetailProductId(detalId);
	}

	@Override
	public Optional<ProductHandleWebsite> findOptionWebsiteByAccountIdProductId(Integer productId, Integer accountId) {
		return productRepository.findOptionWebsiteByProductId(productId, accountId);
	}

	@Override
	public Optional<ProductHandleValue> findOptionWebsiteByProductId(Integer productId) {
		return productRepository.findOptionByProductId(productId);
	}

	@Override
	public void updateStatusProductById(Integer status, Integer id) {
		productRepository.updateStatusProduct(status, id);
	}

	@Override
	public Product createProduct(ProductDTO productDTO){
		Product product = new Product();
		product.setVoteAverage(0.0);
		product.setStatus(9);
		product.setProductName(productDTO.getProductName());
		product.setDescription(productDTO.getDescription());
		product.setImage(productDTO.getImage());
		product.setCreateDate(LocalDateTime.now());
		product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
		return productRepository.save(product);
	}

	@Override
	public ProductResponse updateProduct(ProductUpdateDTO productUpdateDTO) {
		if (CommonUtil.isNotNull(productUpdateDTO.getId())) {
			return null;
		}
		Optional<Product> product = productRepository.findById(productUpdateDTO.getId());
		if (product.isEmpty()) {
			return null;
		}
		Optional<Category> categoryOptional = categoryService.findById((productUpdateDTO.getCategoryId()));
		product.get().setProductName(productUpdateDTO.getProductName());
		product.get().setVoteAverage(productUpdateDTO.getVoteAverage());
		product.get().setDescription(productUpdateDTO.getDescription());
		product.get().setImage(productUpdateDTO.getImage());
		product.get().setStatus(productUpdateDTO.getStatus());
		if (categoryOptional.isPresent()) {
			product.get().setCategory(categoryOptional.get());
		}
		List<TagProduct> tagProductList = product.get().getTagProducts();
		List<MaterialProduct> materialProductList = product.get().getMaterialProducts();
		tagProductService.updateDeletedByProductId(productUpdateDTO.getId());
		materialProductService.updateDeletedByProductId(productUpdateDTO.getId());

		productRepository.save(product.get());
		List<Material> materialList = new ArrayList<>();
		List<Tag> tagList = new ArrayList<>();
		for (Integer materialId : productUpdateDTO.getMaterialProductIds()) {
			Optional<Material> materialOptional = materialService.findById(materialId);
			if (materialOptional.isPresent()) {
				MaterialProduct materialProduct = new MaterialProduct();
				materialProduct.setProduct(product.get());
				materialProduct.setMaterial(materialOptional.get());
				materialProduct.setMaterialProductPK(new MaterialProductPK(materialId, product.get().getId()));
				materialProduct.setCreateDate(LocalDateTime.now());
				materialProduct.setStatus(1);
				materialProductList.add(materialProduct);
				materialProductService.save(materialProduct);
				materialList.add(materialOptional.get());
			}
		}
		for (Integer s : productUpdateDTO.getTagProductIds()) {
			Optional<Tag> tagOptional = tagService.findById(s);
			if (tagOptional.isPresent()) {
				TagProduct tagProduct = new TagProduct();
				tagProduct.setProduct(product.get());
				tagProduct.setTag(tagOptional.get());
				tagProduct.setTagProductPK(new TagProductPK(s, product.get().getId()));
				tagProduct.setCreateDate(LocalDateTime.now());
				tagProduct.setStatus(1);
				tagProductList.add(tagProduct);
				tagProductService.save(tagProduct);
				tagList.add(tagOptional.get());
			}
		}
		product.get().setTagProducts(tagProductList);
		product.get().setMaterialProducts(materialProductList);
		Product productUpdate = productRepository.save(product.get());
		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(productUpdate.getId());
		productResponse.setProductName(productUpdate.getProductName());
		productResponse.setVoteAverage(productUpdate.getVoteAverage());
		productResponse.setDescription(productUpdate.getDescription());
		productResponse.setImage(productUpdate.getImage());
		productResponse.setCreateDate(productUpdate.getCreateDate());
		productResponse.setCategory(categoryOptional.get());
		productResponse.setTagList(tagList);
		productResponse.setMaterialList(materialList);

		return productResponse;
	}

	@Override
	public ProductResponse createArrayOptionValueProduct(ProductRequest productRequest){
		List<Tag> tagList = new ArrayList<>();
		List<Material> materialList = new ArrayList<>();
		for (Integer materialId : productRequest.getMaterialProductIdList()) {
			Optional<Material> materialOptional = materialService.findById(materialId);
			if (materialOptional.isPresent()) {
				materialList.add(materialOptional.get());
			}
		}
		for (Integer s : productRequest.getTagProductIdList()) {
			Optional<Tag> tagOptional = tagService.findById(s);
			if (tagOptional.isPresent()) {
				tagList.add(tagOptional.get());
			}
		}
		Product product = new Product();
		product.setVoteAverage(0.0);
		product.setStatus(9);
		product.setProductName(productRequest.getProductName());
		product.setDescription(productRequest.getDescription());
		product.setImage(productRequest.getImage());
		product.setCreateDate(LocalDateTime.now());
		Optional<Category> categoryOptional = categoryService.findById(productRequest.getCategoryId());
		if (categoryOptional.isPresent()) {
			product.setCategory(categoryOptional.get());
		}
		product = productRepository.save(product);

		MaterialProduct materialProduct = null;
		TagProduct tagProduct = null;

		for (int i = 0; i < tagList.size(); i++) {
			Optional<Tag> tagOptional = tagService.findById(tagList.get(i).getId());
			if (tagOptional.isPresent()) {
				TagProductPK tagProductPK = new TagProductPK(tagOptional.get().getId(), product.getId());
				tagProduct = new TagProduct(tagProductPK, 1, LocalDateTime.now(), product, tagOptional.get());
				tagProductService.save(tagProduct);
			}
		}

		for (int j = 0; j < materialList.size(); j++) {
			Optional<Material> materialOptional = materialService.findById(materialList.get(j).getId());
			if (materialOptional.isPresent()) {
				MaterialProductPK materialProductPK = new MaterialProductPK(materialOptional.get().getId(), product.getId());
				materialProduct = new MaterialProduct(materialProductPK, 1, LocalDateTime.now(), product, materialOptional.get());
				materialProductService.save(materialProduct);
			}
		}

		ProductResponse productResponse = new ProductResponse();
		productResponse.setId(product.getId());
		productResponse.setProductName(product.getProductName());
		productResponse.setVoteAverage(product.getVoteAverage());
		productResponse.setStatus(9);
		productResponse.setDescription(product.getDescription());
		productResponse.setImage(product.getImage());
		productResponse.setCreateDate(product.getCreateDate());
		productResponse.setCategory(product.getCategory());
		productResponse.setTagList(tagList);
		productResponse.setMaterialList(materialList);
		return productResponse;
	}
}
