<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<spring:url value="/resources/javascript/jquery-1.7.1.js" var="jquery_url"/>
<spring:url value="/resources/javascript/jquery-asciinumber.js" var="asciinumber_url"/>
<spring:url value="/resources/css/main.css" var="style"/>
<spring:message code="login.title" var="title"/>
<spring:message code="support" var="support"/>
<spring:message code="company" var="Company"/>
<spring:message code="samenea" var="samenea"/>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<meta http-equiv="Pragma" content="public">
<meta http-equiv="Cache-Control" content="public">
<META HTTP-EQUIV="Expires" CONTENT="0">
<html>
<head>

    <title>${title}</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${style}"/>

      <style type="text/css">
          a {
              text-decoration: none;
          }
      </style>
</head>
<body>
<script type="text/javascript" src="${jquery_url}"></script>
<script type="text/javascript">

    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-40006591-1']);
    _gaq.push(['_trackPageview']);

    (function() {
        var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
    })();

</script>
<table align="center" bgcolor="#ffffff" border="0" cellpadding="0" cellspacing="0">
    <tr valign="top">
        <td nowrap="nowrap" align="center">
            <tiles:insertAttribute name="header" ignore="true"/>
        </td>
    </tr>
    <tr>
        <td>
            <table style="width:100%;">
                <tr>
                    <td width="75%" style="vertical-align: top;">
                        <tiles:insertAttribute name="body" ignore="true"/>
                    </td>
                    <td width="25%" style="vertical-align: top; padding-left:5px;">
                        <tiles:insertAttribute name="menu" ignore="true"/>
                    </td>

                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td>
            <tiles:insertAttribute name="footer" ignore="true"/>
        </td>
    </tr>
    <tr valign="middle">
        <td style="padding-left:5px;font-size:11px;font-family:tahoma,verdana;text-align:center;">
             <span >${support}
                    <a href="http://www.samenea.com/">${Company}
                        ${samenea}
                    </a> </span>

        </td>
    </tr>
</table>
</body>

<script type="text/javascript" src="${asciinumber_url}"></script>

</html>