package co.luisjavm3.user;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "credit-service", url = "http://localhost:8001")
public interface CreditClient {
	@DeleteMapping("delete-credits/{userId}")
	void deleteCreditsByUserId(@PathVariable("userId") Long userId);
}
