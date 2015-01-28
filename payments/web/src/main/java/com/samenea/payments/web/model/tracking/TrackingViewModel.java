package com.samenea.payments.web.model.tracking;

import com.ghasemkiani.util.icu.PersianDateFormat;
import com.ibm.icu.util.ULocale;
import com.samenea.commons.component.utils.persian.DateUtil;
import com.samenea.payments.order.Order;

/**
 * Date: 3/27/13
 * Time: 4:25 PM
 *
 * @Author:payam
 */
public class TrackingViewModel {
    private String status;
    private String createDate;
    private String lastUpDate;
    private String orderID;

    public TrackingViewModel(Order order) {
        PersianDateFormat dateFormat = new PersianDateFormat(
                "yyyy/MM/dd hh:mm:ss ", new ULocale("fa", "IR", ""));
        this.status = order.getStatus().toString();
        if (order.getCreateDate() != null) {
            this.createDate = DateUtil.toString(order.getCreateDate(), dateFormat);
        }
        if (order.getLastUpdateDate() != null) {
        this.lastUpDate = DateUtil.toString(order.getLastUpdateDate(), dateFormat);
        }
        this.orderID = order.getOrderId();
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLastUpDate() {
        return lastUpDate;
    }

    public void setLastUpDate(String lastUpDate) {
        this.lastUpDate = lastUpDate;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }


}
