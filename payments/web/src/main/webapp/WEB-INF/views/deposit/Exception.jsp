
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="excption.genericException" var="message"/>
<spring:url value="/index" var="intro_url"/>
<spring:message code="chargeDeposit.return" var="return"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:message code="technical.error.message" var="contactus_message"/>
<spring:url value="/resources/images/return.png" var="return_img"/>
<spring:message code="menu.contactUS.technical.tel" var="tel"/>
<script>
    $(document).ready(function () {
        $('#return').click(function () {
            window.location="${intro_url}";
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
<div  class="rich-panel ">
    <div  class="rich-panel-header ">
        <span class="richTitleText"> ${message}</span>
        <ul class="error">
            <c:forEach items="${messages}" var="message">
                <li dir="rtl">${message.text}</li>
            </c:forEach>
        </ul>
    </div>
    <div class="rich-panel-body ">
<div >
    <div class="rich-panel-body ">
        <div id="content" style="font-family: tahoma,verdana;font-size:11px;direction:rtl;padding-top:20px;">${contactus_message}&nbsp
        </div>


    </div>
</div>
    <center>
        <button type="button"  id="return" class="negative">
            <img alt="" src="${return_img}">
            ${return}</button>
    </center>
 </div>
 </div>
