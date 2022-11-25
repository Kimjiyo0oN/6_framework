package edu.kh.project.board.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.kh.project.board.model.dao.CommentDAO;
import edu.kh.project.board.model.vo.Comment;
import edu.kh.project.common.Util;

@Service
public class CommentServiceImpl implements CommentService{
	@Autowired
	private CommentDAO dao;

	// 댓글 목록 조회
	@Override
	public List<Comment> selectCommetList(int boardNo) {
		
		return dao.selectCommentList(boardNo);
	}

	@Override
	public int insertComment(Comment comment) {
		// XSS 방지, 개행 문자 처리 
		comment.setCommentContent(Util.XSSHandling(comment.getCommentContent()));
		comment.setCommentContent(Util.newLineHandling(comment.getCommentContent()));
		return dao.insertComment(comment);
	}

	@Override
	public int deleteComment(int commentNo) {

		return dao.delectComment(commentNo);
	}

	@Override
	public int updateComment(Comment comment) {
		comment.setCommentContent(Util.XSSHandling(comment.getCommentContent()));
		comment.setCommentContent(Util.newLineHandling(comment.getCommentContent()));
		return dao.updateComment(comment);
	}
}
