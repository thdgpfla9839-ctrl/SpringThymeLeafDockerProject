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
	public int[] getPageData(int page) {
		// TODO Auto-generated method stub
		
		int totalpage = (int)(Math.ceil(rDao.count()/12.0));
		int startPage = ((page-1)/10*10)+1;
		int endPage = ((page-1)/10*10)+10;
		
		if(endPage>totalpage)
			endPage=totalpage;
		int[] pages = {page,totalpage,startPage,endPage};
		
		return pages;
	}
}
