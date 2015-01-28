<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 3/16/13
  Time: 12:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="help.tracking.title" var="title"/>
<spring:message code="help.tracking.mainTitle" var="main"/>
<spring:message code="help.tracking.reqText1" var="req_text1"/>
<spring:message code="help.tracking.email" var="email"/>
<spring:message code="help.tracking.orderId" var="orderId"/>
<spring:message code="help.tracking.deccription1" var="description1"/>
<spring:message code="help.tracking.deccription2" var="description2"/>
<spring:message code="help.tracking.deccription3" var="description3"/>
<spring:url value="/resources/images/tracking1.png" var="img1"/>
<spring:url value="/resources/images/tracking2.png" var="img2"/>
<style type="text/css">
    .content{
        font-family: tahoma,verdana;
        font-size: 12px;
        margin-right: 10px;
        margin-top: 20px;
        text-align: right;
        width: 730px;
    }
    ul {
        direction: rtl;
    }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <div class="content">
            <span>${main}</span>
            <p>${req_text1}</p>

            <ul>
                <li type=square>${orderId}</li>
                <li type=square>${email} </li>

            </ul>
            <p>${description1}</p>
            <img src="${img1}"/>
            <p>${description2}</p>
            <p>${description3}</p>
            <img src="${img2}"/>

        </div>
    </div>
</div>