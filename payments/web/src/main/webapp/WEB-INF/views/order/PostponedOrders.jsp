<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<spring:message code="postponed.orderID" var="orderID"/>
<spring:message code="postponed.totalPrice" var="totalPrice"/>
<spring:message code="postponed.status" var="staus"/>
<spring:message code="postponed.opertion" var="opration"/>
<spring:message code="postponed.resolve" var="resolve"/>
<spring:message code="postponed.notFound" var="notFound"/>
<spring:message code="postponed.title" var="title"/>
<spring:url value="/resources/images/success.png" var="success_img"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<script type="text/javascript">
    $(document).ready(function() {
        $("#msgSuccess").addClass("successbox");setTimeout(function(){
        $("#msgSuccess").fadeOut("slow")}, 4000);
    });
</script>
<style>
    #list td {
        border: 1px solid red;
        width: 100px;
    }
    .successbox {
        background-color: #EDFCED;
        background-image: url("${success_img}");
        color: #4F8A10;
        background-position: 10px center;
        background-repeat: no-repeat;
        border: 2px solid;
        margin: 10px 0;
        padding: 15px 10px 15px 70px;
        width: 600px;
    }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <div style="direction:rtl; padding-right: 40px;padding-top: 50px; font-family: tahoma,verdana; font-size:10px; ">
          <div style="padding-bottom:20px;">
              <c:if test="${not empty postponedMessage}">
                  <span id="msgSuccess">${postponedMessage}</span>
              </c:if>
          </div>
            <table id="list">
                <tr>
                    <td>${orderID}</td>
                    <td>${totalPrice}</td>
                    <td>${staus}</td>
                    <td>${opration}</td>
                </tr>
                <c:forEach items="${orders}" var="order">
                    <tr>
                        <td>${order.orderId}</td>
                        <td>${order.totalPrice}</td>
                        <td>${order.status}</td>
                        <td style="width: 200px; padding:4px 3px 3px; "><a class="submit"
                                                                           href="<spring:url value="/order/resolve/${order.orderId}"></spring:url>">${resolve}</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>
            <c:if test="${ empty orders}">
            <div id="div_empty" style="padding-top:20px; padding-bottom:20px; ">
                <span>${notFound}</span>
                </div>
            </c:if>
        </div>

    </div>
</div>