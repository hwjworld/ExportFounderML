package system.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

	/**
	 * content中匹配正则的字符串形成数组返回
	 * @param pattern
	 * @param content
	 * @return
	 */
	public static List<String> getListFromMatchedPattern(String pattern, String content)
	{
		List<String> list = new ArrayList<String>();
		if(content==null)return list;
		Pattern pat = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
		Matcher match = pat.matcher(content);
		while(match.find())
		{
			if(!list.contains(match.group(1)))
					list.add(match.group(1));
		}
		return list;
	}
	

	/**
	 * 检查中是否符合后缀数组中[]
	 * @param str
	 * @param ends
	 * @return
	 */
	public static boolean endWith(String str,String[] ends) {
		for(String end:ends){
			if("".equals(end.trim()))
				continue;
			if(str.toLowerCase().endsWith("."+end.toLowerCase())){
				return true;
			}
		}
		return false;
	}

}
