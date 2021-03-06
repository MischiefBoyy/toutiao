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

import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.model.News;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.LikeService;
import com.nowcoder.service.NewsService;
import com.nowcoder.service.UserService;

@Controller
public class HomeController {
	@Autowired
	private NewsService newsService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;

	private List<ViewObject> getNews(int userId, int offset, int limit) {
		List<News> list = newsService.getNewsList(userId, offset, limit);
		int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
		List<ViewObject> vos = new ArrayList<>();
		for (News news : list) {
			ViewObject viewObject = new ViewObject();
			viewObject.set("news", news);
			viewObject.set("user", userService.getUserById(news.getUserId()));
			if (localUserId != 0) {
				viewObject.set("like", likeService.getLikeStatus(localUserId, EntityType.ENTITY_NEWS, news.getId()));
			} else {
				viewObject.set("like", 0);
			}
			vos.add(viewObject);
		}
		return vos;
	}

	@RequestMapping(path = { "/", "/index" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String index(Model model, @RequestParam(value = "pop", defaultValue = "0") int pop) {
		model.addAttribute("vos", getNews(0, 1, 10));
		if (hostHolder.getUser() != null) {
			pop = 0;
		}
		model.addAttribute("pop", pop);
		return "home";
	}

	@RequestMapping(path = { "/user/{userId}" }, method = { RequestMethod.GET, RequestMethod.POST })
	public String userIndex(Model model, @PathVariable("userId") int userId) {
		model.addAttribute("vos", getNews(userId, 1, 10));
		return "home";
	}
}