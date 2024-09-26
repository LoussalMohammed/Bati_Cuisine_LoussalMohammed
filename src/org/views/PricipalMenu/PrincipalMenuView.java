package org.views.PricipalMenu;

import java.util.Scanner;

public class PrincipalMenuView {
    public final static Scanner scanner = new Scanner(System.in);
    public static int getOption() {
        System.out.println("=== Menu Principal ===");
        System.out.println("1. Créer un nouveau projet");
        System.out.println("2. Afficher les projets existants");
        System.out.println("3. Calculer le coût d'un projet");
        System.out.println("0. Quitter");
        int option = scanner.nextInt();
        if(scanner.hasNextLine()) {
            scanner.nextLine();
        }

        return option;
    }
}
