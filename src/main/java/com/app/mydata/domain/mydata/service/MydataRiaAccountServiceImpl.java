package com.app.mydata.domain.mydata.service;


import com.app.mydata.domain.mydata.dto.MydataRiaAccountDTO;
import com.app.mydata.domain.mydata.dto.response.MydataRiaAccountResponseDTO;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountException;
import com.app.mydata.domain.mydata.exception.MydataRiaAccountNotFoundException;
import com.app.mydata.domain.mydata.mapper.MydataKeyMapper;
import com.app.mydata.domain.mydata.mapper.MydataRiaAccountMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class MydataRiaAccountServiceImpl implements MydataRiaAccountService {

    private final MydataRiaAccountMapper mydataRiaAccountMapper;
    private final MydataKeyMapper mydataKeyMapper;

    @Override
    public List<MydataRiaAccountResponseDTO> getAccountsByCiHash(
            String ciHash) {

        if (ciHash == null || ciHash.isBlank()) {
            throw new MydataRiaAccountException("ciHash는 필수입니다.");
        }

        if (mydataKeyMapper.existsByCiHash(ciHash) == 0) {
            throw new MydataRiaAccountException("등록되지 않은 CiHash입니다.");
        }

        List<MydataRiaAccountDTO> accounts = mydataRiaAccountMapper.selectByCiHash(
                ciHash
        );

        if (accounts.isEmpty()) {
            throw new MydataRiaAccountNotFoundException("해당 ciHash에 대한 RIA 계좌 정보가 없습니다.");
        }

        return accounts.stream()
                .map(MydataRiaAccountResponseDTO::new)
                .collect(Collectors.toList());
    }
}
