package com.sist.web.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sist.web.entity.Recipe;
import com.sist.web.entity.RecipeDetail;

public interface RecipeDetailRepository extends JpaRepository<RecipeDetail, Integer>{

	// 상세보기
	public RecipeDetail findByNo(int no);
}
