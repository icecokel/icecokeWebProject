package com.coke.ice.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.coke.ice.dao.BoardDAO;
import com.coke.ice.domain.IceBoard;
import com.coke.ice.domain.IceComment;
import com.coke.ice.domain.IceUser;

@Service
public class BoardServiceImpl implements BoardService {

	@Autowired
	BoardDAO boardDao;

	@Override
	public boolean boardwrite(HttpServletRequest request) {
		boolean result = false;
		HttpSession session = request.getSession();
		String boardtitle = request.getParameter("boardtitle");
		String boardcontent = request.getParameter("boardcontent");


		System.err.println("서비스 테스트2 :::::::::" + boardcontent);

		IceUser user = (IceUser) session.getAttribute("user");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		Date date = new Date();
		String titletime = sdf.format(date);
		String email = user.getEmail();



		if (boardtitle.length() <= 0) {
			boardtitle = user.getNickname() + "님이 " + titletime + "에 남기신 글입니다.";

		}
		int boardnum = 1;
		Integer maxnum = boardDao.maxnum();



		if (maxnum != null) {
			boardnum = maxnum + 1;
		}
		IceBoard board = new IceBoard();
		board.setBoardtitle(boardtitle);
		board.setBoardcontent(boardcontent);
		board.setBoardnum(boardnum);
		board.setEmail(email);



		int r = boardDao.boardwrite(board);

		if (r >= 1) {
			result = true;
		} else {
			result = false;
		}

		return result;
	}

	@Override
	public List<IceBoard> boardlist() {

		System.out.println("flag!@");
		
		
		List<IceBoard> board = boardDao.boardlist();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar cal = Calendar.getInstance();


		String today = sdf.format(cal.getTime());

		for (IceBoard tmp : board) {

			if (today.equals((String) sdf.format(tmp.getUpdatedate()))) {
				tmp.setDispdate("TODAY");
			} else {
				tmp.setDispdate(sdf.format(tmp.getUpdatedate()));

			}
		}

		return board;
	}

	@Override
	public IceBoard boardread(int boardnum) {

		boardDao.readcnt(boardnum);
		return boardDao.boardread(boardnum);
	}

	@Override
	public boolean boardupdate(HttpServletRequest request, int boardnum) {
		String boardtitle = request.getParameter("boardtitle");
		String boardcontent = request.getParameter("boardcontent");

		IceBoard board = new IceBoard();
		board.setBoardnum(boardnum);
		board.setBoardtitle(boardtitle);
		board.setBoardcontent(boardcontent);

		boolean result = false;
		int r = boardDao.boardupdate(board);
		if (r > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	@Override
	public void boarddelete(int boardnum) {

		boardDao.boarddelete(boardnum);
	}

	@Override
	public List<IceBoard> boardpage(int page) {
		List<IceBoard> board = boardDao.boardpage(page);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();

		String today = sdf.format(cal.getTime());

		for (IceBoard tmp : board) {
			if (today.equals((String) sdf.format(tmp.getUpdatedate()))) {
				tmp.setDispdate("TODAY");
			} else {
				tmp.setDispdate(sdf.format(tmp.getUpdatedate()));

			}

		}

		return board;
	}

	@Override
	public List<IceComment> commentlist(int boardnum) {
		List<IceComment> clist = boardDao.commentlist(boardnum);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
		for (IceComment tmp : clist) {
			tmp.setDispdate(sdf.format(tmp.getCommenttime()));

		}

		return clist;
	}

	@Override
	public int commentcnt(int boardnum) {
		int commentcnt = boardDao.commentcnt(boardnum);
		return commentcnt;
	}

	@Override
	public boolean commentwrite(HttpServletRequest request, int boardnum) {
		boolean result = false;
		IceComment icecomment = new IceComment();

		IceUser user = (IceUser) request.getSession().getAttribute("user");
		String commentcontent = request.getParameter("commentcontent");
		int commentlevel = 1;
		String targetemail = null;
		icecomment.setEmail(user.getEmail());
		icecomment.setCommentcontent(commentcontent);
		icecomment.setCommentlevel(commentlevel);
		icecomment.setBoardnum(boardnum);
		icecomment.setTargetemail(targetemail);

		int r = boardDao.commentwrite(icecomment);

		if (r >= 1) {
			result = true;
		}

		return result;
	}

	@Override
	public boolean commentdel(int commentnum) {
		boolean result = false;

		int r = boardDao.commentdel(commentnum);
		if (r >= 1) {
			result = true;

		}

		return result;
	}

	@Override
	public boolean commentusercheck(HttpServletRequest request) {
		boolean result = false;
		int commentnum = Integer.parseInt(request.getParameter("comnum"));
		
		IceComment r = boardDao.commentdelcheck(commentnum);
		String email ="";
		IceUser user = (IceUser) request.getSession().getAttribute("user");
		if (user != null) {
			email = user.getEmail();
			if (r.getEmail().equals(email)) {
				result = true;
			}
		}

		return result;
	}

	@Override
	public boolean commentupdate(HttpServletRequest request) {
		boolean result = false;
		
		IceComment icecomment = new IceComment();
		
		String commentcontent = request.getParameter("comment");
		int commentnum = Integer.parseInt(request.getParameter("comnum"));
		icecomment.setCommentnum(commentnum);
		icecomment.setCommentcontent(commentcontent);
		int r =boardDao.commentupdate(icecomment);
		if(r >=1) {
			result= true;
		}
		
		
		return result;
	}

}
