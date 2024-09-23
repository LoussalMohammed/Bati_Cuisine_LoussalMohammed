package org.views;

import java.awt.desktop.SystemEventListener;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Hello In JAR!!");
        System.out.print("Enter Your Name:\t");
        String name = scanner.nextLine();
        System.out.println("Great Name " +name+ " Hope You Are Doing Fine!");
    }
}
