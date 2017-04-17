package com.ztravel.mediaserver.consts;

public class Consts {

	public enum Header {

		/*** define ***/

		Media_Id("Media-Id"),

		Media_Type("Media-Type"),

		Media_Name("Media-Name"),
		
		Media_Cmd("Media-Cmd"),
		
		Media_Compress("Media-Compress");


		private final String value;

		Header(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		public static String[] headerNames() {
			java.util.List<String> names = new java.util.ArrayList<String>();
			for(Header h :values()) {
				names.add(h.getValue());
			}			
			return names.toArray(new String[values().length]);
		}

	}
	
	public enum COMMAND {
		
		none,
		add,
		get,
		crop,
		getmeta;
	}

	public static final long UPDATE_FREQUENCY = 60 * 60 * 24 * 7;

}
