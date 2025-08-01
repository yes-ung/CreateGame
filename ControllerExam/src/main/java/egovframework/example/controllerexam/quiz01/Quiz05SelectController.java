package egovframework.example.controllerexam.quiz01;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Quiz05SelectController {
	@GetMapping(value = "/api/quiz01.do", produces="application/text; charset=UTF-8;")
	public String quiz01() {
		return "안녕 Ajax";
	}

}
