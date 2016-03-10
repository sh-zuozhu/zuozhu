package com.xiaya.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.spring.mvc.support.JsonResult;
import com.xiaya.rpc.service.IUserAvatarService;

@Controller
@RequestMapping("user/avatar")
public class UserAvatarController {
	
	@Autowired
	private IUserAvatarService userAvatarService;
	
	@JsonResult(desc = "上传头像")
	@RequestMapping("upload")
	public ActionResult uploadAvatar(MultipartFile image) throws IOException{
		return this.userAvatarService.upload(image);
	}
}
