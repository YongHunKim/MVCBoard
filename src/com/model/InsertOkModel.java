package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.BoardDTO;

public class InsertOkModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 데이터(insert.jsp) 받기
		request.setCharacterEncoding("EUC-KR");
		
		String type = (request.getParameter("type")==null)?"1":request.getParameter("type");
		String boardname="";
		switch (type) {
		case "1":
			boardname="board";
			break;
		case "2":
			boardname="normalboard";
			break;
		case "3":
			boardname="databoard";
			break;
		}
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		
		//DAO => 전송 (오라클에 데이터 저장)
		//모아서 전송(DTO)
		BoardDTO dto = new BoardDTO();
		dto.setName(name);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setPwd(pwd);
		
		BoardDAO dao = new BoardDAO();
		dao.boardInsert(dto,boardname,Integer.parseInt(type));
		
		return "list.do?type="+type;
		
	}

}
