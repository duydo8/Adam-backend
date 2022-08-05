package com.example.adambackend.controller.website;

import com.example.adambackend.entities.*;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.address.AddressWebsiteCreate;
import com.example.adambackend.payload.address.AddressWebsiteUpdate;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(value = "*", maxAge = 3600)
@RequestMapping("address")
public class AddressWebsiteController {
    @Autowired
    AddressService addressService;
    @Autowired
    AccountService accountService;
    @Autowired
    ProvinceService provinceService;
    @Autowired
    DistrictService districtService;
    @Autowired
    WardService wardService;

    @GetMapping("findByAccountId")
    public ResponseEntity<?> findByAccountId(@RequestParam("account_id") Integer accountId) {
        try {
            Optional<Account> account = accountService.findById(accountId);
            if (account.isPresent()) {
                return ResponseEntity.ok().body(new IGenericResponse<>(addressService.findByAccountId(accountId), 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        try {
            return ResponseEntity.ok(new IGenericResponse<>(addressService.findAll(), 200, ""));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PostMapping("create")
    public ResponseEntity<?> createAddress(@RequestBody AddressWebsiteCreate addressWebsiteCreate) {
        try {
            Optional<Account> account = accountService.findById(addressWebsiteCreate.getAccountId());
            Optional<Province> province = provinceService.findById(addressWebsiteCreate.getProvinceId());
            Optional<District> district = districtService.findById(addressWebsiteCreate.getDistrictId());
            Optional<Ward> ward = wardService.findById(addressWebsiteCreate.getWardId());
            if (account.isPresent() && province.isPresent() && district.isPresent() && ward.isPresent()) {

                Address address = new Address();
                address.setAddressDetail(addressWebsiteCreate.getAddressDetail());
                address.setCreateDate(LocalDateTime.now());
                address.setIsActive(true);
                address.setIsDeleted(false);

                address.setProvince(province.get());
                address.setDistrict(district.get());
                address.setWard(ward.get());
                address.setPhoneNumber(addressWebsiteCreate.getPhoneNumber());
                address.setFullName(addressWebsiteCreate.getFullName());
                address.setIsDeleted(addressWebsiteCreate.getIsDefault());
                address.setIsDefault(false);
                Address address1 = addressService.save(address);
                return ResponseEntity.ok().body(new IGenericResponse<>(address1, 200, ""));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy account"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestBody AddressWebsiteUpdate addressWebsiteUpdate) {
        try {
            Optional<Account> account = accountService.findById(addressWebsiteUpdate.getAccountId());
            Optional<Address> address = addressService.findById(addressWebsiteUpdate.getId());
            Optional<Province> province = provinceService.findById(addressWebsiteUpdate.getProvinceId());
            Optional<District> district = districtService.findById(addressWebsiteUpdate.getDistrictId());
            Optional<Ward> ward = wardService.findById(addressWebsiteUpdate.getWardId());
            if (account.isPresent() && province.isPresent() && district.isPresent() && ward.isPresent() && address.isPresent()) {
                Address address1 = address.get();
                address1.setAddressDetail(addressWebsiteUpdate.getAddressDetail());

                address1.setProvince(province.get());
                address1.setDistrict(district.get());
                address1.setWard(ward.get());
                address1.setPhoneNumber(addressWebsiteUpdate.getPhoneNumber());
                address1.setFullName(addressWebsiteUpdate.getFullName());
                address1.setIsDeleted(addressWebsiteUpdate.getIsDefault());
                address1.setIsDefault(addressWebsiteUpdate.getIsDefault());
                Address address2 = addressService.save(address1);

                return ResponseEntity.ok().body(new IGenericResponse<Address>(address2, 200, "successfully"));


            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("address_id") Integer addressId, @RequestParam("account_id") Integer accountId) {
        try {
            Optional<Account> account = accountService.findById(accountId);
            Optional<Address> address1 = addressService.findById(accountId);
            if (account.isPresent() && address1.isPresent()) {



                accountService.save(account.get());
                addressService.deleteById(addressId);
                return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
            }
            return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "Không tìm thấy "));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new IGenericResponse<>("", 400, "Oops! Lại lỗi api rồi..."));
        }
    }
}
