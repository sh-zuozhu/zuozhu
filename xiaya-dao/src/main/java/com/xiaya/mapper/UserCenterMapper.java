package com.xiaya.mapper;

import java.util.List;
import java.util.Map;

import com.github.abel533.mapper.Mapper;
import com.xiaya.pojo.UserCenter;

public interface UserCenterMapper extends Mapper<UserCenter>{

	int updateByParamsSelective(UserCenter userCenter);

	List<UserCenter> selectByMap(Map<String, Object> params);
}
