package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.BoardDTO;

public class ReplyOKModel implements Model{

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
		String no = request.getParameter("no");
		String page = request.getParameter("page");
		String name = request.getParameter("name");
		String subject = request.getParameter("subject");
		String content = request.getParameter("content");
		String pwd = request.getParameter("pwd");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		
		request.setAttribute("page", page);
		request.setAttribute("type", type);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		
		//DAO Àü¼Û
		BoardDTO dto = new BoardDTO();
		dto.setName(name);
		dto.setSubject(subject);
		dto.setContent(content);
		dto.setPwd(pwd);
		
		BoardDAO dao = new BoardDAO();		
		dao.boardInsertReply(Integer.parseInt(no), dto,boardname,Integer.parseInt(type));
		
		return "list.do?page="+page;
	}
}
