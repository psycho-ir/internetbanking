<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 2/3/13
  Time: 3:36 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="excption.Delivery" var="delivery"/>
<spring:message code="excption.transactionIdMessage" var="transactionIdMessage"/>
<spring:message code="excption.transactionId" var="idTitle"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<style>
    .errorblock {
        background-color: #FFEEEE;
        border: 1px solid #FF0000;
        color: #FF0000;
        direction: rtl;
        font-family: tahoma,verdana;
        font-size: 12px;
        margin: 16px;
        padding: 8px;
        text-align: right;
    }
</style>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location = "${intro_url}";
        });
    });
</script>
<div  class="rich-panel ">
    <div  class="rich-panel-header ">
        <span class="richTitleText"> </span>
    </div>
    <div class="rich-panel-body ">
    <div style="direction:rtl;padding-right:2px;font-family:tahoma,verdana;font-size:10px;" class='errorblock'>
        <p>${delivery}</p>
    </div>
    <div style="padding-left:250px;padding-top:10px">
        <button type="button"  id="return" class="negative">
            <img alt="" src="${return_img}">
            ${return}</button>
    </div>
</div>
</div>