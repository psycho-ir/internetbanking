package com.samenea.payments.web.model.tracking;

import com.samenea.captcha.CaptchaText;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/19/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class TrackingSearchViewModel {
    @NotEmpty
    private String trackID;
    @NotEmpty
    private String email;
    @CaptchaText
    private  String jCaptchaResponse;
    public TrackingSearchViewModel(String trackID, String email) {
        this.trackID = trackID;
        this.email = email;
    }
    public String getjCaptchaResponse() {
        return jCaptchaResponse;
    }

    public void setjCaptchaResponse(String jCaptchaResponse) {
        this.jCaptchaResponse = jCaptchaResponse;
    }

    public String getTrackID() {
        return trackID;
    }

    public void setTrackID(String trackId) {
        this.trackID = trackId;
    }

    public TrackingSearchViewModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
