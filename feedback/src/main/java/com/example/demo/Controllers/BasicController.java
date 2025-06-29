
package com.example.demo.Controllers;
import org.springframework.web.bind.annotation.*;

@RestController
public class BasicController {
	
	@GetMapping
	public String getSample() {
		return "hello";
	}
    
}
