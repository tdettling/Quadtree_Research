
import java.util.*;
import javax.swing.JFrame;  

public class QuadTree
{

    private QuadTreeNode root = null;

    /**
     * Constructor for objects of class QuadTree
     */
    public QuadTree()
    {

    }

    public void setRoot ( QuadTreeNode q ) {
        root = q;
    }

    public static QuadTreeNode createQuadTree(Triangle t,LinkedList < BaryCentricPoint > points) {
        if (points.size() == 0) { // There are not points in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null,null,true,t,null,0);
            return newNode;
        }
        if (points.size() == 1) { // There is only point in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null, null,false,t,points.get(0),1);
            return newNode;
        }
        // If this point is reached, there are more than two points in this triangle
        // It is necessary to split the triangle
        if (t.up()) {
            return upTriangle(t,points);
        }
        else {
            return downTriangle(t,points);
        }
    }

    private static QuadTreeNode upTriangle(Triangle t,LinkedList < BaryCentricPoint > points) {
        if (points.size() == 0) { // There are not points in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null,null,true,t,null,0);
            return newNode;
        }
        if (points.size() == 1) { // There is only point in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null, null,false,t,points.get(0),1);
            return newNode;
        }

        BaryCentricPoint p[] = t.getVertices();

        BaryCentricPoint middle1 = BaryCentricPoint.middlePoint(p[0],p[1]);
        BaryCentricPoint middle2 = BaryCentricPoint.middlePoint(p[1],p[2]);
        BaryCentricPoint middle3 = BaryCentricPoint.middlePoint(p[0],p[2]);

        Triangle t1 = new Triangle(p[0],middle1,middle3,true);
        Triangle t2 = new Triangle(middle1,middle2,middle3,false);
        Triangle t3 = new Triangle(middle1,p[1],middle2,true);        
        Triangle t4 = new Triangle(middle3,middle2,p[2],true); 

        LinkedList < BaryCentricPoint > pointsInt1 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt2 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt3 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt4 = new LinkedList < BaryCentricPoint > ();

        for(BaryCentricPoint q : points) {
            if (q.getd1() > middle1.getd1()) {
                pointsInt1.add(q);
            }
            else 
            if (q.getd2() > middle2.getd2()) {
                pointsInt3.add(q);
            }
            else
            if (q.getd3() > middle3.getd3()) {
                pointsInt4.add(q);
            }
            else {
                pointsInt2.add(q);
            }
        }
        QuadTreeNode q1 = upTriangle(t1,pointsInt1);
        QuadTreeNode q2 = downTriangle(t2,pointsInt2);
        QuadTreeNode q3 = upTriangle(t3,pointsInt3);
        QuadTreeNode q4 = upTriangle(t4,pointsInt4);
        QuadTreeNode newNode = new QuadTreeNode(q1,q2,q3, q4,false,t,null,points.size());
        return newNode;
    }

    private static QuadTreeNode downTriangle(Triangle t,LinkedList < BaryCentricPoint > points) {
        if (points.size() == 0) { // There are not points in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null,null,true,t,null,0);
            return newNode;
        }
        if (points.size() == 1) { // There is only point in this triangle
            QuadTreeNode newNode = new QuadTreeNode(null,null,null, null,false,t,points.get(0),1);
            return newNode;
        } 

        BaryCentricPoint p[] = t.getVertices();
        BaryCentricPoint middle1 = BaryCentricPoint.middlePoint(p[0],p[1]);
        BaryCentricPoint middle2 = BaryCentricPoint.middlePoint(p[1],p[2]);
        BaryCentricPoint middle3 = BaryCentricPoint.middlePoint(p[0],p[2]);

        Triangle t1 = new Triangle(p[2],middle2,middle3,false);
        Triangle t2 = new Triangle(middle3,middle1,middle2,true);
        Triangle t3 = new Triangle(middle2,p[1],middle1,false);        
        Triangle t4 = new Triangle(middle3,middle1,p[0],false); 

        LinkedList < BaryCentricPoint > pointsInt1 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt2 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt3 = new LinkedList < BaryCentricPoint > ();
        LinkedList < BaryCentricPoint > pointsInt4 = new LinkedList < BaryCentricPoint > ();

        for(BaryCentricPoint q : points) {
            if (q.getd3() < middle3.getd3()) {
                pointsInt1.add(q);
            }
            else 
            if (q.getd1() < middle2.getd1()) {
                pointsInt3.add(q);
            }
            else
            if (q.getd2() < middle3.getd2()) {
                pointsInt4.add(q);
            }
            else {
                pointsInt2.add(q);
            }
        }
        QuadTreeNode q1 = downTriangle(t1,pointsInt1);
        QuadTreeNode q2 = upTriangle(t2,pointsInt2);
        QuadTreeNode q3 = downTriangle(t3,pointsInt3);
        QuadTreeNode q4 = downTriangle(t4,pointsInt4);
        QuadTreeNode newNode = new QuadTreeNode(q1,q2,q3, q4,false,t,null,points.size());
        return newNode;
    }

    public void traverseTree() {
        traverseTree(root);

    }

    public void makeEmpty() {
        root = null;
    }

    private void traverseTree(QuadTreeNode t) {
        if (t != null) {
            System.out.println(t);
            traverseTree(t.getChild1());
            traverseTree(t.getChild2());
            traverseTree(t.getChild3());
            traverseTree(t.getChild4());

        }
    }

    public LinkedList < Triangle > extractTriangles() {
        LinkedList < Triangle > ll = new LinkedList < Triangle > ();
        extractTriangles(root, ll);
        return ll;
    }

