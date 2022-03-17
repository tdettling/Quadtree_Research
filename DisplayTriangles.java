
import java.awt.*; 
import java.awt.geom.*; 
import javax.swing.JFrame;  
import java.util.*;

//tester for commit

public class DisplayTriangles extends Canvas{  
    public static final int SIZE_CANVAS = 700;  
    private static final double TRIANGLE_HEIGHT = 443.0;
    private static final double TRIANGLE_SIZE = 512.0;
    private static final Point2D.Double coordinate0 = new Point2D.Double(100.0,500.0);
    private static final Point2D.Double coordinate1 = new Point2D.Double(612.0,500.0);
    private static final Point2D.Double coordinate2 = new Point2D.Double(356.0,57.0);
    private static final Point2D.Double [] mainTriangle = {coordinate0,coordinate1,coordinate2};
    public void paint(Graphics g2) {
        Graphics2D g = (Graphics2D) g2;
        setBackground(Color.WHITE);
        setForeground(Color.BLUE);
        // Interesting classes:
        //Point2D.Double point = new Point2D.Double(0.0,0.0);
        drawMainTriangle( g);


    }  

    
    
    public void drawMainTriangle(Graphics2D g) {
        // g.draw(new Line2D.Double(mainTriangle[0],mainTriangle[1]));
        // g.draw(new Line2D.Double(mainTriangle[1],mainTriangle[2]));
        // g.draw(new Line2D.Double(mainTriangle[2],mainTriangle[0]));
        BaryCentricPoint p1 = fromCartesian(mainTriangle[0]);       
        BaryCentricPoint p2 = fromCartesian(mainTriangle[1]);        
        BaryCentricPoint p3 = fromCartesian(mainTriangle[2]);
        Triangle mainTriangle = new Triangle(p1,p2,p3,true);
        // BaryCentricPoint middle1 = BaryCentricPoint.middlePoint(p1,p2);
        // BaryCentricPoint middle2 = BaryCentricPoint.middlePoint(p2,p3);
        // BaryCentricPoint middle3 = BaryCentricPoint.middlePoint(p1,p3);
        // System.out.println(middle1);
        // System.out.println(middle2);
        // System.out.println(middle3);
        // Triangle t = new Triangle(middle1,middle2,middle3,true);
        drawTriangle(mainTriangle,g);
        LinkedList < Triangle > subTrianglesToDraw = new LinkedList < Triangle > ();
        calculateSubTriangles(mainTriangle, subTrianglesToDraw, 4);
        for(Triangle t : subTrianglesToDraw) {
            drawTriangle(t,g);
        }
    }
    
    public void calculateSubTriangles(Triangle t,LinkedList < Triangle > list,int depth) {
        if (depth == 0) {
            return;
        }
        BaryCentricPoint p[] = t.getVertices();
        
        BaryCentricPoint middle1 = BaryCentricPoint.middlePoint(p[0],p[1]);
        BaryCentricPoint middle2 = BaryCentricPoint.middlePoint(p[1],p[2]);
        BaryCentricPoint middle3 = BaryCentricPoint.middlePoint(p[0],p[2]);
        
        Triangle t1 = new Triangle(p[0],middle1,middle3,true);
        Triangle t2 = new Triangle(middle1,p[1],middle2,true);
        Triangle t3 = new Triangle(middle1,middle2,middle3,false);
        Triangle t4 = new Triangle(middle3,middle2,p[2],true);
        
        list.add(t1);
        list.add(t2);
        list.add(t3);
        list.add(t4);
        calculateSubTriangles(t1, list,depth-1);
        calculateSubTriangles(t2, list,depth-1);
        calculateSubTriangles(t3, list,depth-1);
        calculateSubTriangles(t4, list,depth-1);
    } 
    
    public void drawTriangle(Triangle t,Graphics2D g) {
        BaryCentricPoint [] baryVertices = t.getVertices();
        Point2D.Double vertex1 = fromBaryCentricPoint(baryVertices[0]);
        Point2D.Double vertex2 = fromBaryCentricPoint(baryVertices[1]);
        Point2D.Double vertex3 = fromBaryCentricPoint(baryVertices[2]);
        g.draw(new Line2D.Double(vertex1,vertex2));
        g.draw(new Line2D.Double(vertex2,vertex3));
        g.draw(new Line2D.Double(vertex1,vertex3));
    }

    public Point2D.Double fromBaryCentricPoint(BaryCentricPoint p) {
        double x = 0.0;
        double y = 0.0;
        x = x + p.getd1()*mainTriangle[0].getX();
        x = x + p.getd2()*mainTriangle[1].getX();
        x = x + p.getd3()*mainTriangle[2].getX();
        y = y + p.getd1()*mainTriangle[0].getY();
        y = y + p.getd2()*mainTriangle[1].getY();
        y = y + p.getd3()*mainTriangle[2].getY();    
        return new Point2D.Double(x,y);
    }

    public static BaryCentricPoint fromCartesian(Point2D.Double p) {
        double x = p.getX();
        double y = p.getY();
        double lambda1,lambda2,lambda3;
        double y1,y2,y3,x1,x2,x3;
        y1 = mainTriangle[0].getY();
        y2 = mainTriangle[1].getY();
        y3 = mainTriangle[2].getY();
        x1 = mainTriangle[0].getX();
        x2 = mainTriangle[1].getX();
        x3 = mainTriangle[2].getX();
        // These equations were taken from: 
        // https://en.wikipedia.org/wiki/Barycentric_coordinate_system
        lambda1 = (( y2 - y3 ) * (x  - x3) + (x3 - x2)*(y  - y3)) /
        (( y2 - y3 ) * (x1 - x3) + (x3 - x2)*(y1 - y3));
        lambda2 = (( y3 - y1 ) * (x  - x3) + (x1 - x3)*(y  - y3))/
        (( y2 - y3 ) * (x1 - x3) + (x3 - x2)*(y1 - y3));
        lambda3 = 1 - lambda1 - lambda2;
        return new BaryCentricPoint(lambda1,lambda2,lambda3);
    }

   
    public static void main(String[] args) {  
        DisplayTriangles m=new DisplayTriangles();  
        JFrame f=new JFrame();  
        f.add(m);  
        f.setSize(SIZE_CANVAS,SIZE_CANVAS);  
        //f.setLayout(null);  
        f.setVisible(true); 
        
        // BaryCentricPoint p1 = m.fromCartesian(mainTriangle[0]);
        // System.out.println(p1);
        // BaryCentricPoint p2 = m.fromCartesian(mainTriangle[1]);
        // System.out.println(p2);
        // BaryCentricPoint p3 = m.fromCartesian(mainTriangle[2]);
        // System.out.println(p3);
        // Point2D.Double d1 = m.fromBaryCentricPoint(p1);
        // System.out.println(d1);
        // Point2D.Double d2 = m.fromBaryCentricPoint(p2);
        // System.out.println(d2);
        // Point2D.Double d3 = m.fromBaryCentricPoint(p3);
        // System.out.println(d3);
        
        
        

    }

}  