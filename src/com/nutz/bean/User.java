package com.nutz.bean;

import java.util.Date;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * Description:
 * 
 * @author Zhida Xu
 * @date 2016年3月2日上午11:08:23
 * @version 1.0
 */
@Table("t_user")
public class User
{
	@Id
	private int id;

	@Name
	@Column
	private String username = null;

	@Column("password")
	private String password = null;

	@Column
	private String salt = null;

	@Column("ct")
	private Date creatTime = null;

	@Column("ut")
	private Date updateTime = null;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public String getSalt()
	{
		return salt;
	}

	public void setSalt(String salt)
	{
		this.salt = salt;
	}

	public Date getCreatTime()
	{
		return creatTime;
	}

	public void setCreatTime(Date creatTime)
	{
		this.creatTime = creatTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	@Override
	public String toString()
	{
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", salt=" + salt
				+ ", creatTime=" + creatTime + ", updateTime=" + updateTime + "]";
	}

}
