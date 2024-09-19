package org.views;

import org.app.Models.Helpers.PasswordUtil;

public class Main {

    public static void main(String[] args) {

        String password = "loussalmohammed@2024.com";

        String hashedPassword = PasswordUtil.hashPassword(password);

        System.out.println(hashedPassword);
    }

}
