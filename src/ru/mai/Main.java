package ru.mai;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

    private static final String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder fileSB = new StringBuilder();
            while (bufferedReader.ready()) {
                fileSB.append(bufferedReader.readLine()).append('\n');
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            Lexer lexer = new Lexer(fileSB.toString());
            Token token;
            do {
                token = lexer.getNextToken();
                System.out.println(
                        new StringBuilder()
                                .append('(')
                                .append(token.getName())
                                .append(' ')
                                .append('\"')
                                .append(token.getLexeme())
                                .append('\"')
                                .append(')')
                                .toString()
                );
            } while (!token.getName().equals("eof"));
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
