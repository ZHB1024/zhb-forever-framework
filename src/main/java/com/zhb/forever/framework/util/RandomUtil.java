package com.zhb.forever.framework.util;

import java.util.Random;
import java.util.UUID;

import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

public class RandomUtil {
	
	private static Random random = new Random();
	
	private static final String[] names = {"张三","李四","王五","麻子","习近平"
            ,"毛泽东","周恩来","邓小平","江泽民","朱镕基"
            ,"彭丽媛","李开复","雷军","小米","apple"
            ,"任正非","华为","中兴","马云","马化腾"};
	private static final String[] sexs = {"男","女"};
	
	public static String getRandomUUID() {
        String uuid = UUID.randomUUID().toString();
        uuid = StringUtil.replace(uuid, "-", "");
        return uuid;
    }
	
	public static String randomName(int i){
        int index = random.nextInt(19);
        return names[index] + i;
    }
    
    public static int randomAge(){
        return random.nextInt(130);
    }
    
    public static String randomSex(){
        int index = random.nextInt(1);
        return sexs[index];
    }
	
	public static String getRandomNumbers(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int value = random.nextInt(10);
            sb.append(String.valueOf(value));
        }
        return sb.toString();
    }
	
	public static String getRandomString(int length) {
        String base = "0123456789abcdefghijklmnopqrstuvwxyz";
        int baseLength = base.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(baseLength);
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }
	
	public static String getRandomCaseSensitiveString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int baseLength = base.length();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(baseLength);
            sb.append(base.charAt(index));
        }
        return sb.toString();
    }

    public static Object getRandomObject(Object[] array) {
        Random random = new Random();
        int n = array.length;
        int index = random.nextInt(n);
        return array[index];
    }

    public static int getRandomInt(int[] array) {
        Random random = new Random();
        int n = array.length;
        int index = random.nextInt(n);
        return array[index];
    }

    public static String getRandomStrings(String[] array, int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int n = array.length;
        for (int i = 0; i < length; ++i) {
            int index = random.nextInt(n);
            sb.append(array[index]);
        }
        return sb.toString();
    }

    public static int getRandomInt(int start, int end) {
        Random random = new Random();
        int n = end - start + 1;
        int index = random.nextInt(n);
        return (start + index);
    }

    public static int getRandomChooseByHash(String resource, int range) {
        if ((range < 0) || (null == resource)) {
            return 0;
        }

        int hashCode = Math.abs(resource.hashCode());
        return getRandomChoose(hashCode, range);
    }

    private static int getRandomChoose(int hashCode, int range) {
        int mod = hashCode % range;
        return mod;
    }
    
    
    /**
     * 产生min-max之间的随机数字
     * @param min
     * @param max
     * @return
     */
    public static final long getRandom(int min, int max){
        return Math.round(Math.random()*(max-min)+min);
    }

    /**
     * 获取min-max之间的n个不重复随机数字<br/>
     * n<=max-min否则返回null
     * @param min
     * @param max
     * @param n
     * @return
     */
    public static final long[] getRandoms(int min, int max, int n){
        if(n > max-min)
            return null;
        long[] longRet = new long[n];
        long intRd = 0; // 存放随机数
        int count = 0; // 记录生成的随机数个数
        int flag = 0; // 是否已经生成过标志
        while (count < n) {
            intRd = getRandom(min, max);
            for (int i = 0; i < count; i++) {
                if (longRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                longRet[count] = intRd;
                count++;
            }
        }
        return longRet;
    }
    
    /**
     * 计算表达式
     * @param expressionString
     * @return
     * @throws OgnlException
     */
    public static final int getExpressionResults(String expressionString) throws OgnlException {
        OgnlContext ctx = new OgnlContext();
        Object expression = Ognl.parseExpression(expressionString);
        return (Integer) Ognl.getValue(expression, ctx, (Object)null);
    }


}
