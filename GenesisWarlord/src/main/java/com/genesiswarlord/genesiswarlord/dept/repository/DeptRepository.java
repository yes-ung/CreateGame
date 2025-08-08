package com.genesiswarlord.genesiswarlord.dept.repository;


import com.genesiswarlord.genesiswarlord.dept.entity.Dept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//   목적 : 실제 CRUD(추가/조회/수정/삭제)를 위한 메소드 또는 SQL을 만드는 곳
// @Repository : @Service 와 비슷, IOC를 위한 어노테이션
// IOC(제어의 역전, Inversion of COntrol), DI(의존성주입,Dependency Injection)
// AOP(공통 기능을 분리해서 사용하게 해주는 기능, 예) @ControllerAdviser)
// JPA 클래스 또는 인터페이스르 상속받으면 기본 메소드를 모두 사용가능
// 추가: 직접 SQL(JPQL)을 작성할 수 있습니다.
// TODO : 상속 사용법: 인터페이스명 extends JpaRepository<엔티티클래스명, 기본키자료형>
@Repository
public interface DeptRepository extends JpaRepository<Dept, Long> {
    //    TODO: like 검색은 기본메소드에 없음, 직접 작성하기
    @Query(value = "select d from Rules d\n" +
            "where d.dname like %:searchKeyword%")
    Page<Dept> selectDeptList(@Param("searchKeyword") String searchKeyword,
                              Pageable pageable);
}
