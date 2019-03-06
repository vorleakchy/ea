package edu.mum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.mum.dao.CommentDao;
import edu.mum.domain.Comment;
import edu.mum.service.CommentService;

@Service
@Transactional 
public class CommentServiceImpl implements CommentService {
	
 	@Autowired
	private CommentDao commentDao;

    	public void save(Comment comment) {
  		commentDao.save(comment);
 	}
   	
   	@PreAuthorize("hasPermission(#comment,'update')")
  	public void update(Comment comment) {
		commentDao.update(comment);
	}
   
   	@PreAuthorize("hasPermission(#comment,'delete')")
  	public void delete(Comment comment) {

  		commentDao.delete(comment.getId());
 	   
	}

  	@PreAuthorize("hasAuthority('list')")	
	public List<Comment> findAll() {
		return (List<Comment>)commentDao.findAll();
	}


 
}
