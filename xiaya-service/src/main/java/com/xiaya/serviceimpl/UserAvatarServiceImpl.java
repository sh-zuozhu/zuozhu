package com.xiaya.serviceimpl;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.ByteArrayPartSource;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.xiaya.core.factory.HttpClientFactory;
import com.xiaya.core.pojo.ActionResult;
import com.xiaya.core.pojo.StatusCode;
import com.xiaya.core.utils.JsonUtils;
import com.xiaya.rpc.service.IUserAvatarService;

@Service
public class UserAvatarServiceImpl extends BaseService implements IUserAvatarService{
	
	 //默认的文件名最大长度
    public static final int DEFAULT_FILE_NAME_LENGTH = 200;
    
    //图片后缀名
    public static final String[] IMAGE_EXTENSION = {
    		"bmp", "gif", "jpg", "jpeg", "png"
    };

	/**
	 * 用户上传头像
	 * @param image
	 * @return
	 * @throws IOException 
	 */
	@Override
	public ActionResult upload(MultipartFile image) throws IOException {
		//校验上传文件的合法性:1.文件名长度;2.后缀名;3.是否是图片,图片有长和宽;
		String originalFileName = image == null ? "" : image.getOriginalFilename();
		int length = originalFileName.length();
		if(StringUtils.isEmpty(originalFileName) || length > DEFAULT_FILE_NAME_LENGTH){
			return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "图片文件名过长!", length);
		}
		
		String extension = FilenameUtils.getExtension(originalFileName);
		for (String str : IMAGE_EXTENSION) {
			if(!str.equalsIgnoreCase(extension)){
				return new ActionResult(StatusCode.PARAMS_EEROR_CODE, "上传文件非图片!", length);
			}
		}
		
		//上传头像
		HttpClient hc = HttpClientFactory.getHttpClient();
		PostMethod pm = new PostMethod();
		pm.setPath("http://10.30.99.38:8999/imageUpload");
		String uuid = String.valueOf(System.currentTimeMillis());
		
		Part[] parts = {
				new StringPart("userName", "traderate"),
				new StringPart("topic", "topic"),
				new FilePart(uuid, new ByteArrayPartSource("", image.getBytes()))
		};
		
		try {
			MultipartRequestEntity mr = new MultipartRequestEntity(parts, pm.getParams());
			pm.setRequestEntity(mr);
			hc.executeMethod(pm);
			String result = pm.getResponseBodyAsString();
			Object data = JsonUtils.parse2Map(result).get("data");
			return new ActionResult(StatusCode.SUCCESS_CODE, "upload success!", data);
		} catch (IOException e) {
			return new ActionResult(StatusCode.SERVER_ERROR_CODE, "upload failed!", null);
		}
	}

}
