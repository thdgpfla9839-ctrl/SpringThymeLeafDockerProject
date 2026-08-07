package com.sist.web.service;


import java.util.List;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;
import com.sist.web.entity.RecipeDetail;
public interface RecipeService {
	

	public List<Recipe> findByTitleContains(String title, int page);
	public List<Recipe> findByChefContains(String chef, int page);
	public List<Recipe> recipeListData(int page);
	public int[] getPageData(int page, int rowsize);
	public List<Chef> chefListData(int page);
	public int[] getPageDataFind(int mode, int page, int rowsize, String fd);
	public int recipeCount();
	public RecipeDetail findByNo(int no);
	

}
 