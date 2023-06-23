public class Node {
    public Node parent, left, right;
    public String data, code;
    public Integer probability;

    public Node(){
        parent = left = right = null;
        data = code = "";
    }
}
