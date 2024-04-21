package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Material;
import com.example.adambackend.payload.material.ListMaterialIdDTO;
import com.example.adambackend.payload.material.MaterialDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("admin/material")
public class MaterialControler {

	@Autowired
	private MaterialService materialService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		return ResponseEntity.ok().body(new IGenericResponse(materialService.findAll(name), 200, "successfully"));
	}

	@PostMapping("create")
	public ResponseEntity<?> createWard(@RequestBody MaterialDTO materialDTO) {
		try {
			Material material = materialService.save(new Material(materialDTO));
			if(CommonUtil.isNotNull(material)){
				return ResponseEntity.ok().body(new IGenericResponse(material, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "can't duplicate name"));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
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
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
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
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			} else {
				return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListMaterialIdDTO listMaterialIdDTO) {
		try {
			materialService.updateDeletedByListId(listMaterialIdDTO.getListMaterialId());
			return ResponseEntity.badRequest().body(new IGenericResponse(200, "successfully "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
