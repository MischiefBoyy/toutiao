package com.nowcoder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;

@Controller
public class NewsController {
	
	@Autowired
	NewsService newsService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(path={"/news/{newsId}"},method= {RequestMethod.GET})
	public String newsDetail(@PathVariable("newsId") int newsId,Model model) {
		
		News news=newsService.getNewsById(newsId);
		if(news != null) {
			List<Comment> comments=commentService.getCommentByEntity(newsId);
			 List<ViewObject> commentVOs = new ArrayList<ViewObject>();
			 for (Comment comment : comments) {
                 ViewObject commentVO = new ViewObject();
                 commentVO.set("comment", comment);
                 commentVO.set("user", userService.getUserById(comment.getUserId()));
                 commentVOs.add(commentVO);
             }
			 model.addAttribute("comments", commentVOs);
			
		}
		model.addAttribute("news", news);
        model.addAttribute("owner", userService.getUserById(news.getUserId()));
		
		return "detail";
	}
	
	@RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
	public String addComment(@RequestParam("newsId") int newsId,
            @RequestParam("content") String content ) {
		commentService.addComment(content, newsId);
		// 更新评论数量，以后用异步实现
        int count = commentService.getCommentCount(newsId, EntityType.ENTITY_NEWS);
        newsService.updateCommentCount(newsId, count);
		return "redirect:/news/"+String.valueOf(newsId);
	}

}
