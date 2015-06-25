package org.treeleaf.common.validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 邮箱格式验证
 * @author yaoshuhong
 * @date 2014-11-5 下午10:10:39
 */
public class EmailValidator implements Validator<String>{
	
	private static Pattern p =  Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");

	@Override
	public boolean validate(String email) {
       Matcher m = p.matcher(email);
	   return m.matches();  
	}
}
