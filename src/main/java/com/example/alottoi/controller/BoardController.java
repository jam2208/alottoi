package com.example.alottoi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import com.example.alottoi.model.Board;
import com.example.alottoi.model.User;
import com.example.alottoi.repository.BoardRepository;

@Controller
public class BoardController {
	@Autowired
	BoardRepository boardRepository;
	
	@Autowired
	HttpSession session;

	@GetMapping("/board/delete/{id}")
	public String boardDelete(@PathVariable("id") long id) {
		boardRepository.deleteById(id);
		return "redirect:/board/list";
	}

	@GetMapping("/board/update/{id}")
	public String boardUpdate(Model model, @PathVariable("id") Long id) {
		Optional<Board> data = boardRepository.findById(id);
		Board board = data.get();
		model.addAttribute("board", board);
		return "board/update";
	}
		
	@PostMapping("/board/update/{id}")
	public String boardUpdate(@ModelAttribute Board board, @PathVariable("id") Long id) {
		User user = (User) session.getAttribute("user_info");

		if (user != null) {
		Optional<Board> data = boardRepository.findById(id);
		Board dbBoard = data.get();
		
		dbBoard.setContent(board.getContent());
		dbBoard.setUserId(user.getId());
		Date currentTime = new Date();
		dbBoard.setDtWrtMdfy(currentTime);
		
		boardRepository.save(dbBoard);
		
		return "redirect:/board/" + id;
		} else {
			return "/guide2";
		}
	}

	@GetMapping("/board/{id}")
	public String boardView(Model model, @PathVariable("id") Long id) {
		Optional<Board> data = boardRepository.findById(id);
		Board board = data.get();
		model.addAttribute("board", board);
		return "board/view";
	}
	
	@GetMapping("/board/list")
	public String boardList(Model model) {

		Sort sort = Sort.by(Order.asc("id"));
		List<Board> list = boardRepository.findAll(sort);
		
		model.addAttribute("list", list);
		return "board/list";
	}

	@GetMapping("/board/write")
	public String boardWrite() {
		return "board/write";
	}
	
	@PostMapping("/board/write")
	public String boardWrite(@ModelAttribute Board board) {
		User user = (User) session.getAttribute("user_info");
		
		if(user != null) {
			board.setUserId(user.getId());
			board.setWriter(user.getEmail());
			Date currentTime = new Date();
			board.setDtWrite(currentTime);

			boardRepository.save(board);

			return "redirect:list";
		} else {
			
			return "guide2";
		}
		

	}
}