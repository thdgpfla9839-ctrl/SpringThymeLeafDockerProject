package com.sist.web.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sist.web.entity.Recipe;
import java.util.*;
@Repository

public interface RecipeRepository extends JpaRepository<Recipe, Integer> // 앞에는 클래스명 뒤에는 해당되는 아이디의 데이터형
{

	/*
	 *  findBy컬럼명연산자
	 *  => 예 : findByName(String name)
	 *          => WHERE name=? ===========> equals
	 *          
	 *     예 : findByTitleStartWith(String title)    
	 *          => WHERE title LIKE 'title%' 
	 *          
	 *     예 : findByTitleEndsWith(String title) 
	 *          => WHERE title LIKE '%title'    
	 *           
	 *     예 : findByTitleContains(String title)  
	 *          => WHERE title LIKE '%title%'  
	 *          
	 *    추가로
	 *    findByOrderByTitleDesc() order by나 group by 뒤에 컬럼이 나와야한다        
	 */
	
	// 타이틀(레시피제목) 검색
	public List<Recipe> findByTitleContains(String title);
	// 쉐프검색
	public List<Recipe> findByChefContains(String chef);
}
