package com.nutz;

import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Localization;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

/**
 * Description:
 * 
 * @author Zhida Xu
 * @date 2016年3月2日上午10:26:58
 * @version 1.0
 */
@SetupBy(value = MainSetup.class)
@IocBy(type = ComboIocProvider.class, args =
{ "*js", "ioc/", "*anno", "com.nutz", "*tx" })
@Ok("json:full")
@Fail("jsp:jsp.500")
@Localization(value="msg/", defaultLocalizationKey="zh-CN")
@Modules(scanPackage = true)
public class MainModule
{

}
