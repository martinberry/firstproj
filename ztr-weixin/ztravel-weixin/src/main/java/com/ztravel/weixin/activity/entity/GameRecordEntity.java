package com.ztravel.weixin.activity.entity;

import org.joda.time.DateTime;

public class GameRecordEntity {

	private String id;

	private String gameResult;

	private Double  gameScore;

	private String gameId;

	private DateTime createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGameResult() {
		return gameResult;
	}

	public void setGameResult(String gameResult) {
		this.gameResult = gameResult;
	}

	public Double getGameScore() {
		return gameScore;
	}

	public void setGameScore(Double gameScore) {
		this.gameScore = gameScore;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public DateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(DateTime createTime) {
		this.createTime = createTime;
	}

}
