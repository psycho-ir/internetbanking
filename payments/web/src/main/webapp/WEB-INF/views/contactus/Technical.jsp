<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 3/27/13
  Time: 9:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="menu.contactUS.technical.title" var="title"/>
<spring:message code="menu.contactUS.technical.text" var="text"/>
<spring:message code="menu.contactUS.technical.tel" var="tel"/>
<style>
   #content{
       font-family: tahoma,verdana;
       font-size: 12px;
       padding: 10px 10px 10px 0;
       text-align: right;
   }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <div id="content">${text}&nbsp${tel}</div>

    </div>
</div>