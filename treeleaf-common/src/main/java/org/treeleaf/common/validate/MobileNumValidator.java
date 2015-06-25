package org.treeleaf.common.validate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证输入的字符串是否是手机号码格式
 * @author yaoshuhong
 * @date 2014-11-14 上午12:02:32
 */
public class MobileNumValidator implements Validator<String>{

	Pattern p = Pattern.compile("^((13[0-9])|(14[0-9])|(15[^4,\\D])|(17[0-9])|(18[0-9]))\\d{8}$");
	
	@Override
	public boolean validate(String s) {
		//中国手机号码段为11位, 前3位———网络识别号；第4-7位———地区编码；第8-11位———用户号码。
		if(StringUtils.isBlank(s) || s.length() != 11 || !NumberUtils.isNumber(s)){
			return false;
		}
		Matcher m = p.matcher(s);
		return m.matches();
	}
}
