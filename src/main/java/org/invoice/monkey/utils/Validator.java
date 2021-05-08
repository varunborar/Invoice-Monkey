package org.invoice.monkey.utils;

public class Validator {

    public static boolean isEmailValid(String email)
    {
        return email.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
    }

    public static boolean isPhoneNumberValid(String phoneNumber)
    {
        return phoneNumber.matches("(0/91)?[7-9][0-9]{9}");
    }

}
