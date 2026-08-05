package com.app.mydata.domain.mydata.api;

import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataTradeException;
import com.app.mydata.domain.mydata.exception.MydataTradeNotFoundException;
import com.app.mydata.domain.mydata.service.MydataTradeService;
import com.app.mydata.global.exception.GlobalExceptionHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MydataTradeApiTest {

    private MydataTradeService mydataTradeService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mydataTradeService = mock(MydataTradeService.class);
        mockMvc = MockMvcBuilders
                .standaloneSetup(new MydataTradeApi(mydataTradeService))
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void getTradesAcceptsValidRequestWithDateRange() throws Exception {
        MydataTradeResponseDTO response = MydataTradeResponseDTO.builder()
                .tradeId(1L)
                .ciHash("test-ci-hash")
                .build();
        when(mydataTradeService.getTradesByCiHash(any())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", "test-ci-hash")
                        .param("fromDate", "2026-01-01")
                        .param("toDate", "2026-12-31"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("myData 거래 내역 조회 성공"))
                .andExpect(jsonPath("$.data[0].tradeId").value(1));

        verify(mydataTradeService).getTradesByCiHash(any());
    }

    @Test
    void getTradesAcceptsRequestWithoutDateRange() throws Exception {
        MydataTradeResponseDTO response = MydataTradeResponseDTO.builder()
                .tradeId(1L)
                .ciHash("test-ci-hash")
                .build();
        when(mydataTradeService.getTradesByCiHash(any())).thenReturn(List.of(response));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", "test-ci-hash"))
                .andExpect(status().isOk());

        verify(mydataTradeService).getTradesByCiHash(any());
    }

    @Test
    void getTradesRejectsMissingCiHash() throws Exception {
        mockMvc.perform(get("/api/mydata/trades"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ciHash는 필수입니다."));

        verify(mydataTradeService, never()).getTradesByCiHash(any());
    }

    @Test
    void getTradesRejectsBlankCiHash() throws Exception {
        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ciHash는 필수입니다."));

        verify(mydataTradeService, never()).getTradesByCiHash(any());
    }

    @Test
    void getTradesReturnsBadRequestWhenCiHashUnregistered() throws Exception {
        when(mydataTradeService.getTradesByCiHash(any()))
                .thenThrow(new MydataTradeException("등록되지 않은 CiHash입니다."));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", "unknown-ci-hash"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("등록되지 않은 CiHash입니다."));
    }

    @Test
    void getTradesReturnsNotFoundWhenNoTradesExist() throws Exception {
        when(mydataTradeService.getTradesByCiHash(any()))
                .thenThrow(new MydataTradeNotFoundException("해당 ciHash에 대한 거래 정보가 없습니다."));

        mockMvc.perform(get("/api/mydata/trades")
                        .param("ciHash", "test-ci-hash"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("해당 ciHash에 대한 거래 정보가 없습니다."));
    }
}