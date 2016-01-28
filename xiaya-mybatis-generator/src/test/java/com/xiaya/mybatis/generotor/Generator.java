package com.xiaya.mybatis.generotor;

import java.io.File;

import org.mybatis.generator.api.ShellRunner;

/**
 * 生成工具<br/>
 * 支持无参模式、简略模式和原生模式
 * @author 丹青生
 *
 * @date 2015-9-2
 */
public class Generator {

	public static void main(String[] args) {
		if(args.length == 0){
			args = new String[]{"resource:generator.xml"};
		}
		if (args.length == 1 && !isEmpty(args[0]) && !"-h".equalsIgnoreCase(args[0])) {
			String configFile = args[0];
			if(configFile.startsWith("\\") || configFile.startsWith("/")){ // Linux绝对路径
				
			}else if(configFile.length() > 2 && configFile.charAt(1) == ':') { // Windows绝对路径
				
			}else if(configFile.startsWith("resource:")){ // resource路径
				configFile = new File("").getAbsolutePath() + "\\src\\main\\resources\\" + configFile.replace("resource:", "");
			}else { // 当前目录
				configFile = new File("").getAbsolutePath() + "\\" + configFile;
			}
			System.out.println("使用配置文件:" + configFile);
			ShellRunner.main(new String[]{"-overwrite",  "-verbose", "-configfile", configFile});
        }else{
        	ShellRunner.main(args);
        }
	}
	
	public static boolean isEmpty(String string){
		return string == null || string.trim().length() == 0;
	}
	
}

