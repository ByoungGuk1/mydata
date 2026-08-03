package com.app.mydata.domain.mydata.service;

import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import java.util.List;

public interface MydataRiaAccountService {

    List<MydataRiaAccountResponseDTO> getAccountsByCiHash(
            String ciHash
    );
}
