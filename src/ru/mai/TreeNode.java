package ru.mai;

public class TreeNode {

    private Token token;
    private TreeNode[] branches = new TreeNode[]{ null, null };

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

    @Override
    public String toString() {
        StringBuilder tree = new StringBuilder().append(token.getLexeme());
        if (branches[0] != null || branches[1] != null) {
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
