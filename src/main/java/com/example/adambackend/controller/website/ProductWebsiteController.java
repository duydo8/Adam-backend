package com.example.adambackend.controller.website;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.product.CustomProductFilterRequest;
import com.example.adambackend.payload.product.ProductWebsiteDTO;
import com.example.adambackend.payload.productWebsiteDTO.*;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.MaterialProductRepository;
import com.example.adambackend.repository.OrderRepository;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    DetailOrderService detailOrderService;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    DetailProductService detailProductService;
    @Autowired
    TagService tagService;
    @Autowired
    ColorService colorService;
    @Autowired
    SizeService sizeService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    MaterialService materialService;
    @Autowired
    TagProductRepository tagProductRepository;
    @Autowired
    MaterialProductRepository materialProductRepository;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1, 200, "Page product"));
    }

    @GetMapping("findTop10productByCreateDate")
    public ResponseEntity<?> findTop10productByCreateDate() {
        List<Product>products=productSevice.findTop10productByCreateDate();
        List<ProductWebsiteDTO> productDTOS= new ArrayList<>();
        for (Product product:products){
            ProductWebsiteDTO productWebsiteDTO= new ProductWebsiteDTO();
            productWebsiteDTO.setId(product.getId());
            productWebsiteDTO.setProductName(product.getProductName());
            productWebsiteDTO.setCreateDate(product.getCreateDate());
            productWebsiteDTO.setDescription(product.getDescription());
            productWebsiteDTO.setImage(product.getImage());
            productWebsiteDTO.setIsComplete(product.getIsComplete());
            productWebsiteDTO.setIsActive(product.getIsActive());
            productWebsiteDTO.setIsDelete(product.getIsDelete());
            productWebsiteDTO.setVoteAverage(product.getVoteAverage());
            List<DetailProduct> detailProducts= product.getDetailProducts();

            List<Double> price=detailProducts.stream().
                    map(e->e.getPriceExport()).collect(Collectors.toList());
            Collections.sort(price);

            Double minPrice= price.get(0);
            Double maxPrice= price.get(price.size()-1);
            productWebsiteDTO.setMaxPrice(maxPrice);
            productWebsiteDTO.setMinPrice(minPrice);
            productDTOS.add(productWebsiteDTO);
        }

        return ResponseEntity.ok().body(new IGenericResponse<>(productDTOS, 200, ""));
    }

    @PostMapping("findByOpionalArrayValue")
    public ResponseEntity<?> findByOpionalArrayValue(@RequestBody ProductWebstieFilterDTO productWebstieFilterDTO) {
        List<Integer> listCategoryId = productWebstieFilterDTO.getListCategoryId();
        List<Integer> listColorId = productWebstieFilterDTO.getListColorId();
        List<Integer> listSizeId = productWebstieFilterDTO.getListSizeId();
        List<Integer> listMaterialId = productWebstieFilterDTO.getListMaterialId();
        List<Integer> listTagId = productWebstieFilterDTO.getListTagId();
        Double bottomPrice = productWebstieFilterDTO.getBottomPrice();
        Double topPrice = productWebstieFilterDTO.getTopPrice();
        if(listCategoryId==null||listCategoryId.isEmpty() ||  listCategoryId.size()==0){
            listCategoryId=new ArrayList<>();
        }
        if(listColorId==null||listColorId.isEmpty() ||  listColorId.size()==0){
            listColorId=new ArrayList<>();
        }
        if(listSizeId==null){
            listSizeId=new ArrayList<>();
        }
        if(listMaterialId==null||  listMaterialId.size()==0){
            listMaterialId=new ArrayList<>();
        }
        if(listTagId==null||listTagId.isEmpty() ||  listTagId.size()==0){
            listTagId=new ArrayList<>();
        }
        if(bottomPrice==null){
            bottomPrice=0.0;
        }
        if(topPrice==null){
            topPrice=999999999.0;
        }
        Integer page = productWebstieFilterDTO.getPage();
        Integer size = productWebstieFilterDTO.getSize();
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomProductFilterRequest> customProductFilterRequests = null;
        //,Sort.by("createDate").descending()
        if (listCategoryId.size() > 0) {
            if (listColorId.size() > 0) {
                if (listSizeId.size() > 0) {
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int k = 0; k < listSizeId.size(); k++) {
                                        for (int l = 0; l < listMaterialId.size(); l++) {
                                            for (int m = 0; m < listTagId.size(); m++) {
                                                customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                        listColorId.get(j), listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int k = 0; k < listSizeId.size(); k++) {
                                        for (int l = 0; l < listMaterialId.size(); l++) {

                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                    listColorId.get(j), listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);

                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        Integer materialId = null;
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int k = 0; k < listSizeId.size(); k++) {

                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                    listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int k = 0; k < listSizeId.size(); k++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                listColorId.get(j), materialId, tagId, bottomPrice, topPrice, pageable);
                                    }
                                }

                            }

                        }
                    }
                } else {
                    Integer sizeId = null;
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {

                                    for (int l = 0; l < listMaterialId.size(); l++) {
                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                                    listColorId.get(j), listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }


                                    }
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                                listColorId.get(j), listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        }
                    } else {
                        Integer materialId = null;
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId, listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                                listColorId.get(j), listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                Integer colorId = null;
                if (listSizeId.size() > 0) {
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {
                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                    colorId, listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }
                                    }

                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {

                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                colorId, listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);

                                    }

                                }
                            }
                        }
                    } else {
                        Integer materialId = null;
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int j = 0; j < listColorId.size(); j++) {
                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k), listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int j = 0; j < listColorId.size(); j++) {
                                        for (int l = 0; l < listMaterialId.size(); l++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), listSizeId.get(k),
                                                    listColorId.get(j), listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    Integer sizeId = null;
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {

                                for (int l = 0; l < listMaterialId.size(); l++) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                                colorId, listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                    }


                                }
                            }
                        }
                    } else {
                        Integer materialId = null;
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {


                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                            colorId, materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                }


                            }
                        } else {
                            Integer tagId = null;
                            for (int i = 0; i < listCategoryId.size(); i++) {

                                customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                        colorId, materialId, tagId, bottomPrice, topPrice, pageable);
                            }

                        }
                    }
                }
            }
        } else {
            Integer cateId = null;
            if (listColorId.size() > 0) {
                if (listSizeId.size() > 0) {
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int j = 0; j < listColorId.size(); j++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {
                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                    listColorId.get(j), listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }
                                    }

                                }
                            }
                        }else{
                            Integer tagId=null;
                            for (int j = 0; j < listColorId.size(); j++) {
                                for (int k = 0; k < listSizeId.size(); k++) {
                                    for (int l = 0; l < listMaterialId.size(); l++) {

                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                    listColorId.get(j), listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);

                                    }

                                }
                            }
                        }
                    }else{
                        Integer materialId=null;
                        if (listTagId.size() > 0) {
                            for (int j = 0; j < listColorId.size(); j++) {
                                for (int k = 0; k < listSizeId.size(); k++) {

                                        for (int m = 0; m < listTagId.size(); m++) {
                                            customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                    listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                        }


                                }
                            }
                        }else{
                            Integer tagId=null;
                            for (int j = 0; j < listColorId.size(); j++) {
                                for (int k = 0; k < listSizeId.size(); k++) {


                                        customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                listColorId.get(j), materialId, tagId, bottomPrice, topPrice, pageable);



                                }
                            }
                        }
                    }
                } else {
                    Integer sizeId = null;
                    if (listMaterialId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int j = 0; j < listColorId.size(); j++) {

                                for (int l = 0; l < listMaterialId.size(); l++) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                                listColorId.get(j), listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                    }
                                }

                            }
                        }

                    } else {
                        Integer materialId = null;
                        if (listTagId.size() > 0) {
                            for (int j = 0; j < listColorId.size(); j++) {


                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                            listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                }
                            }

                        } else {
                            Integer tagId = null;
                            for (int j = 0; j < listColorId.size(); j++) {


                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                            listColorId.get(j), materialId, tagId, bottomPrice, topPrice, pageable);
                                }
                            }
                        }

                    }
                }
            } else {
                Integer colorId = null;
                if (listMaterialId.size() > 0) {
                    if (listSizeId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int k = 0; k < listSizeId.size(); k++) {
                                for (int l = 0; l < listMaterialId.size(); l++) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                colorId, listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        }else{
                            Integer tagId=null;
                            for (int k = 0; k < listSizeId.size(); k++) {
                                for (int l = 0; l < listMaterialId.size(); l++) {

                                        customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                                colorId, listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);

                                }
                            }
                        }
                    } else {
                        Integer sizeId = null;
                        if (listTagId.size() > 0) {

                            for (int l = 0; l < listMaterialId.size(); l++) {
                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                            colorId, listMaterialId.get(l), listTagId.get(m), bottomPrice, topPrice, pageable);
                                }
                            }
                        } else {
                            Integer tagId = null;
                            for (int l = 0; l < listMaterialId.size(); l++) {

                                customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                        colorId, listMaterialId.get(l), tagId, bottomPrice, topPrice, pageable);

                            }
                        }

                    }
                } else {
                    Integer materialId = null;
                    if (listSizeId.size() > 0) {
                        if (listTagId.size() > 0) {
                            for (int k = 0; k < listSizeId.size(); k++) {
                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                            colorId, materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                }
                            }

                        } else {
                            Integer tagId = null;
                            for (int k = 0; k < listSizeId.size(); k++) {

                                customProductFilterRequests = productSevice.findPageableByOption(cateId, listSizeId.get(k),
                                        colorId, materialId, tagId, bottomPrice, topPrice, pageable);

                            }
                        }
                    } else {
                        Integer sizeId = null;
                        if (listTagId.size() > 0) {
                            for (int m = 0; m < listTagId.size(); m++) {
                                customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                        colorId, materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                            }
                        } else {
                            Integer tagId = null;
                            customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                    colorId, materialId, tagId, bottomPrice, topPrice, pageable);
                        }
                    }
                }
            }
        }
