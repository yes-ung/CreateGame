package egovframework.example.controllerexam.web.ex05;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ButtonController {
	@GetMapping("/ex05/example01.do")
	public String example01() {
		return "ex05/example01";
	}

}
