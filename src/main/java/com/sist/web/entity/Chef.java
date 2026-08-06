package com.sist.web.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity // 여기는 테이블명이 필요없다 => 클래스명과 테이블명이 동일하기 때문
@Data
public class Chef {

	@Id 
	private String chef;
	private String poster;
	private String mem_cont1,mem_cont3,mem_cont7,mem_cont2;
}
