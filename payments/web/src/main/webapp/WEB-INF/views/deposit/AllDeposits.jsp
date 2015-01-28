<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:message code="deposit.depositNumber" var="depositNumber"/>
<spring:message code="deposit.description" var="description"/>
<spring:message code="deposit.remainedAmount" var="remainedAmount"/>
<spring:message code="deposit.activity" var="activity"/>
<spring:message code="deposit.charge" var="charge"/>
<spring:url  value="/deposit/charge/" var="charge_url"/>
<script type="text/javascript">
    $(document).ready(function () {
        jQuery(".button").live("click", function(event) {
            window.location='${charge_url}'+'11111';
       });
        $('.dataTable .depNum').each(function() {
            $(this).html(this.html().toPersianDigit());
        });


    });

</script>
<div>
    <table class="dataTable">
        <thead>
          <tr>
            <td>${depositNumber}</td>
            <td>${description}</td>
            <td>${remainedAmount}</td>
            <td>${activity}</td>
          </tr>
        </thead>
        <c:forEach items="${deposits}" var="deposit">
            <tr>
               <td class="depNum" >${deposit.depositNumber}</td>
               <td >${deposit.description}</td>
               <td class="remNum">${deposit.remainedAmount}</td>
               <td><input class="button" type="button" id="btncharge" value="${charge}"></td>
            </tr>
        </c:forEach>
        </table>
</div>