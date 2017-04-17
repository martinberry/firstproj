package com.ztravel.common.security;

import java.util.Random;

/**
 * 生成随机序列
 * http://blog.csdn.net/VisionCat/article/details/6925542
 * @author liuzhuo
 * 生的的可还原序列不唯一
 */
@Deprecated
public class RandomId {

    private static Random random;

    private static String table;

    public RandomId() {

    }

    static {
        random = new Random();
        table = "0123456789";
    }

    public static String randomIdWithoutKeyAndSeed(int id) {
        String ret = null,
            num = String.format("%04d", id);
        int key = random.nextInt(10),
            seed = random.nextInt(10);
        Caesar caesar = new Caesar(table, seed);
        num = caesar.encode(key, num);
        ret = num;
        return ret;
    }

    public static String randomCouponIdWithoutKeyAndSeed(int id) {
        String ret = null,
            num = String.format("%08d", id);
        int key = random.nextInt(10),
            seed = random.nextInt(10);
        Caesar caesar = new Caesar(table, seed);
        num = caesar.encode(key, num);
        ret = num;
        return ret;
    }
    
    public static void main(String args[]){
    	boolean flag=true ;
    	while(flag){
    		String v = randomIdWithoutKeyAndSeed(7902) ;
    		//String v = randomIdWithoutKeyAndSeed(8302) ;
    		System.out.println(v);
    		if(v.equals("7545")){
    			flag = false ;
    		}
    	}
    	
    }

}