package co.luisjavm3.payment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "credit-service", url = "http://localhost:8001")
public interface CreditClient {
	@GetMapping("{id}")
	CreditUser getCredit(@PathVariable("id") Long id);

	@PutMapping("soft-delete/{id}")
	void softDeleteCredit(@PathVariable("id") Long id);
}
