package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class PathFormatter {

    public static String getCanonicalPath(List<String> moves) {
        StringBuilder canonicalPath = new StringBuilder();
        for (String move : moves) {
            canonicalPath.append(move);
        }
        return canonicalPath.toString();
    }

    public static String getFactorizedPath(List<String> moves) {
        if (moves.isEmpty()) {
            return "";
        }
        StringBuilder factorizedPath = new StringBuilder();
        int repeatCount = 1;
        for (int i = 1; i < moves.size(); i++) {
            if (moves.get(i).equals(moves.get(i - 1))) {
                repeatCount++;
            } else {
                if (repeatCount > 1) {
                    factorizedPath.append(repeatCount);
                }
                factorizedPath.append(moves.get(i - 1));
                repeatCount = 1;
            }
        }
        if (repeatCount > 1) {
            factorizedPath.append(repeatCount);
        }
        factorizedPath.append(moves.get(moves.size() - 1));
        return factorizedPath.toString();
    }

    public static String expandFactorizedPath(String factorizedPath) {
        StringBuilder expandedPath = new StringBuilder();
        int i = 0;
        while (i < factorizedPath.length()) {
            char currentCharacter = factorizedPath.charAt(i);
            if (Character.isLetter(currentCharacter)) { // Should be 'F', 'L', or 'R'
                int repeatCount = 1;
                int j = i - 1;
                // Checking for preceding digits that indicate the repeat count.
                while (j >= 0 && Character.isDigit(factorizedPath.charAt(j))) {
                    j--;
                }
                if (j < i - 1) {
                    repeatCount = Integer.parseInt(factorizedPath.substring(j + 1, i));
                }
                expandedPath.append(String.valueOf(currentCharacter).repeat(repeatCount));
            }
            i++;
        }
        return expandedPath.toString();
    }
}