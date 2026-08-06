package com.sist.web.service;

import java.util.*;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sist.web.entity.*;
import com.sist.web.repository.*;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService{

	private final RecipeRepository rDao;
	private final ChefRepository cDao;

	@Override
	public List<Recipe> findByTitleContains(String title) {
		// TODO Auto-generated method stub
		return rDao.findByTitleContains(title);
	}

	@Override
	public List<Recipe> findByChefContains(String chef) {
		// TODO Auto-generated method stub
		return rDao.findByChefContains(chef);
	}

	@Override
	public List<Recipe> recipeListData(int page) {
		// Pageable => 페이지 요청 정보
		// 페이지 번호/ 페이지 크기, 정렬 조건
		Pageable pg = PageRequest.of(page-1,12,Sort.by(Sort.Direction.ASC,"no"));
		Page<Recipe> pList=rDao.findAll(pg);
		List<Recipe> list = new ArrayList<Recipe>();
		
		// 페이지를 리스트로 변환
		if(pList!=null && pList.hasContent())
		{
			list = pList.getContent();
		}
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
}
