package com.ztravel.common.mail;

/**
 * @author wanhaofan
 * */
public class MailEnums {
	public enum ContentType{
		HTML(1,"HTML") ,
		STRING(2,"STRING") ,
		UNPAYHTML(3,"UNPAYHTML") ,
		PAYEDHTML(4,"PAYEDHTML") ,
		CONFIRMHTML(5,"CONFIRM"),
		BACKPAYHTML(6,"BACKPAYHTML") ,
		PIECECONFIRMHTML(7,"PIECECONFIRMHTML");

		private ContentType(int id, String desc){
			this.id = id ;
			this.desc = desc ;
		}

		public int getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		private final int id ;
		private final String desc ;
	}

	public enum BizType{
		FINDPASSWORD(1,"FINDPASSWORD"),
		UNPAY(2,"UNPAY"),
		PAYED(3,"PAYED"),
		CONFIRM(4,"CONFIRM"),
		BACKPAY(5,"BACKPAY"),
		PIECECONFIRM(6,"PIECECONFIRM");

		private BizType(int id, String desc){
			this.id = id ;
			this.desc = desc ;
		}

		public int getId() {
			return id;
		}

		public String getDesc() {
			return desc;
		}

		private final int id ;
		private final String desc ;
	}
}
