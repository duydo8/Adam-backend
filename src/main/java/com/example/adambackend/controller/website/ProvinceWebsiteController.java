package com.example.adambackend.controller.website;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.ProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/province")
public class ProvinceWebsiteController {
	@Autowired
	private ProvinceService provinceService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(provinceService.findAll(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