//
        return ResponseEntity.ok().body(new IGenericResponse<>(customProductFilterRequests, 200, ""));

    }

    @GetMapping("findProductByTagName")
    public ResponseEntity<?> findProductByTag(@RequestParam("tag_name") String tagName) {
        Optional<Tag> tagOptional = tagService.findByTagName(tagName);
        if (tagOptional.isPresent()) {
            return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findAllByTagName(tagName), 200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));
        }
    }

    @GetMapping("findTop10ProductBestSale")
    public ResponseEntity<?> findTop10ProductBestSale() {
        List<Product> products= productSevice.findTop10ProductBestSale();
        List<ProductWebsiteDTO> productDTOS= new ArrayList<>();
        for (Product product:products){
            ProductWebsiteDTO productWebsiteDTO= new ProductWebsiteDTO();
            productWebsiteDTO.setId(product.getId());
            productWebsiteDTO.setProductName(product.getProductName());
            productWebsiteDTO.setCreateDate(product.getCreateDate());
            productWebsiteDTO.setDescription(product.getDescription());
            productWebsiteDTO.setImage(product.getImage());
            productWebsiteDTO.setIsComplete(product.getIsComplete());
            productWebsiteDTO.setIsActive(product.getIsActive());
            productWebsiteDTO.setIsDelete(product.getIsDelete());
            productWebsiteDTO.setVoteAverage(product.getVoteAverage());
            List<DetailProduct> detailProducts= product.getDetailProducts();

            List<Double> price=detailProducts.stream().
                    map(e->e.getPriceExport()).collect(Collectors.toList());
            Collections.sort(price);

            Double minPrice= price.get(0);
            Double maxPrice= price.get(price.size()-1);
            productWebsiteDTO.setMaxPrice(maxPrice);
            productWebsiteDTO.setMinPrice(minPrice);
            productDTOS.add(productWebsiteDTO);

        }

        return ResponseEntity.ok().body(new IGenericResponse<>(productDTOS, 200, ""));


    }

    @GetMapping("findTop10ProductByCountQuantityInOrderDetail")
    public ResponseEntity<?> findTop10ProductByCountQuantityInOrderDetail() {
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(detailOrderService.findTop10ProductByCountQuantityInOrderDetail(), 200, ""));
    }

    @GetMapping("findOptionProductById")
    public ResponseEntity<?> findOptionProductById(@RequestParam("id") Integer id) {
        Optional<Product> productOptional = productSevice.findById(id);
        if (productOptional.isPresent()) {
            List<Integer> listTagId= tagProductRepository.findTagIdByProductId(id);
            List<Integer> listMaterialId=materialProductRepository.findMaterialIdByProductId(id);
            List<DetailProduct> detailProductList= detailProductService.findAllByProductId(id);
            Set<Tag> tagList= new HashSet<>();
            Set<Material> materialList= new HashSet<>();
            Set<Color>colorList= new HashSet<>();
            Set<Size> sizeList= new HashSet<>();
            for (DetailProduct dp: detailProductList
            ) {
                Color c= colorService.findByDetailProductId(dp.getId());
                Size s = sizeService.findByDetailProductId(dp.getId());
                colorList.add(c);
                sizeList.add(s);
            }
            for (Integer x :listTagId
            ) {
                Optional<Tag> tagOptional=tagService.findById(x);
                tagList.add(tagOptional.get());

            }
            for (Integer x :listMaterialId
            ) {
                Optional<Material> materialOptional=materialService.findById(x);
                materialList.add(materialOptional.get());

            }

            OptionalProduct optionalProduct= new OptionalProduct(tagList,materialList,colorList,sizeList);
            return ResponseEntity.ok().body(new IGenericResponse<>(optionalProduct,200,""));

        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

    }


    @GetMapping("findProductsByCurrentOrderOfAccountId")
    public ResponseEntity<?> findProductsByCurrentOrder(@RequestParam("account_id")Integer accountId){
        Integer orderId= orderRepository.findCurrentOrderId(accountId);
        if(orderId!=null){
            List<CustomProductFilterRequest> productWebstieFilterDTOS=
                    orderRepository.findByOrderId(orderId);
            return ResponseEntity.ok().body(new IGenericResponse<>(productWebstieFilterDTOS,200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"no order found"));
    }
}
