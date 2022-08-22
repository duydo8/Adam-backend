package com.example.adambackend.controller.admin;

import com.example.adambackend.payload.detailOrder.DetailOrderDTO;
import com.example.adambackend.payload.detailOrder.DetailOrderDTOResponse;
import com.example.adambackend.payload.response.IGenericResponse;
import com.example.adambackend.repository.DetailOrderRepository;
import com.example.adambackend.repository.OrderRepository;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class test {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DetailOrderRepository detailOrderRepository;

    @PostMapping("updateDetailCode")
    public ResponseEntity<?> updateDetailCode() {

        List<DetailOrderDTO> detailOrderList = detailOrderRepository.findAlls();
        List<DetailOrderDTOResponse> detailOrderDTOResponses = detailOrderList.stream().map(e -> new
                DetailOrderDTOResponse(e.getId(), e.getQuantity(), e.getPrice(), e.getTotalPrice(), e.getIsDeleted(), e.getDetailOrderCode()
                , e.getIsActive(), e.getCreateDate(), e.getReason(), e.getOrderId())).collect(Collectors.toList());
        System.out.println(detailOrderDTOResponses.size());
        for (int i = 0; i < detailOrderDTOResponses.size(); i++
        ) {
            String x = RandomString.make(3) + detailOrderDTOResponses.get(i).getOrderId() + i;
            detailOrderRepository.updateById(x, detailOrderDTOResponses.get(i).getId());
        }
        return ResponseEntity.ok().body(new IGenericResponse<>("", 200, ""));

    }

}
