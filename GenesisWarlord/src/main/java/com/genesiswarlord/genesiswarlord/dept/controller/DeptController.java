package com.genesiswarlord.genesiswarlord.dept.controller;


import com.genesiswarlord.genesiswarlord.dept.dto.DeptDto;
import com.genesiswarlord.genesiswarlord.dept.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Log4j2
@Controller
public class DeptController {
//    생성자 DI : 서비스
    private final DeptService deptService;
//    전체조회:(페이징: 매개변수(Pageable), 결과(Page))
//    현재페이지번호(page), 화면에 보일 개수(size)
//    @PageableDefault(page = 0, size = 3) : jsp에서 값을 보내지 않을 경우 기본값을 설정하는 어노테이션
//    쿼리스트링 : @RequestParam()
    @GetMapping("/dept")
    public String selectDeptList(@RequestParam(defaultValue = "") String searchKeyword,
                       @PageableDefault(page = 0, size = 3) Pageable pageable,
                       Model model) {
//        1) 전체 조회
        Page<DeptDto> pages = deptService.selectDeptList(searchKeyword, pageable);
//        로깅
        log.info(pages.getContent());// 결과 확인
//        2) jsp 로 전달: Model 사용
        model.addAttribute("depts", pages.getContent());
        model.addAttribute("pages", pages);
        return "dept/dept_all";
    }
    //    추가 페이지 열기
    @GetMapping("/dept/addition")
    public String createDeptView() {
        return "dept/add_dept";
    }
    //    저장 버튼클릭시 insert
    @PostMapping("/dept/add")
    public String insert(@ModelAttribute DeptDto deptDto) {
//        서비스 insert
        deptService.save(deptDto);
        return "redirect:/dept";
    }


}
