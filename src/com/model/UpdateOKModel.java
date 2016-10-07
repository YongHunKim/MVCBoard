package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.BoardDTO;

public class UpdateOKModel implements Model {

	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
		}		
		String page = request.getParameter("page");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		String no = request.getParameter("no");
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
		
		System.out.println(ss);
		BoardDAO dao = new BoardDAO();
		int result = dao.boardUpdateData(Integer.parseInt(no), dto, boardname);
		
		request.setAttribute("type", type);
		request.setAttribute("result", result);
		request.setAttribute("page", page);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		request.setAttribute("no", no);
		
		return "board/update_ok.jsp";
	}

}
