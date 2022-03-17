 public  class BaryCentricPoint {
        double d1;
        double d2;
        double d3;
        BaryCentricPoint( double d1,double d2,double d3) {
            this.d1 = d1;
            this.d2 = d2;
            this.d3 = d3;
        }
        
        public double getd1(){
            return d1;
        }
        public double getd2() {
            return d2;
        }
        public double getd3() {
            return d3;
        }
        
        public String toString() {
            return d1+" "+d2+" "+d3;
        }
        
        public static BaryCentricPoint middlePoint(BaryCentricPoint p1, BaryCentricPoint p2) {
            double d1;
            double d2;
            double d3;
            if (p1.getd1() == p2.getd1()) {
                d1 = p1.getd1();
            }
            else {
                d1 = (p1.getd1() + p2.getd1())/2.0;
            }
            if (p1.getd2() == p2.getd2()) {
                d2 = p1.getd2();
            }
            else {
                d2 = (p1.getd2() + p2.getd2())/2.0;
            }
             if (p1.getd3() == p2.getd3()) {
                d3 = p1.getd3();
            }
            else {
                d3 = (p1.getd3() + p2.getd3())/2.0;
            }
            
            
            return new BaryCentricPoint(d1,d2,d3);
        }
    }