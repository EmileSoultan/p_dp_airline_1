package app.clients;

import app.dto.PaymentDto;
import app.entities.Payment;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "${app.feign.config.name}", url = "${app.feign.config.url}")
public interface PaymentFeignClient {
   @PostMapping("/api/payments")
    PaymentDto savePayment(Payment payment);

}
