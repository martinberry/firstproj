package com.ztravel.mediaserver.client;

public class MediaCompressMsgEntity {
	
	private String mediaId;
	
	private String compressType;


	public String getMediaId() {
		return mediaId;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public String getCompressType() {
		return compressType;
	}

	public void setCompressType(String compressType) {
		this.compressType = compressType;
	}
	
	public MediaCompressMsgEntity() {
		
	}
	
	public MediaCompressMsgEntity(String mediaId, String compressType) {
		this.mediaId = mediaId;
		this.compressType = compressType;
	}

}
