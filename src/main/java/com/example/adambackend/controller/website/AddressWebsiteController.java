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
    public ResponseEntity<?> findByAccountId(@RequestParam("account_id")Integer accountId){
        Optional<Account> account= accountService.findById(accountId);
        if(account.isPresent()){
            return ResponseEntity.ok().body(new IGenericResponse<>(addressService.findByAccountId(accountId),200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found"));

         }
    @GetMapping("findAll")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(new IGenericResponse<>(addressService.findAll(), 200, ""));
    }
    @PostMapping("create")
    public ResponseEntity<?> createAddress( @RequestBody AddressWebsiteCreate addressWebsiteCreate) {
        Optional<Account> account = accountService.findById(addressWebsiteCreate.getAccountId());
        Optional<Province> province= provinceService.findById(addressWebsiteCreate.getProvinceId());
        Optional<District> district= districtService.findById(addressWebsiteCreate.getDistrictId());
        Optional<Ward> ward=wardService.findById(addressWebsiteCreate.getWardId());
        if (account.isPresent()&&province.isPresent()&&district.isPresent()&&ward.isPresent()) {

            Address address= new Address();
            address.setAddressDetail(addressWebsiteCreate.getAddressDetail());
            address.setCreateDate(LocalDateTime.now());
            address.setIsActive(true);
            address.setIsDeleted(false);
            address.setAccount(account.get());
            address.setProvince(province.get());
            address.setDistrict(district.get());
            address.setWard(ward.get());
            Address address1=addressService.save(address);
            return ResponseEntity.ok().body(new IGenericResponse<>(address1,200,""));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found account"));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(

            @RequestBody AddressWebsiteUpdate addressWebsiteUpdate) {
        Optional<Account> account = accountService.findById(addressWebsiteUpdate.getAccountId());
        Optional<Address> address = addressService.findById(addressWebsiteUpdate.getId());
        Optional<Province> province= provinceService.findById(addressWebsiteUpdate.getProvinceId());
        Optional<District> district= districtService.findById(addressWebsiteUpdate.getDistrictId());
        Optional<Ward> ward=wardService.findById(addressWebsiteUpdate.getWardId());
        if (account.isPresent()&&province.isPresent()&&district.isPresent()&&ward.isPresent()&& address.isPresent()) {
           Address address1= address.get();
            address1.setAddressDetail(addressWebsiteUpdate.getAddressDetail());
            address1.setAccount(account.get());
            address1.setProvince(province.get());
            address1.setDistrict(district.get());
            address1.setWard(ward.get());
            Address address2=addressService.save(address1);

            return ResponseEntity.ok().body(new IGenericResponse<Address>(address2, 200, "successfully"));


        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam("address_id") Integer addressId, @RequestParam("account_id") Integer accountId) {
        Optional<Account> account = accountService.findById(accountId);
        Optional<Address> address1 = addressService.findById(accountId);
        if (account.isPresent() && address1.isPresent()) {
            List<Address> addresses = account.get().getAddressList();
            addresses.remove(address1.get());
            account.get().setAddressList(addresses);
            accountService.save(account.get());
            addressService.deleteById(addressId);
            return ResponseEntity.ok().body(new HandleExceptionDemo(200, "successfully"));
        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found "));
    }


}
