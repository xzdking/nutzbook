package com.nutz.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.nutz.lang.Lang;
import org.nutz.lang.random.R;
import org.nutz.lang.util.NutMap;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.nutz.bean.User;

public class Toolkit
{
	public static final Log log = Logs.get();

	public static String captcha_attr = "nutz_captcha";

	public static boolean checkCaptcha(String expected, String actual)
	{
		if (expected == null || actual == null || actual.length() == 0 || actual.length() > 24)
		{
			return false;
		}
		return actual.equalsIgnoreCase(expected);
	}

	public static String passwordEncode(String password, String slat)
	{
		String str = slat + password + slat + password.substring(4);
		return Lang.digest("SHA-512", str);
	}

	private static final String Iv = "\0\0\0\0\0\0\0\0";

	private static final String Transformation = "DESede/CBC/PKCS5Padding";

	// 3DES加密的封装

	public static String _3DES_encode(byte[] key, byte[] data)
	{
		SecretKey secretKey = new SecretKeySpec(key, "DESede");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Iv.getBytes());

		try
		{
			Cipher cipher = Cipher.getInstance(Transformation);
			cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec);
			byte[] re = cipher.doFinal(data);
			return Lang.fixedHexString(re);
		} catch (Exception e)
		{
			log.info("3DES FAIL?", e);
			e.printStackTrace();
		}
		return null;
	}

	public static String _3DES_decode(byte[] key, byte[] data)
	{
		SecretKey secretKey = new SecretKeySpec(key, "DESede");
		IvParameterSpec ivParameterSpec = new IvParameterSpec(Iv.getBytes());
		try
		{
			Cipher cipher = Cipher.getInstance(Transformation);
			cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
			byte[] re = cipher.doFinal(data);
			return new String(re);
		} catch (Exception e)
		{
			log.debug("BAD 3DES decode", e);
			e.printStackTrace();
		}
		return null;
	}
	// kv字符串转换

	public static NutMap kv2map(String kv)
	{
		NutMap nutMap = new NutMap();
		if (kv == null || kv.length() == 0 || !kv.contains("="))
		{
			return nutMap;
		}
		String[] tmps = kv.split(",");
		for (String tmp : tmps)
		{
			if (!tmp.contains("="))
			{
				continue;
			}
			String[] tmps2 = tmp.split("=", 2);
			nutMap.put(tmps2[0], tmps2[1]);
		}
		return nutMap;
	}

	public static String randomPassword(User user)
	{
		String password = R.sg(10).next();
		String slat = R.sg(48).next();
		user.setSalt(slat);
		user.setPassword(password);
		return password;
	}

	// 密码加密hash
	public static byte[] hexstr2bytearray(String str)
	{
		byte[] re = new byte[str.length() / 2];

		for (int i = 0; i < re.length; i++)
		{
			int r = Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			re[i] = (byte) r;
		}
		return re;
	}
}
