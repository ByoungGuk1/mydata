package com.app.mydata.domain.mydata.service;


import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import com.app.mydata.domain.mydata.dto.request.MydataRiaAccountRequestDTO;
import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountException;
import com.app.mydata.domain.mydata.mapper.MydataKeyMapper;
import com.app.mydata.domain.mydata.mapper.MydataRiaAccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MydataRiaAccountServiceImplTest {

    @Mock
    private MydataRiaAccountMapper mydataRiaAccountMapper;

    @Mock
    private MydataKeyMapper mydataKeyMapper;

    @InjectMocks
    private MydataRiaAccountServiceImpl mydataRiaAccountService;

    @Test
    void getAccountsByCiHashReturnsAccountsWhenCiHashIsRegistered() {
        String ciHash = "test-ci-hash";
        MydataRiaAccountRequestDTO request = MydataRiaAccountRequestDTO.builder()
                .ciHash(ciHash)
                .build();

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(1);

        MydataRiaAccountDTO dto = MydataRiaAccountDTO.builder()
                .mydataAccountId(1L)
                .ciHash(ciHash)
                .brokerName("증권사A")
                .riaLimit(BigDecimal.valueOf(30_000_000))
                .riaCumulativeSell(BigDecimal.ZERO)
                .build();
        when(mydataRiaAccountMapper.selectByCiHash(ciHash)).thenReturn(List.of(dto));

        List<MydataRiaAccountResponseDTO> result = mydataRiaAccountService.getAccountsByCiHash(request);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCiHash()).isEqualTo(ciHash);
        assertThat(result.get(0).getBrokerName()).isEqualTo("증권사A");
    }

    @Test
    void getAccountsByCiHashThrowsExceptionWhenCiHashIsUnregistered() {
        String ciHash = "unknown-ci-hash";
        MydataRiaAccountRequestDTO request = MydataRiaAccountRequestDTO.builder()
                .ciHash(ciHash)
                .build();

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(0);

        assertThatThrownBy(() -> mydataRiaAccountService.getAccountsByCiHash(request))
                .isInstanceOf(MydataRiaAccountException.class)
                .hasMessage("등록되지 않은 CiHash입니다.");

        verifyNoInteractions(mydataRiaAccountMapper);
    }

    @Test
    void getAccountsByCiHashReturnsEmptyListWhenNoAccountsExist() {
        String ciHash = "test-ci-hash";
        MydataRiaAccountRequestDTO request = MydataRiaAccountRequestDTO.builder()
                .ciHash(ciHash)
                .build();

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(1);
        when(mydataRiaAccountMapper.selectByCiHash(ciHash)).thenReturn(List.of());

        List<MydataRiaAccountResponseDTO> result = mydataRiaAccountService.getAccountsByCiHash(request);

        assertThat(result).isEmpty();
    }
}