package com.xieweihao.utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 字符串工具
 * 
 * @author wx
 * 
 */
public final class StringUtils {

	public static final String SYMBO_START = "${";
	public static final String SYMBO_END = "}";

	private StringUtils() {
	}

	/**
	 * 把字符串列表用分割符组合成字符串
	 * 
	 * @param list
	 *            List 字符串列表
	 * @param split
	 *            String 分割符
	 * @return String
	 */
	public static String listToString(List list, String split) {
		String ret = null;
		try {
			if (list != null && list.size() != 0) {
				for (int i = 0; i < list.size(); i++) {
					if (i == 0) {
						ret = (String) list.get(i);
					} else {
						ret += split + (String) list.get(i);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 把字符串按照分割符截取成列表
	 * 
	 * @param input
	 *            String 需要分割的字符串
	 * @param split
	 *            String 分割符号
	 * @return List
	 */
	public static List stringToList(String input, String split) {
		List ret = new ArrayList();
		try {
			if (input != null && input.length() != 0) {
				String[] str = input.split(split);
				for (int i = 0; i < str.length; i++) {
					ret.add(str[i]);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}

	/**
	 * 保存字符串到指定的文件对象
	 * 
	 * @param source
	 *            保存到文件的符串数据
	 * @param file
	 *            目标文件
	 * @param append
	 *            是否添加到文件结尾
	 * @throws IOException
	 */
	public static void saveStringToFile(String source, File file, boolean append)
			throws IOException {
		final int buffer = 10240; // 缓存
		FileOutputStream fos = null;
		BufferedOutputStream bos = null;
		PrintStream ps = null;
		try {
			fos = new FileOutputStream(file, append);
			bos = new BufferedOutputStream(fos, buffer);
			ps = new PrintStream(bos, true);
			ps.print(source);
		} finally {
			try {
				if (null != ps) {
					ps.close();
				}
			} catch (Exception e) {
			}
			try {
				if (null != bos) {
					bos.close();
				}
			} catch (Exception e) {
			}
			try {
				if (null != fos) {
					fos.close();
				}
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 是否为空串
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isBlank(String value) {
		if (value == null || value.trim().length() == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 字符串是为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrEmpty(String str) {
		return (str == null) || (str.length() == 0);
	}
	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		return !isStrEmpty(str);
	}
	/**
	 * 字符串是为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrBlank(String str) {
		int strLen;
		if ((str == null) || ((strLen = str.length()) == 0))
			return true;
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	/**
	 * 字符串不为空
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isStrNotBlank(String str) {
		return !isBlank(str);
	}

	/**
	 * 去掉左右空白,
	 * 
	 * @param value
	 * @return
	 */
	public static String trim(String value) {
		if (value == null) {
			return null;
		} else {
			return value.trim();
		}
	}

	/**
	 * 去掉左右空白
	 * 
	 * @param input
	 * @return
	 */
	public static String trimForNull(String input) {
		if (null == input) {
			return "";
		} else {
			return input.trim();
		}
	}

	/**
	 * 
	 * @param str
	 * @param symbo
	 * @param values
	 * @return
	 */
	public static String replace(String str, String symbo, String... values) {
		for (String id : values) {
			str = str.replace(symbo, id);
		}
		return str;
	}

	/**
	 * 替换str中的${}
	 * 
	 * @param context
	 * @param str
	 * @return
	 */
	public static String symbo(Map context, String str) {
		int start = 0;
		int end = 0;
		while ((start = str.indexOf(SYMBO_START)) >= 0) {
			end = str.indexOf(SYMBO_END);
			String symboName = str.substring(start + SYMBO_START.length(), end);
			String value = (String) context.get(symboName);
			str = str.substring(0, start) + value
					+ str.substring(end + SYMBO_END.length());
		}
		return str;
	}

	/**
	 * 用系统属性替换${}
	 * 
	 * @param str
	 * @return
	 */
	public static String symbo(String str) {
		int start = 0;
		int end = 0;
		while ((start = str.indexOf(SYMBO_START)) >= 0) {
			end = str.indexOf(SYMBO_END);
			String symboName = str.substring(start + SYMBO_START.length(), end);
			String value = System.getProperty(symboName);
			str = str.substring(0, start) + value
					+ str.substring(end + SYMBO_END.length());
		}
		return str;
	}

	/**
	 * 包装字符器
	 * 
	 * @param value
	 * @return
	 */
	public static String wrap(String value, char c) {
		if (StringUtils.isBlank(value)) {
			return value;
		} else {
			StringBuffer pwd = new StringBuffer();
			for (int i = 0; i < value.length(); i++) {
				pwd.append(c);
			}
			return pwd.toString();
		}
	}

	/**
	 * 包装密码
	 * 
	 * @param value
	 * @return
	 */
	public static String wrapPwd(String value) {
		return wrap(value, '*');
	}

	/**
	 * 获取拼音码
	 * 
	 * @param str
	 * @return
	 */
	public static String spellCode(String str) {

		String returnstr;
		byte[] bytestr;
		int i = 0, j;
		returnstr = "";
		bytestr = str.getBytes();
		//
		for (; i < bytestr.length;) {
			if ((bytestr[i] < 128) && (bytestr[i] > 0)) {
				i++;
				continue;
			} else {
				j = (bytestr[i] + 256) * 1000 + bytestr[i + 1] + 256;

				// 判断编码

				// A
				if ((j >= 176161) && (j <= 176196)) {
					returnstr += "A";

				}

				// B
				if ((j >= 176197) && (j <= 178192)) {
					returnstr += "B";

				}

				// C
				if ((j >= 178193) && (j <= 180237)) {
					returnstr += "C";

				}

				// D
				if ((j >= 180238) && (j <= 182233)) {
					returnstr += "D";

				}

				// E
				if ((j >= 182234) && (j <= 183161)) {
					returnstr += "E";

				}

				// F
				if ((j >= 183162) && (j <= 184192)) {
					returnstr += "F";

				}

				// G
				if ((j >= 184193) && (j <= 185253)) {
					returnstr += "G";

				}

				// H
				if ((j >= 185254) && (j <= 187246)) {
					returnstr += "H";

				}

				// J
				if ((j >= 187247) && (j <= 191165)) {
					returnstr += "J";

				}

				// K
				if ((j >= 191167) && (j <= 192171)) {
					returnstr += "K";

				}

				// L
				if ((j >= 192172) && (j <= 194231)) {
					returnstr += "L";

				}

				// M
				if ((j >= 194232) && (j <= 196194)) {
					returnstr += "M";

				}

				// N
				if ((j >= 196195) && (j <= 197181)) {
					returnstr += "N";

				}

				// O
				if ((j >= 197182) && (j <= 197189)) {
					returnstr += "O";

				}

				// P
				if ((j >= 197190) && (j <= 198217)) {
					returnstr += "P";

				}

				// Q
				if ((j >= 198218) && (j <= 200186)) {
					returnstr += "Q";

				}

				// R
				if ((j >= 200187) && (j <= 200245)) {
					returnstr += "R";

				}

				// S
				if ((j >= 200246) && (j <= 203249)) {
					returnstr += "S";

				}

				// T
				if ((j >= 203250) && (j <= 205217)) {
					returnstr += "T";

				}

				// W
				if ((j >= 205218) && (j <= 206243)) {
					returnstr += "W";

				}

				// X
				if ((j >= 206244) && (j <= 209184)) {
					returnstr += "X";

				}

				// Y
				if ((j >= 209185) && (j <= 212208)) {
					returnstr += "Y";

				}

				// Z
				if ((j >= 212208) && (j <= 216160)) {
					returnstr += "Z";

				}

				i += 2;

			}
		}

		return returnstr;

	}
	
	// 十六进制转ASCII码
    public static String convertHexToString(String hex){
    	StringBuilder sb = new StringBuilder();
    	StringBuilder temp = new StringBuilder();

    	for( int i=0; i<hex.length()-1; i+=2 ){
    		String output = hex.substring(i, (i + 2));
    		int decimal = Integer.parseInt(output, 16);
    		sb.append((char)decimal);
    		temp.append(decimal);
    	}
    	 return sb.toString();
    }

    // 十六进制转OID
    public static String convertToString(String hex){
    	String oid="";
    	int ss;
    	int sd;
    	String bigStr = "";
    	int j =0;

    	for( int i=0; i<hex.length()-1; i+=2 ){
    		String output = hex.substring(i, (i + 2));
    		int decimal = Integer.parseInt(output, 16);
    		if(i==0){
    			ss = decimal/40;
        		sd = decimal%40;
        		oid = ss+"."+sd;
    		}else{
    			if(decimal>128 || (i==j && j!=0)){
    				String uu = hexString2binaryString(output);
    				String first = uu.substring(0, 1);
    				
    				if("1".equals(first)){
    					bigStr += uu.substring(1, 8);
    					j = i+2;
    				}else{
    					oid += "."+binaryToDecimal(bigStr + uu.substring(1, 8));
    					j=0;
    					bigStr="";
    				}
    				
    				
    			}else{
    				oid += "."+decimal;
    			}
    		}
    	}
    	 return oid;
    }
    
    //十六进制转二进制
    public static String hexString2binaryString(String hexString)
    {
        if (hexString == null || hexString.length() % 2 != 0)
            return null;
        String bString = "", tmp;
        for (int i = 0; i < hexString.length(); i++)
        {
            tmp = "0000"+ Integer.toBinaryString(Integer.parseInt(hexString.substring(i, i + 1), 16));
            bString += tmp.substring(tmp.length() - 4);
        }
        return bString;
    }
    
    //二进制转十进制
    public static int  binaryToDecimal(String binarySource) {  
        BigInteger bi = new BigInteger(binarySource, 2);    //转换为BigInteger类型  
        return Integer.parseInt(bi.toString());     //转换成十进制  
    }
    
    //二进制转十六进制
    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
 
            stringBuilder.append(i + ":");
 
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + ";");
        }
        return stringBuilder.toString();
    }
    
    /***
	 * 生成uid 8位数字
	 */
	public static String generateNumUID(){
	    Random random = new Random();
	    String result="";
	    for(int i=0;i<8;i++){
	        //首字母不能为0
	        result += (random.nextInt(9)+1);
	    }
	    return result;
	}
    
	//取十进制负数补码
    public static String printComplementCode(byte a){
    	String intStr = "";
        for (int i = 0; i < 8; i++){
            // 0x80000000 是一个首位为1，其余位数为0的整数
        	byte t = (byte) ((a & 0x80 >>> i) >>> (7 - i));
        	intStr +=t;
        }
		return intStr;
    }
    
 
	//十进制转16进制
	public static String toHex(int num) {
		char[] map = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
		if(num == 0) 
			return "0";
		String result = "";
		while(num != 0){
			int x = num&0xF;
			result = map[(x)] + result;
			num = (num >>> 4);
		}
		return result;
	}
	
}
