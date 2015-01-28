<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 1/27/13
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:message code="chargeDeposit.depositNumber" var="depositNumber" />
<spring:message code="chargeDeposit.toDepositNumber" var="toDepositNumber" />
<spring:message code="chargeDeposit.description" var="description" />
<spring:message code="chargeDeposit.email" var="email" />
<spring:message code="chargeDeposit.amount" var="amount" />
<spring:message code="chargeDeposit.submit" var="submit" />
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="chargeDeposit.depositEmailDescription" var="depositEmailDescription" />
<spring:message code="confirm.continue" var="continue"/>
<spring:url value="/deposit/redirectToBank" var="redirectToBank_url"/>
<spring:message code="confirm.title" var="title"/>
<spring:message code="confirm.equals" var="equals"/>
<spring:message code="confirm.name" var="name"/>
<spring:message code="confirm.regard" var="regard"/>
<spring:message code="confirm.settle" var="settle"/>
<spring:message code="confirm.cancel" var="cancel"/>
<spring:message code="chargeDeposit.phoneNumber" var="phoneNumber" />
<spring:message code="chargeDeposit.rial" var="rial" />
<spring:url value="/deposit/charge" var="charge_url"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<script>
    $(document).ready(function () {
        function separate(input) {
            var nStr = input + '';
            nStr = nStr.replace(/\,/g, "");
            x = nStr.split('.');
            x1 = x[0];
            x2 = x.length > 1 ? '.' + x[1] : '';
            var rgx = /(\d+)(\d{3})/;
            while (rgx.test(x1)) {
                x1 = x1.replace(rgx, '$1' + ',' + '$2');
            }
            input=x1 + x2;
            return input;
        }
        $(".numbers").each(function () {
            $(this).html(separate($(this).text()));
        });
        $('#return').click(function () {
            window.location="${charge_url}";
        });
        $('#send').click(function () {

            $("#confirmDepositViewModel").submit();

        });

    });
</script>
<style type="text/css">
.info{text-align:right;  color:red;  }
.error{text-align:right;  color:red;  }
.value{text-align:right; padding:3px;}
tr{ width:300px !important; }
.title{color:red;}
</style>
<div  class="rich-panel " style="height:300px">
    <div  class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body " style="width:693px !important; ">


<form:form method="POST" commandName="confirmDepositViewModel" action="${redirectToBank_url}">

<div style="direction:rtl ; padding-right:50px; padding-top:20px; font-size:14px;font-family:tahoma,verdana;">
    <ul class="error">
        <c:forEach items="${messages}" var="message">
            <li dir="rtl">${message.text}</li>
        </c:forEach>
    </ul>
    <p> ${amount}&nbsp<span class="title numbers">${confirmDepositViewModel.amountDepositNumeric}&nbsp${rial}</span>&nbsp${equals}&nbsp<span class="title">${confirmDepositViewModel.amountDepositLetter}
        &nbsp${rial}</span>&nbsp${toDepositNumber}&nbsp<span class="title">${confirmDepositViewModel.numberDeposit}</span>
        &nbsp${name}&nbsp<span class="title"><c:forEach items="${confirmDepositViewModel.accountsHolderName}" var="account">
              ${account}
        </c:forEach>
        </span>&nbsp${regard}&nbsp<span class="title">&nbsp${confirmDepositViewModel.description}&nbsp&nbsp</span>${settle}
    </p>
    <table style="margin:auto;padding-top:20px;font-size:14px;font-family:tahoma,verdana;">
        <tr>
            <td >
                    ${amount}
            </td>
            <td >
                <span class="title numbers" >${confirmDepositViewModel.amountDepositNumeric}&nbsp${rial}</span>
                <form:hidden path="amountDepositNumeric"  />
            </td>
        </tr>
        <tr>
            <td >
                <span >${equals}</span>
            </td>
            <td >
                <span class="title">${confirmDepositViewModel.amountDepositLetter}&nbsp${rial} </span>

            </td></tr>
        <tr>
            <td  >
                    ${depositNumber}
            </td>
            <td >
                <span class="title" >${confirmDepositViewModel.numberDeposit}</span>
                <form:hidden path="numberDeposit"  />
                <form:hidden path="email"  />
                <form:hidden path="phoneNumber"  />
                <form:hidden path="amount"  />
            </td>
        </tr><tr>
        <td >
            <span >${name}</span>
        </td>
        <td >
            <c:forEach items="${confirmDepositViewModel.accountsHolderName}" var="account">
                <span class="title" >${account}</span>
            </c:forEach>
        </td>
    </tr><tr>
        <td >
            <span >${regard}</span>
        </td>
        <td >
            <span class="title" >${confirmDepositViewModel.description}</span>
        </td>
    </table>

</div>
    <div style="padding-right:250px; float:right; direction: ltr; padding-top:20px;">
        <form:button type="button"  id="return" class="negative" tabindex="2">
            <img alt="" src="${return_img}">
            ${return}</form:button>
        <form:button class="positive" id="send" tabindex="1">
            <img alt="" src="${tick}">
            ${submit}
        </form:button>
    </div>
</form:form>
</div>
</div>
