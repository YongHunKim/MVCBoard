package com.model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.*;
import java.util.*;
import com.dao.*;

public class ListModel implements Model{
	
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
		case "3":
			boardname ="board_data";
			break;
		}
		
		String strPage = request.getParameter("page");
		if(strPage == null)	strPage = "1";	//list.jsp
		
		int curPage = Integer.parseInt(strPage);
		
		String fs = (request.getParameter("fs")==null)?"":request.getParameter("fs");
		String ss = (request.getParameter("ss")==null)?"":request.getParameter("ss");
		
		BoardDAO dao = new BoardDAO();
		List<BoardDTO> list;
		int totalPage;
		int count;
		
		if(fs.equals("") && ss.equals("")){
			list = dao.boardListData(curPage,boardname);
			totalPage = dao.boardTotalPage(boardname);
			count = dao.boardCount(boardname);
			count = count-((curPage*5)-5);
		}else{
			list = dao.boardFindListData(curPage,fs,ss,boardname);
			totalPage = dao.boardFindTotalPage(fs,ss,boardname);
			count = dao.boardFindCount(fs,ss,boardname);
			count = count-((curPage*5)-5);
		}
				
		String msg = "관리자에 의해 삭제된 게시물입니다.";
		
		String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		
		//[1][2][3][4][5][6].....
		// fp		  	  tf
		//블록나누기
		int block = 5;
		int fromPage = ((curPage-1)/block * block)+1;
		int toPage = ((curPage-1)/block * block) + block;
		
		if(toPage > totalPage)
			toPage = totalPage;
		
		request.setAttribute("list", list);
		request.setAttribute("curPage", curPage);
		request.setAttribute("totalPage", totalPage);
		request.setAttribute("count", count);
		request.setAttribute("msg", msg);
		request.setAttribute("today", today);
		request.setAttribute("block", block);
		request.setAttribute("fromPage", fromPage);
		request.setAttribute("toPage", toPage);
		request.setAttribute("fs", fs);
		request.setAttribute("ss", ss);
		request.setAttribute("type", type);
		
		return "board/list.jsp";
		
	}
	
}
