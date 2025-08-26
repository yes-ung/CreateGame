package com.genesiswarlord.genesiswarlord.rules.controller;


import com.genesiswarlord.genesiswarlord.rules.entity.BarracksInfo;
import com.genesiswarlord.genesiswarlord.rules.service.BarracksInfoService;
import com.genesiswarlord.genesiswarlord.rules.service.RulesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
public class RulesController {

    private final RulesService rulesService;
    private final BarracksInfoService barracksInfoService;


    @GetMapping("/")
    public String index(Model model) {
        BarracksInfo barracksInfo = barracksInfoService.findBarracksInfoById();
        model.addAttribute("barracksInfo", barracksInfo);
        List<BarracksInfo> barracksInfos = barracksInfoService.findAllBarracksInfo();
        model.addAttribute("barracksInfos", barracksInfos);
        return "index";
    }

    @GetMapping("/game")
    public String cocos() {
        return "redirect:/web-mobile/index.html"; // 이 경로로 리디렉션
    }

}
