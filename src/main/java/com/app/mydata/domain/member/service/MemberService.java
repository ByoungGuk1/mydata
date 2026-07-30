package com.app.mydata.domain.member.service;

import com.app.mydata.domain.member.dto.response.MemberResponseDTO;

public interface MemberService {
  // 회원 정보 조회
  public MemberResponseDTO getMemberById(String id);
}
