package com.sist.web.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.springframework.stereotype.Repository;

import java.util.*;
import com.sist.web.vo.*;
@Mapper
@Repository
public interface DataBoardMapper {

	@Select("SELECT no,name,subject,"
			+"TO_CHAR(regdate,'yyyy-MM-dd')as dbday,"
			+"hit,filecount "
			+"FROM SPRINGDATABOARD "
			+"ORDER BY no DESC "
			+"OFFSET #{start} ROWS FETCH NEXT 10 ROWS ONLY")
	public List<DataBoardVO> databoardListData(int start);
	
	// 괄호 잘닫아주기 => ceil 다음에 괄호를 안 줘서 디비가 인식을 못했어
	@Select("SELECT CEIL (COUNT(*)/10.0) FROM SPRINGDATABOARD")
	public int databoardTotalPage();
	
	// 디비에서 시퀀스 안 줘서 여기서 만들어줌
	@SelectKey(keyProperty = "no", resultType = int.class, before = true, statement = "SELECT NVL(MAX(no)+1,1) as no FROM SPRINGDATABOARD")
	@Insert("INSERT INTO SPRINGDATABOARD VALUES("
			+"#{no},#{name},#{subject},#{content},#{pwd},SYSDATE,0"
			+"#{filename},#{filesize},#{filecount})")
	public void databoardInsert(DataBoardVO vo);
	
	// 상세보기, 수정, 삭제
}
