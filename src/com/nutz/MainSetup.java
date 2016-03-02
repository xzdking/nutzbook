package com.nutz;

import java.util.Date;

import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.nutz.bean.User;

/**
 * Description:
 * 
 * @author Zhida Xu
 * @date 2016年3月2日上午11:11:40
 * @version 1.0
 */
public class MainSetup implements Setup
{

	@Override
	public void init(NutConfig nutConfig)
	{
		Ioc ioc = nutConfig.getIoc();
		Dao dao = ioc.get(Dao.class);
		Daos.createTablesInPackage(dao, "com.nutzbook", false);

		// 初始化默认根用户
		if (dao.count(User.class) == 0)
		{
			User user = new User();
			user.setUsername("admin");
			user.setPassword("123456");
			user.setCreatTime(new Date());
			user.setUpdateTime(new Date());
			dao.insert(user);
		}
	}

	@Override
	public void destroy(NutConfig nutConfig)
	{
		// TODO Auto-generated method stub

	}

}
