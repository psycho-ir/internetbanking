package com.samenea.payments.web.model.follow;

import com.ghasemkiani.util.icu.PersianDateFormat;
import com.ibm.icu.util.ULocale;
import com.samenea.commons.component.utils.persian.DateUtil;
import com.samenea.commons.tracking.model.Track;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/19/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FollowViewModel {

    private String subSystem;
    private String occurrenceDate;
    private String description;



    private String followId;
    public String getOccurrenceDate() {
        return occurrenceDate;
    }

    public void setOccurrenceDate(String occurrenceDate) {
        this.occurrenceDate = occurrenceDate;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getSubSystem() {
        return subSystem;
    }

    public void setSubSystem(String subSystem) {
        this.subSystem = subSystem;
    }
    public String getFollowId() {
        return followId;
    }

    public void setFollowId(String followId) {
        this.followId = followId;
    }




    public FollowViewModel(Track track) {
        PersianDateFormat dateFormat = new PersianDateFormat(
                "yyyy/MM/dd hh:mm:ss ", new ULocale("fa", "IR", ""));
        if (track.getOccurrenceDate() != null) {
            this.occurrenceDate = DateUtil.toString(track.getOccurrenceDate(),dateFormat);
        }
        this.followId = track.getTrackID();
        this.description = track.getDescription();
        this.subSystem = track.getSubSystem();
    }
}
