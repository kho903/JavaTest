package com.jikim.thejavatest.member;

import java.util.Optional;

import com.jikim.thejavatest.domain.Member;

public interface MemberService {

	Optional<Member> findById(Long memberId) throws MemberNotFoundException;
}
