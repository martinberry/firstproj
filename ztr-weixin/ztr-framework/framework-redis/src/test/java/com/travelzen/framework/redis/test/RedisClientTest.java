/**
 * 
 */
package com.travelzen.framework.redis.test;

import com.travelzen.framework.redis.client.RedisClient;

/**
 * @author zuoning.shen
 *
 */
public class RedisClientTest {
    public static void main(String[] args){
        RedisClient client = RedisClient.getInstance();
        for(int i = 0; i < 100; i++){
            String uid = Integer.toString(100001 + i);
            String name = "jason" + i;
            int age = 28;
            User user = new User(uid, name, age);
            client.set(uid, user);
            System.out.println(client.get(uid));
        }
    }
}
