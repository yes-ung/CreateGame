package egovframework.example.controllerexam.web.ex05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	@GetMapping("/home.do")
	public String home() {
		return "ex05/home";
	}

}