    private void extractTriangles(QuadTreeNode t, LinkedList < Triangle > ll) {
        if (t != null) {
            ll.add(t.getTriangle());
            extractTriangles(t.getChild1(),ll);
            extractTriangles(t.getChild2(),ll);
            extractTriangles(t.getChild3(),ll);
            extractTriangles(t.getChild4(),ll);
        }
    }

    public LinkedList < BaryCentricPoint >  visitAllPoints() {
        LinkedList < BaryCentricPoint > points = new LinkedList < BaryCentricPoint > ();
        visitAllPoints(root,points);
        return points;
    }

    private void visitAllPoints(QuadTreeNode t,LinkedList < BaryCentricPoint > points) {
        if (t != null) {
            int nPointsInTriangle = (t.getPointsContained());
           
            if (nPointsInTriangle > 1) {
                visitAllPoints(t.getChild1(),points);
                visitAllPoints(t.getChild2(),points);
                visitAllPoints(t.getChild3(),points);
                visitAllPoints(t.getChild4(),points);
                return; 
            }
             if (nPointsInTriangle == 0) {
                return;
            }
            if (nPointsInTriangle == 1) {
                points.add( t.getPoint());
                return;
            }
        }
        return ;
    }

    public LinkedList < BaryCentricPoint >  visitAllPointsIncludingEmptyTriangles() {
        LinkedList < BaryCentricPoint > points = new LinkedList < BaryCentricPoint > ();
        visitAllPointsIncludingEmptyTriangles(root,points);
        return points;
    }

    private void visitAllPointsIncludingEmptyTriangles(QuadTreeNode t,LinkedList < BaryCentricPoint > points) {
        if (t != null) {
            int nPointsInTriangle = (t.getPointsContained());
           
            if (nPointsInTriangle > 1) {
                visitAllPointsIncludingEmptyTriangles(t.getChild1(),points);
                visitAllPointsIncludingEmptyTriangles(t.getChild2(),points);
                visitAllPointsIncludingEmptyTriangles(t.getChild3(),points);
                visitAllPointsIncludingEmptyTriangles(t.getChild4(),points);
                return; 
            }
             if (nPointsInTriangle == 0) {
                points.add(t.getTriangle().getMiddlePoint());
                return;
            }
            if (nPointsInTriangle == 1) {
                points.add( t.getPoint());
                return;
            }
        }
        return ;
    }
    
    public static void main(String args[]) {
        BaryCentricPoint vertex1 = new BaryCentricPoint(1.0,0.0,0.0);
        BaryCentricPoint vertex2 = new BaryCentricPoint(0.0,1.0,0.0);
        BaryCentricPoint vertex3 = new BaryCentricPoint(0.0,0.0,1.0);

        Triangle mainTriangle = new Triangle(vertex1,vertex2,vertex3,true);

        BaryCentricPoint p1 = new BaryCentricPoint(0.3,0.4,0.3);
        BaryCentricPoint p2 = new BaryCentricPoint(0.7,0.2,0.1);
        BaryCentricPoint p3 = new BaryCentricPoint(0.1,0.7,0.2);
        BaryCentricPoint p4 = new BaryCentricPoint(0.2,0.1,0.7);
        BaryCentricPoint p5 = new BaryCentricPoint(0.3,0.3,0.4);

        LinkedList < BaryCentricPoint > ll = new LinkedList < BaryCentricPoint > ();
        ll.add(p1);
        // ll.add(p2);
        // ll.add(p3);
        // ll.add(p4);
        // ll.add(p5);
        System.out.println("Quadtree with 1 point: ");
        System.out.println("List of points: ");
        for(BaryCentricPoint bcp : ll) {
            System.out.println(bcp);
        }
        QuadTree qt = new QuadTree();
        qt.setRoot(createQuadTree(mainTriangle,ll));        
        qt.traverseTree();
        qt.makeEmpty();
        System.out.println("-----------------------");
        System.out.println("Quadtree with 2 points: ");
        ll.add(p2);
        System.out.println("List of points: ");
        for(BaryCentricPoint bcp : ll) {
            System.out.println(bcp);
        }
        qt = new QuadTree();
        qt.setRoot(createQuadTree(mainTriangle,ll));
        qt.traverseTree();
        qt.makeEmpty();
        System.out.println("-----------------------");
        System.out.println("Quadtree with 3 points: ");
        ll.add(p3);
        System.out.println("List of points: ");
        for(BaryCentricPoint bcp : ll) {
            System.out.println(bcp);
        }
        qt = new QuadTree();
        qt.setRoot(createQuadTree(mainTriangle,ll));
        qt.traverseTree();
        qt.makeEmpty();
        System.out.println("-----------------------");
        System.out.println("Quadtree with 4 points: ");
        ll.add(p4);
        System.out.println("List of points: ");
        for(BaryCentricPoint bcp : ll) {
            System.out.println(bcp);
        }
        qt = new QuadTree();
        qt.setRoot(createQuadTree(mainTriangle,ll));
        qt.traverseTree();
        qt.makeEmpty();
        System.out.println("-----------------------");
        System.out.println("Quadtree with 5 points: ");
        ll.add(p5);
        System.out.println("List of points: ");
        for(BaryCentricPoint bcp : ll) {
            System.out.println(bcp);
        }
        qt = new QuadTree();
        qt.setRoot(createQuadTree(mainTriangle,ll));
        qt.traverseTree();
        DisplayForQuadTrees dfqt = new DisplayForQuadTrees(ll,qt);
        JFrame f=new JFrame();  
        f.add(dfqt);  
        f.setSize(DisplayTriangles.SIZE_CANVAS,DisplayTriangles.SIZE_CANVAS);  
        //f.setLayout(null);  
        f.setVisible(true); 

        //qt.makeEmpty();
        System.out.println("-----------------------");
    }
}
