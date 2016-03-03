package com.nutz.modules;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.dao.QueryResult;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Attr;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.filter.CheckSession;

import com.nutz.bean.User;

@IocBean
@At("/user")
@Ok("json:{locked:'password|salt',ignoreNull:true}")
@Fail("http:500")
@Filters(@By(type = CheckSession.class, args =
{ "me", "/" }))
public class UserModule
{
	@Inject
	protected Dao dao;

	@At
	public Object login(@Param("username") String username, @Param("password") String password, HttpSession httpSession)
	{
		User user = dao.fetch(User.class, Cnd.where("username", "=", username).and("password", "=", password));
		if (user == null)
		{
			return false;
		} else
		{
			httpSession.setAttribute("me", username);
			return true;
		}
	}

	@At
	@Ok(">>:/index.jsp")
	public void logout(HttpSession httpSession)
	{
		httpSession.invalidate();
	}

	@At
	public int count()
	{
		return dao.count(User.class);
	}

	@At
	protected String checkUser(User user, boolean create)
	{
		if (user == null)
		{
			return "空对象";
		}

		if (create)
		{
			if (Strings.isBlank(user.getUsername()) || Strings.isBlank(user.getPassword()))
			{
				return "用户名或密码不能为空";
			}
		} else
		{
			if (Strings.isBlank(user.getPassword()))
			{
				return "密码不能为空";
			}
		}

		String password = user.getPassword().trim();

		if (6 > password.length() || password.length() > 12)
		{
			return "密码长度错误";
		}

		user.setPassword(password);

		if (create)
		{

			int count = dao.count(User.class, Cnd.where("username", "=", user.getUsername()));

			if (count != 0)
			{
				return "用户已经存在";
			}
		} else
		{
			if (user.getId() < 1)
			{
				return "用户Id非法";
			}
		}
		if (user.getUpdateTime() != null)
		{
			user.setUsername(user.getUsername().trim());
		}
		return null;
	}

	@At
	public Object add(@Param("..") User user)
	{
		NutMap nutMap = new NutMap();
		String msg = checkUser(user, false);
		if (msg != null)
		{
			return nutMap.setv("ok", false).setv("msg", msg);
		}

		user.setCreatTime(new Date());
		user.setUpdateTime(new Date());
		dao.insert(user);
		return nutMap.setv("ok", true).setv("msg", msg);
	}

	@At
	public Object update(@Param("..") User user)
	{
		NutMap nutMap = new NutMap();
		String msg = checkUser(user, false);
		if (msg != null)
		{
			return nutMap.setv("ok", false).setv("msg", msg);
		}
		user.setUsername(null);
		user.setCreatTime(null);
		user.setUpdateTime(new Date());
		dao.updateIgnoreNull(user);

		return nutMap.setv("ok", true);
	}

	@At
	public Object delete(@Param("id") int id, @Attr("me") int me)
	{
		if (me == id)
		{
			return new NutMap().setv("ok", false).setv("msg", "不能删除当前用户!!");
		}
		dao.delete(User.class, id);
		return new NutMap().setv("ok", true);
	}

	@At
	public Object query(@Param("username") String username, @Param("..") Pager pager)
	{
		Cnd cnd = Strings.isBlank(username) ? null : Cnd.where("username", "like", "%" + username + "%");
		QueryResult queryResult = new QueryResult();
		queryResult.setList(dao.query(User.class, cnd, pager));
		pager.setRecordCount(dao.count(User.class, cnd));
		queryResult.setPager(pager);
		return queryResult;// 默认分页是第1页,每页20条
	}

	@At("/")
	@Ok("jsp:index")
	public void index() // 真实路径是 /WEB-INF/jsp/user/list.jsp
	{
	}

}
