package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Tag;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.tag.ListTagIdDTO;
import com.example.adambackend.payload.tag.TagDTO;
import com.example.adambackend.payload.tag.TagUpdate;
import com.example.adambackend.repository.TagProductRepository;
import com.example.adambackend.repository.TagRepository;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/tag")
public class TagController {
    @Autowired
    TagRepository tagService;
    @Autowired
    ProductSevice productSevice;
    @Autowired
    TagProductRepository tagProductRepository;

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.findAlls(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
    @GetMapping("findByName")
    public ResponseEntity<?> findByCateName(@RequestParam("name")String name){
        return ResponseEntity.ok(new IGenericResponse<>(tagService.findByName(name),200,""));
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody TagDTO tagDTO) {
        try {
            Tag tag = new Tag();
            tag.setTagName(tagDTO.getTagName());
            tag.setIsDelete(false);
            tag.setCreateDate(LocalDateTime.now());
            tag.setIsActive(true);
            tagService.save(tag);
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tag), 200, ""));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new HandleExceptionDemo(500, "can't duplicate name"));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody TagUpdate tagUpdate
    ) {
        try {
            Optional<Tag> tagOptional = tagService.findById(tagUpdate.getId());
            if (tagOptional.isPresent()) {
                tagOptional.get().setTagName(tagUpdate.getTagName());
                tagOptional.get().setIsActive(tagUpdate.getIsActive());
                tagOptional.get().setIsDelete(tagUpdate.getIsDeleted());
                return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tagOptional.get()), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
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
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
            } else {
                return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy category"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("deleteByListId")
    public ResponseEntity<?> deleteArrayTagId(@RequestBody ListTagIdDTO listTagIdDTO) {
        try {
            List<Integer> listTagId = listTagIdDTO.getTagIdList();
            System.out.println(listTagId.size());
            if (listTagId.size() > 0) {
                for (Integer x : listTagId
                ) {
                    Optional<Tag> tagOptional = tagService.findById(x);
                    if (tagOptional.isPresent()) {
                        tagProductRepository.updateDeletedTagId(x);
                        tagService.updateDeletedTagId(x);

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
