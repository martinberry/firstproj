package com.ztravel.product.client.wo;

import java.util.List;
import java.util.Map;

public class OrderFlightWo {

	private List<FlightInfoWo> go ;

	private List<FlightInfoWo> back ;

	private Map<String, List<FlightInfoWo>> middle ;

	//页面展示用
	private List<FlightInfoWo> main ;

	public List<FlightInfoWo> getGo() {
		return go;
	}

	public void setGo(List<FlightInfoWo> go) {
		this.go = go;
	}

	public List<FlightInfoWo> getBack() {
		return back;
	}

	public void setBack(List<FlightInfoWo> back) {
		this.back = back;
	}

	public Map<String, List<FlightInfoWo>> getMiddle() {
		return middle;
	}

	public void setMiddle(Map<String, List<FlightInfoWo>> middle) {
		this.middle = middle;
	}

	public List<FlightInfoWo> getMain() {
		return main;
	}

	public void setMain(List<FlightInfoWo> main) {
		this.main = main;
	}

}
