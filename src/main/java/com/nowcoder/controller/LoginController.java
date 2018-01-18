package com.nowcoder.controller;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.nowcoder.service.UserService;
import com.nowcoder.util.ToutiaoUtil;

@Controller
public class LoginController {
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private UserService userService;



	@RequestMapping(path = { "/reg/" }, method = { RequestMethod.POST })
	@ResponseBody
	public String register(@RequestParam("username") String username, @RequestParam("password") String password) {
		String result = null;
		if (StringUtils.isEmpty(username)) {
			return ToutiaoUtil.getJSONString(1, "用户名不能为空。");
		}
		if (StringUtils.isEmpty(password)) {
			return ToutiaoUtil.getJSONString(1, "密码不能为空。");
		}
		result = userService.regUser(username, password);

		return result;
	}
	
	@RequestMapping(path = { "/login/" }, method = { RequestMethod.POST })
	@ResponseBody
	public String login(@RequestParam("username") String username
						, @RequestParam("password") String password
						, @RequestParam("rember") int remember
						,HttpServletResponse response) {
		if (StringUtils.isEmpty(username)) {
			return ToutiaoUtil.getJSONString(1, "用户名不能为空。");
		}
		if (StringUtils.isEmpty(password)) {
			return ToutiaoUtil.getJSONString(1, "密码不能为空。");
		}
		try {	
			Map<String, Object> map=userService.login(username, password, remember);
			if(map.containsKey("ticket")) {
				Cookie cookie=new Cookie("ticket", map.get("ticket").toString());
				cookie.setPath("/");
				if(remember > 0) {
					cookie.setMaxAge(3600 * 24 * 7);
				}else {
					cookie.setMaxAge(3600 * 24);
				}
				response.addCookie(cookie);
				return ToutiaoUtil.getJSONString(0, "登录成功");
			}else {
				return ToutiaoUtil.getJSONString(1, map);
			}
		} catch (Exception e) {
			logger.error("注册异常："+e.getMessage());
			return ToutiaoUtil.getJSONString(1, "登录异常");
		}
	}
	
	
//	@RequestMapping(path = { "/logout/" }, method = { RequestMethod.GET,RequestMethod.POST })
//	public String logout(@CookieValue("ticket") String ticket) {
//		userService.logout(ticket);
//		return "redirect:/";
//	}
	
	
}
