package com.cos.blog.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
// 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해줌. IoC를 해준다. 메모리를 대신 띄워준다.
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;
import com.cos.blog.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor // final을 쓰면 초기화를 해줘야하는데 이 어노테이션을 사용하면 초기화를 해줌
public class BoardService {
 
	private final BoardRepository boardRepository; 
	private final ReplyRepository replyRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public void write(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> list(Pageable pageable){
		return boardRepository.findAll(pageable); // pageable을 넣으면 return 타입이 Page가 됨
	}

	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 상세보기 실패: 아이디를 찾을 수 없습니다.");
				});
	}
	
	@Transactional
	public void delete(int id) {
		System.out.println("글 삭제하기: "+id);
		boardRepository.deleteById(id);
	}

	@Transactional
	public void update(int id, Board requestBoard) {
		// 영속화를 먼저 시켜야함
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글 찾기 실패: 아이디를 찾을 수 없습니다.");
				}); // 영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수로 종료시(Service가 종료될 때) 트랜잭션이 종료됩니다. 이때 더티체킹 - 자동 업데이트가 됨. db flush
	}
	
	@Transactional
	public void commentWrite(ReplySaveRequestDto replySaveRequestDto) {
		/*
		 * User user =
		 * userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 유저 id를 찾을 수 없습니다."); }); //
		 * 영속화 완료 Board board =
		 * boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
		 * return new IllegalArgumentException("댓글 쓰기 실패: 게시글 id를 찾을 수 없습니다."); }); //
		 * 영속화 완료
		 */		
		/*
		 * Reply reply = Reply.builder() .user(user) .board(board)
		 * .content(replySaveRequestDto.getContent()) .build();
		 */
		int result =replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService: "+result); 
		// 오브젝트를 출력하게되면 자동으로 toString()이 호출됨..
	}

	@Transactional
	public void commentDelete(int replyId) {
		replyRepository.deleteById(replyId);
	}
	
}
