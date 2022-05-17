package com.jikim.thejavatest.study;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jikim.thejavatest.domain.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}
