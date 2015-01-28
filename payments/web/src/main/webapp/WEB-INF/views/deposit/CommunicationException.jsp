<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 2/12/13
  Time: 6:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value=" /displayOrder/" var="confirm_url"/>
<spring:message code="excption.genericException" var="message"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location="${intro_url}";
        });
    });
</script>
<div  class="rich-panel ">
    <div  class="rich-panel-header ">
        <span class="richTitleText"></span>
    </div>
    <div class="rich-panel-body ">
<div style="direction:rtl;padding:50px 50px 0px 0px;  ">
    <p>${message}</p>
    <a href="${confirm_url}${orderId}"></a>
</div>
        <button type="button"  id="return" class="negative">
            <img alt="" src="${return_img}">
            ${return}</button>

    </div>
</div>