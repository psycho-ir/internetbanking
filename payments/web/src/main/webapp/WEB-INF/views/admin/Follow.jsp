<%--
  Created by IntelliJ IDEA.
  User: ngs
  Date: 2/19/13
  Time: 11:57 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<spring:message code="trackViewModel.Id" var="id"/>
<spring:message code="trackViewModel.SubSystem" var="subSystem"/>
<spring:message code="trackViewModel.Description" var="description"/>
<spring:message code="trackViewModel.OccurrenceDate" var="occurrenceDate"/>
<spring:url value="tracking" var="tracking_search_url"/>
<spring:message code="trackingSearchViewModel.title" var="title"/>
<spring:url value="/resources/images/ui-bg_td.png" var="bg_td"/>
<spring:message code="chargeDeposit.submit" var="submit"/>
<spring:url value="/resources/images/tick.png" var="tick"/>
<spring:url value="/admin/logout" var="logout_Url"/>
<spring:message code="admin.logout" var="logout"/>

<style>
    .rich-panel-body{
        text-align:right;
        font-family: tahoma,verdana;
        font-size: 12px;
    }
    #list {
        border-collapse: collapse;
        font-family: tahoma,verdana;
        font-size: 12px;
        margin: auto;
    }

    #list td {
        color: #2E6E9E;
        background: url("${bg_td}") repeat-x scroll 50% 50% #DFEFFC;
        border: solid 1px #C5DBEC;
        width: 100px;
    }
    #list th {
        color: #2E6E9E;
        background: url("${bg_td}") repeat-x scroll 50% 50% #DFEFFC;
        border: solid 1px #C5DBEC;
        width: 10px;
    }
    .rich-panel-header > a {
        color: red;
        float: left;
    }
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
        <a href='<c:url value="/j_spring_security_logout"/>'>${logout}</a>
    </div>
    <div class="rich-panel-body " style="width:500px; ">
        <form:form method="POST" commandName="followSearchViewModel" action="${follow_search_url}">
            <div style="padding-top:5px; ">
            <form:button class="positive" id="send" tabindex="8">
                ${submit}
                <img alt="" src="${tick}">
            </form:button>
            <form:input path="followId" id="followId"/>
             </div>


            <div style=" padding-bottom: 10px;padding-top: 10px;padding-left:5px;   ">
                <table id="list">
                    <tr>
                    <th>${id}</th>
                    <th>${subSystem}</th>
                    <th>${description}</th>
                    <th>${occurrenceDate}</th>
                </tr>
                <c:forEach var="followViewModel" items="${followViewModel}">
                    <tr>
                        <td><span>${followViewModel.followId}</span></td>
                        <td><span>${followViewModel.subSystem}</span></td>
                        <td><span>${followViewModel.description}</span></td>
                        <td><span>${followViewModel.occurrenceDate}</span></td>
                    </tr>
                </c:forEach>
            </table>
                </div>
        </form:form>
    </div>
</div>