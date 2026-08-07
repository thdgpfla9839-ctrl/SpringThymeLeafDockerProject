package com.sist.web.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;
import com.sist.web.entity.RecipeDetail;
import com.sist.web.repository.ChefRepository;
import com.sist.web.repository.RecipeDetailRepository;
import com.sist.web.repository.RecipeRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository rDao;
	private final ChefRepository cDao;
	private final RecipeDetailRepository rdDao;

	@Override
	public List<Recipe> findByTitleContains(String title, int page) {
		// TODO Auto-generated method stub
		final int ROWSIZE = 12;
		Pageable pg = PageRequest.of(page-1, ROWSIZE,Sort.by(Sort.Direction.ASC,"no"));
		
     /*
	 *  SELECT *
	 *  FROM recipe
	 *  WHERE title LIKE '%데이터%'
	 *  ORDER BY no ASC
	 *  OFFSET page-1 ROWS FETCH NEXT ROWSIZE ROWS ONLY
	 * 
	 */
		Page<Recipe> pList = rDao.findByTitleContains(title,pg);
		List<Recipe> list = new ArrayList<Recipe>();
		if(pList!=null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
	}

	@Override
	public List<Recipe> findByChefContains(String chef, int page) {
		// TODO Auto-generated method stub
		final int ROWSIZE = 12;
		Pageable pg = PageRequest.of(page-1, ROWSIZE,Sort.by(Sort.Direction.ASC,"no"));
		
     /*
	 *  SELECT *
	 *  FROM recipe
	 *  WHERE title LIKE '%데이터%'
	 *  ORDER BY no ASC
	 *  OFFSET page-1 ROWS FETCH NEXT ROWSIZE ROWS ONLY
	 * 
	 */
		Page<Recipe> pList = rDao.findByChefContains(chef,pg);
		List<Recipe> list = new ArrayList<Recipe>();
		if(pList!=null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
	}

	@Override
	public List<Recipe> recipeListData(int page) {
		/*
		 * // Pageable => 페이지 요청 정보 // 페이지 번호/ 페이지 크기, 정렬 조건 Pageable pg =
		 * PageRequest.of(page-1,12,Sort.by(Sort.Direction.ASC,"no")); Page<Recipe>
		 * pList=rDao.findAll(pg); List<Recipe> list = new ArrayList<Recipe>();
		 * 
		 * // 페이지를 리스트로 변환 if(pList!=null && pList.hasContent()) { list =
		 * pList.getContent(); }
		 */
		
		int start=(page*12)-12;
		List<Recipe> list = rDao.recipeListData(page);
		return list;
	}
    
	
	@Override
	public int[] getPageData(int page, int rowsize) {
		// TODO Auto-generated method stub
		
		int totalpage = (int)(Math.ceil(rDao.count()/(double)rowsize));
		int startPage = ((page-1)/10*10)+1;
		int endPage = ((page-1)/10*10)+10;
		
		if(endPage>totalpage)
			endPage=totalpage;
		int[] pages = {page,totalpage,startPage,endPage};
		
		return pages;
	}

	@Override
	public List<Chef> chefListData(int page) {
		Pageable pg = PageRequest.of(page-1,20); // 20개씩 데이터 자르기
		Page<Chef> pList=cDao.findAll(pg);
		List<Chef> list = new ArrayList<Chef>();
		
		// 페이지를 리스트로 변환
		if(pList!=null && pList.hasContent())
		{
			list = pList.getContent();
		}
		return list;
		
	}

	// getPageDataFind : 검색 개수가 전체 몇건인지를 세서 총 페이지 수를 계산하는 것
	@Override
	public int[] getPageDataFind(int mode, int page, int rowsize, String fd) {
		// TODO Auto-generated method stub
		int count = 0;
		if(mode==1)
		{
			count = (int)rDao.countByTitleContains(fd);
		}
		else
		{
			count = (int)rDao.countByChefContains(fd);
		}
		
		int totalpage = (int)(Math.ceil(count/12.0));
		int startPage = ((page-1)/10*10)+1;
		int endPage = ((page-1)/10*10)+10;
		
		if(endPage>totalpage)
			endPage=totalpage;
		int[] pages = {page,totalpage,startPage,endPage};
		
		return pages;
	}

	@Override
	public int recipeCount() {
		// TODO Auto-generated method stub
		return rDao.recipeCount();
	}

	@Override
	public RecipeDetail findByNo(int no) {
		// TODO Auto-generated method stub
		return rdDao.findByNo(no);
	}
}
