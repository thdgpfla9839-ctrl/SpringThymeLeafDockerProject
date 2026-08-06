package com.sist.web.service;

import java.util.*;
import com.sist.web.entity.*;
public interface RecipeService {
	
	// 타이틀(레시피제목) 검색
	public List<Recipe> findByTitleContains(String title);
	// 쉐프검색
	public List<Recipe> findByChefContains(String chef);
	
	public List<Recipe> recipeListData(int page);
	public int[] getPageData(int page, int rowsize);
	
	
	public List<Chef> chefListData(int page);

}
