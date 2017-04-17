package com.ztravel.front.member.test;


public interface MorphiaEntity<I> {

	I getId();

	void setId(I id);

	void setId(String id);

}
