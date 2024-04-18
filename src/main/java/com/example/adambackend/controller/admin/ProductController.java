package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Category;
import com.example.adambackend.entities.Color;
import com.example.adambackend.entities.DetailProduct;
import com.example.adambackend.entities.Material;
import com.example.adambackend.entities.MaterialProduct;
import com.example.adambackend.entities.MaterialProductPK;
import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Size;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.entities.TagProduct;
import com.example.adambackend.entities.TagProductPK;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.product.ListProductIdDTO;
import com.example.adambackend.payload.product.ProductDTO;
import com.example.adambackend.payload.product.ProductResponse;
import com.example.adambackend.payload.product.ProductUpdateDTO;
import com.example.adambackend.payload.product.ProductUpdateIsActive;
import com.example.adambackend.payload.productWebsiteDTO.OptionalProduct;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.CategoryService;
import com.example.adambackend.service.ColorService;
import com.example.adambackend.service.CommentService;
import com.example.adambackend.service.DetailProductService;
import com.example.adambackend.service.FavoriteService;
import com.example.adambackend.service.MaterialProductService;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.SizeService;
import com.example.adambackend.service.TagProductService;
import com.example.adambackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(value = "*", maxAge = 36000000)
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

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ModelMapper modelMapper;


    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page,
                                              @RequestParam("size") int size
            , @RequestParam(value = "name", required = false) String name) {
        try {
            Pageable pageable= PageRequest.of(page, size,Sort.by("createDate").descending());
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
                Product product = new Product();
                product.setVoteAverage(0.0);
                product.setStatus(9);
                product.setProductName(productDTO.getProductName());
                product.setDescription(productDTO.getDescription());
                product.setImage(productDTO.getImage());
                product.setCreateDate(LocalDateTime.now());
                product.setCategory(categoryService.findById(productDTO.getCategoryId()).get());
                return ResponseEntity.ok().body(new IGenericResponse<Product>(productSevice.save(product), 200, "success"));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody ProductUpdateDTO productUpdateDTO) {
        try {
            Optional<Product> product1 = productSevice.findById(productUpdateDTO.getId());
            Optional<Category> categoryOptional = categoryService.findById((productUpdateDTO.getCategoryId()));
            if (product1.isPresent() ) {
                product1.get().setProductName(productUpdateDTO.getProductName());
                product1.get().setVoteAverage(productUpdateDTO.getVoteAverage());
                product1.get().setDescription(productUpdateDTO.getDescription());
                product1.get().setImage(productUpdateDTO.getImage());

                product1.get().setStatus(productUpdateDTO.getStatus());
                if(categoryOptional.isPresent()){
                    product1.get().setCategory(categoryOptional.get());
                }
                List<TagProduct> tagProductList = product1.get().getTagProducts();
                List<MaterialProduct> materialProductList = product1.get().getMaterialProducts();
                tagProductService.updateDeletedByProductId(productUpdateDTO.getId());
                materialProductService.updateDeletedByProductId(productUpdateDTO.getId());

                productSevice.save(product1.get());
                List<Material> materialList = new ArrayList<>();
                List<Tag> tagList = new ArrayList<>();
                for (Integer materialId : productUpdateDTO.getMaterialProductIds()
                ) {

                    Optional<Material> materialOptional = materialService.findById(materialId);
                    if (materialOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false) {
                        MaterialProduct materialProduct = new MaterialProduct();
                        materialProduct.setProduct(product1.get());
                        materialProduct.setMaterial(materialOptional.get());
                        materialProduct.setMaterialProductPK(new MaterialProductPK(materialId, product1.get().getId()));
                        materialProduct.setCreateDate(LocalDateTime.now());
                        materialProduct.setIsDeleted(false);
                        materialProduct.setIsActive(true);
                        materialProductList.add(materialProduct);
                        materialProductRepository.save(materialProduct);
                        materialList.add(materialOptional.get());
                    }
                }
                for (Integer s : productUpdateDTO.getTagProductIds()
                ) {
                    Optional<Tag> tagOptional = tagService.findById(s);
                    if (tagOptional.isPresent() && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                        TagProduct tagProduct = new TagProduct();
                        tagProduct.setProduct(product1.get());
                        tagProduct.setTag(tagOptional.get());
                        tagProduct.setTagProductPK(new TagProductPK(s, product1.get().getId()));
                        tagProduct.setCreateDate(LocalDateTime.now());
                        tagProduct.setIsDeleted(false);
                        tagProduct.setIsActive(true);
                        tagProductList.add(tagProduct);
                        tagProductRepository.save(tagProduct);
                        tagList.add(tagOptional.get());
                    }

                }
                product1.get().setTagProducts(tagProductList);
                product1.get().setMaterialProducts(materialProductList);
                Product product = productSevice.save(product1.get());
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setProductName(product.getProductName());
                productResponse.setVoteAverage(product.getVoteAverage());

                productResponse.setDescription(product.getDescription());
                productResponse.setImage(product.getImage());
                productResponse.setCreateDate(product.getCreateDate());

                productResponse.setCategory(categoryOptional.get());
                productResponse.setTagList(tagList);
                productResponse.setMaterialList(materialList);

                return ResponseEntity.ok().body(new IGenericResponse<ProductResponse>(productResponse, 200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "Xin chờ"));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("product_id") Integer productId) {
        try {
            Optional<Product> product1 = productSevice.findById(productId);
            if (product1.isPresent()) {
                productSevice.deleteById(productId);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "success"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("updateStatus")
    public ResponseEntity updateIsActive(@RequestBody ProductUpdateIsActive productUpdateIsActive) {
        try {
            System.out.println(productUpdateIsActive.getStatus()+" "+ productUpdateIsActive.getId());
            Optional<Product> product1 = productSevice.findById(productUpdateIsActive.getId());
            if (product1.isPresent()) {

                productSevice.updateProductsIsActive(productUpdateIsActive.getStatus(), productUpdateIsActive.getId());
                return ResponseEntity.ok().body(new IGenericResponse<>(200, "Thành công"));
            }
            return ResponseEntity.badRequest().body(new IGenericResponse(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("createArrayOptionValueProduct")
    public ResponseEntity<?> createArrayOptionValueProduct(Integer productId, Integer detailProductId) {
        try {
            List<Tag> tagList = new ArrayList<>();
            List<Material> materialList = new ArrayList<>();

            for (Integer materialId : productRequest.getMaterialProductIdList()
            ) {

                Optional<Material> materialOptional = materialService.findById(materialId);
                if (materialOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false) {
                    materialList.add(materialOptional.get());
                }
            }
            for (Integer s : productRequest.getTagProductIdList()
            ) {
                Optional<Tag> tagOptional = tagService.findById(s);
                if (tagOptional.isPresent() && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                    tagList.add(tagOptional.get());
                }

            }


            Product product = new Product();
            product.setVoteAverage(0.0);
            product.setIsDelete(false);
            product.setIsComplete(false);
            product.setProductName(productRequest.getProductName());
            product.setDescription(productRequest.getDescription());
            product.setImage(productRequest.getImage());
            product.setCreateDate(LocalDateTime.now());
            product.setCategory(categoryService.findById(productRequest.getCategoryId()).get());
            product.setIsActive(true);
            productSevice.save(product);
            List<TagProduct> tagProductList = new ArrayList<>();
            List<MaterialProduct> materialProductList = new ArrayList<>();
            MaterialProduct materialProduct = null;
            TagProduct tagProduct = null;
            if (categoryService.findById(productRequest.getCategoryId()).isPresent()) {
                for (int i = 0; i < tagList.size(); i++) {
                    for (int j = 0; j < materialList.size(); j++) {
                        Optional<Material> materialOptional = materialService.findById(materialList.get(j).getId());
                        Optional<Tag> tagOptional = tagService.findById(tagList.get(i).getId());
                        if (materialOptional.isPresent() && tagOptional.isPresent() && materialOptional.get().getIsActive() == true && materialOptional.get().getIsDeleted() == false
                                && tagOptional.get().getIsActive() == true && tagOptional.get().getIsDelete() == false) {
                            materialProduct = new MaterialProduct
                                    (new MaterialProductPK(materialOptional.get().getId(), product.getId()),
                                            false, materialOptional.get(), true, LocalDateTime.now(), product);
                            tagProduct = new TagProduct(new TagProductPK(tagOptional.get().getId(), product.getId()), false, tagOptional.get(), true, product, LocalDateTime.now());
                            materialProductRepository.save(materialProduct);
                            tagProductRepository.save(tagProduct);
                            materialProductList.add(materialProduct);
                            tagProductList.add(tagProduct);
                        }
                    }


                }
                product.setTagProducts(tagProductList);
                product.setMaterialProducts(materialProductList);
                Product product1 = productSevice.save(product);
                ProductResponse productResponse = new ProductResponse();
                productResponse.setId(product.getId());
                productResponse.setProductName(product1.getProductName());
                productResponse.setVoteAverage(product1.getVoteAverage());
                productResponse.setIsDelete(product1.getIsDelete());
                productResponse.setDescription(product1.getDescription());
                productResponse.setImage(product1.getImage());
                productResponse.setCreateDate(product1.getCreateDate());
                productResponse.setIsActive(product1.getIsActive());
                productResponse.setCategory(product.getCategory());
                productResponse.setTagList(tagList);
                productResponse.setMaterialList(materialList);
                productResponse.setIsComplete(false);
                return ResponseEntity.ok().body(new IGenericResponse<>(productResponse, 200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not exists"));

            }
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
                List<Integer> listTagId = tagProductRepository.findTagIdByProductId(id);
                List<Integer> listMaterialId = materialProductRepository.findMaterialIdByProductId(id);
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
                for (Integer x : listTagId
                ) {
                    Optional<Tag> tagOptional = tagService.findById(x);
                    tagList.add(tagOptional.get());

                }
                for (Integer x : listMaterialId
                ) {
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
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListProductIdDTO listProductIdDTO) {
        try {
            List<Integer> list = listProductIdDTO.getListProductId();


            if (list.size() > 0) {
                for (Integer x : list
                ) {
                    Optional<Product> productOptional = productSevice.findById(x);

                    if (productOptional.isPresent()) {
                        System.out.println(x);
                        productSevice.updateProductsDeleted(x);
                    }
                }
                return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
