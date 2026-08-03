package com.app.mydata.domain.mydata.service;

import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountException;
import com.app.mydata.domain.mydata.mapper.MydataKeyMapper;
import com.app.mydata.domain.mydata.mapper.MydataRiaAccountMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MydataRiaAccountServiceImplTest {

    @Mock
    MydataRiaAccountMapper mydataRiaAccountMapper;

    @Mock
    MydataKeyMapper mydataKeyMapper;

    @InjectMocks
    MydataRiaAccountServiceImpl mydataRiaAccountService;

    @Test
    void getAccountsByCiHash_정상요청이면_결과를_반환한다() {
        String ciHash = "test-ci-hash";

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(1);

        MydataRiaAccountDTO dto = MydataRiaAccountDTO.builder()
                .mydataAccountId(1L)
                .ciHash(ciHash)
                .brokerName("증권사A")
                .build();

        when(mydataRiaAccountMapper.selectByCiHash(ciHash)).thenReturn(List.of(dto));

        List<MydataRiaAccountResponseDTO> result = mydataRiaAccountService.getAccountsByCiHash(ciHash);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getCiHash()).isEqualTo(ciHash);
    }

    @Test
    void getAccountsByCiHash_ciHash가_null이면_예외를_던진다() {
        assertThatThrownBy(() -> mydataRiaAccountService.getAccountsByCiHash(null))
                .isInstanceOf(MydataRiaAccountException.class);

        verifyNoInteractions(mydataRiaAccountMapper);
    }

    @Test
    void getAccountsByCiHash_ciHash가_빈문자열이면_예외를_던진다() {
        assertThatThrownBy(() -> mydataRiaAccountService.getAccountsByCiHash(""))
                .isInstanceOf(MydataRiaAccountException.class);

        verifyNoInteractions(mydataRiaAccountMapper);
    }

    @Test
    void getAccountsByCiHash_등록되지않은_ciHash면_예외를_던진다() {
        String ciHash = "unknown-ci-hash";

        when(mydataKeyMapper.existsByCiHash(ciHash)).thenReturn(0);

        assertThatThrownBy(() -> mydataRiaAccountService.getAccountsByCiHash(ciHash))
                .isInstanceOf(MydataRiaAccountException.class);

        verifyNoInteractions(mydataRiaAccountMapper);
    }
}