<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 2/12/13
  Time: 9:01 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.samenea.com/jsp/tags2" prefix="captcha" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=utf-8" %>
<spring:message code="loanInstalment.number" var="loanNumber"/>
<spring:message code="loanInstalment.submit" var="submit"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="required.loanNumber" var="requiredLoanNumber"/>
<spring:message code="loanInstalment.title" var="title"/>
<spring:message code="loanInstalment.email" var="email"/>
<spring:message code="NotEmpty.chargeDepositViewModel.email" var="emailError"/>
<spring:url value="/loan/confirm" var="loanInstalment_url"/>
<spring:message code="sample.email" var="sample_email"/>
<spring:message code="required.loanNumber" var="loanNumberError"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="captcha.text" var="captchaTxt"/>
<spring:url value="/jcaptcha.jpg" var="captcha_url"/>
<spring:url value="/resources/images/reload-captcha.gif" var="reload_captcha"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<spring:message code="chargeDeposit.phoneNumber" var="phoneNumber"/>
<spring:message code="NotEmpty.chargeDepositViewModel.phoneNumber" var="phoneNumberError"/>
<spring:message code="sample.phoneNumber" var="sample_phoneNumber"/>
<spring:message code="loan.help.info" var="help_info" />
<spring:url value="/resources/javascript/loan.js" var="loan_js_url"/>
<spring:url value="/resources/css/loan.css" var="loan_css_url"/>
<script type="text/javascript" src="${loan_js_url}"></script>
<link rel="stylesheet" type="text/css" media="screen" href="${loan_css_url}"/>
<script  type="text/javascript">
    $(document).ready(function () {
        $('#btnReload').click(function(){
            $("#img_captcha").attr('src','${captcha_url}' + '?' + (new Date()).getTime());
        }); });
</script>
<div  class="rich-panel ">
    <div  class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
<%--         <p style="font-family:tahoma,verdana; font-size:12px; direction:rtl" class="info_help">
             ${help_info}
         </p>--%>
    <form:form method="POST" commandName="loanInstalmentViewModel" action="${loanInstalment_url}">
        ${simia}

            <form:errors path="*" cssClass="errorblock" element="div"/>


        <c:if test="${not empty message_time_error}">
            <div class="errorblock">${message_time_error}</div>
        </c:if>


        <div style="direction:rtl; ">
            <table>

                <tr>
                    <td class="label">
                        <label>${loanNumber} :</label>
                    </td>
                    <td>
                        <form:input path="number" cssClass="numericBox part required" tabindex="1"/>
                    </td>
                    <td id="msgnumber" style="display: none" class="error">${loanNumberError}</td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3" class="sample">
                            ${sample_loanNumber}
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">
                        <label>${email}:</label>
                    </td>
                    <td>
                        <form:input path="email" cssClass="required ltrBox" tabindex="2"/>
                    </td>
                    <td id="msgemail" style="display: none" class="error">${emailError}</td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3" class="sample">
                            ${sample_email}
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td class="label">
                        <label>${phoneNumber}:</label>
                    </td>
                    <td>
                        <form:input path="phoneNumber" cssClass="required ltrBox numeric" tabindex="3"/>
                    </td>
                    <td id="msgphoneNumber" style="display:none; " class="error">${phoneNumberError}</td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3" class="sample">
                            ${sample_phoneNumber}
                    </td>
                    <td></td>
                </tr>
                <captcha:IfCaptchaShouldBeDisplayed>
                <tr>
                    <td class="label" style="padding-top:40px; ">
                        <img src='${reload_captcha}' id="btnReload"/>
                    </td>
                    <td colspan="2">
                        <img src='${captcha_url}' id="img_captcha"/>
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td></td>
                    <td colspan="3" class="sample">${captchaTxt}:</td>
                    <td></td>
                </tr>
                <tr><td></td>
                    <td colspan="3"><form:input  path="jCaptchaResponse" cssClass="required ltrBox" tabindex="4"/></td>
                    <td></td>
                </tr>
                </captcha:IfCaptchaShouldBeDisplayed>

            </table>
        </div>
        <div style="text-align:center;  ">
            <form:button class="positive" id="send" tabindex="5">
                ${submit}
                <img alt="" src="${tick}">
            </form:button>
            <form:button type="button"  id="return" class="negative" tabindex="6">

                ${return}<img alt="" src="${return_img}"></form:button>

        </div>
    </form:form>
    </div>
     </div>

