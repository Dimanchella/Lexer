package ru.mai;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class Main {

    private static final String INPUT_FILE_LEXER = "input_lexer.txt";
    private static final String INPUT_FILE_ANALYZER = "input_analyzer.txt";

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream(INPUT_FILE_ANALYZER);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            StringBuilder fileSB = new StringBuilder();
            while (bufferedReader.ready()) {
                fileSB.append(bufferedReader.readLine()).append('\n');
            }

            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();

            //printTokens(fileSB.toString());
            printAnalyzeResult(fileSB.toString());

        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static void printTokens(String inputStr) throws Exception {
        Lexer lexer = new Lexer(inputStr);
        Token token;
        do {
            token = lexer.getNextToken();
            System.out.println(token.toString());
        } while (!token.getName().equals("eof"));
    }

    private static void printAnalyzeResult(String inputStr) {
        Analyzer analyzer = new Analyzer();
        for (String str: inputStr.split("\n")) {
            try {
                analyzer.analyzeExpression(str);
                System.out.println(str + "\t | " + analyzer.getRoot().toString() + "\t | " + analyzer.getValue());
            } catch (Exception e) {
                System.err.println(e + " " + str);
            }
        }
    }
}
