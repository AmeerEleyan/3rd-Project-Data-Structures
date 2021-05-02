package project3;

public final class Utilities {
    private Utilities() {
    }

    public static int getYearFromFileName(String fileName) {
        int count = 0;
        String year = "";
        for (int i = 0; i < fileName.length(); ++i) {
            if (Character.isDigit(fileName.charAt(i))) {
                year += fileName.charAt(i);
                count++;
                if (count == 4) break;
            } else {
                count = 0;
            }
        }
        if (count == 0) return 0;
        return Integer.parseInt(year.trim());
    }
}
