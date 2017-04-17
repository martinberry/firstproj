package com.ztravel.operator.basicdata.entity;

import java.io.Serializable;

import com.github.jmkgreen.morphia.annotations.Entity;
import org.bson.types.ObjectId;
import com.github.jmkgreen.morphia.annotations.Id;

@Entity(value="nickNameLib", noClassnameStored=true)
public class NickNameEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1145483154301645438L;
	
	@Id
	private ObjectId id;
	
	private String nickName;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
	
	

}
