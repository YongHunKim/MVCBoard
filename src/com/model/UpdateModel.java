package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.BoardDTO;

public class UpdateModel implements Model {

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
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.boardContentData(Integer.parseInt(no),boardname,1);
		
		request.setAttribute("dto",dto );
		request.setAttribute("page", page);
		request.setAttribute("no", no);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		request.setAttribute("type", type);
		
		return "board/update.jsp";
	}

}
