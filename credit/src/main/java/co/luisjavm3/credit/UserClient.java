package co.luisjavm3.credit;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8000")
public interface UserClient {
	@GetMapping("{id}")
	User getUserById(@PathVariable("id") Long id);
}
