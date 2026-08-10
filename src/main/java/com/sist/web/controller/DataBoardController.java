package com.sist.web.controller;

import java.io.File;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.sist.web.service.DataBoardService;
import com.sist.web.vo.DataBoardVO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DataBoardController {

	private final DataBoardService dService;
	@GetMapping("/databoard/list")
	public String databoard_list(@RequestParam(value = "page", required = false) String page, Model model)
	{
		if(page==null)
			page = "1";
		int curpage = Integer.parseInt(page);
		int start = (curpage*10)-10;
		List<DataBoardVO> list = dService.databoardListData(start);
		int totalpage = dService.databoardTotalPage();
		
		model.addAttribute("list",list);
		model.addAttribute("curpage",curpage);
		model.addAttribute("totalpatge",totalpage);
		model.addAttribute("main_html", "databoard/list");
		return "main/main";
	}
	
	@GetMapping("/databoard/insert")
	public String databoard_insert(Model model)
	{
		model.addAttribute("main_html", "databoard/insert");
		return "main/main";
	}
	
	// 경로?
	
	@PostMapping("/databoard/insert_ok")
	public String databoard_insert_ok(@ModelAttribute("vo") DataBoardVO vo, HttpServletRequest request)
	throws Exception
	{
		// getServletContext() 반드시 request를 통해서 갖고온다
		String uploadDir=request.getServletContext().getRealPath("/upload");
		File dir = new File(uploadDir);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		return "redirect:/databoard/list";
	}
}
