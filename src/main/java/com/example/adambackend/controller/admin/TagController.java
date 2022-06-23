package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Product;
import com.example.adambackend.entities.Tag;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.TagDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProductSevice;
import com.example.adambackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("admin/tag")
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    ProductSevice productSevice;
    @GetMapping("findAll")
    public ResponseEntity<?> findAll(){
        return  ResponseEntity.ok().body(new IGenericResponse<>(tagService.findAll(),200,""));
    }
    @PostMapping("create")
    public ResponseEntity<?> create(@RequestBody TagDTO tagDTO){
        Tag tag= new Tag();
        tag.setTagName(tagDTO.getTagName());
        tag.setIsDelete(false);
        tag.setCreateDate(LocalDateTime.now());
        tag.setIsActive(true);
        tagService.save(tag);
        return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tag),200,""));
    }
    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody Tag tag
                                    ){

        Optional<Tag>tagOptional= tagService.findById(tag.getId());
        if( tagOptional.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<>(tagService.save(tag),200,""));
        }
        return  ResponseEntity.badRequest().body(new HandleExceptionDemo(400,"not found "));
    }
    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("tag_id") Integer id) {
        Optional<Tag> tagOptional = tagService.findById(id);
        if (tagOptional.isPresent()) {
            tagService.deleteById(id);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
        } else {
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found category"));
        }
    }

}
