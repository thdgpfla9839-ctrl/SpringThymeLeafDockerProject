package com.sist.web.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
	public Page<Recipe> findByTitleContains(String title, Pageable pg);
	/*
	 *  SELECT *
	 *  FROM recipe
	 *  WHERE title LIKE '%데이터%'
	 *  OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
	 * 
	 */
	
	
	public Page<Recipe> findByChefContains(String chef, Pageable pg);
	
	// 카운드 개수 
	public long countByTitleContains(String title);
	/*
	 *  SELECT COUNT(*)
	 *  FROM recipe
	 *  WHERE title LIKE '%데이터%'
	 */
	public long countByChefContains(String chef);
	
	// 여기서는 대소문자 구분하지 않아서 그냥 써도 됨
	@Query(
			value=""" 
			SELECT *
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
			            INTERSECT 
			            SELECT no FROM recipedetail) 
			ORDER BY no DESC
			OFFSET :start ROWS FETCH NEXT 12 ROWS ONLY            
			""",nativeQuery = true)
	public List<Recipe> recipeListData(@Param("start") int start);
	
	@Query(
			value=""" 
			SELECT COUNT(*)
			FROM recipe
			WHERE no IN(SELECT no FROM recipe
			            INTERSECT 
			            SELECT no FROM recipedetail)      
			""",nativeQuery = true)
	public int recipeCount();
}
