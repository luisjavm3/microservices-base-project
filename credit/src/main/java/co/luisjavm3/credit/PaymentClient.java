package co.luisjavm3.credit;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "payment-service", url = "http://localhost:8002")
public interface PaymentClient {
	@GetMapping("credit/{creditId}")
	List<Payment> getPaymentsByCreditId(@PathVariable("creditId") Long creditId);

	@DeleteMapping("credit/{creditId}")
	void deletePaymentsByCreditId(@PathVariable("creditId") Long creditId);
}
