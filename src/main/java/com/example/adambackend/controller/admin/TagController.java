package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Tag;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.tag.ListTagIdDTO;
import com.example.adambackend.payload.tag.TagDTO;
import com.example.adambackend.payload.tag.TagUpdate;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagProductService;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/tag")
public class TagController {

    @Autowired
    private TagService tagService;

    @Autowired
    private ProductSevice productSevice;

    @Autowired
    private TagProductService tagProductService;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.findAll(name), 200, "successfully"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody TagDTO tagDTO) {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(new Tag(tagDTO)), 200, "successfully"));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new IGenericResponse(500, "can't duplicate name"));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody TagUpdate tagUpdate) {
        try {
            Optional<Tag> tagOptional = tagService.findById(tagUpdate.getId());
            if (tagOptional.isPresent()) {
                tagOptional.get().setTagName(tagUpdate.getTagName());
                tagOptional.get().setStatus(tagUpdate.getStatus());
                return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tagOptional.get()), 200, "successfully"));
            }
            return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("tag_id") Integer id) {
        try {
            Optional<Tag> tagOptional = tagService.findById(id);
            if (tagOptional.isPresent()) {
                tagService.deleteById(id);
                return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
            } else {
                return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListTagIdDTO listTagIdDTO) {
        try {
            List<Integer> listTagId = listTagIdDTO.getTagIdList();
            System.out.println(listTagId.size());
            if (!listTagId.isEmpty()) {
                for (Integer id : listTagId) {
                    Optional<Tag> tagOptional = tagService.findById(id);
                    if (tagOptional.isPresent()) {
                        tagProductService.updateDeletedByTagId(id);
                        tagService.deleteById(id);
                    }
                }
                return ResponseEntity.ok().body(new IGenericResponse<>("", 200, "successfully"));
            }
            return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

}
