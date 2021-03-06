package com.coke.ice;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.coke.ice.domain.IceBoard;
import com.coke.ice.service.BoardService;


@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	

	@GetMapping("/board/list")
	public String boardlist (Model model, HttpServletRequest request) {
		
		System.out.println("flag!");
		
		String pageparam = request.getParameter("page");
		int page = 0 ;
		if(pageparam != null ) {
			page = (Integer.parseInt(pageparam)-1)*10;
		}
		List<IceBoard> boardori = boardService.boardlist();
		List<IceBoard> board = boardService.boardpage(page);
		
		System.out.println(page);
		System.out.println(board);
		
		int boardcnt = 0;
		int boardsize = boardori.size();
		
		if(boardsize % 10 == 0) {
			boardcnt = boardsize /10;
		}else {
			boardcnt = boardsize /10 +1;
		}
		
		
		model.addAttribute("boardlist", board);
		model.addAttribute("boardcnt", boardcnt);
		return "/board/list";
	}
	@GetMapping("/board/write")
	public String boardwrite (Model model) {
		
		return "/board/write";
	}
	
	
	@PostMapping("board/write")
	public String boardwrite (Model model, HttpServletRequest request) {

		
		boolean r =boardService.boardwrite(request);
		
		if(r == true) {
			System.err.println("성공");
			
			return "redirect:/board/list";
			
		}else {
			System.err.println("실패");
			return "redirect:/board/write";
		}
		
	}
	@GetMapping("/read/{boardnum}")
	public String boardread(Model model , @PathVariable("boardnum") int boardnum) {
	
		model.addAttribute("boardread",boardService.boardread(boardnum));
		model.addAttribute("comments", boardService.commentlist(boardnum));
		model.addAttribute("commentcnt", boardService.commentcnt(boardnum));
		
		return "/board/read";
	}
	
	@GetMapping("/update/{boardnum}")
	public String boardupdate (Model model ,@PathVariable("boardnum") int boardnum){
		model.addAttribute("board",boardService.boardread(boardnum));
		
		return "/board/update";
	}
	
	@PostMapping("/update/{boardnum}")
	public String boardupdate (Model model , HttpServletRequest request , @PathVariable("boardnum") int boardnum){
		System.out.println("보트 업데이트 컨트롤라ㅓ ::"   + boardnum);
		
		boolean result = boardService.boardupdate(request ,boardnum);
		
		if (result == true) {
			System.err.println("성공");

			return "redirect:/board/list";
		}else {
			System.err.println("실패");
			return "redirect:/board/list";
		
		}
	}

	@PostMapping("board/delete")
	public String boarddelete (HttpServletRequest request) {
		int boardnum = Integer.parseInt(request.getParameter("boardnum"));
		
		boardService.boarddelete(boardnum);
		
		return "";
	}
}
