package com.ztravel.weixin.back.bean;


public class ComplexMenuButton extends MenuButtonVo {

	private MenuButtonVo[] subMenuButtonVos;

	public MenuButtonVo[] getSubMenuButtonVos() {
		return subMenuButtonVos;
	}

	public void setSubMenuButtonVos(MenuButtonVo[] subMenuButtonVos) {
		this.subMenuButtonVos = subMenuButtonVos;
	}

}
