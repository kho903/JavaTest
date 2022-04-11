package com.jikim.thejavatest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

import java.time.Duration;
import java.util.function.Supplier;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

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
	@DisplayName("assumeTest")
	@EnabledOnOs(OS.MAC)
	@EnabledOnJre(JRE.JAVA_11)
	@EnabledIfEnvironmentVariable(named = "HOME", matches = "/Users/kimjihun")
	void assumeTest() {
		assumeTrue(System.getenv().get("HOME").equals("/Users/kimjihun"));
		assumingThat(System.getenv().get("HOME").equals("/Users/kimjihun"), () -> {
			System.out.println("kimjihun");
			Study study = new Study(10);
			assertThat(study.getLimit()).isGreaterThan(0);
		});
		assumingThat(System.getenv().get("HOME").equals("/Users/kimjihun1"), () -> {
			System.out.println("kimjihun1");
			Study study = new Study(10);
			assertThat(study.getLimit()).isGreaterThan(0);
		});
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}

	@Test
	@DisabledOnOs(OS.MAC)
	@DisabledOnJre(JRE.JAVA_11)
	void create_new_study_again() {
		System.out.println("create1");
	}

	@DisplayName("스터디 만들기 반복")
	@RepeatedTest(value = 10, name = "{displayName}, {currentRepetition} / {totalRepetitions}")
	void repeatTest(RepetitionInfo repetitionInfo) {
		System.out.println("test : " + repetitionInfo.getCurrentRepetition() + " / " +
			repetitionInfo.getTotalRepetitions());
	}

	@DisplayName("스터디 만들기")
	@ParameterizedTest(name = "{index} {displayName} message = {0}")
	@ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요."})
	@EmptySource
	@NullSource
	@NullAndEmptySource // @NullSource + @EmptySource
	void parameterizedTest(String message) {
		System.out.println(message);
	}

	static class StudyConverter extends SimpleArgumentConverter {
		@Override
		protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
			assertEquals(Study.class, targetType, "Can only convert to Study");
			return new Study(Integer.parseInt(source.toString()));
		}
	}

	@DisplayName("valueSourceAndConverterTest")
	@ParameterizedTest(name = "{index} {displayName} message = {0}")
	@ValueSource(ints = {10, 20, 40})
	void valueSourceAndConverterTest(
		// Integer limit
		@ConvertWith(StudyConverter.class) Study study
	) {
		// System.out.println(limit);
		System.out.println(study.getLimit());
	}

	static class StudyAggregator implements ArgumentsAggregator {

		@Override
		public Object aggregateArguments(ArgumentsAccessor accessor, ParameterContext context) throws
			ArgumentsAggregationException {
			return new Study(accessor.getInteger(0), accessor.getString(1));
		}
	}

	@DisplayName("csvSource")
	@ParameterizedTest(name = "{index} {displayName} message = {0}")
	@CsvSource({"10, '자바 스터디'", "20, 스프링"})
	void csvSourceTest(
		// Integer limit, String name
		// ArgumentsAccessor argumentsAccessor
		@AggregateWith(StudyAggregator.class) Study study
	) {
		// Study study = new Study(limit, name);
		// Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
		System.out.println(study);
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