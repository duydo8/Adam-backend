package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Size;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.payload.size.ListSizeIdDTO;
import com.example.adambackend.payload.size.SizeDTO;
import com.example.adambackend.payload.size.SizeUpdate;
import com.example.adambackend.service.SizeService;
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

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("admin/size")
public class SizeController {

	@Autowired
	private SizeService sizeService;

	@PostMapping("create")
	public ResponseEntity<?> createSize(@RequestBody SizeDTO sizeDTO) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse(sizeService.save(sizeDTO), 200, "success"));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody SizeUpdate sizeUpdate) {
		try {
			Optional<Size> sizeOptional = sizeService.findById(sizeUpdate.getId());
			Size size = sizeService.findByName(sizeUpdate.getSizeName());
			if (sizeOptional.isPresent() && !CommonUtil.isNotNull(size)) {
				sizeOptional.get().setSizeName(sizeUpdate.getSizeName());
				sizeOptional.get().setStatus(sizeUpdate.getStatus());
				return ResponseEntity.ok().body(new IGenericResponse(sizeService.save(sizeOptional.get()), 200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("size_id") Integer sizeId) {
		try {
			Optional<Size> size1 = sizeService.findById(sizeId);
			if (size1.isPresent()) {
				sizeService.deleteById(sizeId);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(sizeService.findAll(name), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListSizeIdDTO listSizeIdDTO) {
		try {
			List<Integer> list = listSizeIdDTO.getListSizeId();
			if (list.size() > 0) {
				for (Integer id : list) {
					Optional<Size> sizeOptional = sizeService.findById(id);
					if (sizeOptional.isPresent()) {
						sizeService.deleteById(id);
					}
				}
				return ResponseEntity.ok().body(new IGenericResponse<>(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
