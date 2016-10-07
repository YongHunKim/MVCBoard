package com.dao;

import java.sql.*;
import java.util.*;

public class BoardDAO {
	private Connection conn;
	private PreparedStatement ps;
	private final String URL = "jdbc:oracle:thin:@211.238.142.76:1521:ORCL";
	
	//1. 드라이버 등록
	public BoardDAO() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	//2. 연결 객체 얻기
	public void getConnection() {
		try {
			conn = DriverManager.getConnection(URL, "scott", "tiger");
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	//3. 연결 해제
	public void disConnection() {
		try {
			if(ps != null) ps.close();
			if(conn != null) conn.close();
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
	//4. 기능
	//1) 목록 ==> SELECT
	public List<BoardDTO> boardListData(int page,String boardname) {
		List<BoardDTO> list = new ArrayList<>();
		try {
			getConnection();	
			String sql="";
			if(boardname=="board"){
				sql = "SELECT no,subject,name,regdate,hit,group_tab,TO_CHAR(regdate, 'YYYY-MM-DD') FROM "+boardname+" ORDER BY group_id DESC,group_step ASC";	
			}else{
				sql = "SELECT no,subject,name,regdate,hit,group_tab,TO_CHAR(regdate, 'YYYY-MM-DD'),repcount FROM "+boardname+" ORDER BY group_id DESC,group_step ASC";
			}
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			int rowSize = 10;
			int i = 0;
			int j = 0;
			int pagecnt = (page*rowSize)-rowSize;
			while(rs.next()) {
				if(i < rowSize && j >= pagecnt) {
					BoardDTO dto = new BoardDTO();
					dto.setNo(rs.getInt(1));
					dto.setSubject(rs.getString(2));
					dto.setName(rs.getString(3));
					dto.setDate(rs.getDate(4));
					dto.setHit(rs.getInt(5));
					dto.setGroup_tab(rs.getInt(6));
					dto.setDbday(rs.getString(7));
					if(boardname!="board"){
						dto.setReplycount(rs.getInt(8));
					}
					list.add(dto);
					i++;
				}
				j++;
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		
		return list;
		
	}
	
	//2) 내용보기 => SELECT ~ WHERE
	public BoardDTO boardContentData(int no,String boardname,int update) {
		BoardDTO dto = new BoardDTO();
		try {
			getConnection();
			String sql="";
			if(update==0){
				sql = "UPDATE "+boardname+" SET hit=hit+1 WHERE no=?";				
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				int result = ps.executeUpdate();
				ps.close();
			}
			//데이터 읽기
			sql = "SELECT no,name,subject,content,regdate,hit FROM "+boardname+" WHERE no=?";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			
			ResultSet rs = ps.executeQuery();
			rs.next();
			dto.setNo(rs.getInt(1));
			dto.setName(rs.getString(2));
			dto.setSubject(rs.getString(3));
			dto.setContent(rs.getString(4));
			dto.setDate(rs.getDate(5));
			dto.setHit(rs.getInt(6));
			rs.close();
					
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		
		return dto;
		
	}
	
	//3) 추가 ====> INSERT
	public void boardInsert(BoardDTO dto,String boardname,int type) {
		try {
			getConnection();
			String sql="";
			if(type==1){
				sql = "INSERT INTO "+boardname+"(no,name,subject,content,pwd,group_id) VALUES ((SELECT NVL(MAX(no)+1,1) FROM "+boardname+"),?,?,?,?,(SELECT NVL(MAX(group_id)+1,1) FROM "+boardname+"))";
			}else if(type==2){
				sql = "INSERT INTO "+boardname+"(brno,no,name,subject,content,pwd,group_id) VALUES (1,(SELECT NVL(MAX(no)+1,1) FROM "+boardname+"),?,?,?,?,(SELECT NVL(MAX(group_id)+1,1) FROM "+boardname+"))";
			}
			
			
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getPwd());
			//실행요청
			ps.executeUpdate();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			disConnection();
		}
		
	}
	
	//답글 입력
	public void boardInsertReply(int root,BoardDTO dto,String boardname,int type){
		try{
			/*
			 * Group_id : 연관된 글을 묶어주는 컬럼(답변그룹)
			 * Group_step : 글의 정렬 순서를 지정해 주는 컬럼 
			 * Group_tab : 몇번째 단계의 답변인지 나타내는 컬럼
			 */
			getConnection();
			conn.setAutoCommit(false);
			String sql = "SELECT group_id,group_step,group_tab "
					+ "FROM "+boardname+" "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ResultSet rs = ps.executeQuery();
			rs.next();
			
			int gi = rs.getInt("group_id");
			int gs = rs.getInt("group_step");
			int gt = rs.getInt("group_tab");
			rs.close();
			ps.close();
			
			sql = "UPDATE "+boardname+" SET "
					+ "group_step=group_step+1 "
					+ "where group_id=? AND group_step>?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, gi);
			ps.setInt(2, gs);
			ps.executeUpdate();
			ps.close();
			
			if(type==1){
				sql="INSERT INTO "+boardname+"(no,name,subject,content,pwd,group_id,group_step,group_tab,root) "
						+ "VALUES((SELECT MAX(no)+1 FROM "+boardname+"),?,?,?,?,?,?,?,?)";
			}else if(type==2){
				sql="INSERT INTO "+boardname+"(brno,no,name,subject,content,pwd,group_id,group_step,group_tab,root) "
						+ "VALUES(1,(SELECT MAX(no)+1 FROM "+boardname+"),?,?,?,?,?,?,?,?)";
			}
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getPwd());
			ps.setInt(5, gi);
			ps.setInt(6, gs+1);
			ps.setInt(7, gt+1);
			ps.setInt(8, root);
			ps.executeUpdate();
			ps.close();
			
			
			sql="UPDATE "+boardname+" SET "
					+ "depth=depth+1 "
					+ "WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, root);
			ps.executeUpdate();
			
			conn.commit();
		}catch(Exception ex){
			try{
				conn.rollback();
			}catch(Exception e){
				e.printStackTrace();
			}
		}finally{
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			disConnection();
		}
	}
	
	//삭제
	public boolean BoardDelete(int no,String pwd,String boardname){
		boolean bCheck=false;
		try {
			getConnection();
			String sql = "SELECT pwd FROM "+boardname+" WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			String db_pwd = rs.getString("pwd");
			rs.close();
			ps.close();
			if(db_pwd.equals(pwd)){
				bCheck=true;
				sql = "select case when root!=0 then root else no END as root, depth from "+boardname
						+ " where no=?";
				ps = conn.prepareStatement(sql);
				ps.setInt(1, no);
				rs = ps.executeQuery();
				rs.next();
				int root = rs.getInt("root");
				int depth = rs.getInt("depth");
				rs.close();
				ps.close();
				if(depth==0){
					//삭제
					sql = "DELETE FROM "+boardname+" WHERE no=?";
					ps = conn.prepareStatement(sql);
					ps.setInt(1, no);
					ps.executeUpdate();
					ps.close();
				}else{
					//update(삭제된거 처럼 보이게)
					sql="UPDATE "+boardname+" SET "
							+ "subject=?,content=? WHERE no=?";
					String msg = "관리자에 의해 삭제된 게시물입니다.";
					ps = conn.prepareStatement(sql);
					ps.setString(1, msg);
					ps.setString(2, msg);
					ps.setInt(3, no);
					ps.executeUpdate();
					ps.close();
				}
				
				//depth 감소
				sql = "UPDATE "+boardname+" SET "
						+ "depth=depth-1 WHERE no=? AND depth>0";
				ps=conn.prepareStatement(sql);
				ps.setInt(1, root);
				ps.executeUpdate();
				ps.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		
		return bCheck;
	}
	//4) 수정 ====> UPDATE
	//5) 답변 ====> INSERT
	
	
	//총페이지 구하기 ==> CEIL(COUNT(*)/10)
	public int boardTotalPage(String boardname) {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10) FROM "+boardname;
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		
		return total;
		
	}
	
	public int boardCount(String boardname) {
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT COUNT(*) FROM "+boardname;
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		
		return total;
		
	}
	
	public List<BoardDTO> boardFindListData(int curPage,String fs,String ss,String boardname){
		List<BoardDTO> list = new ArrayList<>();
		try {
			getConnection();
			int rowSize = 10;
			int start = (curPage*rowSize-rowSize)+1;
			int end = start+rowSize-1;
			String sql="";
			if(boardname=="board"){
				sql="SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD') as dbday FROM "
						+ "(SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD') as dbday,ROWNUM as num from "
						+ "(SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD') as dbday from "+boardname+" where "+fs+" like '%"+ss+"%' ORDER BY group_id desc, group_step))"
						+ "where num between "+start+" and "+end;
			}else{
				sql="SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD'),repcount as dbday FROM "
						+ "(SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD') as dbday,ROWNUM as num,repcount from "
						+ "(SELECT no,subject,name,regdate,hit,group_tab,group_id,group_step,TO_CHAR(regdate, 'YYYY-MM-DD') as dbday,repcount from "+boardname+" where "+fs+" like '%"+ss+"%' ORDER BY group_id desc, group_step))"
						+ "where num between "+start+" and "+end;
			}
			System.out.println(sql);
			ps= conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next()){
				BoardDTO dto = new BoardDTO();
				dto.setNo(rs.getInt(1));
				dto.setSubject(rs.getString(2));
				dto.setName(rs.getString(3));
				dto.setDate(rs.getDate(4));
				dto.setHit(rs.getInt(5));
				dto.setGroup_tab(rs.getInt(6));
				dto.setDbday(rs.getString(7));
				if(boardname!="board"){
					dto.setReplycount(rs.getInt(8));
				}
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	
	public int boardFindTotalPage(String fs,String ss,String boardname){
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT CEIL(COUNT(*)/10) FROM "+boardname+" where "+fs+" like '%"+ss+"%'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		
		return total;
	}
	
	public int boardFindCount(String fs,String ss,String boardname){
		int total = 0;
		try {
			getConnection();
			String sql = "SELECT COUNT(*) FROM "+boardname+" where "+fs+" like '%"+ss+"%'";
			ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			rs.next();
			total = rs.getInt(1);
			rs.close();
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			disConnection();
		}
		
		return total;
	}
	
	public int boardUpdateData(int no,BoardDTO dto,String boardname){
		int result=0;
		
		try {
			getConnection();
			String sql = "UPDATE "+boardname+" SET name=?, subject=?, content=?, pwd=? WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getName());
			ps.setString(2, dto.getSubject());
			ps.setString(3, dto.getContent());
			ps.setString(4, dto.getPwd());
			ps.setInt(5, no);
			result = ps.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return result;
	}
	
	public int insertReply(int no,String boardname,String content){
		int result=0;
		try{
			getConnection();
			String sql = "SELECT brno,no FROM "+boardname+" WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int brno = rs.getInt("brno");
			int contentno = rs.getInt("no");
			rs.close();
			ps.close();
			
			/*sql = "SELECT max(repno)+1 FROM board_reply WHERE brno="+brno+" AND no="+contentno;
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			rs.next();
			int repno = rs.getInt(1);			
			rs.close();
			ps.close();*/
			
			sql = "INSERT INTO board_reply(brno,no,repno,content) "
					+ "VALUES("+brno+","+no+",(SELECT NVL(MAX(repno),0)+1 FROM board_reply WHERE brno="+brno+" AND no="+contentno+"),'"+content+"')";
			ps = conn.prepareStatement(sql);
			result = ps.executeUpdate();
			ps.close();
			
			sql="UPDATE "+boardname+" SET repcount=repcount+1 WHERE brno=? AND no=?";
			ps = conn.prepareStatement(sql);
			System.out.println(sql);
			ps.setInt(1, brno);
			ps.setInt(2, contentno);
			ps.executeUpdate();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}finally {
			disConnection();
		}
		return result;
	}
	
	public List<ReplyDTO> repListData(int no,String boardname){
		List<ReplyDTO> list = new ArrayList<>();
		try {
			getConnection();
			String sql = "SELECT brno,no FROM "+boardname+" WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int brno = rs.getInt("brno");
			int contentno = rs.getInt("no");
			rs.close();
			ps.close();
			
			sql = "SELECT brno,no,repno,content FROM board_reply WHERE brno=? AND no=? ORDER BY repno";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, brno);
			ps.setInt(2, contentno);
			rs = ps.executeQuery();
			while(rs.next()){
				ReplyDTO dto = new ReplyDTO();
				dto.setBrno(rs.getInt("brno"));
				dto.setContentno(rs.getInt("no"));
				dto.setRepno(rs.getInt("repno"));
				dto.setContent(rs.getString("content"));
				list.add(dto);
			}
			rs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return list;
	}
	
	public int repCount(int no,String boardname){
		int result= 0;
		try{
			getConnection();
			String sql = "SELECT brno,no FROM "+boardname+" WHERE no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, no);
			ResultSet rs = ps.executeQuery();
			rs.next();
			int brno = rs.getInt("brno");
			int contentno = rs.getInt("no");
			rs.close();
			
			sql = "SELECT count(*) FROM board_reply WHERE brno=? AND no=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, brno);
			ps.setInt(2, contentno);
			rs = ps.executeQuery();
			rs.next();
			result = rs.getInt(1);
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			disConnection();
		}
		return result;
	}
	
	
}
