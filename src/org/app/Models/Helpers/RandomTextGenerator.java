package org.app.Models.Helpers;

import java.security.SecureRandom;

public class RandomTextGenerator {
        private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        private static final SecureRandom RANDOM = new SecureRandom();

        public static String generateRandomString(int length) {
            StringBuilder result = new StringBuilder(length);
            for (int i = 0; i < length; i++) {
                int index = RANDOM.nextInt(CHARACTERS.length());
                result.append(CHARACTERS.charAt(index));
            }
            return result.toString();
        }
}
