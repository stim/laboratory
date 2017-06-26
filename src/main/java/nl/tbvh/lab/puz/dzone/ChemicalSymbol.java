package nl.tbvh.lab.puz.dzone;

/**
 * https://dzone.com/articles/java-code-challenge-chemical-symbol-naming-part-on
 */
public class ChemicalSymbol {

    public boolean check(String element, String symbol) {
        return checkCaseSensitive(element.toLowerCase(), symbol.toLowerCase());
    }

    private boolean checkCaseSensitive(String element, String symbol) {
        if (symbol.length() != 2) {
            return false;
        }
        char firstChar = symbol.charAt(0);
        char secondChar = symbol.charAt(1);

        int firstCharIndex = element.indexOf(firstChar);
        if (firstCharIndex < 0) {
            return false;
        }
        if (firstCharIndex == element.length()) {
            return false;
        }

        return element.indexOf(secondChar, firstCharIndex + 1) > 0;
    }

}
