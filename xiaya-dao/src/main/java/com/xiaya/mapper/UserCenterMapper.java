package com.xiaya.mapper;

import com.github.abel533.mapper.Mapper;
import com.xiaya.pojo.UserCenter;

public interface UserCenterMapper extends Mapper<UserCenter>{

	int updateByParamsSelective(UserCenter userCenter);

}
