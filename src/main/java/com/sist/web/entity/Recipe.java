package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Recipe {

	@Id
	private int no;
	private String title,poster,chef,link; // 링크는 만개의 레시피에서 해당 쉐프를 클릭하면 그사람이 작성한 레시피들로 이동하기 위한 링크
	private int hit;
	
	
}
