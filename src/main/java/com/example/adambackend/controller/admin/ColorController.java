package com.example.adambackend.controller.admin;

import com.example.adambackend.common.CommonUtil;
import com.example.adambackend.entities.Color;
import com.example.adambackend.payload.color.ColorDTO;
import com.example.adambackend.payload.color.ColorUpdate;
import com.example.adambackend.payload.color.ListColorIdDTO;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ColorService;
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

import java.util.Optional;

@RestController
@RequestMapping("admin/color")
public class ColorController {

	@Autowired
	private ColorService colorService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll(@RequestParam(value = "name", required = false) String name) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(colorService.findAll(name), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("create")
	public ResponseEntity<?> create(@RequestBody ColorDTO colorDTO) {
		try {
			Color color = colorService.save(new Color(colorDTO));
			if (CommonUtil.isNotNull(color)) {
				return ResponseEntity.ok().body(new IGenericResponse(color, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "update unsuccessfully check colorName is duplicate "));
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(new IGenericResponse(400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody ColorUpdate colorUpdate) {
		try {
			Color color = colorService.update(colorUpdate);
			if (CommonUtil.isNotNull(color)) {
				return ResponseEntity.ok().body(new IGenericResponse(color, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "update unsuccessfully check " +
					"colorName is duplicate or id is not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("color_id") Integer colorId) {
		try {
			Optional<Color> colorOptional = colorService.findById(colorId);
			if (colorOptional.isPresent()) {
				colorService.deleteById(colorId);
				return ResponseEntity.ok().body(new IGenericResponse(200, "success"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("deleteByListId")
	public ResponseEntity<?> deleteArrayTagId(@RequestBody ListColorIdDTO listColorIdDTO) {
		try {
			String message = colorService.updateColorsDeleted(listColorIdDTO.getListColorId());
			return ResponseEntity.ok().body(new IGenericResponse(200, message));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>(400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
