package com.nowcoder.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.NewsService;

@Controller
public class HomeController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private HostHolder holder;
	
	
	private List<ViewObject> getNews(int userId, int offset, int limit){
		List<News> list=newsService.getNewsList(userId,offset,limit);
		List<ViewObject> vos = new ArrayList<>();
		return null;
	}
}