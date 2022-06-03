import java.util.ArrayList;
import java.util.List;

public class ParameterParser {
    private char[] chars = null;
    private int pos = 0;
    private int len = 0;
    private int i1 = 0;
    private int i2 = 0;

    public ParameterParser() {
    }

    private boolean hasChar() {
        return pos < len;
    }

    private String getToken(boolean quoted) {
        while (i1 < i2 && Character.isWhitespace(chars[i1])) {
            ++i1;
        }

        while (i2 > i1 && Character.isWhitespace(chars[i2 - 1])) {
            --i2;
        }

        if (quoted && i2 - i1 >= 2 && chars[i1] == 34 && chars[i2 - 1] == 34) {
            ++i1;
            --i2;
        }

        String result = null;
        if (i2 >= i1) {
            result = new String(chars, i1, i2 - i1);
        }

        return result;
    }

    private boolean isOneOf(char ch, char[] charray) {
        boolean result = false;

        for (int i = 0; i < charray.length; ++i) {
            if (ch == charray[i]) {
                result = true;
                break;
            }
        }

        return result;
    }

    private String parseToken(char[] terminators) {
        i1 = pos;

        for (i2 = pos; hasChar(); ++pos) {
            char ch = chars[pos];
            if (isOneOf(ch, terminators)) {
                break;
            }

            ++i2;
        }

        return getToken(false);
    }

    private String parseQuotedToken(char[] terminators) {
        i1 = pos;
        i2 = pos;
        boolean quoted = false;

        for (boolean charEscaped = false; hasChar(); ++pos) {
            char ch = chars[pos];
            if (!quoted && isOneOf(ch, terminators)) {
                break;
            }

            if (!charEscaped && ch == 34) {
                quoted = !quoted;
            }

            charEscaped = !charEscaped && ch == 92;
            ++i2;
        }

        return getToken(true);
    }

    public List parse(String str, char separator) {
        return str == null ? new ArrayList() : this.parse(str.toCharArray(), separator);
    }

    public List parse(char[] chars, char separator) {
        return chars == null ? new ArrayList() : this.parse(chars, 0, chars.length, separator);
    }

    public List parse(char[] chars, int offset, int length, char separator) {
        if (chars == null) {
            return new ArrayList();
        } else {
            List params = new ArrayList();
            this.chars = chars;
            pos = offset;
            len = length;
            String paramName = null;
            String paramValue = null;

            while (true) {
                do {
                    do {
                        if (!hasChar()) {
                            return params;
                        }

                        paramName = parseToken(new char[] {
                            '=', separator
                        });
                        paramValue = null;
                        if (hasChar() && chars[pos] == 61) {
                            ++pos;
                            paramValue = parseQuotedToken(new char[] {
                                separator
                            });
                        }

                        if (hasChar() && chars[pos] == separator) {
                            ++pos;
                        }
                    } while (paramName == null);
                } while (paramName.equals("") && paramValue == null);

                params.add(new NameValuePair(paramName, paramValue));
            }
        }
    }
}
