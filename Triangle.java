
public class Triangle
{
    private BaryCentricPoint [] vertices = new BaryCentricPoint[3];
    private boolean up = true;

    public Triangle(BaryCentricPoint p1, BaryCentricPoint p2,BaryCentricPoint p3,boolean up) {
        vertices[0] = p1;
        vertices[1] = p2;
        vertices[2] = p3;
        this.up = up;
    }

    public BaryCentricPoint[] getVertices() {
        return vertices;
    }

    public boolean up() {
        return up;
    }

    public String toString() {
        return "Triangle: "+vertices[0].toString()+" "+vertices[1].toString()+" "+vertices[2].toString();
    }

    public BaryCentricPoint getMiddlePoint() {
        if (up) {
            double coor1 = (vertices[0].getd1()+vertices[1].getd1()+vertices[2].getd1()) / 3.0;
            double coor2 = (vertices[0].getd2()+vertices[1].getd2()+vertices[2].getd2()) / 3.0;
            double coor3 = (vertices[0].getd3()+vertices[1].getd3()+vertices[2].getd3()) / 3.0;
            //System.out.println("Center Point: ["+vertices[0]+"]["+vertices[1]+"]["+vertices[2]+"]->"+coor1+","+coor2+","+coor3);
            return new BaryCentricPoint(coor1,coor2,coor3);
        }
        else {
            double coor1 = (vertices[0].getd1()+vertices[1].getd1()+vertices[2].getd1()) / 3.0;
            double coor2 = (vertices[0].getd2()+vertices[1].getd2()+vertices[2].getd2()) / 3.0;
            double coor3 = (vertices[0].getd3()+vertices[1].getd3()+vertices[2].getd3()) / 3.0;
            //System.out.println("Center Point: ["+vertices[0]+"]["+vertices[1]+"]["+vertices[2]+"]->"+coor1+","+coor2+","+coor3);
            return new BaryCentricPoint(coor1,coor2,coor3);
        }
    }
}

