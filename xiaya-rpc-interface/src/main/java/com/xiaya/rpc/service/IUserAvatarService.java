package com.xiaya.rpc.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.xiaya.core.pojo.ActionResult;

public interface IUserAvatarService {
	
	/**
	 * 用户上传头像
	 * @param image
	 * @return
	 * @throws IOException 
	 */
	ActionResult upload(MultipartFile image) throws IOException;

	/**
	 * 裁剪头像并保存
	 * @param imgUrl
	 * @param x
	 * @param y
	 * @param destWidth
	 * @param destHeight
	 * @return
	 */
	ActionResult cutAvatar(String imgUrl, Integer x, Integer y, Integer destWidth, Integer destHeight);

}
