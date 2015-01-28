<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 2/13/13
  Time: 3:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:message code="loanInstalment.number" var="loanNumber"/>
<spring:message code="loanInstalment.customerInfo" var="customerInfo"/>

<spring:message code="loanInstallment.loanAmount" var="loanAmount"/>
<spring:message code="loanInstallment.installmentAmount" var="installmentAmount"/>
<spring:message code="loanInstallment.penaltyAmount" var="penaltyAmount"/>
<spring:message code="loanInstallment.installmentspayable" var="installmentspayable"/>
<spring:message code="loan.type" var="typeOfLoan"/>
<spring:message code="confirm.continue" var="continue"/>
<spring:message code="chargeDeposit.submit" var="submit"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="confirm.cancel" var="cancel"/>
<spring:url value="/loan/redirectToBank" var="loanInstalment_url"/>
<spring:message code="confirm.equals" var="equals"/>
<spring:message code="chargeDeposit.rial" var="rial"/>
<spring:url value="/loan/pay" var="loan_confirm_url"/>
<spring:message code="loanInstalment.title" var="title"/>
<spring:message code="loanInstallments.installmentspayableNumber" var="installmentspayableNumber"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>

<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location = "${loan_confirm_url}";
        });
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
            input = x1 + x2;
            return input;
        }

        $(".numbers").each(function () {
            $(this).html(separate($(this).text()));
        });

    });
</script>
<style>
    td {
        padding-top: 3px;
        font-size:11px !important;
    }

    .title {
        color: red;
    }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">

            <form:form method="POST" commandName="confirmLoanInstallmentViewModel" action="${loanInstalment_url}">
                <div style="direction:rtl ; padding-right:50px; padding-top:20px; font-size:14px;  font-family:tahoma,verdana;">
            <table >
                <tr>
                    <td>
                            ${loanNumber}
                        <form:hidden path="loanNumber"/>
                        <form:hidden path="email"/>
                        <form:hidden path="installmentspayableNumber"/>
                        <form:hidden path="installmentspayableAmount"/>
                        <form:hidden path="phoneNumber"/>

                    </td>
                    <td class="title">
                            ${confirmLoanInstallmentViewModel.loanNumber}
                    </td>
                </tr>
                <tr>
                    <td>
                        ${customerInfo}
                    </td>
                    <td class="title">
                            ${confirmLoanInstallmentViewModel.name}&nbsp;${confirmLoanInstallmentViewModel.lastName}
                    </td>
                </tr>
                <tr>
                    <td>
                            ${typeOfLoan}
                    </td>
                    <td class="title">
                            ${confirmLoanInstallmentViewModel.typeOfLoan}
                    </td>
                </tr>
                <tr>
                    <td>
                            ${loanAmount}
                    </td>
                    <td class="title">
                        <span class="numbers">${confirmLoanInstallmentViewModel.loanAmount}</span></td>
                </tr>
                <tr>
                    <td >
                            ${installmentspayableNumber}
                    </td>
                    <td class="title">
                            ${confirmLoanInstallmentViewModel.installmentspayableNumber}
                                    </td>
                </tr>
                <tr>
                    <td>
                            ${installmentAmount}
                    </td>
                    <td class="title">
                        <span class="numbers"> ${confirmLoanInstallmentViewModel.orginalAmount}</span></td>
                </tr>
                <tr>
                    <td>
                            ${penaltyAmount}
                    </td>
                    <td class="title">
                        <span class="numbers"> ${confirmLoanInstallmentViewModel.penaltyAmount}</span></td>
                </tr>
                <tr>
                    <td style=" width: 200px;">
                            ${installmentspayable}
                    </td>
                    <td class="title">
                        <span class="numbers"> ${confirmLoanInstallmentViewModel.installmentspayableAmount}</span>&nbsp ${equals}&nbsp
                            ${confirmLoanInstallmentViewModel.installmentspayableAmountLetter}&nbsp${rial}
                    </td>
                </tr>

            </table>
                    <div style="padding-right:250px;padding-top:50px;">
                        <form:button class="positive" id="send">
                            <img alt="" src="${tick}">
                            ${submit}
                        </form:button>
                        <form:button type="button"  id="return" class="negative">
                            <img alt="" src="${return_img}">
                            ${return}</form:button>

                    </div>
                </div>

            </form:form>

    </div>
</div>
