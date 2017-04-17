package com.ztravel.product.front.wo;

import java.util.List;

/**
 * 用于详情页calendar浮层
 * @author xujunhui
 *
 */
public class FHTipWo {
	private List<FlightInfoCalWo> flightInfos;
	private HotelInfo hotelInfo;
/**
 * dataList: [//根据数组长度 表示要显示的天数
			{  // 第一天
				flightInfos:[
					{
						fromCity: '1上海',
						toCity: '1金边',
						departureTime: '07:10',
						arrivalTime: '12:15',
						flightNo: 'K555'
					},
					{
						fromCity: '1金边',
						toCity: '1新罗',
						departureTime: '14:12',
						arrivalTime: '15:15',
						flightNo: 'K666'
					}
				],
				hotelInfo:{
					hotelNameCn: '1吴哥奇迹温泉度假酒店吴哥奇迹温泉度假酒店吴哥奇迹温泉度假酒店',
					hotelNameEn: 'Angkor Miracle Resort & SpaAngkor Miracle Resort & SpaAngkor Miracle Resort & Spa',
					rating: '5'
				}
			},
		]
 */
	public List<FlightInfoCalWo> getFlightInfos() {
		return flightInfos;
	}
	public void setFlightInfos(List<FlightInfoCalWo> flightInfos) {
		this.flightInfos = flightInfos;
	}
	public HotelInfo getHotelInfo() {
		return hotelInfo;
	}
	public void setHotelInfo(HotelInfo hotelInfo) {
		this.hotelInfo = hotelInfo;
	}
}
