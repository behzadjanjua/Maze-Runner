package ca.mcmaster.se2aa4.mazerunner;

import java.util.List;

public class PathFormatter {
    public static String getCanonicalPath(List<String> moves) {
        StringBuilder sb = new StringBuilder();
        for (String move : moves) {
            sb.append(move);
        }
        return sb.toString();
    }
    
    public static String getFactorizedPath(List<String> moves) {
        if (moves.isEmpty()) return "";
        StringBuilder sb = new StringBuilder();
        int count = 1;
        for (int i = 1; i < moves.size(); i++) {
            // Count repeated moves.
            if (moves.get(i).equals(moves.get(i - 1))) {
                count++;
            } else {
                // Append the count if more than one, then after, the move.
                if (count > 1) {
                    sb.append(count);
                }
                sb.append(moves.get(i - 1));
                count = 1;
            }
        }
        // Appends the final set of moves.
        if (count > 1) {
            sb.append(count);
        }
        sb.append(moves.get(moves.size() - 1));
        return sb.toString();
    }
    
    public static String expandFactorizedPath(String factorizedPath) {
        StringBuilder expanded = new StringBuilder();
        int i = 0;
        while (i < factorizedPath.length()) {
            char c = factorizedPath.charAt(i);
            if (Character.isLetter(c)) {
                int count = 1;
                int j = i - 1;
                // Check if there is a number before the letter.
                while (j >= 0 && Character.isDigit(factorizedPath.charAt(j))) {
                    j--;
                }
                if (j < i - 1) {
                    count = Integer.parseInt(factorizedPath.substring(j + 1, i));
                }
                // Append the character 'count' times.
                for (int k = 0; k < count; k++) {
                    expanded.append(c);
                }
            }
            i++;
        }
        return expanded.toString();
    }
}