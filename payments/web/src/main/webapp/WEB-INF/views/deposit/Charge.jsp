<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://www.samenea.com/jsp/tags2" prefix="captcha" %>
<%@ taglib uri="http://www.samenea.com/jsp/appfeature" prefix="af" %>
<%@ page contentType="text/html; charset=utf-8" %>
<spring:message code="chargeDeposit.depositNumber" var="depositNumber"/>
<spring:message code="chargeDeposit.description" var="description"/>
<spring:message code="chargeDeposit.email" var="email"/>
<spring:message code="chargeDeposit.amount" var="amount"/>
<spring:message code="chargeDeposit.title" var="title"/>
<spring:message code="chargeDeposit.submit" var="submit"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="chargeDeposit.phoneNumber" var="phoneNumber"/>
<spring:message code="chargeDeposit.depositEmailDescription" var="depositEmailDescription"/>
<spring:message code="Email.chargeDepositViewModel.email" var="emailError"/>
<spring:message code="sample.depositNumber" var="sample_depositNumber"/>
<spring:message code="sample.email" var="sample_email"/>
<spring:message code="sample.phoneNumber" var="sample_phoneNumber"/>
<spring:message code="sample.amount" var="sample_amount"/>
<spring:message code="required.depositNumber" var="requiredDeositNumber"/>
<spring:message code="minamount.min" var="min"/>
<spring:message code="NotEmpty.chargeDepositViewModel.depositNumber1" var="depsitNumberError"/>
<spring:message code="Size.chargeDepositViewModel.description" var="decriptionSize"/>
<spring:message code="NotEmpty.chargeDepositViewModel.phoneNumber" var="phoneNumberError"/>
<spring:message code="NotEmpty.chargeDepositViewModel.amount" var="amountError"/>
<spring:message code="isValid.constraints.AmountSize" var="amountsizeError"/>
<spring:message code="chargeDeposit.inValidNumber" var="inValidNumber"/>
<spring:message code="maxRang.chargeDepositViewModel.amount" var="amountmaxRang"/>
<spring:message code="isValid.constraints.deposit" var="depositNumberErrorMessage"/>
<spring:message code="NotEmpty.chargeDepositViewModel.description" var="descriptionError"/>
<spring:url value="/deposit/confirm" var="confirm_url"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="captcha.text" var="captchaTxt"/>
<spring:url value="/jcaptcha.jpg" var="captcha_url"/>
<spring:url value="/resources/images/reload-captcha.gif" var="reload_captcha"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<spring:url value="/resources/javascript/charge.js" var="charge_js_url"/>
<spring:url value="/resources/css/charge.css" var="charge_css_url"/>
<link rel="stylesheet" type="text/css" media="screen" href="${charge_css_url}"/>
<script type="text/javascript">
$(document).ready(function () {

    separateAmount('1',${maxAmount});
    separateAmount('2',${minAmount});

    $('#btnReload').click(function () {
        $("#img_captcha").attr('src', '${captcha_url}' + '?' + (new Date()).getTime());
    });
    function isBlank(pString) {
        if (!pString || pString.length == 0) {
            return true;
        }
        return !/[^\s]+/.test(pString);
    }



    function separate() {
        var input = $('#amount');
        var nStr = input.val() + '';
        nStr = nStr.replace(/\,/g, "");
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        input.val(x1 + x2);
    }
    function IsNumeric(value)
    {  for (i = 0 ; i < value.length ; i++) {
        if ((value.charAt(i) < '0') || (value.charAt(i) > '9')) return false
    }
        return true;
    }
    function checkAmount() {
        $('#msgamount').html("");

        var isAmount = true;
        var amount = $('#amount');
        if (!isBlank(amount.val())) {

            separate();


            var cleanString = amount.val().replace(/,/gi, '');
            if(!IsNumeric(cleanString)){
                $('#msgamount').show();
                $(amount).addClass("highlight");
                $('#msgamount').html('${inValidNumber}');
                return false;
            }

            if (cleanString < ${minAmount}) {
                $('#msgamount').show();
                $(amount).addClass("highlight");
                $('#msgamount').html('${minAmountText}');
                return false;
            } else {
                if (cleanString > (${maxAmount}+1)) {
                    $('#msgamount').show();
                    $(amount).addClass("highlight");
                    $('#msgamount').html('${maxAmountText}');
                    return false;
                }
            }
        } else {
            $('#msgamount').show();
            $('#msgamount').html('${amountError}');
            $(amount).addClass("highlight");
            return false;
        }
        $('#msgamount').hide();
        $(amount).removeClass("highlight");
        return true;

    }

    function checkDescription() {
        var isDescription = true;
        var description = $('#description');
        if (isBlank(description.val()))
        {
            $(description).addClass("highlight");
            $('#msgdescription').show();
            $('#msgdescriptionSize').hide();
            isDescription = false;
        } else {
            if (!(description.val().length >= ${minLengthDescription} && description.val().length <= ${maxLengthDescription}))
            {
                $(description).addClass("highlight");
                $('#msgdescriptionSize').show();
                $('#msgdescription').hide();
                isDescription = false;
            }
            else
            {
                $(description).removeClass("highlight");
                $('#msgdescription').hide();
                $('#msgdescriptionSize').hide();
            }

        }
        return isDescription;
    }

    function separateAmount(id,value) {
        var nStr = value + '';
        nStr = nStr.replace(/\,/g, "");
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        var result=x1 + x2;
        if(id="1"){
            $('#maxAmountText').html($('#maxAmountText').text().replace(/${maxAmount}/g, result));
        }else if(id="2"){
            $('#minAmountText').html($('#minAmountText').text().replace(/${minAmount}/g, result));
        }
    }
    $('#return').click(function () {
        window.location = "${intro_url}";
    });
    $('#amount').bind("change", checkAmount);
    $('#amount').bind("keyup", separate);
    $('#description').bind("change", checkDescription);

});

