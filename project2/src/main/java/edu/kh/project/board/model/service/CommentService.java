package edu.kh.project.board.model.service;

import java.util.List;

import edu.kh.project.board.model.vo.Comment;

public interface CommentService {

	/** 댓글 목록 조회
	 * @param boardNo
	 * @return rList
	 */
	List<Comment> selectCommetList(int boardNo);

	int insertComment(Comment comment);

	int deleteComment(int commentNo);

	int updateComment(Comment comment);

}
