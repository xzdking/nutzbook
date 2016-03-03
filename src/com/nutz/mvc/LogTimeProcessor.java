package com.nutz.mvc;

import javax.servlet.http.HttpServletRequest;

import org.nutz.lang.Stopwatch;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.ActionContext;
import org.nutz.mvc.annotation.ReqHeader;
import org.nutz.mvc.impl.processor.AbstractProcessor;

public class LogTimeProcessor extends AbstractProcessor
{

	private static final Log log = Logs.get();

	public LogTimeProcessor()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void process(ActionContext ac) throws Throwable
	{
		Stopwatch stopwatch = Stopwatch.begin();

		doNext(ac);

		stopwatch.stop();

		if (log.isDebugEnabled())
		{
			HttpServletRequest httpServletRequest = ac.getRequest();
			log.debugf("[%4s]URI=%s %sms", httpServletRequest.getMethod(), stopwatch.getDuration());
		}
	}

}
