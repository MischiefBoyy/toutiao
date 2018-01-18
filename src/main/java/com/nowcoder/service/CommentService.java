package com.nowcoder.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;

@Service
public class CommentService {
	
	@Autowired
	CommentDAO commentDao;
	@Autowired
	HostHolder holder;
	
	public List<Comment> getCommentByEntity(int entityId){
		return commentDao.selectByEntity(entityId, EntityType.ENTITY_NEWS);
	}
	
	public int addComment(String content,int eneityId) {
		Comment comment=new Comment();
		comment.setContent(content);
		comment.setCreatedDate(new Date());
		comment.setEntityId(eneityId);
		comment.setEntityType(EntityType.ENTITY_NEWS);
		comment.setStatus(0);
		comment.setUserId(holder.getUser().getId());
        return commentDao.addComment(comment);
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDao.getCommentCount(entityId, entityType);
    }
	
	

}
