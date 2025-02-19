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
@CrossOrigin(value = "*", maxAge = 36000000)
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    private DetailOrderService detailOrderService;
    @Autowired
    private ProductSevice productSevice;
    @Autowired
    private DetailProductService detailProductService;
    @Autowired
    private TagService tagService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private SizeService sizeService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        try {
            Page<Product> page1 = productSevice.findPage(page, size);
            return ResponseEntity.ok().body(new IGenericResponse<>(page1, 200, "Page product"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findTop10productByCreateDate")
    public ResponseEntity<?> findTop10productByCreateDate() {
        try {
            List<ProductTop10Create> products = productSevice.findTop10productByCreateDate();
            List<ProductWebsiteDTO> productDTOS = new ArrayList<>();
            for (ProductTop10Create product : products) {
                ProductWebsiteDTO productWebsiteDTO = new ProductWebsiteDTO();
                productWebsiteDTO.setId(product.getId());
                productWebsiteDTO.setProductName(product.getProductName());
                productWebsiteDTO.setCreateDate(product.getCreateDate());
                productWebsiteDTO.setDescription(product.getDescription());
                productWebsiteDTO.setImage(product.getImage());
                productWebsiteDTO.setIsComplete(product.getIsComplete());
                productWebsiteDTO.setIsActive(product.getIsActive());
                productWebsiteDTO.setIsDelete(product.getIsDelete());
                productWebsiteDTO.setVoteAverage(product.getVoteAverage());
                productWebsiteDTO.setMinPrice(product.getMinPrice());
                productWebsiteDTO.setMaxPrice(product.getMaxPrice());
                productDTOS.add(productWebsiteDTO);
            }
            return ResponseEntity.ok().body(new IGenericResponse<>(productDTOS, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("findByOpionalArrayValue")
    public ResponseEntity<?> findByOpionalArrayValue(@RequestBody ProductWebstieFilterDTO productWebstieFilterDTO) {
        try {
            List<Integer> listCategoryId = productWebstieFilterDTO.getListCategoryId();
            List<Integer> listColorId = productWebstieFilterDTO.getListColorId();
            List<Integer> listSizeId = productWebstieFilterDTO.getListSizeId();
            List<Integer> listMaterialId = productWebstieFilterDTO.getListMaterialId();
            List<Integer> listTagId = productWebstieFilterDTO.getListTagId();
            Double bottomPrice = productWebstieFilterDTO.getBottomPrice();
            Double topPrice = productWebstieFilterDTO.getTopPrice();
            if (listCategoryId == null || listCategoryId.isEmpty() || listCategoryId.isEmpty()) {
                listCategoryId = new ArrayList<>();
            }
            if (listColorId == null || listColorId.isEmpty() || listColorId.isEmpty()) {
                listColorId = new ArrayList<>();
            }
            if (listSizeId == null) {
                listSizeId = new ArrayList<>();
            }
            if (listMaterialId == null || listMaterialId.isEmpty()) {
                listMaterialId = new ArrayList<>();
            }
            if (listTagId == null || listTagId.isEmpty() || listTagId.isEmpty()) {
                listTagId = new ArrayList<>();
            }
            if (bottomPrice == null) {
                bottomPrice = 0.0;
            }
            if (topPrice == null) {
                topPrice = 999999999.0;
            }
            Integer page = productWebstieFilterDTO.getPage();
            Integer size = productWebstieFilterDTO.getSize();
            Pageable pageable = PageRequest.of(page, size);
            Page<CustomProductFilterRequest> customProductFilterRequests = null;
            if (!listCategoryId.isEmpty()) {
                if (!listColorId.isEmpty()) {
                    if (!listSizeId.isEmpty()) {
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listSizeId) {
                                            for (Integer element : listMaterialId) {
                                                for (Integer integer1 : listTagId) {
                                                    customProductFilterRequests = productSevice.findPageableByOption(integer, item,
                                                            value, element, integer1, bottomPrice, topPrice, pageable);
                                                }
                                            }
                                        }
                                    }
                                }
                            } else {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listSizeId) {
                                            for (Integer element : listMaterialId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, item,
                                                        value, element, null, bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listSizeId) {
                                            for (Integer element : listTagId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, item,
                                                        value, null, element, bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            } else {
                                Integer tagId = null;
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listSizeId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(integer, item,
                                                    value, null, tagId, bottomPrice, topPrice, pageable);
                                        }
                                    }

                                }

                            }
                        }
                    } else {
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listMaterialId) {
                                            for (Integer element : listTagId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, null,
                                                        value, item, element, bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            } else {
                                Integer tagId = null;
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listMaterialId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(integer, null,
                                                    value, item, tagId, bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            }
                        } else {
                            Integer materialId = null;
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listTagId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(integer, null, value, materialId, item, bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            } else {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listColorId) {
                                        for (Integer item : listMaterialId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(integer, null,
                                                    value, item, null, bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Integer colorId = null;
                    if (!listSizeId.isEmpty()) {
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listMaterialId) {
                                            for (Integer element : listTagId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, value,
                                                        null, item, element, bottomPrice, topPrice, pageable);
                                            }
                                        }

                                    }
                                }
                            } else {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listMaterialId) {

                                            customProductFilterRequests = productSevice.findPageableByOption(integer, value,
                                                    null, item, null, bottomPrice, topPrice, pageable);

                                        }

                                    }
                                }
                            }
                        } else {
                            Integer materialId = null;
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listColorId) {
                                            for (Integer element : listTagId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, value, item, materialId, element, bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            } else {
                                Integer tagId = null;
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listColorId) {
                                            for (Integer element : listMaterialId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(integer, value,
                                                        item, element, tagId, bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Integer sizeId = null;
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listMaterialId) {
                                        for (Integer item : listTagId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(integer, sizeId,
                                                    colorId, value, item, bottomPrice, topPrice, pageable);
                                        }


                                    }
                                }
                            }
                        } else {
                            Integer materialId = null;
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listCategoryId) {
                                    for (Integer value : listTagId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(integer, sizeId,
                                                colorId, materialId, value, bottomPrice, topPrice, pageable);
                                    }


                                }
                            } else {
                                for (Integer integer : listCategoryId) {
                                    customProductFilterRequests = productSevice.findPageableByOption(integer, sizeId,
                                            colorId, materialId, null, bottomPrice, topPrice, pageable);
                                }

                            }
                        }
                    }
                }
            } else {
                Integer cateId = null;
                if (!listColorId.isEmpty()) {
                    if (!listSizeId.isEmpty()) {
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listMaterialId) {
                                            for (Integer element : listTagId) {
                                                customProductFilterRequests = productSevice.findPageableByOption(cateId, value,
                                                        integer, item, element, bottomPrice, topPrice, pageable);
                                            }
                                        }

                                    }
                                }
                            } else {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listMaterialId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, value,
                                                    integer, item, null, bottomPrice, topPrice, pageable);

                                        }

                                    }
                                }
                            }
                        } else {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listSizeId) {
                                        for (Integer item : listTagId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, value,
                                                    integer, null, item, bottomPrice, topPrice, pageable);
                                        }


                                    }
                                }
                            } else {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listSizeId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(null, value,
                                                integer, null, null, bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        }
                    } else {
                        if (!listMaterialId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listMaterialId) {
                                        for (Integer item : listTagId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                                    integer, value, item, bottomPrice, topPrice, pageable);
                                        }
                                    }

                                }
                            }

                        } else {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listColorId) {
                                    for (Integer value : listTagId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                                integer, null, value, bottomPrice, topPrice, pageable);
                                    }
                                }
                            } else {
                                Integer tagId = null;
                                for (Integer integer : listColorId) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(cateId, null,
                                                integer, null, tagId, bottomPrice, topPrice, pageable);
                                    }
                                }
                            }

                        }
                    }
                } else {
                    Integer colorId = null;
                    if (!listMaterialId.isEmpty()) {
                        if (!listSizeId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listSizeId) {
                                    for (Integer value : listMaterialId) {
                                        for (Integer item : listTagId) {
                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, integer,
                                                    colorId, value, item, bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            } else {
                                for (Integer integer : listSizeId) {
                                    for (Integer value : listMaterialId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(null, integer,
                                                null, value, null, bottomPrice, topPrice, pageable);

                                    }
                                }
                            }
                        } else {
                            if (!listTagId.isEmpty()) {

                                for (Integer integer : listMaterialId) {
                                    for (Integer value : listTagId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                                null, integer, value, bottomPrice, topPrice, pageable);
                                    }
                                }
                            } else {
                                Integer tagId = null;
                                for (Integer integer : listMaterialId) {
                                    customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                            null, integer, tagId, bottomPrice, topPrice, pageable);
                                }
                            }
                        }
                    } else {
                        if (!listSizeId.isEmpty()) {
                            if (!listTagId.isEmpty()) {
                                for (Integer integer : listSizeId) {
                                    for (Integer value : listTagId) {
                                        customProductFilterRequests = productSevice.findPageableByOption(null, integer,
                                                null, null, value, bottomPrice, topPrice, pageable);
                                    }
                                }
                            } else {
                                for (Integer integer : listSizeId) {
                                    customProductFilterRequests = productSevice.findPageableByOption(null, integer,
                                            null, null, null, bottomPrice, topPrice, pageable);
                                }
                            }
                        } else {
                            if (!listTagId.isEmpty()) {
                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                            null, null, listTagId.get(m), bottomPrice, topPrice, pageable);
                                }
                            } else {
                                customProductFilterRequests = productSevice.findPageableByOption(null, null,
                                        null, null, null, bottomPrice, topPrice, pageable);
                            }
                        }
                    }
                }
            }
            return ResponseEntity.ok().body(new IGenericResponse<>(customProductFilterRequests, 200, ""));
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
                return ResponseEntity.ok().body(new IGenericResponse<>(productSevice.findAllByTagName(tagName), 200, ""));
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
                productWebsiteDTO.setIsComplete(product.getIsComplete());
                productWebsiteDTO.setIsActive(product.getIsActive());
                productWebsiteDTO.setIsDelete(product.getIsDelete());
                productWebsiteDTO.setVoteAverage(product.getVoteAverage());
                List<DetailProduct> detailProducts = product.getDetailProducts();

                List<Double> price = detailProducts.stream().
                        map(e -> e.getPriceExport()).collect(Collectors.toList());
                Collections.sort(price);

                Double minPrice = price.get(0);
                Double maxPrice = price.get(price.size() - 1);
                productWebsiteDTO.setMaxPrice(maxPrice);
                productWebsiteDTO.setMinPrice(minPrice);
                productDTOS.add(productWebsiteDTO);

            }

            return ResponseEntity.ok().body(new IGenericResponse<>(productDTOS, 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }

    }

    @GetMapping("findTop10ProductByCountQuantityInOrderDetail")
    public ResponseEntity<?> findTop10ProductByCountQuantityInOrderDetail() {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(detailOrderService.findTop10ProductByCountQuantityInOrderDetail(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findOptionProductById")
    public ResponseEntity<?> findOptionProductById(@RequestParam("product_id") Integer product_id,
                                                   @RequestParam(value = "account_id", required = false) Integer account_id) {
        try {
            Optional<Favorite> favorite = favoriteRepository.findByAccountIdAndProductId(account_id, product_id);
            Optional<Product> productOptional = productSevice.findById(product_id);
            Boolean isFavorite = false;
            ProductOptionalDTO productOptionalDTO = null;
            if (productOptional.isPresent()) {
                if (!favorite.isPresent()) {

                    Optional<ProductHandleValue> productHandleValue = productSevice.findOptionWebsiteByProductId(product_id);
                    if (productHandleValue.isPresent()) {
                        productOptionalDTO = new ProductOptionalDTO(productHandleValue.get().getId(),
                                productHandleValue.get().getDescription(), productHandleValue.get().getIsActive(),
                                productHandleValue.get().getMaxPrice(), productHandleValue.get().getMinPrice()
                                , productHandleValue.get().getProductName(), productHandleValue.get().getVoteAverage(), isFavorite, null);
                        List<DetailProduct> detailProducts = detailProductService.findAllByProductId(product_id);
                        Set<Integer> colorIdList = detailProducts.stream().map(e -> e.getColor().getId()).collect(Collectors.toSet());
                        Set<Integer> sizeIdList = detailProducts.stream().map(e -> e.getSize().getId()).collect(Collectors.toSet());
                        List<ValueOption> colorOptionList = new ArrayList<>();
                        for (Integer x : colorIdList
                        ) {
                            Optional<Color> color = colorService.findById(x);
                            ValueOption colorOption = new ValueOption();
                            colorOption.setId(color.get().getId());
                            colorOption.setName(color.get().getColorName());
                            colorOptionList.add(colorOption);

                        }
                        OptionProduct optionColorProduct = new OptionProduct("Color", colorOptionList);
                        List<ValueOption> sizeOptionList = new ArrayList<>();


                        for (Integer x : sizeIdList
                        ) {
                            Optional<Size> sizeOptional = sizeService.findById(x);
                            ValueOption sizeOption = new ValueOption();
                            sizeOption.setId(sizeOptional.get().getId());
                            sizeOption.setName(sizeOptional.get().getSizeName());
                            sizeOptionList.add(sizeOption);

                        }

                        OptionProduct optionSizeProduct = new OptionProduct("Size", sizeOptionList);
                        //


                        List<OptionProduct> optionProducts = new ArrayList<>();
                        optionProducts.add(optionSizeProduct);
                        optionProducts.add(optionColorProduct);

                        productOptionalDTO.setOptions(optionProducts);

                        return ResponseEntity.ok().body(new IGenericResponse<>(productOptionalDTO, 200, ""));
                    } else {
                        return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "that bai"));
                    }
                } else {
                    isFavorite = true;
                    Optional<ProductHandleWebsite> productHandleValue1 = productSevice.findOptionWebsiteByAccountIdProductId(product_id, account_id);
                    if (productHandleValue1.isPresent()) {
                        productOptionalDTO = new ProductOptionalDTO(productHandleValue1.get().getId(),
                                productHandleValue1.get().getDescription(), productHandleValue1.get().getIsActive(),
                                productHandleValue1.get().getMaxPrice(), productHandleValue1.get().getMinPrice()
                                , productHandleValue1.get().getProductName(), productHandleValue1.get().getVoteAverage(), isFavorite, null);
                        List<DetailProduct> detailProducts = detailProductService.findAllByProductId(product_id);
                        Set<Integer> colorIdList = detailProducts.stream().map(e -> e.getColor().getId()).collect(Collectors.toSet());
                        Set<Integer> sizeIdList = detailProducts.stream().map(e -> e.getSize().getId()).collect(Collectors.toSet());
                        List<ValueOption> colorOptionList = new ArrayList<>();

                        for (Integer x : colorIdList
                        ) {
                            Optional<Color> color = colorService.findById(x);
                            ValueOption colorOption = new ValueOption();
                            colorOption.setId(color.get().getId());
                            colorOption.setName(color.get().getColorName());
                            colorOptionList.add(colorOption);

                        }
                        OptionProduct optionColorProduct = new OptionProduct("Color", colorOptionList);
                        List<ValueOption> sizeOptionList = new ArrayList<>();


                        for (Integer x : sizeIdList
                        ) {
                            Optional<Size> sizeOptional = sizeService.findById(x);
                            ValueOption sizeOption = new ValueOption();
                            sizeOption.setId(sizeOptional.get().getId());
                            sizeOption.setName(sizeOptional.get().getSizeName());
                            sizeOptionList.add(sizeOption);

                        }

                        OptionProduct optionSizeProduct = new OptionProduct("Size", sizeOptionList);
                        //


                        List<OptionProduct> optionProducts = new ArrayList<>();
                        optionProducts.add(optionSizeProduct);
                        optionProducts.add(optionColorProduct);

                        productOptionalDTO.setOptions(optionProducts);

                        return ResponseEntity.ok().body(new IGenericResponse<>(productOptionalDTO, 200, ""));

                    } else {

                        return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "that bai"));
                    }
                }

            } else {
                return ResponseEntity.ok().body(new IGenericResponse(200, ""));
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }


    @GetMapping("findProductsByCurrentOrderOfAccountId")
    public ResponseEntity<?> findProductsByCurrentOrder(@RequestParam("account_id") Integer accountId) {
        try {
            Integer orderId = orderRepository.findCurrentOrderId(accountId);
            if (orderId != null) {
                List<CustomProductFilterRequest> productWebstieFilterDTOS =
                        orderRepository.findByOrderId(orderId);
                return ResponseEntity.ok().body(new IGenericResponse<>(productWebstieFilterDTOS, 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "no order found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
