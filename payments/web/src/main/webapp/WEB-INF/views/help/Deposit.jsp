<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 3/16/13
  Time: 12:20 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="help.deposit.title" var="title"/>
<spring:message code="help.deposit.mainTitle" var="main"/>
<spring:message code="help.deposit.reqText1" var="req_text1"/>
<spring:message code="help.deposit.reqText2" var="req_text2"/>
<spring:message code="help.bankCardNumber" var="bankCardNumber"/>
<spring:message code="help.pass2" var="pass2"/>
<spring:message code="help.cvv2" var="cvv2"/>
<spring:message code="help.expirationDate" var="expiration"/>
<spring:message code="help.deposit.number" var="depositNumber"/>
<spring:message code="help.deposit.depcrption1" var="description1"/>
<spring:message code="help.deposit.depcrption2" var="description2"/>
<spring:message code="help.deposit.depcrption3" var="description3"/>
<spring:message code="help.deposit.confirm.description2" var="confirm_description2"/>
<spring:message code="help.deposit.confirm.description1" var="confirm_description1"/>
<spring:message code="help.deposit.selectBank.description1" var="bankSelection_description1"/>
<spring:message code="help.deposit.selectBank.description2" var="bankSelection_description2"/>
<spring:message code="help.deposit.selectBank.description3" var="bankSelection_description3"/>
<spring:message code="help.deposit.selectBank.description4" var="bankSelection_description4"/>
<spring:message code="help.deposit.result.description" var="result_description"/>
<spring:url value="/resources/images/dep1.png" var="img1"/>
<spring:url value="/resources/images/dep2.png" var="img2"/>
<spring:url value="/resources/images/3.png" var="img3"/>
<spring:url value="/resources/images/dep4.png" var="img4"/>
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
            <p>${req_text2}</p>
            <ul>
                <li type=square>${bankCardNumber}</li>
                <li type=square>${pass2} </li>
                <li type=square> ${cvv2}</li>
                <li type=square>${expiration} </li>
                <li type=square>${depositNumber} </li>
            </ul>
            <p>${description1}</p>
            <img src="${img1}"/>
            <p>${description2}</p>
            <p>${description3}</p>
            <img src="${img2}"/>
            <p>${confirm_description1}</p>
            <p>${confirm_description2}</p>
            <p>${bankSelection_description1}&nbsp${bankSelection_description2}&nbsp ${bankSelection_description3}&nbsp ${bankSelection_description4}</p>
            <img src="${img3}"/>
            <img src="${img4}"/>
             <p style="color:red">${result_description}</p>
        </div>
    </div>
</div>