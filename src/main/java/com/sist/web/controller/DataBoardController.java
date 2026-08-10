package com.sist.web.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
		// 리얼패스를 가져와야 우분투나 aws에서 실행이 된다 안그러면 .yml 속 file의 업로드 경로를 우분투나 aws 실행시 계속 변경해야하는 번거로움이 있다
		
		// [경로에 따른 디렉토리 생성]
		// new File("upload") => mkdir
		// new File("/upload/image") => mkdirs
		String uploadDir=request.getServletContext().getRealPath("/upload");
		System.out.println(uploadDir);
		File dir = new File(uploadDir);
		if(!dir.exists())
		{
			dir.mkdir();
		}
		List<MultipartFile> files = vo.getFiles();
		String filename=""; // 파일이 여러개면 ,로 구분하려고 a.jpg,b.jpg ...
		String filesize="";
		boolean bCheck = false; // 파일 존재여부 구분
		for(MultipartFile file:files)
		{
			if(file.isEmpty())
			{
				bCheck = false;
			}
			else
			{
				String oname = file.getOriginalFilename();
				File f = new File(uploadDir,oname);
				if(f.exists())
				{
					// aaa.jpg => name="aaa"  ext =".jpg"
					String name = oname.substring(0,oname.lastIndexOf("."));
					String ext = oname.substring(oname.lastIndexOf("."));
					int count =1;
					while(f.exists())
					{
						String newName = name+"("+count+")"+ext;
						f = new File(uploadDir+"/"+newName);
						count++;
					}
				}
				// 업로드
				// Paths.get() => 운영체제에 따라 /인지 \인지 나눠진다 => 자동으로 변경해준다
				Path path = Paths.get(uploadDir,f.getName());
				Files.copy(file.getInputStream(), path);
				// 파일에 저장한 데이터 가져오기
				filename+=f.getName()+",";
				filesize+=f.length()+",";
				bCheck = true;
				
			}
		}
		// 디비 처리
		if(bCheck==true)
		{
			filename = filename.substring(0,filename.lastIndexOf(","));
			filesize = filesize.substring(0,filesize.lastIndexOf(","));
			vo.setFilename(filename);
			vo.setFilesize(filesize);
			vo.setFilecount(files.size());
		}
		else // 업로드 아닌 상태
		{
			vo.setFilename("");
			vo.setFilesize("");
			vo.setFilecount(0);
		}
		dService.databoardInsert(vo);
		return "redirect:/databoard/list";
	}
}
