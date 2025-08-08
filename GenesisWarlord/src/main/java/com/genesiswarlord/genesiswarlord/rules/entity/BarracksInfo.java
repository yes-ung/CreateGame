package com.genesiswarlord.genesiswarlord.rules.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;



@Entity
@Table(name = "BARRACKS_INFO")
//롬북 어노테이션
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(of = "category", callSuper = false) // of="기본키",callSuper = false(부모필드는 제외)
                                                  // 의미: dno만으로 equals, hashCode 함수를 만들겠다는 의미
public class BarracksInfo{

    @Id                                         //필드 위에 붙이고 ,기본키임을 지정하는 어노테이션
    private String category;
    private String title;
    private String npcName;
    private String npcImgUrl;
    private String content;

}
