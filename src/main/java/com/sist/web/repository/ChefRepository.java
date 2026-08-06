package com.sist.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;

import java.util.*;
public interface ChefRepository extends JpaRepository<Chef, String> // 앞에는 클래스명 뒤에는 해당되는 아이디의 데이터형
{

	// 이용해 볼 것들
	// join Recipe => Chef => @Query
	// findAll
	// count
	
}
