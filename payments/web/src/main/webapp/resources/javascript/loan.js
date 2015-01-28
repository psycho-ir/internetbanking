$(document).ready(function () {
    $('#phoneNumber').asciiNumber();
    $('#number').asciiNumber();
    $('#loanInstalmentViewModel').submit(function () {
        var numberMsg = checkNumber();
        var emailMsg = checkEmail();
        var phoneNumberMsg = checkPhoneNumber();
        return(msgnumber&&emailMsg&&phoneNumberMsg);
    });
    function isBlank(pString) {
        if (!pString || pString.length == 0) {
            return true;
        }
        return !/[^\s]+/.test(pString);
    }

    function checkNumber() {
        var isNumber = true;
        var number = $('#number');
        if (isNaN(number.val()) || isBlank(number.val())) {
            $(number).addClass("highlight");
            $("#msgnumber").show();
            isNumber = false;
        } else {
            $("#msgnumber").hide();
            $(number).removeClass("highlight");
        }
        return isNumber;
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
    $('#return').click(function () {
        window.location="${intro_url}";
    });
    $('#email').bind("change", checkEmail);
    $('#number').bind("change", checkNumber);
    $('#phoneNumber').bind("change", checkPhoneNumber);
});