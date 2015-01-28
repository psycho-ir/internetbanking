<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<table align="center" border="0" cellpadding="0" cellspacing="0">
    <tr valign="top">
        <td align="center" nowrap="nowrap">
            <img name="arm" src="https://epay.ebsamen.com/payments/resources/images/samen-header.jpg" border="0">
        </td>
    </tr>
    <tr valign="middle">
        <td align="center" nowrap="nowrap">
            <table align="center" class="headerTable" bgcolor="#828b8b" border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr valign="middle">
                    <td align="left" width="30%">
                        <span class="topInfoTitle"></span>
                        <span class="topInfo"></span>
                    </td>
                    <td align="right" width="70%">
                        &nbsp;<span class="topInfoTitle"></span>
                        <span class="topInfo"> </span>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<font face="Tahoma" style="font-size:9pt">
<#if type == "InstallmentPay">
    <table  align="center" border="1"  style="direction:rtl;text-align:right;width:995px;">
        <tr>
            <td style="text-align: center; background-color:#459357;color:white;">${INSTALLMENT_TXT}</td>
        </tr>
        <tr>
            <td>${INSTALLMENT_TXT} ${INSTALLMENT_NUMBER} ${lOAN_TXT} ${LOAN_NUMBER} ${BYNAME_TXT}${FIRST_NAME} ${LAST_NAME}</td>
        </tr>
        <#if resultType=="POSTPONED">
            <tr>
                <td>${POSTPONED_TXT} https://epay.ebsamen.com/payments/</td>            </tr>
        <#elseif resultType=="DELIVERED">
            <tr>
                <td>${DELIVERED_TXT}</td>
            </tr>
        </#if>

        <tr>
            <td>${AMOUNT_TXT}${AMOUNT} ${RIAL_TXT}</td>
        </tr>
        <tr>
            <td>${DATE_TXT}${DATE}</td>
        </tr>
        <tr>
            <td>${ORDERID_TXT}${ORDER_ID}</td>
        </tr>
        <tr>
            <td style="text-align:center;">${SAMEN_TXT}</td>
        </tr>
        <tr>
            <td style="text-align:center; background-color:#459357;color:white;">https://epay.ebsamen.com/payments</td>
        </tr>
    </table>
<#elseif type=="DepositCharge">
    <table align="center" border="1" style="direction:rtl;text-align:right; width:995px;" >
        <tr>
            <td style="text-align: center; background-color:#459357;color:white;"> ${TRANSFER_TXT}
            </td>
        </tr>
        <tr>
            <td>${TRANSFER_TXT}${DEPOSIT_NUMBER} ${BYNAME_TXT}${FIRST_NAME} ${LAST_NAME}</td>
        </tr>
        <#if resultType=="POSTPONED">
            <tr>
                <td>${POSTPONED_TXT} https://epay.ebsamen.com/payments/</td>
            </tr>
        <#elseif resultType=="DELIVERED">
            <tr>
                <td>${DELIVERED_TXT}</td>
            </tr>
        </#if>

        <tr>
            <td>${AMOUNT_TXT}${AMOUNT} ${RIAL_TXT}</td>
        </tr>
        <tr>
            <td>${DATE_TXT}${DATE}</td>
        </tr>
        <tr>
            <td>${ORDERID_TXT}${ORDER_ID}</td>
        </tr>
        <tr>
            <td style="text-align:center;">${SAMEN_TXT}</td>
        </tr>
        <tr>
            <td style="text-align:center; background-color:#459357;color:white;">https://epay.ebsamen.com/payments</td>
        </tr>
    </table>
</#if>
</font>
