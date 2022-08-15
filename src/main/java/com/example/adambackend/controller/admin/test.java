package com.example.adambackend.controller.admin;

import com.example.adambackend.entities.DetailOrder;
import com.example.adambackend.entities.Order;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DetailOrderRepository;
import com.example.adambackend.repository.OrderRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class test {
    @Autowired
    OrderRepository orderRepository;
            
    @Autowired
    DetailOrderRepository detailOrderRepository;
    @PostMapping("updateDetailCode")
    public ResponseEntity<?> updateDetailCode(){

        List<DetailOrder> detailOrderList=detailOrderRepository.findAll();
        for (int i=0;i<detailOrderList.size();i++
             ) {
            String x= RandomString.make(3)+ detailOrderList.get(i).getOrder().getId()+i;
            detailOrderList.get(i).setDetailOrderCode(x);
            detailOrderRepository.save(detailOrderList.get(i));
        }
        return  ResponseEntity.ok().body(new IGenericResponse<>("",200,""));
        
    }
    
}
