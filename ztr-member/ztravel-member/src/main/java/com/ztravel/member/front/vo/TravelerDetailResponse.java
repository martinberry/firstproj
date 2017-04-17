/**
 *
 */
package com.ztravel.member.front.vo;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.member.po.TravelerEntity;

/**
 * @author zuoning.shen
 *
 */
public class TravelerDetailResponse extends AjaxResponse{
    private TravelerEntity traveler;

    public TravelerEntity getTraveler() {
        return traveler;
    }

    public void setTraveler(TravelerEntity traveler) {
        this.traveler = traveler;
    }
}
