package com.jikim.thejavatest.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.jikim.thejavatest.study.StudyStatus;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Study {

	@Id @GeneratedValue
	private Long id;
	private StudyStatus status = StudyStatus.DRAFT;
	private int limitCount;
	private String name;
	private LocalDateTime openedDateTime;
	@ManyToOne
	private Member owner;

	public Study(int limitCount, String name) {
		this.limitCount = limitCount;
		this.name = name;
	}

	public Study(int limitCount) {
		if (limitCount < 0) {
			throw new IllegalStateException("limit은 0보다 커야 한다.");
		}
		this.limitCount = limitCount;
	}

	public void open() {
		this.openedDateTime = LocalDateTime.now();
		this.status = StudyStatus.OPENED;
	}
}
