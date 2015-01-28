<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 3/27/13
  Time: 5:30 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:message code="trackingViewModel.orderID" var="orderID"/>
<spring:message code="trackingViewModel.createDate" var="createDate"/>
<spring:message code="trackingViewModel.lastUpDate" var="lastUpDate"/>
<spring:message code="trackingViewModel.staus" var="status"/>
<spring:message code="trackingSearchViewModel.title" var="title"/>
<spring:url value="/resources/images/ui-bg_td.png" var="bg_td"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>

<style type="text/css">
    #list {
        border-collapse: collapse;
        font-family: tahoma,verdana;
        font-size: 12px;
        margin: auto;
    }

    #list td {
        color: #2E6E9E;
        background: url("${bg_td}") repeat-x scroll 50% 50% #DFEFFC;
        border: solid 1px #C5DBEC;
        width: 150px;
    }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <div style="direction:rtl; padding-bottom: 10px;padding-top: 10px;  ">
            <table id="list">
                <tr>
                    <td>${orderID}</td>
                    <td>${createDate}</td>
                    <td>${lastUpDate}</td>
                    <td>
                        ${status}
                    </td>
                </tr>
                <tr>
                    <td>${trackingViewModel.orderID}</td>
                    <td style="direction:ltr; ">${trackingViewModel.createDate}</td>
                    <td style="direction:ltr;">${trackingViewModel.lastUpDate}</td>
                    <td><spring:message code="trackingViewModel.${trackingViewModel.status}"></spring:message>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>