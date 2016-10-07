package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.ReplyDTO;

public class CommentModel implements Model{

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
		
		String strNo = request.getParameter("no");
		String strPage = request.getParameter("page");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		String content = request.getParameter("content");
		
		//댓글입력
		BoardDAO dao = new BoardDAO();
		int result = dao.insertReply(Integer.parseInt(strNo),boardname,content);
		
		
		//댓글 리스트 가져오기
		List<ReplyDTO> repList = dao.repListData(Integer.parseInt(strNo), boardname);
		
		request.setAttribute("comList", repList);
		request.setAttribute("no", strNo);
		request.setAttribute("page", strPage);
		request.setAttribute("fs", fs);
		request.setAttribute("ss", ss);
		request.setAttribute("type", type);
		
		
		return "content.do?type="+type+"&no="+strNo+"&page="+strPage+"&fs="+fs+"&ss="+ss;
	}

}
