<%--
  Created by IntelliJ IDEA.
  User: payam
  Date: 4/7/13
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="sea" uri="http://www.samenea.com/jsp/appfeature" %>
<%@ taglib uri="http://www.samenea.com/jsp/appfeature" prefix="af" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<spring:url value="http://www.samen.ir" var="samen_url"/>
<spring:message var="title" code="intro.title"/>
<spring:message var="wellcom_txt" code="intro.description"/>
<spring:message var="main_part1_txt" code="intro.mainText"/>
<spring:message var="main_txt_deposit" code="intro.mainTextDeposit"/>
<spring:message var="main_txt_loan" code="intro.mainTextLoan"/>
<spring:message var="link_to_deposit" code="intro.linkToChargeDeposit"/>
<spring:message var="link_to_loan" code="intro.linkToPayLoan"/>
<spring:message var="main_txt_deposit_ok" code="intro.mainDposit_txt2"/>
<spring:url value="/deposit/charge" var="charge_url"/>
<spring:url value="/loan/pay" var="loan_url"/>
<spring:url value="/resources/images/deposit.jpg" var="deposit_img"/>
<spring:url value="/resources/images/payLoan.jpg" var="loan_img"/>
<spring:message var="titel_loan" code="loan.title"/>
<spring:message var="titel_deposit" code="chargeDeposit.title"/>


<style>
    .linkServices {
        color: #006600;
    }

    .service {
        cursor: pointer;
    }
    .info{text-align:right;  color:red;  }
    .error{text-align:right;  color:red;  }
    .value{text-align:right; padding:3px;}
    tr{ width:300px !important; }
    .title{color:red;}
</style>
<div class="rich-panel ">
    <div class="rich-panel-header ">
        <span class="richTitleText"> ${title}</span>
    </div>
    <div class="rich-panel-body ">
        <div style="direction:rtl; padding: 10px 50px 0px 0px;font-size:12px  ">
            <div style="font-size:12px; font-family:tahoma,verdana;">
                <ul class="error">
                    <c:forEach items="${messages}" var="message">
                        <li dir="rtl">${message.text}</li>
                    </c:forEach>
                </ul>
                <p>${wellcom_txt}</p>

                <p style="width:630px;">${main_part1_txt}
                    <af:IsAppFeatureEnabled feature="charge">
                        </br>
                        -${main_txt_deposit}
                        <a class="linkServices" href="${charge_url}">${link_to_deposit}</a> ${main_txt_deposit_ok}
                    </af:IsAppFeatureEnabled>
                    <af:IsAppFeatureEnabled feature="loan">
                    </br>

                    -
                    <a class="linkServices" href="${loan_url}">${link_to_loan}</a> &nbsp
                        ${main_txt_loan}
                </p>
                </af:IsAppFeatureEnabled>
                <div>

                        <ul>
                            <af:IsAppFeatureEnabled feature="charge">
                                <li><a href="${charge_url}">${titel_deposit}</a></li>
                            </af:IsAppFeatureEnabled>
                            <af:IsAppFeatureEnabled feature="loan">
                                <li><a href="${loan_url}">${titel_loan}</a></li>
                            </af:IsAppFeatureEnabled>
                        </ul>


                </div>
            </div>
        </div>
    </div>
</div>