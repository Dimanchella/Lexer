package ru.mai;

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
        value = calculateTree(root);
    }


    private String calculateTree(TreeNode node) {
        StringBuilder value = new StringBuilder();
        if (node.getToken().getName().equals("operator")) {
            boolean isParse = true;
            double num_1 = 0;
            double num_2 = 0;
            String strNum_1 = "0";
            String strNum_2 = "0";

            if (node.getFirstBranchNode() != null) {
                strNum_1 = calculateTree(node.getFirstBranchNode());
            }
            if (node.getSecondBranchNode() != null) {
                strNum_2 = calculateTree(node.getSecondBranchNode());
            }

            try {
                num_1 = Double.parseDouble(strNum_1);
            } catch (NumberFormatException nfe) {
                isParse = false;
            }
            try {
                num_2 = Double.parseDouble(strNum_2);
            } catch (NumberFormatException nfe) {
                isParse = false;
            }

            switch (node.getToken().getLexeme()) {
                case "+": {
                    if (isParse) {
                        value.append(num_1 + num_2);
                    } else {
                        value.append(strNum_1).append(node.getToken().getLexeme()).append(strNum_2);
                    }
                    break;
                }
                case "-": {
                    if (isParse) {
                        if (node.getSecondBranchNode() != null) {
                            value.append(num_1 - num_2);
                        } else {
                            value.append(-num_1);
                        }
                    } else {
                        if (node.getSecondBranchNode() != null) {
                            value.append(strNum_1).append(node.getToken().getLexeme()).append(strNum_2);
                        } else {
                            value.append("-(").append(strNum_1).append(")");
                        }
                    }
                    break;
                }
                case "*": {
                    if (isParse) {
                        value.append(num_1 * num_2);
                    } else {
                        value.append(strNum_1).append(node.getToken().getLexeme()).append(strNum_2);
                    }
                    break;
                }
                case "/": {
                    if (isParse) {
                        value.append(num_1 / num_2);
                    } else {
                        value.append(strNum_1).append(node.getToken().getLexeme()).append(strNum_2);
                    }
                    break;
                }
                case "^": {
                    if (isParse) {
                        value.append(Math.pow(num_1, num_2));
                    } else {
                        value.append(strNum_1).append(node.getToken().getLexeme()).append(strNum_2);
                    }
                    break;
                }
            }
        } else {
            value.append(node.getToken().getLexeme());
        }
        return value.toString();
    }

    private TreeNode nextToken(String checkLexeme) throws Exception {
        if (token.getLexeme() != null && token.getLexeme().equals(checkLexeme)) {
            TreeNode node = new TreeNode(token);
            token = lexer.getNextToken();
            return node;
        } else {
            throw new Exception("Bad expression!");
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
