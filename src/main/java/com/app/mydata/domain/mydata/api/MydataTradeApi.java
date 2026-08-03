package com.app.mydata.domain.mydata.api;

import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import com.app.mydata.domain.mydata.service.MydataTradeService;
import com.app.mydata.global.response.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mydata/trades")
public class MydataTradeApi {

    private final MydataTradeService mydataTradeService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MydataTradeResponseDTO>>> getTrades(
            @RequestParam String ciHash,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate
    ) {
        List<MydataTradeResponseDTO> result = mydataTradeService.getTradesByCiHash(
                ciHash, fromDate, toDate);
        return ResponseEntity.ok(ApiResponseDTO.of("myData 거래 내역 조회 성공", result));
    }
}
