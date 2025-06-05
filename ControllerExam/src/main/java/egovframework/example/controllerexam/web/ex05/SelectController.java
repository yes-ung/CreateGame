package egovframework.example.controllerexam.web.ex05;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SelectController {
	@GetMapping(value="/api/select.do", produces="application/text; charset=UTF-8;")

	public String example01() {
		return "Hello Ajax";
	}
}
