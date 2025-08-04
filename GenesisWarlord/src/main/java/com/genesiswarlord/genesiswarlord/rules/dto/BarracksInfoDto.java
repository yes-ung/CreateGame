package com.genesiswarlord.genesiswarlord.rules.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BarracksInfoDto {
    private String category;
    private String title;
    private String npcName;
    private String npcImgUrl;
    private String content;
}
