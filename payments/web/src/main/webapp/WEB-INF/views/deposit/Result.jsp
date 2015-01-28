<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 1/30/13
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:message code="chargeDeposit.toDepositNumber" var="toDepositNumber" />
<spring:message code="chargeDeposit.description" var="description" />
<spring:message code="chargeDeposit.email" var="email" />
<spring:message code="chargeDeposit.amount" var="amount" />
<spring:message code="chargeDeposit.submit" var="submit" />
<spring:message code="chargeDeposit.depositEmailDescription" var="depositEmailDescription" />
<spring:message code="confirm.continue" var="continue"/>
<spring:message code="result.title" var="title"/>
<spring:message code="confirm.equals" var="equals"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:message code="result.pdfCreate" var="pdf"/>
<spring:message code="confirm.name" var="name"/>
<spring:message code="loanInstallments.payfor" var="regard"/>
<spring:message code="result.settle" var="settle"/>
<spring:message code="chargeDeposit.rial" var="rial" />
<spring:message code="bank.referenceId" var="referenceIdMsg" />
<spring:message code="result.transactionId" var="transactionIdMsg" />
<spring:url value="/index" var="intro_url"/>
<spring:url value="/revenuereport" var="export_url"/>
<spring:url value="/resources/images/file_pdf.png" var="pdf_icon"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location="${intro_url}";
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
            input=x1 + x2;
            return input;
        }
        $(".number").each(function () {
            $(this).html(separate($(this).text()));
        });

    });
</script>

<style>
    .title{color:red;}
</style>
<div  class="rich-panel " style="width:693px !important; ">
    <div  class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
<div style="direction:rtl; padding: 10px 10px 0px 0px;font-size:12px;font-family:tahoma,verdana; ">
           <p>&nbsp
           ${amount}&nbsp
        <span class="number">  ${resultViewModel.amount}</span> ${rial} &nbsp ${equals} &nbsp ${resultViewModel.amountLetter}  &nbsp${rial}&nbsp

        <c:if test="${resultViewModel.typeOfResult=='DepositCharge'}">

                        ${toDepositNumber} &nbsp
                ${resultViewModel.depositNumber} &nbsp  ${settle}&nbsp
        </c:if>
        <c:if test="${resultViewModel.typeOfResult=='InstallmentPay'}">

                        ${regard}
              ${resultViewModel.installmentNumber}
            &nbsp ${settle} &nbsp
        </c:if>
        <c:if test="${referenceId != ''}">
            <br />
           ${referenceIdMsg} :&nbsp; ${referenceId}
        </c:if>
       </p>
    <p>${transactionIdMsg}:
        ${orderId} </p>
    <p class="warningMessage" >
            ${spam_email_text}

   </p>
</div>
    <div style="padding-left: 100px;padding-top: 10px; height: 80px;">
        <div style="float: right; padding-top: 20px; padding-right:10px;">
        <button type="button"  id="return" class="negative">
            <img alt="" src="${return_img}">
            ${return}</button></div>
        <div style="width: 100px;float:right;font-size:11px;">
     <a href="${export_url}">
         <table style="font-family:tahoma,verdana; ">
             <tr style="text-align:center; ">
                 <td> <img src="${pdf_icon}" /></td>
             </tr>
             <tr>  <td>  ${pdf}</td></tr>

         </table>
    </a>     </div>
    </div>
</div>
</div>