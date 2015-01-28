
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html; charset=utf-8" %>
<spring:url value="/resources/javascript/jquery-1.7.1.js" var="jquery_url"/>
<spring:url value="/resources/images/Logo.png" var="header"/>
<spring:url value="/resources/images/bg.png" var="bg"/>
<spring:message code="login.title" var="title"/>
<spring:url value="/resources/css/main.css" var="style"/>
﻿<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${title}</title>
    <script type="text/javascript" src="${jquery_url}"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="${style}"/>

</head>
<body>

<div class="main">
    <tiles:insertAttribute name="header" ignore="true"/>

    <tiles:insertAttribute name="body" ignore="true"/>

    <tiles:insertAttribute name="footer" ignore="true"/>
</div>
</body>
</html>