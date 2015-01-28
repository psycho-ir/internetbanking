<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 1/15/13
  Time: 11:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.samenea.com/jsp/tags" prefix="samen" %>
<spring:message code="login.title" var="title"/>
<spring:message code="login.CIF" var="CIF"/>
<spring:message code="login.username" var="username"/>
<spring:message code="login.password" var="password"/>
<spring:message code="login.blankusernameMessage" var="blankusernameMessage"/>
<spring:message code="login.blankpasswordMessage" var="blankpasswordMessage"/>
<spring:message code="login.incorrect" var="incorrect"/>
<spring:message code="login.login" var="login"/>
<spring:message code="login.enter" var="enter"/>
<spring:url value="/login" var="login_url"/>
<spring:url value="resources/javascript/jquery-1.7.1.js" var="jquery_url"/>
<spring:url value="resources/css/login.css" var="style"/>
<spring:url value="resources/javascript/jquery-captcha.js" var="jQueryCaptchaUrl"/>
<spring:url value="resources/javascript/jquery-captcha-style.css" var="jQueryCaptchaCssUrl"/>
<spring:url value="/captcha/image" var="captchaImage"/>
<spring:url value="/resources/images/Logo.png" var="logo"/>
<link rel="stylesheet" type="text/css" media="screen" href="${style}"/>
    <style>
        .errorblock {
            color: #ff0000;
            background-color: #ffEEEE;
            border: 1px solid #ff0000;
            padding: 8px;
            margin: 16px;
            font-family: tahoma;
            font-size: 10px;
            text-align: right;
        }
    </style>

<div id="container">
    <form name='f' action="<c:url value='j_spring_security_check' />"
          method='POST'>
        <h1><img src="${logo}"/> <span>${title}</span></h1>
        <div class="form">
            <table>
                <tr>
                    <td>
                        <input type='text' name='j_cif'/>
                    </td>
                    <td> ${CIF}
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type='text' name='j_username' value=''>
                    </td>
                    <td>
                        ${username}
                    </td>
                </tr>
                <tr>
                    <td>
                        <input type='password' name='j_password'/></td>
                    <td>
                        ${password} </td>
                </tr>
                <tr>
                    <td><c:if test="${not empty error}">
                        <div class="errorblock">

                                ${incorrect}
                        </div>
                    </c:if></td>
                </tr>
                <tr>
                    <td><input name="submit" type="submit" value="${enter}"/></td>
                </tr>

            </table>
        </div>

    </form>
</div>

