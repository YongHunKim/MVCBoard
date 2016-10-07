package com.model;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dao.BoardDAO;
import com.dao.BoardDTO;
import com.dao.ReplyDTO;

public class ContentModel implements Model {

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
		int no = Integer.parseInt(strNo);
		String strPage = request.getParameter("page");
		String fs = request.getParameter("fs");
		String ss = request.getParameter("ss");
		
		BoardDAO dao = new BoardDAO();
		BoardDTO dto = dao.boardContentData(Integer.parseInt(strNo),boardname,0);
		//content에 댓글이 있는지 판단
		if(dao.repCount(no, boardname)>0){
			//글에 쓰여진 댓글이 있으면 담기		
			List<ReplyDTO> repList = new ArrayList();
			repList = dao.repListData(no, boardname);
			request.setAttribute("repList", repList);
		}
		
		request.setAttribute("dto", dto);
		request.setAttribute("page", strPage);
		request.setAttribute("no", strNo);
		request.setAttribute("fs", fs);
		request.setAttribute("ss",ss);
		request.setAttribute("type", type);
		return "board/content.jsp?type="+type+"&no="+strNo+"&page="+strPage+"&fs="+fs+"&ss="+ss;
		
	}

}
