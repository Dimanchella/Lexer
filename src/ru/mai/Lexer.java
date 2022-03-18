package ru.mai;

import java.util.HashMap;

public class Lexer {

    private HashMap<Integer, String> states = new HashMap<>();
    private String researchedString;
    private int lastPos;

    public Lexer(String researchedString) {
        setResearchedString(researchedString);
        createStates();
    }

    private void createStates() {
        states.put(0, null);
        states.put(1, "number");
        states.put(2, "number");
        states.put(3, null);
        states.put(4, null);
        states.put(5, "number");
        states.put(6, "operator");
        states.put(7, "id");
        states.put(8, "lparel");
        states.put(9, "rparel");
        states.put(10, "comma");
    }


    public void setResearchedString(String researchedString) {
        this.researchedString = researchedString;
        lastPos = 0;
    }


    private boolean isAcceptingState(int state) {
        return states.get(state) != null;
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


    private String getTokenName(int state) {
        return states.get(state);
    }

    private String getTokenLexeme(int nextLastPos) {
        String lexeme = researchedString.substring(lastPos, nextLastPos);
        lastPos = nextLastPos;
        return lexeme;
    }


    private boolean isSpaceSeparator(char ch) {
        return ch == ' ' || ch == '\n' || ch == '\r';
    }

    private boolean skipSpaceSeparator() {
        while (lastPos < researchedString.length()) {
            if (isSpaceSeparator(researchedString.charAt(lastPos))) {
                lastPos++;
            } else {
                return true;
            }
        }
        return false;
    }

    //private int getLastAcceptingState() {

    //}

    public Token getNextToken() throws Exception {
        int state = 0;
        int lastAccepting = -1;
        int nextLastPos = lastPos;
        char currentChar = ' ';

        for (int pos = lastPos; state >= 0; pos++) {
            if (isAcceptingState(state)) {
                lastAccepting = state;
                nextLastPos = pos;
            }
            if (pos < researchedString.length()) {
                currentChar = researchedString.charAt(pos);
                state = dfaTransition(currentChar, state);
            } else {
                state = -1;
            }
        }

        if (lastAccepting >= 0) {
            return new Token(getTokenName(lastAccepting), getTokenLexeme(nextLastPos));
        } else if (lastPos >= researchedString.length()) {
            return new Token("eof", null);
        } else if (isSpaceSeparator(researchedString.charAt(lastPos)) && skipSpaceSeparator()) {
            return getNextToken();
        } else {
            throw new Exception("Bad lexeme!");
        }
    }
}