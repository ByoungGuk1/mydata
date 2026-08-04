package com.app.mydata.domain.mydata.service;

import com.app.mydata.domain.mydata.dto.MydataTradeDTO;
import com.app.mydata.domain.mydata.dto.response.MydataTradeResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataTradeException;
import com.app.mydata.domain.mydata.exception.MydataTradeNotFoundException;
import com.app.mydata.domain.mydata.mapper.MydataKeyMapper;
import com.app.mydata.domain.mydata.mapper.MydataTradeMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MydataTradeServiceImplTest {

    @Mock
    MydataTradeMapper mydataTradeMapper;

    @Mock
    MydataKeyMapper mydataKeyMapper;

    @InjectMocks
    MydataTradeServiceImpl mydataTradeService;

    @Test
    void getTradesByCiHash_정상요청이면_결과를_반환한다() {
        String ciHash = "test-ci-hash";
        LocalDate fromDate = LocalDate.of(2026, 1, 1);
        LocalDate toDate = LocalDate.of(2026, 12, 31);

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(1);

        MydataTradeDTO dto = MydataTradeDTO.builder()
                .tradeId(1L)
                .ciHash(ciHash)
                .brokerName("증권사A")
                .build();

        when(mydataTradeMapper.selectByCiHashAndPeriod(ciHash, fromDate, toDate))
                .thenReturn(List.of(dto));

        List<MydataTradeResponseDTO> result = mydataTradeService.getTradesByCiHash(ciHash, fromDate, toDate);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCiHash()).isEqualTo(ciHash);
    }

    @Test
    void getTradesByCiHash_ciHash가_null이면_예외를_던진다() {
        assertThatThrownBy(() -> mydataTradeService.getTradesByCiHash(null, null, null))
                .isInstanceOf(MydataTradeException.class);

        verifyNoInteractions(mydataTradeMapper);
    }

    @Test
    void getTradesByCiHash_ciHash가_빈문자열이면_예외를_던진다() {
        assertThatThrownBy(() -> mydataTradeService.getTradesByCiHash("", null, null))
                .isInstanceOf(MydataTradeException.class);

        verifyNoInteractions(mydataTradeMapper);
    }

    @Test
    void getTradesByCiHash_등록되지않은_ciHash면_예외를_던진다() {
        String ciHash = "unknown-ci-hash";

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(0);

        assertThatThrownBy(() -> mydataTradeService.getTradesByCiHash(ciHash, null, null))
                .isInstanceOf(MydataTradeException.class);

        verifyNoInteractions(mydataTradeMapper);
    }

    @Test
    void getTradesByCiHash_조회결과가_없으면_NotFound예외를_던진다() {
        String ciHash = "test-ci-hash";

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(1);
        when(mydataTradeMapper.selectByCiHashAndPeriod(ciHash, null, null)).thenReturn(List.of());

        assertThatThrownBy(() -> mydataTradeService.getTradesByCiHash(ciHash, null, null))
                .isInstanceOf(MydataTradeNotFoundException.class);
    }
}