package com.samenea.payments.web.model;

/**
 * Date: 1/16/13
 * Time: 3:17 PM
 *
 * @Author:payam
 */
public class View {
    public static final String UNHANDLED_EXCEPTION = "exception";
    public class ContactUS{
    public static final String TECHNICAL="contactus/technical";
    }
    public class Default {
        public final static String HOME_PAGE = "/index";
    }
    public  class Tracking{
        public static  final String TRACKING="tracking";
        public static  final String ORDER="tracking/order";
    }
    public class Help{
        public final static  String LOAN="help/loan";
        public final static  String DEPOSIT="help/deposit";
        public final static  String TRACKING="help/tracking";

    }
    public class Deposit {
        public static final String All_DEPOSITS = "deposit/all";
        public static final String DEPOSIT_CHARGE = "deposit/charge";
        public static final String DEPOSIT_CONFIRM = "deposit/confirm";
        public static final String RESULT = "deposit/result";
        public static final String FAILED = "deposit/failed";

    }
    public class Excption{
        public static final String GENERIC="/excption";
    }
    public class Loan{
        public static final String LOAN_PAY="loan/pay";
        public static final String LOAN_CONFIRM="loan/confirm";

    }
    public class Error {

        public final static String PageNotFound = "pageNotFound";
    }
    public class Admin{
        public final static String Follow = "admin/follow";
    }
    public class Order {
        public static final String POSTPONEDS = "order/postponeds";
    }
}
