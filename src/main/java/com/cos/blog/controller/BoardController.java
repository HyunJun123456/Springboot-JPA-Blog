package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"", "/"})
	public String index(Model model, @PageableDefault(size=3, sort="id", direction = Sort.Direction.DESC) Pageable pageable) { // 컨트롤러에서 세션을 어떻게 찾는지?
		// /WEB-INF/views/index.jsp
		//System.out.println("로그인 사용자 아이디: "+principal.getUsername());
		model.addAttribute("boards", boardService.list(pageable)); // jsp에 request 정보라고 생각하면 됨
		return "index"; // viewResolver 작동!! 
		// 모델의 정보를 가지고 이동함
	}
	
	@GetMapping("/board/{id}")
	public String findById(@PathVariable int id, Model model) {
		model.addAttribute("board", boardService.detail(id));
		return "board/detail";
	}
	
	@GetMapping("/board/{id}/updateForm")
	public String updateForm(@PathVariable int id, Model model) {// PathVariable로 id값을 받기
		//Model: 해당 데이터를 가지고 view로 이동
		model.addAttribute("board", boardService.detail(id));
		return "board/updateForm";
	}
	
	// USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
	
	
	
}
