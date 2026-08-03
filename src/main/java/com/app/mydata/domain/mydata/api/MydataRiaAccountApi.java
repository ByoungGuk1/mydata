package com.app.mydata.domain.mydata.api;

import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import com.app.mydata.domain.mydata.service.MydataRiaAccountService;
import com.app.mydata.global.response.ApiResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mydata/ria-accounts")
public class MydataRiaAccountApi {

    private final MydataRiaAccountService mydataRiaAccountService;

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<MydataRiaAccountResponseDTO>>> getRiaAccounts(
            @RequestParam String ciHash
    ) {
        List<MydataRiaAccountResponseDTO> result = mydataRiaAccountService.getAccountsByCiHash(
                ciHash);
        return ResponseEntity.ok(ApiResponseDTO.of("myData RIA 계좌 조회 성공", result));
    }
}
