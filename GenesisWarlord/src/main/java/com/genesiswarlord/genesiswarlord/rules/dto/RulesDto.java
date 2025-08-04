package com.genesiswarlord.genesiswarlord.rules.dto;

import lombok.*;

// 목적: 엔티티클래스는 필드를 생략 또는 추가 불가합니다.
//      그런데 화면에 불필요한 필드 또는 보안목적으로 생략하고 싶을떄
//      DTO(디자인 패턴) 를 사용합니다.
//     장점: 유연한 코딩
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RulesDto {
    //    필드는 엔티티 클래스를 참고해서 만드세요
    private Long dno;       // 기본키, 시퀀스
    private String dname;   // 부서명
    private String loc;     // 부서위치
}