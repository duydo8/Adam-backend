package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.Material;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.material.ListMaterialIdDTO;
import com.example.adambackend.payload.material.MaterialDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.MaterialProductService;
import com.example.adambackend.service.MaterialService;
import com.example.adambackend.service.ProductSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/material")
public class MaterialControler {

	@Autowired
	private MaterialService materialService;

	@Autowired
	private ProductSevice productSevice;

	@Autowired
	private MaterialProductService materialProductService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		return ResponseEntity.ok().body(new IGenericResponse(materialService.findAll(name), 200, "successfully"));
	}

	@PostMapping("create")
	public ResponseEntity<?> createWard(@RequestBody MaterialDTO materialDTO) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(materialService.save(new Material(materialDTO)), 200, "successfully"));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new HandleExceptionDemo(500, "can't duplicate name"));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> updateEvent(@RequestBody MaterialDTO materialDTO) {
		try {
			Optional<Material> materialOptional = materialService.findById(materialDTO.getId());
			if (materialOptional.isPresent()) {
				materialOptional.get().setMaterialName(materialDTO.getMaterialName());
				materialOptional.get().setStatus(materialDTO.getStatus());
				return ResponseEntity.ok().body(new IGenericResponse(materialService.save(materialOptional.get()),
						200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Ward"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> deleteEvent(@RequestParam("material_id") Integer id) {
		try {
			Optional<Material> materialOptional = materialService.findById(id);
			if (materialOptional.isPresent()) {
				materialService.deleteById(id);
				return ResponseEntity.ok().body(new HandleExceptionDemo(200, ""));
			} else {
				return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy Ward"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListMaterialIdDTO listMaterialIdDTO) {
		try {
			List<Integer> listMaterialIdDTOx = listMaterialIdDTO.getListMaterialId();

			System.out.println(listMaterialIdDTOx.size());
			if (listMaterialIdDTOx.size() > 0) {
				for (Integer x : listMaterialIdDTOx
				) {
					Optional<Material> materialOptional = materialService.findById(x);
					if (materialOptional.isPresent()) {
						materialProductRepository.updateMaterialProductsDeleted(x);
						materialService.updateDeleteByArrayId(x);

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
