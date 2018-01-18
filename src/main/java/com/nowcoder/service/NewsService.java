package com.nowcoder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nowcoder.dao.NewsDAO;
import com.nowcoder.model.News;

@Service
public class NewsService {
	@Autowired
	private NewsDAO newsDAO;
	
	
	public News getNewsById(int id) {
		return newsDAO.getById(id);
	}
	
	
	public List<News> getNewsList(int userId,int page,int limit){
		return newsDAO.selectByUserIdAndOffset(userId, (page-1)*limit, limit);
	}
	
	public int updateCommentCount(int id,int count) {
		return newsDAO.updateCommentCount(id, count);
	}
	
	public int addNews(News news) {
		return newsDAO.addNew(news);
	}


	public void updateLikeCount(int newsId, int likeCount) {
		newsDAO.updateLikeCount(newsId, likeCount);
	}

}
