package com.jikim.thejavatest.study;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jikim.thejavatest.member.MemberService;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

	/*@Mock
	MemberService memberService;

	@Mock
	StudyRepository studyRepository;*/

	@Test
	void createStudyService(
		@Mock MemberService memberService,
		@Mock StudyRepository studyRepository
	) {
		// MemberService memberService = mock(MemberService.class);
		//
		// StudyRepository studyRepository = mock(StudyRepository.class);
		StudyService studyService = new StudyService(memberService, studyRepository);

		assertNotNull(studyService);
	}

}