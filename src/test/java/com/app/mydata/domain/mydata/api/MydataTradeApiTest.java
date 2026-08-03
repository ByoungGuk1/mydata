package com.app.mydata.domain.mydata.api;

import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataTradeException;
import com.app.mydata.domain.mydata.service.MydataTradeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MydataTradeApi.class)
class MydataTradeApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MydataTradeService mydataTradeService;

    @Test
    void 거래내역조회_성공시_200과_결과를_반환한다() throws Exception {
        MydataTradeResponseDTO response = MydataTradeResponseDTO.builder()
                .tradeId(1L)
                .ciHash("test-ci-hash")
                .build();

        when(mydataTradeService.getTradesByCiHash(eq("test-ci-hash"), any(), any()))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", "test-ci-hash")
                        .param("fromDate", "2026-01-01")
                        .param("toDate", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].tradeId").value(1));
    }

    @Test
    void ciHash가_없으면_예외발생시_400을_반환한다() throws Exception {
        when(mydataTradeService.getTradesByCiHash(any(), any(), any()))
                .thenThrow(new MydataTradeException("ciHash는 필수입니다."));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ciHash는 필수입니다."));
    }
}