package com.example.adambackend.service.impl;


import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.example.adambackend.payload.detailProduct.DetailProductDTO;
import com.example.adambackend.payload.detailProduct.DetailProductRequest;
import com.example.adambackend.payload.detailProduct.DetailProductUpdateAdmin;
import com.example.adambackend.payload.detailProduct.NewDetailProductDTO;
import com.example.adambackend.repository.DetailProductRepository;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DetailProductServiceImpl implements DetailProductService {
	@Autowired
	private DetailProductRepository detailProductRepository;

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private ColorService colorService;

	@Autowired
	private SizeService sizeService;

	@Override
	public List<DetailProduct> findAll() {
		return detailProductRepository.findAll();
	}

	@Override
	public DetailProduct save(DetailProduct DetailProduct) {
		return detailProductRepository.save(DetailProduct);
	}

	@Override
	public void deleteById(Integer id) {
		detailProductRepository.deleteById(id);
	}

	@Override
	public Optional<DetailProduct> findById(Integer id) {
		return detailProductRepository.findById(id);
	}

	@Override
	public List<DetailProduct> findAllByProductId(Integer idProduct) {
		return detailProductRepository.findAllByProductId(idProduct);
	}

	@Override
	public void deleteByProductId(Integer productId) {
		detailProductRepository.deleteByProductId(productId);
	}

	@Override
	public DetailProduct createDetailProduct(DetailProductDTO detailProductDTO) {
		Optional<Product> product = productSevice.findById(detailProductDTO.getProductId());
		Optional<Color> color = colorService.findById(detailProductDTO.getColorId());
		Optional<Size> size = sizeService.findById(detailProductDTO.getSizeId());
		if (product.isPresent() && color.isPresent()
				&& size.isPresent()) {
			DetailProduct detailProduct = new DetailProduct();
			detailProduct.setQuantity(detailProductDTO.getQuantity());
			detailProduct.setProduct(product.get());
			detailProduct.setImageProduct(detailProductDTO.getProductImage());
			detailProduct.setPriceImport(detailProductDTO.getPriceImport());
			detailProduct.setPriceExport(detailProductDTO.getPriceExport());
			detailProduct.setStatus(1);
			detailProduct.setColor(color.get());
			detailProduct.setSize(size.get());
			detailProduct.setCreateDate(LocalDateTime.now());
			return detailProductRepository.save(detailProduct);
		}
		return null;
	}

	@Override
	public DetailProduct updateDetailProduct(DetailProductUpdateAdmin detailProduct) {
		Optional<DetailProduct> detailProductOptional = detailProductRepository.findById(detailProduct.getId());
		Optional<Size> sizeOptional = sizeService.findById(detailProduct.getSizeId());
		Optional<Color> colorOptional = colorService.findById(detailProduct.getColorId());
		if (detailProductOptional.isPresent() && colorOptional.isPresent() && sizeOptional.isPresent()) {
			detailProductOptional.get().setQuantity(detailProduct.getQuantity());
			detailProductOptional.get().setPriceExport(detailProduct.getPriceExport());
			detailProductOptional.get().setPriceImport(detailProduct.getPriceImport());
			detailProductOptional.get().setStatus(detailProduct.getStatus());
			detailProductOptional.get().setImageProduct(detailProduct.getProductImage());
			detailProductOptional.get().setColor(colorOptional.get());
			detailProductOptional.get().setSize(sizeOptional.get());
			return detailProductRepository.save(detailProductOptional.get());
		}
		return null;
	}

	@Override
	public boolean deleteDetailProduct(Integer productId, Integer detailProductId) {
		Optional<Product> product = productSevice.findById(productId);
		Optional<DetailProduct> detailProduct = detailProductRepository.findById(detailProductId);
		if (product.isPresent() && detailProduct.isPresent()) {
			List<DetailProduct> detailProducts = product.get().getDetailProducts();
			for (DetailProduct detailProduct1 : detailProducts
			) {
				if (detailProduct1.equals(detailProduct.get())) {
					detailProducts.remove(detailProduct1);
					product.get().setDetailProducts(detailProducts);
					productSevice.save(product.get());
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public List<DetailProduct> createListDetailProductByOption(DetailProductRequest detailProductRequest) {
		Optional<Product> productOptional = productSevice.findById(detailProductRequest.getProductId());
		List<DetailProduct> detailProductList = new ArrayList<>();
		if (!productOptional.isPresent()) {
			return detailProductList;
		}
		List<Color> colorList = new ArrayList<>();
		List<Size> sizeList = new ArrayList<>();
		for (Integer colorId : detailProductRequest.getColorIdList()) {
			Optional<Color> color = colorService.findById(colorId);
			if (color.isPresent()) {
				colorList.add(color.get());
			}
		}
		for (Integer s : detailProductRequest.getSizeIdList()) {
			Optional<Size> size = sizeService.findById(s);
			if (size.isPresent()) {
				sizeList.add(size.get());
			}
		}
		DetailProduct detailProduct = new DetailProduct();
		detailProduct.setProduct(productSevice.findById(detailProductRequest.getProductId()).get());
		for (int i = 0; i < sizeList.size(); i++) {
			for (int j = 0; j < colorList.size(); j++) {
				detailProduct.setPriceImport(detailProductRequest.getPriceImport());
				detailProduct.setPriceExport(detailProductRequest.getPriceExport());
				detailProduct.setQuantity(detailProductRequest.getQuantity());
				detailProduct.setCreateDate(LocalDateTime.now());
				detailProduct.setColor(colorList.get(j));
				detailProduct.setSize(sizeList.get(i));
				detailProductList.add(detailProductRepository.save(detailProduct));
			}
		}
		productOptional.get().setStatus(1);
		productSevice.save(productOptional.get());
		return detailProductList;
	}

	@Override
	public List<DetailProduct> updateListDetailProductAfterCreate(List<NewDetailProductDTO> newDetailProductDTOList) {
		List<DetailProduct> detailProducts = new ArrayList<>();
		if (!newDetailProductDTOList.isEmpty()) {
			for (NewDetailProductDTO n : newDetailProductDTOList) {
				Optional<DetailProduct> detailProduct = detailProductRepository.findById(n.getId());
				if (detailProduct.isPresent()) {
					detailProduct.get().setPriceImport(n.getPriceImport());
					detailProduct.get().setPriceExport(n.getPriceExport());
					detailProduct.get().setQuantity(n.getQuantity());
					Product p = productSevice.findByDetailProductId(detailProduct.get().getId());
					p.setStatus(1);
					productSevice.save(p);
					detailProduct.get().setImageProduct(p.getImage());
					detailProducts.add(detailProductRepository.save(detailProduct.get()));
				}
			}
		}
		return detailProducts;
	}

	@Override
	public List<DetailProduct> updateArrayOptionValueDetailProduct(DetailProductRequest detailProductRequest) {
		List<DetailProduct> detailProductList = new ArrayList<>();
		Optional<Product> productOptional = productSevice.findById(detailProductRequest.getProductId());
		if (!productOptional.isPresent()) {
			return detailProductList;
		}
		detailProductRepository.deleteByProductId(detailProductRequest.getProductId());
		List<Color> colorList = new ArrayList<>();
		List<Size> sizeList = new ArrayList<>();
		for (Integer colorId : detailProductRequest.getColorIdList()) {
			Optional<Color> color = colorService.findById(colorId);
			if (color.isPresent()) {
				colorList.add(color.get());
			}
		}
		for (Integer s : detailProductRequest.getSizeIdList()) {
			Optional<Size> size = sizeService.findById(s);
			if (size.isPresent()) {
				sizeList.add(size.get());
			}
		}

		for (int i = 0; i < sizeList.size(); i++) {
			for (int j = 0; j < colorList.size(); j++) {
				DetailProduct detailProduct = new DetailProduct();
				detailProduct.setProduct(productSevice.findById(detailProductRequest.getProductId()).get());
				detailProduct.setPriceImport(detailProductRequest.getPriceImport());
				detailProduct.setPriceExport(detailProductRequest.getPriceExport());
				detailProduct.setQuantity(detailProductRequest.getQuantity());
				detailProduct.setStatus(1);
				detailProduct.setCreateDate(LocalDateTime.now());
				detailProduct.setColor(colorList.get(j));
				detailProduct.setSize(sizeList.get(i));
				detailProductList.add(detailProduct);
				detailProductRepository.save(detailProduct);
			}
		}
		return detailProductList;
	}

	@Override
	public String deleteByListId(List<Integer> listDetailProductId){
		System.out.println(listDetailProductId.size());
		if (!listDetailProductId.isEmpty()) {
			for (Integer id : listDetailProductId) {
				Optional<DetailProduct> detailProduct = detailProductRepository.findById(id);
				if (detailProduct.isPresent()) {
					detailProductRepository.updateDetailProductsDeleted(id);
				}
			}
			return "success";
		}
		return "not found";
	}
}
