package com.genesiswarlord.genesiswarlord.dept.service;


import com.genesiswarlord.genesiswarlord.common.MapStruct;
import com.genesiswarlord.genesiswarlord.dept.dto.DeptDto;
import com.genesiswarlord.genesiswarlord.dept.entity.Dept;
import com.genesiswarlord.genesiswarlord.dept.repository.DeptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

// 목적: 업무로직(기능)을 작성하는 곳
// TODO: spring (인터페이스,자식클래스)
//       springboot(클래스)
//  DI : 1) 필드 DI  : @Autowired 를 필드에서 붙여서 사용
//       2) 생성자 DI(추천): 매개변수 1개짜리 생성자를 만들어서 사용
@Service
@RequiredArgsConstructor
public class DeptService {
    //    생성자 DI
    private final DeptRepository deptRepository;
    private final MapStruct mapStruct;       // 복사 라이브러리

    //    전체조회(페이징): like 검색
//    spring: 매개변수(Criteria), 결과(PagenationInfo)
//    JPA   : 매개변수(Pageable), 결과(Page:결과배열, 현재페이지번호 등)
//    조회: DB 결과 -> 엔터티클래스 -> DTO 복사(생략) -> DTO로 화면에 표시
//     예)  dto.dno=dept.dno (복사) , 복사 라이브러리(엔티티 <-> DTO:MapStruct)
//    TODO: page.map(data->mapStruct.toDto(data)); 의미
//      스트림(자동반복문), for문으로 모두 변경(엔티티 -> DTO)
//      stream.map(실행문): 배열의 끝까지 자동 반복 실행
    public Page<DeptDto> selectDeptList(String searchKeyword,
                                        Pageable pageable) {
        Page<Dept> page=deptRepository.selectDeptList(searchKeyword, pageable);
        return page.map(data->mapStruct.toDto(data));
    }
//    추가: save() : 기본메소드(sql 코딩 필요없음)
//    조회 : db결과->엔티티 저장->DTO 복사->화면표시
//    추가: 화면입력->DTO 저장->엔티티 복사-> DB저장
    public void save(DeptDto deptDto) {
        Dept dept = mapStruct.toEntity(deptDto);
        deptRepository.save(dept);
    }



}
