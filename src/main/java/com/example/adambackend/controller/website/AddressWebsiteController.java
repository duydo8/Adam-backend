package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.entities.District;
import com.example.adambackend.entities.Province;
import com.example.adambackend.entities.Ward;
import com.example.adambackend.payload.address.AddressWebsiteDto;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import com.example.adambackend.service.DistrictService;
import com.example.adambackend.service.ProvinceService;
import com.example.adambackend.service.WardService;
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
@RequestMapping("address")
public class AddressWebsiteController {

	@Autowired
	private AddressService addressService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private ProvinceService provinceService;

	@Autowired
	private DistrictService districtService;

	@Autowired
	private WardService wardService;

	@GetMapping("findByAccountId")
	public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId) {
		try {
			Optional<Account> account = accountService.findById(accountId);
			if (account.isPresent()) {
				return ResponseEntity.ok().body(new IGenericResponse<>(addressService.findByAccountId(accountId), 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@GetMapping("findAll")
	public ResponseEntity<?> findAll() {
		try {
			return ResponseEntity.ok(new IGenericResponse<>(addressService.findAll(), 200, "successfully"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PostMapping("create")
	public ResponseEntity<?> createAddress(@RequestBody AddressWebsiteDto addressWebsiteDto) {
		try {
			Optional<Account> account = accountService.findById(addressWebsiteDto.getAccountId());
			Optional<Province> province = provinceService.findById(addressWebsiteDto.getProvinceId());
			Optional<District> district = districtService.findById(addressWebsiteDto.getDistrictId());
			Optional<Ward> ward = wardService.findById(addressWebsiteDto.getWardId());
			if (account.isPresent() && province.isPresent() && district.isPresent() && ward.isPresent()) {
				Address address = new Address(addressWebsiteDto);
				address.setAccount(account.get());
				address.setProvince(province.get());
				address.setDistrict(district.get());
				address.setWard(ward.get());
				address = addressService.save(address);
				return ResponseEntity.ok().body(new IGenericResponse<>(address, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found"));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@PutMapping("update")
	public ResponseEntity<?> update(@RequestBody AddressWebsiteDto addressWebsiteDto) {
		try {
			Optional<Account> account = accountService.findById(addressWebsiteDto.getAccountId());
			Optional<Address> addressOptional = addressService.findById(addressWebsiteDto.getId());
			Optional<Province> province = provinceService.findById(addressWebsiteDto.getProvinceId());
			Optional<District> district = districtService.findById(addressWebsiteDto.getDistrictId());
			Optional<Ward> ward = wardService.findById(addressWebsiteDto.getWardId());
			if (account.isPresent() && province.isPresent() && district.isPresent() && ward.isPresent() && addressOptional.isPresent()) {
				Address address = addressService.updateAddress(addressOptional.get(), province.get(), district.get(),
						ward.get(), addressWebsiteDto);
				return ResponseEntity.ok().body(new IGenericResponse(address, 200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam("address_id") Integer addressId, @RequestParam("account_id") Integer accountId) {
		try {

			Optional<Address> address = addressService.findById(accountId);
			if (address.isPresent()) {
				addressService.deleteById(addressId);
				return ResponseEntity.ok().body(new IGenericResponse(200, "successfully"));
			}
			return ResponseEntity.badRequest().body(new IGenericResponse(400, "not found "));
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
		}
	}
}
