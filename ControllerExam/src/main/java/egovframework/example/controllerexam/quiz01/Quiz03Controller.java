package egovframework.example.controllerexam.quiz01;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Quiz03Controller {
	@GetMapping("qz03/quiz01.do")
	public String quiz01(Model model,
			@RequestParam(defaultValue = "")int dno,
			@RequestParam(defaultValue = "")String dname,
			@RequestParam(defaultValue = "")String loc) {
		model.addAttribute("quiz01",dno);
		model.addAttribute("quiz02",dname);
		model.addAttribute("quiz03",loc);
		return "qz03/quiz01";
	}
	
	@GetMapping("qz03/quiz02.do")
	public String quiz02(Model model,
			@RequestParam(defaultValue = "")String eno,
			@RequestParam(defaultValue = "")String ename,
			@RequestParam(defaultValue = "")String job,
			@RequestParam(defaultValue = "")String hiredate) {
		
		List<String> list = new ArrayList<>();
		list.add(eno);
		list.add(ename);
		list.add(job);
		list.add(hiredate);
		model.addAttribute("list",list);
		return "qz03/quiz02";
		
		
		
	}
	
	@GetMapping("qz03/quiz03.do")
	public String quiz03(Model model,
			@RequestParam(defaultValue = "")String eno,
			@RequestParam(defaultValue = "")String ename,
			@RequestParam(defaultValue = "")String job,
			@RequestParam(defaultValue = "")String hiredate) {
		List<String> list = new ArrayList<String>();
		list.add(eno);
		list.add(ename);
		list.add(job);
		list.add(hiredate);
		model.addAttribute("list",list);
		return "qz03/quiz03";
	}
	

}
