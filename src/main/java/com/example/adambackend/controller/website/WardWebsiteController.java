package com.example.adambackend.controller.website;

import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ward")
public class WardWebsiteController {
	@Autowired
	private WardService wardService;

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(wardService.findAll(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findByDistrictId")
	public ResponseEntity<?> findByProviceId(@RequestParam("district_id") Integer districtId) {
		try {
			return ResponseEntity.ok().body(new IGenericResponse<>(wardService.findByDistrictId(districtId), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
