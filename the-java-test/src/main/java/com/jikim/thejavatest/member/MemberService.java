package com.jikim.thejavatest.member;

import java.util.Optional;

import com.jikim.thejavatest.domain.Member;
import com.jikim.thejavatest.domain.Study;

public interface MemberService {

	Optional<Member> findById(Long memberId) throws MemberNotFoundException;

	void validate(Long memberId);

	void notify(Study newStudy);

	void notify(Member member);
}
