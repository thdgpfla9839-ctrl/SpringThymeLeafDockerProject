package com.sist.web.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.entity.Chef;
import com.sist.web.entity.Recipe;
import com.sist.web.entity.RecipeDetail;
import com.sist.web.service.RecipeService;

import lombok.RequiredArgsConstructor;

@Controller // 서비스에서 받은 값을 HTML로 전송만 하는 역할
@RequiredArgsConstructor // 이 안에는 생성자 + @Autowired가 포함돼 있음
                         // 반드시 라이브러리에 롬복이 있어야 사용이 가능하다
public class RecipeController {

	private final RecipeService rService; // 알서비스에 자동으로 주소값을 넣어줄거야
	
	@GetMapping("/main/main")
	public String main_main(@RequestParam(value = "page",required = false) String page, Model model)
	{
		if(page==null)
			page="1";
		List<Recipe> list = rService.recipeListData(Integer.parseInt(page));
		int[] pages = rService.getPageData(Integer.parseInt(page),12); // 페이지 12개로 나눠라
		int count = rService.recipeCount();
		model.addAttribute("pages",pages);
		model.addAttribute("list",list);
		model.addAttribute("count",count);
		
		// <th:block th:include="${main_html}"></th:block>
		model.addAttribute("main_html","main/home"); // 우리 .yml 파일을 보면 우리 파일 읽는 경로가 templates/라서 
		                                             // 항상 앞에 탬플릿이 붙어서 경로를 찾을거야 
		                                            // 그렇기 때문에 home뒤에 .html을 붙이면 이중으로 붙어서 에러가 날거야
		return "main/main";
	}
	
	
	// 쉐프 목록 출력
	@GetMapping("/recipe/chef_list")
	public String recipe_chef(@RequestParam(value = "page",required = false) String page, Model model)
	{
		if(page==null)
			page="1";
		List<Chef> list = rService.chefListData(Integer.parseInt(page));
		int[] pages = rService.getPageData(Integer.parseInt(page),20); 
		
		model.addAttribute("pages",pages);
		model.addAttribute("list",list);
		
		
		// <th:block th:include="${main_html}"></th:block>
		model.addAttribute("main_html","recipe/chef"); 
		return "main/main";
	}
	
	// 화면이동만 함
	 @GetMapping("/recipe/find")
	   public String recipe_find(Model model)
	   {
		   model.addAttribute("main_html", "recipe/find");
		   return "main/main";
	   }
	 
	 // 쉐프 클릭하면 해당쉐프의 레시피로 
	   @GetMapping("/recipe/chef_recipe")
	   public String chef_recipe(@RequestParam("chef")String chef, Model model)
	   {
		   model.addAttribute("chef",chef);
		   model.addAttribute("main_html", "recipe/chef_recipe");
		   return "main/main";
	   }
	   
	   // 상세보기
	   @GetMapping("/recipe/detail")
	   public String recipe_detail(@RequestParam("no") int no, Model model)
	   {
		   RecipeDetail vo = rService.findByNo(no);
		   model.addAttribute("vo",vo);
		   List<String> mList = new ArrayList<String>();
		   List<String> iList = new ArrayList<String>();
		   String [] makes = vo.getFoodmake().split("\n");
		   for(String s:makes)
		   {
			   StringTokenizer st = new StringTokenizer(s,"^");
			   mList.add(st.nextToken());
			   iList.add(st.nextToken());
		   }
		   
		   model.addAttribute("mList",mList);
		   model.addAttribute("iList",iList);
		   model.addAttribute("main_html", "recipe/detail");
		   return "main/main";
	   }
	}