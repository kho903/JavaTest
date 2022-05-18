package com.jikim.thejavatest.study;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
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
		// Given
		StudyService studyService = new StudyService(memberService, studyRepository);
		assertNotNull(studyService);

		Member member = new Member();
		member.setId(1L);
		member.setEmail("gmldnr2222@naver.com");

		Study study = new Study(10, "테스트");

		given(memberService.findById(1L)).willReturn(Optional.of(member));
		given(studyRepository.save(study)).willReturn(study);

		// When
		studyService.createNewStudy(1L, study);

		// Then
		assertNotNull(study.getOwner());
		assertEquals(member, study.getOwner());

		// verify(memberService, times(1)).notify(study);
		then(memberService).should().notify(study);

		// 순서
		InOrder inOrder = inOrder(memberService);
		inOrder.verify(memberService).notify(study);
		inOrder.verify(memberService).notify(member);

		// verifyNoMoreInteractions(memberService);
		then(memberService).shouldHaveNoMoreInteractions();

	}

	@DisplayName("다른 사용자가 볼 수 있도록 스터디를 공개한다.")
	@Test
	void openStudy(
		@Mock MemberService memberService,
		@Mock StudyRepository studyRepository
	) {
		// Given
		StudyService studyService = new StudyService(memberService, studyRepository);
		Study study = new Study(10, "더 자바, 테스트");
		assertNull(study.getOpenedDateTime());
		// TODO studyRepository Mock 객체의 save 메소드를호출 시 study를 리턴하도록 만들기.
		given(studyRepository.save(study)).willReturn(study);

		// When
		studyService.openStudy(study);

		// Then
		// TODO study의 status가 OPENED로 변경됐는지 확인
		assertEquals(study.getStatus(), StudyStatus.OPENED);
		// TODO study의 openedDataTime이 null이 아닌지 확인
		assertNotNull(study.getOpenedDateTime());
		// TODO memberService의 notify(study)가 호출 됐는지 확인.
		then(memberService).should().notify(study);
	}

}