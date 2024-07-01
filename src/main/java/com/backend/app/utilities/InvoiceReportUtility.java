package com.backend.app.utilities;

import com.backend.app.persistence.entities.*;
import com.backend.app.persistence.repositories.OrderDishItemRepository;
import lombok.AllArgsConstructor;
import lombok.Data;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceReportUtility {

    @Autowired
    private OrderDishItemRepository orderDishItemRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public JasperPrint generateReport(
            UserEntity user,
            OrderDishEntity orderDish,
            PaymentEntity payment
    ) throws IOException, JRException {


        Resource resource = resourceLoader.getResource("classpath:reports/invoice.jrxml");
        final JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());

        Resource logoResource = resourceLoader.getResource("classpath:reports/images/logoCompany.png");
        InputStream logoCompany = logoResource.getInputStream();

        List<OrderDishItemEntity> orderDishItems = orderDishItemRepository.findByOrderDish(orderDish);

        String total = "S/." + payment.getAmount();

        // Generate report
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("logoCompany", logoCompany);
        parameters.put("firstName", user.getFirstName());
        parameters.put("lastName", user.getLastName());
        parameters.put("dni", user.getDni() != null ? user.getDni() : "No registered");
        parameters.put("phoneNumber", user.getPhoneNumber() != null ? user.getPhoneNumber() : "No registered");
        parameters.put("email", user.getEmail());
        parameters.put("status", payment.getStatus().name());
        parameters.put("subTotal", total);
        parameters.put("igv", String.valueOf(0));
        parameters.put("totalPayment", total);
        parameters.put("ds", new JRBeanArrayDataSource(
                orderDishItems.stream().map(orderDishItem -> new ReportEntity(
                                orderDishItem.getId(),
                                orderDishItem.getDish().getName(),
                                orderDishItem.getQuantity(),
                                orderDishItem.getDish().getPrice(),
                                Math.round(orderDishItem.getQuantity() * orderDishItem.getDish().getPrice() * 100.0) / 100.0
                        )
                ).toArray()
        ));

        return JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

    }

    @Data
    @AllArgsConstructor
    public static class ReportEntity {
        private Long idDish;
        private String name;
        private int quantity;
        private Double price;
        private Double total;

    }



}