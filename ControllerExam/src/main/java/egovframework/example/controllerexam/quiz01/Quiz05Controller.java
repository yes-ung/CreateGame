package egovframework.example.controllerexam.quiz01;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Quiz05Controller {
	@GetMapping("qz05/quiz01.do")
	public String quiz01() {
		return "qz05/quiz01";
	}
	

}
