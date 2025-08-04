package com.genesiswarlord.genesiswarlord.rules.repository;

import com.genesiswarlord.genesiswarlord.rules.entity.BarracksInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BarracksInfoRepository extends JpaRepository<BarracksInfo, String> {
}
