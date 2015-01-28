<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 2/19/13
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.samenea.com/jsp/appfeature" prefix="af" %>
<spring:url value="/index" var="intro_url"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="pageNotFound.message" var="message"/>
<spring:url value="/resources/images/PageNotFound.png" var="PageNotFound_image"/>
<spring:url value="/deposit/charge" var="charge_url"/>
<spring:url value="/loan/pay" var="loan_url"/>
<spring:message var="titel_loan" code="loan.title"/>
<spring:message var="titel_deposit" code="chargeDeposit.title"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return"/>
<style>
    ul {
        direction: rtl;
        float: right;
        padding-right: 100px;
        padding-top: 20px;
    }
</style>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location = "${intro_url}";
        });
    });
</script>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> </span>
    </div>
    <div class="rich-panel-body ">
        <div style="direction:rtl; padding: 50px 50px 0px 0px;font-size:12px  ">
            <ul>
                <af:IsAppFeatureEnabled feature="charge">
                    <li><a href="${charge_url}">${titel_deposit}</a></li>
                </af:IsAppFeatureEnabled>
                <af:IsAppFeatureEnabled feature="loan">
                    <li><a href="${loan_url}">${titel_loan}</a></li>
                </af:IsAppFeatureEnabled>
            </ul>
            <div style="padding: 50px 50px 50px 50px">
                <img src="${PageNotFound_image}">
            </div>
        </div>
    </div>
</div>