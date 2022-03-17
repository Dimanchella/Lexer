package ru.mai;

public class TreeNode {

    private Token token;
    private TreeNode[] branches = new TreeNode[2];

    public TreeNode(Token token) {
        this.token = token;
    }

    public Token getToken() {
        return token;
    }

    public void setFirstBranchNode(TreeNode node) {
        branches[0] = node;
    }

    public TreeNode getFirstBranchNode() {
        return branches[0];
    }

    public void setSecondBranchNode(TreeNode node) {
        branches[1] = node;
    }

    public TreeNode getSecondBranchNode() {
        return branches[1];
    }

    public String calculation() {
        StringBuilder value = new StringBuilder();
        if (token.getName().equals("operator")) {
            boolean isParse = true;
            double num_1 = 0;
            double num_2 = 0;
            try {
                num_1 = Double.parseDouble(branches[0].calculation());
            } catch (NumberFormatException nfe) {
                isParse = false;
            }
            try {
                if (branches[1] != null) {
                    num_2 = Double.parseDouble(branches[1].calculation());
                }
            } catch (NumberFormatException nfe) {
                isParse = false;
            }
            switch (token.getLexeme()) {
                case "+": {
                    if (isParse) {
                        value.append(num_1 + num_2);
                    } else {
                        value.append(branches[0].calculation())
                                .append(token.getLexeme())
                                .append(branches[1].calculation());
                    }
                    break;
                }
                case "-": {
                    if (isParse) {
                        if (branches[1] != null) {
                            value.append(num_1 - num_2);
                        } else {
                            value.append(-num_1);
                        }
                    } else {
                        if (branches[1] != null) {
                            value.append(branches[0].calculation())
                                    .append(token.getLexeme())
                                    .append(branches[1].calculation());
                        } else {
                            value.append("-")
                                    .append(branches[0].calculation());
                        }
                    }
                    break;
                }
                case "*": {
                    if (isParse) {
                        value.append(num_1 * num_2);
                    } else {
                        value.append(branches[0].calculation())
                                .append(token.getLexeme())
                                .append(branches[1].calculation());
                    }
                    break;
                }
                case "/": {
                    if (isParse) {
                        value.append(num_1 / num_2);
                    } else {
                        value.append(branches[0].calculation())
                                .append(token.getLexeme())
                                .append(branches[1].calculation());
                    }
                    break;
                }
                case "^": {
                    if (isParse) {
                        value.append(Math.pow(num_1, num_2));
                    } else {
                        value.append(branches[0].calculation())
                                .append(token.getLexeme())
                                .append(branches[1].calculation());
                    }
                    break;
                }
            }
        } else {
            value.append(token.getLexeme());
        }
        return value.toString();
    }

    @Override
    public String toString() {
        StringBuilder tree = new StringBuilder().append(token.getLexeme());
        if (token.getName().equals("operator")) {
            tree.append("(");
            if (branches[0] != null) {
                tree.append(branches[0].toString());
            }
            if (branches[1] != null) {
                tree.append(", ").append(branches[1].toString());
            }
            tree.append(")");
        }
        return tree.toString();
    }
}
