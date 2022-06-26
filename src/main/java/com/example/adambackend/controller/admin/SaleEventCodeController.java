package com.example.adambackend.controller.admin;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(value = "*",maxAge = 3600 )
@RequestMapping("admin/saleEventCode")
public class SaleEventCodeController {
}
