package com.jikim.thejavatest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // _ -> ""
class StudyTest {

	@Test
	@DisplayName("스터디 만들기")
	void create_new_study() {
		// Study study = new Study();
		Study study = new Study(-10);

		assertAll(
			() -> assertNotNull(study),
			() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
				() -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "여야 한다."),
			() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
		);

		assertNotNull(study);
		assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 DRAFT여야 한다.");
		assertEquals(StudyStatus.DRAFT, study.getStatus(),
			() -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "여야 한다.");
		// 다음과 같이 문자열 연산이 들어가면 람다식이 성능적으로 유리
		assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
			@Override
			public String get() {
				return "스터디를 처음 만들면 상태값이 DRAFT여야 한다.";
			}
		});
		// 위의 테스트에서 예외가 터지면 아래 테스트의 상태는 모른다.
		// -> assertAll
		assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
	}

	@Test
	@DisplayName("assertAll")
	void create_new_study_assert_all() {
		Study study = new Study(-10);

		assertAll(
			() -> assertNotNull(study),
			() -> assertEquals(StudyStatus.DRAFT, study.getStatus(),
				() -> "스터디를 처음 만들면 상태값이 " + StudyStatus.DRAFT + "여야 한다."),
			() -> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.")
		);
	}

	@Test
	@Disabled("assertThrows")
	void create_study_assert_throws() {
		IllegalStateException exception = assertThrows(IllegalStateException.class, () -> new Study(-10));
		String message = exception.getMessage();
		assertEquals("limit은 0보다 커야 한다.", message);
	}

	@Test
	@Disabled("assertTimeOut")
	void create_study_assert_time_out() {
		// assertTimeout(Duration.ofMillis(100), () -> {
		// 	new Study(10);
		// 	Thread.sleep(300);
		// });

		assertTimeoutPreemptively(Duration.ofMillis(100), () -> {
			new Study(10);
			Thread.sleep(300);
		});
		// TODO: ThreadLocal
	}

	@Test
	@DisplayName("assertJ.core.api.Assertions")
	void assertJ() {
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}

	@Test
	void create_new_study_again() {
		System.out.println("create1");
	}

	@Test
	@Disabled
	void disabled() {
		System.out.println("disabled");
	}

	@BeforeAll
	static void beforeAll() {
		System.out.println("before all");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("after all");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("before each");
	}

	@AfterEach
	void afterEach() {
		System.out.println("after each");
	}
}