
public class QuadTreeNode
{
    private QuadTreeNode child1;
    private QuadTreeNode child2;
    private QuadTreeNode child3;
    private QuadTreeNode child4;
    private boolean empty;
    private Triangle t;
    private BaryCentricPoint p;
    private int pointsContained;

    public QuadTreeNode(QuadTreeNode c1,
    QuadTreeNode c2,
    QuadTreeNode c3,
    QuadTreeNode c4,
    boolean e,
    Triangle t,
    BaryCentricPoint p,
    int pointsContained) {
        child1 = c1;
        child2 = c2;
        child3 = c3;
        child4 = c4;
        empty = e;
        this.t = t;
        this.p = p;
        this.pointsContained = pointsContained;
    }

    public void setChild1(QuadTreeNode child) {
        child1 = child;
    }

    public BaryCentricPoint getPoint() {
        return p;
    }
    
    public void setChild2(QuadTreeNode child) {
        child2 = child;
    }

    public void setChild3(QuadTreeNode child) {
        child3 = child;
    }

    public void setChild4(QuadTreeNode child) {
        child4 = child;
    }
    
    public QuadTreeNode getChild1() {
        return child1;
    }
    public QuadTreeNode getChild2() {
        return child2;
    }
    public QuadTreeNode getChild3() {
        return child3;
    }
    public QuadTreeNode getChild4() {
        return child4;
    }
    
    public Triangle getTriangle() {
        return t;
    }
    
    public int getPointsContained() {
        return this.pointsContained;
    }
    

    public String toString() {
        if (empty) {
            return "QuadTreeNode: "+t.toString()+" Empty Triangle.";
        }
        else {
            return "QuadTreeNode: "+t.toString()+" contains "+pointsContained+" points.";
        }
    }

}
