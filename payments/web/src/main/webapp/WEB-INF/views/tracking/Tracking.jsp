<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 3/27/13
  Time: 3:21 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://www.samenea.com/jsp/tags2" prefix="captcha" %>
<spring:url value="tracking" var="tracking_search_url"/>
<spring:message code="trackingViewModel.orderID" var="orderID"/>
<spring:message code="sample.email" var="sample_email"/>
<spring:message code="loanInstalment.email" var="email"/>
<spring:message code="chargeDeposit.submit" var="submit"/>
<spring:message code="trackingSearchViewModel.errorMessage" var="errorMessage"/>
<spring:message code="trackingSearchViewModel.title" var="title"/>
<spring:message code="NotEmpty.chargeDepositViewModel.email" var="emailError"/>
<spring:message code="required.trackIDError" var="trackIDError"/>
<spring:message code="captcha.text" var="captchaTxt"/>
<spring:url value="/jcaptcha.jpg" var="captcha_url"/>
<spring:url value="/resources/images/reload-captcha.gif" var="reload_captcha"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location = "${intro_url}";
        });
    });
</script>
<style>
    label {
        font-size: 11px !important;
        direction: rtl;
        font-family: tahoma, verdana;
        text-align: right;
    }

    #trackingSearchViewModel > div {
        padding-bottom: 10px;
        padding-right: 40px;
        padding-top: 10px;
        font-size: 12px;
    }

    .errorblock {
        color: #ff0000;
        background-color: #ffEEEE;
        border: 1px solid #ff0000;
        padding: 8px;
        margin: 16px;
        font-family: tahoma;
        font-size: 10px;
        text-align: right;
        direction: rtl;
    }

    .highlight {
        background: none repeat scroll 0 0;
        border-color: red !important;
        border-style: solid;
        border-width: 1px;
    }

    .error {
        color: red;
        font-family: tahoma, verdana;
        font-size: 11px;
    }

    .sample {
        color: #0099A1;
        font-size: 9px !important;
    }

    .label {
        float: left;
    }

    #btnReload {
        cursor: pointer;
    }
</style>
<script type="text/javascript">
    $(document).ready(function () {
        $('#email').bind("change", checkEmail);
        $('#trackID').bind("change", checkTrackID);
        $('#trackingSearchViewModel').submit(function () {
            var trackMsg = checkTrackID();
            var emailMsg = checkEmail();
            return(trackMsg && emailMsg);
        });
        $('#btnReload').click(function () {
            $("#img_captcha").attr('src', '${captcha_url}' + '?' + (new Date()).getTime());
        });
        function isBlank(pString) {
            if (!pString || pString.length == 0) {
                return true;
            }
            return !/[^\s]+/.test(pString);
        }

        function checkTrackID() {
            var isTrack = true;
            var track = $('#trackID');
            if (isBlank(track.val())) {
                $(track).addClass("highlight");
                $("#msgtrack").show();
                isTrack = false;
            } else {
                $("#msgtrack").hide();
                $(track).removeClass("highlight");
            }
            return isTrack;
        }

        function checkEmail() {
            var isEmail = true;
            var email = $('#email');
            var pattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
            if (pattern.test(email.val())) {
                $(email).removeClass("highlight");
                $('#msgemail').hide();
            } else {
                $(email).addClass("highlight");
                $('#msgemail').show();
                isEmail = false;
            }
            return isEmail;
        }

        $('#return').click(function () {
            window.location = "${samen_url}";
        });

    });
</script>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <c:if test="${hasError=='true'}">
            <div class="errorblock">${errorMessage}</div>
        </c:if>

        <form:form method="POST" commandName="trackingSearchViewModel" action="${tracking_search_url}">
            <form:errors path="*" cssClass="errorblock" element="div"/>
            <div style="direction: rtl;">
                <table style="direction:rtl;font-size:12px; font-family:tahoma,verdana;">
                    <tr>
                        <td class="label">
                            <label>
                                    ${orderID}:
                            </label>
                        </td>
                        <td>
                            <form:input path="trackID" id="trackID" tabindex="1"/></td>
                        <td id="msgtrack" style="display: none" class="error">${trackIDError}</td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label>
                                    ${email}:
                            </label>
                        </td>
                        <td><form:input path="email" id="email" tabindex="2"/></td>
                        <td id="msgemail" style="display: none" class="error">${emailError}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample">
                                ${sample_email}
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
                        <tr>
                            <td></td>
                            <td colspan="3"><form:input path="jCaptchaResponse" cssClass="required ltrBox"
                                                        tabindex="7"/></td>
                            <td></td>
                        </tr>
                    </captcha:IfCaptchaShouldBeDisplayed>

                </table>
            </div>
            <div style=" text-align:center;  ">
                <form:button type="button" id="return" class="negative" tabindex="4">
                    <img alt="" src="${return_img}">
                    ${return}</form:button>
                <form:button class="positive" id="send" tabindex="3">
                    <img alt="" src="${tick}">
                    ${submit}
                </form:button>


            </div>
        </form:form>
    </div>
</div>