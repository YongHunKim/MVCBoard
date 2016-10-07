package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class InsertModel implements Model {
	
	@Override
	public String handlerRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("EUC-KR");
		String type = request.getParameter("type");
		request.setAttribute("type", type);
		return "board/insert.jsp";
	}

}
