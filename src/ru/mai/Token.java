package ru.mai;

public class Token {

    private String name;
    private String lexeme;

    public Token() { }

    public Token(String name, String lexeme) {
        this.lexeme = lexeme;
        this.name = name;
    }

    public String getLexeme() {
        return lexeme;
    }

    public  void setLexeme(String value) { lexeme = value; }

    public String getName() {
        return name;
    }

    public  void setName(String value) { name = value; }
}
