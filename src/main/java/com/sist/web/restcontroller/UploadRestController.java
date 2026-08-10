package com.sist.web.restcontroller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadRestController {

	// yml에 값을 가져올 때
	@Value("${file.upload_dir}")
	private String uploadDir;
	// 같은 이름이 들어오면 괄호치고 번호를 줘서 구분 => 자동으로 처리 안 해줘서 직접 만들어줘야함
	private static int count=1; // 다중 업로드 시 같은 파일명인 경우
	
	// 여기는 넘어갈 때 포트가 multipart/form-data => 프로토콜 약속 => post 기반이라 postmapping으로 받아야함
	@PostMapping("/upload_ok")
	public String upload_ok(@RequestParam(value="file", required = false) MultipartFile file) // 업로드 파일 한개만 받아서 파일로 받음
	throws Exception
	{
		// 업로드 폴더가 없으면 먼저 만들어라
		File f = new File(uploadDir);
		if(!f.exists())
		{
			f.mkdir();
		}
		if(file.isEmpty())
		{
			return "파일이 존재하지 않습니다.";
		}
		// 사용자가 보낸 파일명
		String oname=file.getOriginalFilename();
		File files = new File(uploadDir+"/"+oname);
		
		String newName=oname; // 업로드용
		// 파일 중복 없이 처리
		if(files.exists())
		{
			// aaa.jpg => aaa(1).jpg 같은 파일명인 경우 괄호에 숫자로 구분하기 위해 
			String name = oname.substring(0,oname.lastIndexOf("."));
			String ext = oname.substring(oname.lastIndexOf("."));
			newName = name+"("+count+")"+ext;
			count++;
		}
		// upload 하기
		Path savePath = Paths.get(uploadDir,newName);
		Files.copy(file.getInputStream(), savePath);
		return "업로드 성공:"+oname+",변경:"+newName;
	}
	
	@PostMapping("/multi-upload")
	public String multi_upload(@RequestParam(value = "files", required = false) List<MultipartFile> files)
	throws Exception
	{
		for(MultipartFile file:files)
		{
			if(file.isEmpty())
			{
				return "파일이 존재하지 않습니다.";
			}
			else
			{
				String oname = file.getOriginalFilename();
				System.out.println(oname);
				File f = new File(uploadDir+"/"+oname);
				if(f.exists())
				{
					String name = oname.substring(0,oname.lastIndexOf("."));
					String ext = oname.substring(oname.lastIndexOf("."));
					int cnt =1;
					while(f.exists())
					{
						String newName = name+"("+cnt+")"+ext;
						System.out.println(newName);
						f = new File(uploadDir+"/"+newName);
						cnt++;
					}
				}
				Path savePath = Paths.get(uploadDir,f.getName());
				Files.copy(file.getInputStream(), savePath);
				
			}
			
			
		}
		return "다중 업로드 완료";
	}
	
}
