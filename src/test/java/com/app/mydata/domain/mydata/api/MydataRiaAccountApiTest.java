package com.app.mydata.domain.mydata.api;

import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountException;
import com.app.mydata.domain.mydata.service.MydataRiaAccountService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MydataRiaAccountApi.class)
class MydataRiaAccountApiTest {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    MydataRiaAccountService mydataRiaAccountService;

    @Test
    void RIA계좌조회_성공시_200과_결과를_반환한다() throws Exception {
        MydataRiaAccountResponseDTO response = MydataRiaAccountResponseDTO.builder()
                .mydataAccountId(1L)
                .ciHash("test-ci-hash")
                .build();

        when(mydataRiaAccountService.getAccountsByCiHash(eq("test-ci-hash")))
                .thenReturn(List.of(response));

        mockMvc.perform(get("/api/mydata/ria-accounts")
                        .param("ciHash", "test-ci-hash"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].mydataAccountId").value(1));
    }

    @Test
    void ciHash가_없으면_예외발생시_400을_반환한다() throws Exception {
        when(mydataRiaAccountService.getAccountsByCiHash(any()))
                .thenThrow(new MydataRiaAccountException("ciHash는 필수입니다."));

        mockMvc.perform(get("/api/mydata/ria-accounts")
                        .param("ciHash", ""))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("ciHash는 필수입니다."));
    }
}
