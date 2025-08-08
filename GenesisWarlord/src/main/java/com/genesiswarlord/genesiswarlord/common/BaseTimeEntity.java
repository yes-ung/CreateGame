package com.genesiswarlord.genesiswarlord.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
// 목적 : JPA 공통 클래스, 생성일시, 수정일시 값을 자동으로 만들어 줍니다.
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTimeEntity {

    @CreatedDate                        //insert시 생성일시 자동생성
    @Column(updatable = false)
    private LocalDateTime insertTime;   //생성일시
    @LastModifiedDate                   // update시 수정일시 자동생성
    private LocalDateTime  updateTime;  // 수정일시
}

