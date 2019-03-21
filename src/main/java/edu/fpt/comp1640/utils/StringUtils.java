package edu.fpt.comp1640.utils;

import java.io.IOException;

public class StringUtils {
    private static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private static char toHex(int n) {
        return hexDigit[n & 0xF];
    }

    public static String escape(String str) {
        if (str == null) {
            return "null";
        }
        StringBuilder sb = new StringBuilder(str.length() * 2);
        escape(str, sb);
        return sb.toString();
    }

    public static void escape(String str, Appendable sb) {
        try {
            int len = str.length();
            for (int i = 0; i < len; i++) {
                char c = str.charAt(i);
                if (c > 61 && c < 127) {
                    if (c == '\\') {
                        sb.append('\\').append('\\');
                        continue;
                    }
                    sb.append(c);
                    continue;
                }
                switch (c) {
                    case ' ':
                        sb.append(' ');
                        break;
                    case '\t':
                        sb.append('\\').append('t');
                        break;
                    case '\n':
                        sb.append('\\').append('n');
                        break;
                    case '\r':
                        sb.append('\\').append('r');
                        break;
                    case '\f':
                        sb.append('\\').append('f');
                        break;
                    case '=':
                    case ':':
                    case '#':
                    case '!':
                        sb.append('\\').append(c);
                        break;
                    default:
                        if (c < 0x0020 || c > 0x007e) {
                            sb.append('\\').append('u')
                                .append(toHex(c >> 12 & 0xF)).append(toHex(c >> 8 & 0xF))
                                .append(toHex(c >> 4 & 0xF)).append(toHex(c & 0xF));
                        } else {
                            sb.append(c);
                        }
                }
            }
        } catch (IOException e) { }
    }

    public static String unescape(String str) {
        if (str == null) {
            return null;
        }

        int off = 0;
        int outLen = 0;
        int len = str.length();

        char[] inp = str.toCharArray();
        char[] out = new char[len * 2];

        while (off < len) {
            char c = inp[off++];
            if (c != '\\') {
                out[outLen++] = c;
                continue;
            }
            c = inp[off++];
            if (c == 'u') {
                int value = 0;
                for (int i = 0; i < 4; i++) {
                    c = inp[off++];
                    switch (c) {
                        case '0':
                        case '1':
                        case '2':
                        case '3':
                        case '4':
                        case '5':
                        case '6':
                        case '7':
                        case '8':
                        case '9':
                            value = (value << 4) + c - '0';
                            break;
                        case 'a':
                        case 'b':
                        case 'c':
                        case 'd':
                        case 'e':
                        case 'f':
                            value = (value << 4) + 10 + c - 'a';
                            break;
                        case 'A':
                        case 'B':
                        case 'C':
                        case 'D':
                        case 'E':
                        case 'F':
                            value = (value << 4) + 10 + c - 'A';
                            break;
                        default:
                            throw new IllegalArgumentException("Malformed \\uXXXX encoding.");
                    }
                }
                out[outLen++] = (char) value;
            } else {
                if (c == 't') {
                    c = '\t';
                } else if (c == 'r') {
                    c = '\r';
                } else if (c == 'n') {
                    c = '\n';
                } else if (c == 'f') {
                    c = '\f';
                }
                out[outLen++] = c;
            }
        }
        return new String(out, 0, outLen);
    }
}
