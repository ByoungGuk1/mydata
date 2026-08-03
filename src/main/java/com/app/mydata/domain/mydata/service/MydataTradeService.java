package com.app.mydata.domain.mydata.service;

import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import java.time.LocalDate;
import java.util.List;

public interface MydataTradeService {

    List<MydataTradeResponseDTO> getTradesByCiHash(
            String ciHash,
            LocalDate fromDate,
            LocalDate toDate
    );
}
