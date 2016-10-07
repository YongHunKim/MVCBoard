package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;

public class DeleteOKModel implements Model {

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
		String pwd = request.getParameter("pwd");
		String no = request.getParameter("no");
		String page = request.getParameter("page");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		BoardDAO dao = new BoardDAO();
		boolean bCheck = dao.BoardDelete(Integer.parseInt(no), pwd,boardname);
		
		request.setAttribute("type", type);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		request.setAttribute("bCheck", bCheck);
		request.setAttribute("page", page);
		
		return "board/delete_ok.jsp";
	}
}
