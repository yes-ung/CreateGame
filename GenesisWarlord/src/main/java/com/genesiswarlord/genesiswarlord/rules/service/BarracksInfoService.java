package com.genesiswarlord.genesiswarlord.rules.service;

import com.genesiswarlord.genesiswarlord.common.MapStruct;
import com.genesiswarlord.genesiswarlord.rules.entity.BarracksInfo;
import com.genesiswarlord.genesiswarlord.rules.repository.BarracksInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarracksInfoService {

    private final BarracksInfoRepository barracksInfoRepository;
    private final MapStruct mapStruct;

    public BarracksInfo findBarracksInfoById(){

        return barracksInfoRepository.findById("1").orElse(null);
    }
    public List<BarracksInfo> findAllBarracksInfo(){
        return barracksInfoRepository.findAll();
    }
}
