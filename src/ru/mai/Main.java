package ru.mai;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

    private static final String INPUT_FILE = "input.txt";

    public static void main(String[] args) {
        try {
            //FileInputStream fileInputStream = new FileInputStream(INPUT_FILE);
            //InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            //BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(INPUT_FILE)));
            StringBuilder fileSB = new StringBuilder();
            while (bufferedReader.ready()) {
                fileSB.append(bufferedReader.readLine()).append('\n');
            }
            bufferedReader.close();

            String fileStr = fileSB.toString();
            Lexer lexer = new Lexer();
            int startPos = 0;
            while (startPos < fileSB.length()) {
                Token token = new Token();
                startPos = lexer.getNextToken(fileStr, startPos, token);
                if (token.getName() != null) {
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
                }
            }

            //bufferedReader.close();
            //inputStreamReader.close();
            //fileInputStream.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
