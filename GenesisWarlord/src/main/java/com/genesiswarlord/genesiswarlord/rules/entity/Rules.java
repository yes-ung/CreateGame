package com.genesiswarlord.genesiswarlord.rules.entity;


import com.genesiswarlord.genesiswarlord.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;


// 목적: JPA에서 관리하는 클래스, 이걸보고 자동으로 sql 만듬
//  JPA 어노테이션
@Entity                           //JPA 관리클래스로 만드는 어노테이션
@Table(name = "TB_DEPT")          // DB 테이블과 클래스를 연결하는 어노테이션
@SequenceGenerator(               // DB시권스, JPA시퀀스 2개를 설정
        name = "SQ_DEPT_JPA",     //JPA시퀀스 이름
        sequenceName = "SQ_DEPT", //DB시퀀스 이름
        allocationSize = 1        // 동기화설정(DB 1개 증가하면 똑같이 증가설정)
)
//롬북 어노테이션
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "dno", callSuper = false) // of="기본키",callSuper = false(부모필드는 제외)
                                                  // 의미: dno만으로 equals, hashCode 함수를 만들겠다는 의미
public class Rules extends BaseTimeEntity {

    @Id                                         //필드 위에 붙이고 ,기본키임을 지정하는 어노테이션
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "SQ_DEPT_JPA") // JPA 시퀀스이름 넣기
    private Long dno;      // 기본키, 시퀀스
    private String dname;  // 부서명
    private String loc;    // 부서위치

}
