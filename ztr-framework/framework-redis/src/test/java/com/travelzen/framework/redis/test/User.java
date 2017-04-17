/**
 * 
 */
package com.travelzen.framework.redis.test;

/**
 * @author zuoning.shen
 *
 */
public class User {
    private String uid;
    private String name;
    private int age;
    public User(String uid, String name, int age){
        this.uid = uid;
        this.name = name;
        this.age = age;
    }
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
}
