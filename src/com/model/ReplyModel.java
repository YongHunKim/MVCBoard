package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ReplyModel implements Model{

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
		String strNo=request.getParameter("no");
		String strPAge = request.getParameter("page");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		
		request.setAttribute("type", type);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		request.setAttribute("no", strNo);
		request.setAttribute("page", strPAge);
		
		return "board/reply.jsp";
	}

}
