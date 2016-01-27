package com.xiaya.core.httputils;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import retrofit.RestAdapter;

//@Service
@Configurable
public class RetrofitHttpConfiguration {

	@Value("${ucenter.domain}")
	private String ucenterDomain;
	
	@Value("${retrofit.debug}")
	private boolean retrofitDebug;
	
	@Bean
	public UcenterHttpUtils ucenterHttpUtils(){
		RestAdapter.LogLevel logLevel = RestAdapter.LogLevel.NONE;
		if(retrofitDebug){
			logLevel = RestAdapter.LogLevel.FULL;
		}
		RestAdapter restAdapter = new RestAdapter.Builder()
				.setEndpoint(ucenterDomain)
				.setLogLevel(logLevel)
				.build();
		return restAdapter.create(UcenterHttpUtils.class);
	}
}