</script>
<script type="text/javascript" src="${charge_js_url}"></script>

<style type="text/css">
    .info{text-align:right;  color:red;  }
    .error{text-align:right;  color:red;  }
    .value{text-align:right; padding:3px;}
    tr{ width:300px !important; }
    .title{color:red;}
</style>

<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <ul class="error">
            <c:forEach items="${messages}" var="message">
                <li dir="rtl">${message.text}</li>
            </c:forEach>
        </ul>

        <form:form method="POST" commandName="chargeDepositViewModel" action="${confirm_url}">
            <form:errors path="*" cssClass="errorblock" element="div"/>
            <c:if test="${depositNumberError=='true'}">
                <div class="errorblock">${depositNumberErrorMessage}</div>
            </c:if>
            <c:if test="${amountSizeError=='true'}">
                <div class="errorblock">${amountsizeError}</div>
            </c:if>
            <c:if test="${descriptionSizeError=='true'}">
                <div class="errorblock">${decriptionSize}</div>
            </c:if>
            <c:if test="${not empty message_time_error}">
                <div class="errorblock">${message_time_error}</div>
            </c:if>
            <div>
                <table>

                    <tr>
                        <td>
                            <label class="label">${depositNumber} :</label>
                        </td>
                        <td>
                            <form:input id="depositNumber2" path="depositNumber2" cssClass="numeric part required"
                                        tabindex="2"/>
                            .
                            <form:input path="depositNumber1" id="depositNumber1" cssClass="numeric required"
                                        tabindex="1"/>


                        </td>
                        <td id="msgdeposit" style="display:none; " class="error">${depsitNumberError}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample">
                                ${sample_depositNumber}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label>${amount}:</label>
                        </td>
                        <td>
                            <form:input path="amount" cssClass=" required ltrBox" tabindex="3"/>
                        </td>
                        <td id="msgamount" style="display:none; " class="error"></td>

                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample">
                                ${sample_amount}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample" id="maxAmountText">
                                ${maxAmountText}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample" id="minAmountText">
                                ${minAmountText}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label>${description}:</label>
                        </td>
                        <td>
                            <form:textarea path="description" cssClass="txtDes required" tabindex="4"
                                           cssStyle="width: 227px; resize:none; "/>
                        </td>
                        <td id="msgdescription" style="display:none; " class="error">${descriptionError}</td>
                        <td id="msgdescriptionSize" style="display:none; " class="error">${decriptionSize}</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample">
                                ${maxDescriptionText}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td colspan="3" class="sample">
                                ${minDescriptionText}
                        </td>
                        <td></td>
                    </tr>
                    <tr>
                        <td class="label">
                            <label>${phoneNumber}:</label>
                        </td>
                        <td>
                            <form:input path="phoneNumber" cssClass="required ltrBox numeric" tabindex="5"/>
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

                    <tr>
                        <td class="label">
                            <label>${email}:</label>
                        </td>
                        <td>
                            <form:input path="email" cssClass="required ltrBox" tabindex="6"/>
                        </td>
                        <td id="msgemail" style="display:none; " class="error">${emailError}</td>
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
            <div style="padding-right:250px; direction: ltr; ">
                <form:button type="button"  id="return" class="negative" tabindex="9">
                    <img alt="" src="${return_img}">
                ${return}</form:button>
                <form:button class="positive" id="send" tabindex="8">
                    <img alt="" src="${tick}">
                ${submit}
                </form:button>
            </div>
        </form:form>


    </div>
</div>
