package com.jikim.thejavatest;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

// @ExtendWith(FindSlowTestExtension.class)
class StudyTagTest {

	@RegisterExtension
	static FindSlowTestExtension findSlowTestExtension = new FindSlowTestExtension(1000L);

	@Test
	// @Tag("fast")
	@FastTest
	void TagFast() throws InterruptedException {
		Thread.sleep(1005L);
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}

	@Test
	// @Tag("slow")
	@SlowTest
	void TagSlow() throws InterruptedException {
		Thread.sleep(1005L);
		Study study = new Study(10);
		assertThat(study.getLimit()).isGreaterThan(0);
	}
}