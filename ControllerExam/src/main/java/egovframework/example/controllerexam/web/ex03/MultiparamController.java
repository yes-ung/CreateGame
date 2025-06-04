package egovframework.example.controllerexam.web.ex03;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MultiparamController {
	
	//따라하기 예제1
	@GetMapping("/ex03/example01.do")
		public String example01(Model model,
			@RequestParam(defaultValue ="") String name,
			@RequestParam(defaultValue = "")String id) {
		
		model.addAttribute("name",name);
		model.addAttribute("id",id);
		return "ex03/example01";
	}
	
	// 따라하기 예제 3
	@GetMapping("/ex03/example02.do")
	public String example02(Model model,
			@RequestParam(defaultValue = "")String fno,
			@RequestParam(defaultValue = "")String title,
			@RequestParam(defaultValue = "")String content) {
		List<String> list = new ArrayList<String>();
		list.add(fno);
		list.add(title);
		list.add(content);
		model.addAttribute("list" ,list);
		return "ex03/example02";
		
	}


}
