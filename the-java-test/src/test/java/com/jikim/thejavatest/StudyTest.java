package com.jikim.thejavatest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class) // _ -> ""
class StudyTest {

	@Test
	@DisplayName("스터디 만들기")
	void create_new_study() {
		Study study = new Study();
		assertNotNull(study);
		System.out.println("create");
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