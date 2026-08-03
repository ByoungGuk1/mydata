package com.app.mydata.domain.mydata.service;

import com.app.mydata.domain.mydata.dto.MydataTradeDTO;
import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataTradeException;
import com.app.mydata.domain.mydata.mapper.MydataKeyMapper;
import com.app.mydata.domain.mydata.mapper.MydataTradeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MydataTradeServiceImpl implements MydataTradeService {

    private final MydataTradeMapper mydataTradeMapper;
    private final MydataKeyMapper mydataKeyMapper;

    @Override
    public List<MydataTradeResponseDTO> getTradesByCiHash(
            String ciHash,
            LocalDate fromDate,
            LocalDate toDate) {

        if (ciHash == null || ciHash.isBlank()) {
            throw new MydataTradeException("ciHash는 필수입니다.");
        }

        if (mydataKeyMapper.existsByCiHash(ciHash) == 0) {
            throw new MydataTradeException("등록되지 않은 CiHash입니다.");
        }

        List<MydataTradeDTO> trades = mydataTradeMapper.selectByCiHashAndPeriod(
                ciHash, fromDate, toDate
        );

        return trades.stream()
                .map(MydataTradeResponseDTO::new)
                .collect(Collectors.toList());
    }
}
