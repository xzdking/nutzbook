package com.nutz.modules;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;

public abstract class BaseModule
{

	@Inject
	protected Dao dao;

}
