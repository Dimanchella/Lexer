package ru.mai;

public class Token {

    private String name;
    private String lexeme;

    public Token(String name, String lexeme) {
        this.lexeme = lexeme;
        this.name = name;
    }

    public String getLexeme() {
        return lexeme;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "(" + name + " \"" + lexeme + "\")";
    }
}
