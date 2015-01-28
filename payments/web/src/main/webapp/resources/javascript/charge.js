$(document).ready(function () {
    $('#amount').asciiNumber();
    $('#depositNumber1').asciiNumber();
    $('#depositNumber2').asciiNumber();
    $('#phoneNumber').asciiNumber();
    $('#btnReload').click(function () {
        $("#img_captcha").attr('src', '${captcha_url}' + '?' + (new Date()).getTime());
    });
    $('#chargeDepositViewModel').submit(function () {
        var depositMsg = checkDeposit();
        var amountMsg = checkAmount();
        var emailMsg = checkEmail();
        var phoneNumberMsg = checkPhoneNumber();
        var descriptionMsg = checkDescription();
        if (depositMsg && amountMsg && phoneNumberMsg && emailMsg && descriptionMsg) {

            var depositAmountstr = $('#amount');
            var str = depositAmountstr.val();
            $('#amount').val(str.replace(/,/g, ""));
            $("#chargeDepositViewModel").submit();
            return true;
        }
        else {
            return false;
        }
    });
    function isBlank(pString) {
        if (!pString || pString.length == 0) {
            return true;
        }
        return !/[^\s]+/.test(pString);
    }
    function checkDeposit() {

        var isDeposit = true;
        var part1 = $('#depositNumber1').val();
        var part2 = $('#depositNumber2').val();
        if (!IsNumeric(part1) || isBlank(part1)) {
            $('#depositNumber1').addClass("highlight");
            isDeposit = false;
        } else {
            $('#depositNumber1').removeClass("highlight");
        }
        if (!IsNumeric(part2) || isBlank(part2)) {
            $('#depositNumber2').addClass("highlight");
            isDeposit = false;
        } else {
            $('#depositNumber2').removeClass("highlight");
        }
        if (isDeposit) {
            $('#msgdeposit').hide();
        } else {
            $('#msgdeposit').show();
        }
        return isDeposit;
    }
    function separate() {
        var input = $('#amount');
        var nStr = input.val() + '';
        nStr = nStr.replace(/\,/g, "");
        x = nStr.split('.');
        x1 = x[0];
        x2 = x.length > 1 ? '.' + x[1] : '';
        var rgx = /(\d+)(\d{3})/;
        while (rgx.test(x1)) {
            x1 = x1.replace(rgx, '$1' + ',' + '$2');
        }
        input.val(x1 + x2);
    }
    function IsNumeric(value)
    {  for (i = 0 ; i < value.length ; i++) {
        if ((value.charAt(i) < '0') || (value.charAt(i) > '9')) return false
    }
        return true;
    }
    function checkEmail() {
        var isEmail = true;
        var email = $('#email');
        var pattern = /^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\.([a-zA-Z])+([a-zA-Z])+/;
        if (pattern.test(email.val())) {
            $(email).removeClass("highlight");
            $('#msgemail').hide();
        } else {
            $(email).addClass("highlight");
            $('#msgemail').show();
            isEmail = false;
        }
        return isEmail;
    }
    function checkPhoneNumber() {
        var isPhoneNumber = true;
        var phoneNumber = $('#phoneNumber');
        var pattern = /09\d\d\d\d\d\d\d\d\d$/;
        if (pattern.test(phoneNumber.val())) {
            $(phoneNumber).removeClass("highlight");
            $('#msgphoneNumber').hide();
        } else {
            $(phoneNumber).addClass("highlight");
            $('#msgphoneNumber').show();
            isPhoneNumber = false;
        }
        return isPhoneNumber;
    }
    $('#return').click(function () {
        window.location = "${intro_url}";
    });
    $('#depositNumber1').bind("change", checkDeposit);
    $('#depositNumber2').bind("change", checkDeposit);
    $('#email').bind("change", checkEmail);
    $('#phoneNumber').bind("change", checkPhoneNumber);


});
