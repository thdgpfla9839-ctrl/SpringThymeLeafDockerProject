package com.sist.web.service;

import org.springframework.stereotype.Service;
import java.util.*;
import com.sist.web.mapper.*;
import com.sist.web.vo.*;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class DataBoardServiceImpl implements DataBoardService{
   private final DataBoardMapper mapper;

   @Override
   public List<DataBoardVO> databoardListData(int start) {
	// TODO Auto-generated method stub
	return mapper.databoardListData(start);
   }

   @Override
   public int databoardTotalPage() {
	// TODO Auto-generated method stub
	return mapper.databoardTotalPage();
   }
   
   @Override
   public void databoardInsert(DataBoardVO vo) {
	// TODO Auto-generated method stub
	mapper.databoardInsert(vo);
   }
   
   
}