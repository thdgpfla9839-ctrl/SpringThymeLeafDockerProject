package com.sist.web.restcontroller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import java.util.*;
import com.sist.web.entity.*;
import com.sist.web.service.*;

@RestController // 화면 변경을 못해, 데이터 전송역할만 갖고 있어 => 그래서 라우터 기능이 있는 레시피 컨트롤러로 가서 작업할 거야
@RequiredArgsConstructor
public class RecipeRestController {

	private final RecipeService rService;
	
	// 검색을 하다 문제발생하는 경우 -> 맨처음에 한번은 화면출력할 때 겟방식 근데 버튼을 누르면 포스트로 보내줘야하는 거지 => 똑같은 코딩이 두번이 돼
	@RequestMapping("/recipe/find_vue") // 겟과 포스트를 동시에 처리할 때 사용한다
	// ResponseEntity : 반드시 비동기로 작성한다, 데이터와 에러를 한번에 툴력?
	 public ResponseEntity<Map> recipe_find(
		      @RequestParam("page") int page,
		      @RequestParam("fd") String fd
		   )
		   {   Map map=new HashMap();
		       try
		       {
		    	   List<Recipe> list=
		    			   rService.findByTitleContains(fd, page);
		    	   int[] pages=rService.getPageDataFind(1, page, 12, fd);
		    	   
		    	   map.put("list", list);
		    	   map.put("pages", pages);
		       }catch(Exception ex)
		       {
		    	   return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		       }
			   return ResponseEntity.ok(map);
		   }
	
	
	@RequestMapping("/recipe/recipe_chef_vue") // 겟과 포스트를 동시에 처리할 때 사용한다
	// ResponseEntity : 반드시 비동기로 작성한다, 데이터와 에러를 한번에 툴력?
	public ResponseEntity<Map> recipe_chef(
			@RequestParam("page") int page,
			@RequestParam("chef") String chef
			)
	{   Map map=new HashMap();
	try
	{
		List<Recipe> list=
				rService.findByChefContains(chef, page);
		int[] pages=rService.getPageDataFind(2, page, 12, chef);
		
		map.put("list", list);
		map.put("pages", pages);
	}catch(Exception ex)
	{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	return ResponseEntity.ok(map);
	}
	
	
		}