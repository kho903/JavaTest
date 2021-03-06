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

		Study study = new Study(10, "?????????");

		given(memberService.findById(1L)).willReturn(Optional.of(member));
		given(studyRepository.save(study)).willReturn(study);

		// When
		studyService.createNewStudy(1L, study);

		// Then
		assertNotNull(study.getOwner());
		assertEquals(member, study.getOwner());

		// verify(memberService, times(1)).notify(study);
		then(memberService).should().notify(study);

		// ??????
		InOrder inOrder = inOrder(memberService);
		inOrder.verify(memberService).notify(study);
		inOrder.verify(memberService).notify(member);

		// verifyNoMoreInteractions(memberService);
		then(memberService).shouldHaveNoMoreInteractions();

	}

	@DisplayName("?????? ???????????? ??? ??? ????????? ???????????? ????????????.")
	@Test
	void openStudy(
		@Mock MemberService memberService,
		@Mock StudyRepository studyRepository
	) {
		// Given
		StudyService studyService = new StudyService(memberService, studyRepository);
		Study study = new Study(10, "??? ??????, ?????????");
		assertNull(study.getOpenedDateTime());
		// TODO studyRepository Mock ????????? save ?????????????????? ??? study??? ??????????????? ?????????.
		given(studyRepository.save(study)).willReturn(study);

		// When
		studyService.openStudy(study);

		// Then
		// TODO study??? status??? OPENED??? ??????????????? ??????
		assertEquals(study.getStatus(), StudyStatus.OPENED);
		// TODO study??? openedDataTime??? null??? ????????? ??????
		assertNotNull(study.getOpenedDateTime());
		// TODO memberService??? notify(study)??? ?????? ????????? ??????.
		then(memberService).should().notify(study);
	}

}