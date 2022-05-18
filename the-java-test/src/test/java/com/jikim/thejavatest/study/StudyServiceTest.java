package com.jikim.thejavatest.study;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.jikim.thejavatest.domain.Member;
import com.jikim.thejavatest.domain.Study;
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

		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		Member member = new Member();
		member.setId(1L);
		member.setEmail("gmldnr2222@naver.com");

		when(memberService.findById(any())).thenReturn(Optional.of(member));

		Study study = new Study(10, "java");

		assertEquals("gmldnr2222@naver.com", memberService.findById(1L).get().getEmail());
		assertEquals("gmldnr2222@naver.com", memberService.findById(2L).get().getEmail());

		doThrow(new IllegalArgumentException()).when(memberService).validate(1L);

		assertThrows(IllegalArgumentException.class, () -> {
			memberService.validate(1L);
		});

		memberService.validate(2L);

		when(memberService.findById(any()))
			.thenReturn(Optional.of(member))
			.thenThrow(new RuntimeException())
			.thenReturn(Optional.empty());

		Optional<Member> byId = memberService.findById(1L);
		assertEquals("gmldnr2222@naver.com", byId.get().getEmail());

		assertThrows(RuntimeException.class, () -> {
			memberService.findById(2L);
		});

		assertEquals(Optional.empty(), memberService.findById(3L));

		// studyService.createNewStudy(1L, study);

	}

	@Test
	void ex(
		@Mock MemberService memberService,
		@Mock StudyRepository studyRepository
	) {
		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		Member member = new Member();
		member.setId(1L);
		member.setEmail("gmldnr2222@naver.com");

		Study study = new Study(10, "테스트");

		// TODO memberService 객체에 findById 메소드를 1L 값으로 호출하면 Optional.of(member) 객체를 리턴하도록 Stubbing
		when(memberService.findById(1L)).thenReturn(Optional.of(member));

		// TODO studyRepository 객체에 save 메소드를 study 객체로 호출하면 study 객체 그대로 리턴하도록 Stubbing
		when(studyRepository.save(study)).thenReturn(study);

		studyService.createNewStudy(1L, study);

		assertNotNull(study.getOwner());
		assertEquals(member, study.getOwner());

	}

}