package com.junhwei.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.reflect.Member;

public interface InfoRepository extends JpaRepository<Info, Long> {
}
