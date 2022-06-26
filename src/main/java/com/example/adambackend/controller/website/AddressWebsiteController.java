package com.example.adambackend.controller.website;

import com.example.adambackend.entities.Account;
import com.example.adambackend.entities.Address;
import com.example.adambackend.exception.HandleExceptionDemo;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.service.AccountService;
import com.example.adambackend.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("create")
    public ResponseEntity<?> createAddress(@RequestParam("account_id") Integer accountId, @RequestBody Address address) {
        Optional<Account> account = accountService.findById(accountId);
        if (account.isPresent()) {
            Address address1 = addressService.save(address);
            List<Address> addresses = account.get().getAddressList();
            addresses.add(address);
            account.get().setAddressList(addresses);
            accountService.save(account.get());
            return ResponseEntity.ok().body(new IGenericResponse<Address>(address1, 200, "successfully"));


        }
        return ResponseEntity.badRequest().body(new HandleExceptionDemo(400, "not found account"));
    }

    @PutMapping("update")
    public ResponseEntity<?> update(@RequestParam("account_id") Integer accountId, @RequestBody Address address) {
        Optional<Account> account = accountService.findById(accountId);
        Optional<Address> address1 = addressService.findById(address.getId());
        if (account.isPresent() && address1.isPresent()) {
            Address address2 = addressService.save(address);
            List<Address> addresses = account.get().getAddressList();
            addresses.add(address);
            account.get().setAddressList(addresses);
            accountService.save(account.get());
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
