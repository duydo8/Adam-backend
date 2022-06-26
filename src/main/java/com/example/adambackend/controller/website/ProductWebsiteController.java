package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.CustomProductFilterRequest;
import com.example.adambackend.payload.ProductWebstieFilterDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("/product")
public class ProductWebsiteController {
    @Autowired
    ProductSevice productSevice;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    TagService tagService;

    @GetMapping("findAllByPageble")
    public ResponseEntity<?> findAllByPageble(@RequestParam("page") int page, @RequestParam("size") int size) {
        Page<Product> page1 = productSevice.findPage(page, size);
        return ResponseEntity.ok().body(new IGenericResponse<Page<Product>>(page1, 200, "Page product"));
    }

    @GetMapping("findTop10productByCreateDate")
    public ResponseEntity<?> findTop10productByCreateDate() {
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findTop10productByCreateDate(), 200, ""));
    }

    @GetMapping("findByColorSizePriceBrandAndMaterial")
    public ResponseEntity<?> findByColorSizePriceBrandAndMaterial(@RequestBody ProductWebstieFilterDTO productWebstieFilterDTO) {
        List<Integer> listCategoryId = productWebstieFilterDTO.getListCategoryId();
        List<Integer> listColorId = productWebstieFilterDTO.getListColorId();
        List<Integer> listSizeId = productWebstieFilterDTO.getListSizeId();
        List<Integer> listMaterialId = productWebstieFilterDTO.getListMaterialId();
        List<Integer> listTagId = productWebstieFilterDTO.getListTagId();
        Double bottomPrice = productWebstieFilterDTO.getBottomPrice();
        Double topPrice = productWebstieFilterDTO.getTopPrice();
        Integer page = productWebstieFilterDTO.getPage();
        Integer size = productWebstieFilterDTO.getSize();
        Pageable pageable = PageRequest.of(page, size);
        List<CustomProductFilterRequest> customProductFilterRequests = new ArrayList<>();
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
                        }
                    }
                }else{
                    Integer sizeId=null;
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
                        }
                    }else{
                        Integer materialId=null;
                        if (listTagId.size() > 0) {
                            for (int i = 0; i < listCategoryId.size(); i++) {
                                for (int j = 0; j < listColorId.size(); j++) {
                                    for (int m = 0; m < listTagId.size(); m++) {
                                        customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId, listColorId.get(j), materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                    }
                                }
                            }
                        }
                    }
                }
            }else{
                Integer colorId=null;
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
                        }
                    }
                }
            else{
                Integer sizeId=null;
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
                }else{
                    Integer materialId=null;
                    if (listTagId.size() > 0) {
                        for (int i = 0; i < listCategoryId.size(); i++) {


                                for (int m = 0; m < listTagId.size(); m++) {
                                    customProductFilterRequests = productSevice.findPageableByOption(listCategoryId.get(i), sizeId,
                                            colorId, materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                                }



                        }
                    }else{
                        for (int i = 0; i < listCategoryId.size(); i++) {
                            Integer tagId = null;
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

                    }
                    } else {
                        Integer sizeId = null;
                        if (listTagId.size() > 0) {
                            for (int m = 0; m < listTagId.size(); m++) {
                                customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                        colorId, materialId, listTagId.get(m), bottomPrice, topPrice, pageable);
                            }
                        }else{
                            Integer tagId=null;
                            customProductFilterRequests = productSevice.findPageableByOption(cateId, sizeId,
                                    colorId, materialId, tagId, bottomPrice, topPrice, pageable);
                        }
                    }
                }
            }
        }
//
        return  ResponseEntity.ok().body(new IGenericResponse<>(customProductFilterRequests,200,""));

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
        return ResponseEntity.ok().body(new IGenericResponse<List<Product>>(productSevice.findTop10ProductBestSale(), 200, ""));

    }

}
