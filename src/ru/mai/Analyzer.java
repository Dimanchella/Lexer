package ru.mai;

import com.sun.source.tree.Tree;

import java.util.ArrayList;
import java.util.List;

public class Analyzer {

    private Token token;
    private Lexer lexer = new Lexer("");
    private TreeNode root = null;
    private String value = null;

    public TreeNode getRoot() {
        return root;
    }

    public String getValue() {
        return value;
    }


    public void analyzeExpression(String expr) throws Exception {
        lexer.setResearchedString(expr);
        token = lexer.getNextToken();
        root = E();
        if (!token.getName().equals("eof")) {
            throw new Exception("Bad expression!");
        }
        value = root.calculation();
    }


    private TreeNode nextToken(String checkLexeme) throws Exception {
        if (token.getLexeme().equals(checkLexeme)) {
            TreeNode node = new TreeNode(token);
            token = lexer.getNextToken();
            return node;
        } else {
            throw new Exception("Bad token: " + token.getLexeme());
        }
    }


    private TreeNode E() throws Exception {
        TreeNode node_1 = T();
        TreeNode node_2 = E_();
        if (node_2 != null && node_2.getToken().getName().equals("operator")) {
            node_2.setFirstBranchNode(node_1);
            node_1 = node_2;
        }
        return node_1;
    }

    private TreeNode E_() throws Exception {
        TreeNode node = null;
        if (token.getName().equals("operator") && (token.getLexeme().equals("+") || token.getLexeme().equals("-"))) {
            node = nextToken(token.getLexeme());
            node.setSecondBranchNode(E());
        }
        return node;
    }

    private TreeNode T() throws Exception {
        TreeNode node_1 = F();
        TreeNode node_2 = T_();
        if (node_2 != null && node_2.getToken().getName().equals("operator")) {
            node_2.setFirstBranchNode(node_1);
            node_1 = node_2;
        }
        return node_1;
    }

    private TreeNode T_() throws Exception {
        TreeNode node = null;
        if (token.getName().equals("operator") && (token.getLexeme().equals("*") || token.getLexeme().equals("/"))) {
            node = nextToken(token.getLexeme());
            node.setSecondBranchNode(T());
        }
        return node;
    }

    private TreeNode F() throws Exception {
        TreeNode node_1 = V();
        TreeNode node_2 = F_();
        if (node_2 != null && node_2.getToken().getName().equals("operator")) {
            node_2.setFirstBranchNode(node_1);
            node_1 = node_2;
        }
        return node_1;
    }

    private TreeNode F_() throws Exception {
        TreeNode node = null;
        if (token.getName().equals("operator") && token.getLexeme().equals("^")) {
            node = nextToken("^");
            node.setSecondBranchNode(F());
        }
        return node;
    }

    private TreeNode V() throws Exception {
        TreeNode node = null;
        if (token.getName().equals("lparel")) {
            nextToken("(");
            node = E();
            nextToken(")");
        } else if (token.getName().equals("id") || token.getName().equals("number")) {
            node = nextToken(token.getLexeme());
        } else if (token.getName().equals("operator") && token.getLexeme().equals("-")) {
            node = nextToken(token.getLexeme());
            node.setFirstBranchNode(V());
        } else {
            throw new Exception("Bad expression!");
        }
        return node;
    }
}
