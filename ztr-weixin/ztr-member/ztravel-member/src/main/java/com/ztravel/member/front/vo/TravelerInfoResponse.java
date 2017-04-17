/**
 *
 */
package com.ztravel.member.front.vo;

import java.util.List;

import com.ztravel.common.bean.AjaxResponse;
import com.ztravel.reuse.member.entity.SimpleTravelerEntity;

/**
 * @author zuoning.shen
 *
 */
public class TravelerInfoResponse extends AjaxResponse{
    private List<SimpleTravelerEntity> travelerList;

    public List<SimpleTravelerEntity> getTravelerList() {
        return travelerList;
    }

    public void setTravelerList(List<SimpleTravelerEntity> travelerList) {
        this.travelerList = travelerList;
    }
}
