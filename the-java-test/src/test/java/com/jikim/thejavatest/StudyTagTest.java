package com.jikim.thejavatest;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

class StudyTagTest {
	@Test
	// @Tag("fast")
	@FastTest
	void TagFast() {
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}

	@Test
	// @Tag("slow")
	@SlowTest
	void TagSlow() {
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}
}