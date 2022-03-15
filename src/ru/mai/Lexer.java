package ru.mai;

public class Lexer {

    private boolean isReceivingState(int state) {
        return (
                state == 1
                        || state == 2
                        || state == 5
                        || state == 6
                        || state == 7
                        || state == 8
                        || state == 9
                        || state == 10
        );
    }

    private int dfaTransition(char ch, int state) {
        switch (state) {
            case 0: {
                if (ch >= 'a' && ch <= 'z' || ch == '_') {
                    return 7;
                } else if (ch == '(') {
                    return 8;
                } else if (ch == ')') {
                    return 9;
                } else if (ch == ',') {
                    return 10;
                } else if (ch == '+' || ch == '*' || ch == '/' || ch == '^' || ch == '-') {
                    return 6;
                } else if (ch >= '0' && ch <= '9') {
                    return 1;
                } else {
                    return -1;
                }
            }
            case 1: {
                if (ch >= '0' && ch <= '9') {
                    return 1;
                } else if (ch == '.') {
                    return 2;
                } else if (ch == 'e' || ch == 'E') {
                    return 3;
                }
            }
            case 2: {
                if (ch >= '0' && ch <= '9') {
                    return 2;
                } else if (ch == 'e' || ch == 'E') {
                    return 3;
                } else {
                    return -1;
                }
            }
            case 3: {
                if (ch == '-' || ch == '+') {
                    return 4;
                } else if (ch >= '0' && ch <= '9') {
                    return 5;
                } else {
                    return -1;
                }
            }
            case 4:
            case 5: {
                if (ch >= '0' && ch <= '9') {
                    return 5;
                } else {
                    return -1;
                }
            }
            case 7: {
                if (ch >= 'a' && ch <= 'z' || ch == '_' || ch >= '0' && ch <= '9') {
                    return 7;
                } else {
                    return -1;
                }
            }
            default: {
                return -1;
            }
        }
    }

    private String getTokenName(int state) throws Exception {
        switch (state) {
            case 1:
            case 2:
            case 5: {
                return "number";
            }
            case 6: {
                return "operator";
            }
            case 7: {
                return "id";
            }
            case 8: {
                return "lparen";
            }
            case 9: {
                return "rparen";
            }
            case 10: {
                return "comma";
            }
            default: {
                throw new Exception("Bad state!");
            }
        }
    }

    private String getTokenLexeme(String string, int startPos, int lastPos) throws Exception {
        StringBuilder lexeme = new StringBuilder();
        for (int i = startPos; i <= lastPos; i++) {
            lexeme.append(string.charAt(i));
        }
        return lexeme.toString();
    }


    public int getNextToken(String string, int startPos, Token token) throws Exception {
        int state = 0;
        int lastAccepting = -1;
        int lastPos = -1;
        char currentChar = ' ';

        for (int pos = 0; state >= 0; pos++) {
            if (isReceivingState(state)) {
                lastAccepting = state;
                lastPos = startPos + pos;
            }
            if (startPos + pos < string.length()) {
                currentChar = string.charAt(startPos + pos);
                state = dfaTransition(currentChar, state);
            } else {
                state = -1;
            }
        }
        if (lastAccepting >= 0) {
            token.setName(getTokenName(lastAccepting));
            token.setLexeme(getTokenLexeme(string, startPos, lastPos - 1));
            return lastPos;
        } else if (currentChar == ' ' || currentChar == '\n' || currentChar == '\r') {
            while (startPos < string.length()) {
                if (
                        string.charAt(startPos) == ' '
                        || string.charAt(startPos) == '\n'
                        || string.charAt(startPos) == '\r'
                ) {
                    startPos++;
                } else {
                    return getNextToken(string, startPos, token);
                }
            }
            return startPos;
        } else {
            throw new Exception("Bad lexeme.");
        }
    }
}