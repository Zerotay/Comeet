package com.a506.comeet.app.board.controller;

import com.a506.comeet.app.board.controller.dto.BoardCreateRequestDto;
import com.a506.comeet.app.board.controller.dto.BoardSearchResponseDto;
import com.a506.comeet.app.board.controller.dto.BoardUpdateRequestDto;
import com.a506.comeet.app.board.entity.Board;
import com.a506.comeet.app.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody BoardCreateRequestDto req) {
        String memberId = "요청자";
        Board board = boardService.create(req, memberId);
        return ResponseEntity.ok(board.getId());
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<LocalDateTime> update(@RequestBody BoardUpdateRequestDto req, @PathVariable(value = "boardId") Long boardId) {
        String memberId = "요청자";
        Board board = boardService.update(req, boardId, memberId);
        return ResponseEntity.ok(board.getUpdatedAt());
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> delete(@PathVariable(value = "boardId") Long boardId){
        String memberId = "요청자";
        boardService.delete(boardId, memberId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{boardId}")
    public ResponseEntity<BoardSearchResponseDto> search(@PathVariable(value = "boardId") Long boardId){
        String memberId = "요청자";
        return ResponseEntity.ok(boardService.search(boardId, memberId));
    }
}