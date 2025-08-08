package com.genesiswarlord.genesiswarlord.rules.service;


import com.genesiswarlord.genesiswarlord.common.MapStruct;
import com.genesiswarlord.genesiswarlord.rules.repository.RulesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 목적: 업무로직(기능)을 작성하는 곳
// TODO: spring (인터페이스,자식클래스)
//       springboot(클래스)
//  DI : 1) 필드 DI  : @Autowired 를 필드에서 붙여서 사용
//       2) 생성자 DI(추천): 매개변수 1개짜리 생성자를 만들어서 사용
@Service
@RequiredArgsConstructor
public class RulesService {
    //    생성자 DI
    private final RulesRepository rulesRepository;
    private final MapStruct mapStruct;       // 복사 라이브러리





}
