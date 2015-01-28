package com.samenea.payments.web.model.follow;

/**
 * Created with IntelliJ IDEA.
 * User: ngs
 * Date: 2/19/13
 * Time: 3:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class FollowSearchViewModel {

    private String followId;

    public FollowSearchViewModel(String followId, String email) {
        this.followId = followId;
        this.email = email;
    }

    private String email;
    public String getfollowId() {
        return followId;
    }

    public void setfollowId(String followId) {
        this.followId = followId;
    }

    public FollowSearchViewModel() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
