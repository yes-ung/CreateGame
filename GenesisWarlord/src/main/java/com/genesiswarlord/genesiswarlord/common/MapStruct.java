package com.genesiswarlord.genesiswarlord.common;


import com.genesiswarlord.genesiswarlord.dept.dto.DeptDto;
import com.genesiswarlord.genesiswarlord.dept.entity.Dept;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",                                                 // spring 과 연결
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // null 제외 기능(update 시 사용)
)
public interface MapStruct {
    //    TODO: 여기서 할것: 각 업무별로 함수명 정하기
//         -> 라이브러리가 알아서 그 함수명으로 복사해 줍니다.
//    TODO: 1) 부서: Dept <-> DeptDto
    DeptDto toDto(Dept dept);
    Dept toEntity(DeptDto deptDto);


}
