   class Line {
        double a, b, c;
        Line(double x1, double y1, double x2, double y2){
            a = y1-y2;
            b = x2-x1;
            c = -(a*x1+b*y1);
        }
        Line(){}
       
        /**
         * perpendicular line going through (x, y)
         */
        Line perp(double x, double y){
            Line ret = new Line();
            ret.a = b;
            ret.b = -a;
            ret.c = -(ret.a*x + ret.b*y);
            return ret;
        }
        double[] intersect(Line other){
            double det = a*other.b - b*other.a;
            double[] ret = new  double[2];
            ret[0] = -(other.b*c - b*other.c)/det;
            ret[1] = -(a*other.c - other.a*c)/det;
            return ret;
        }
        @Override
        public String toString() {
            return "Line{a:" + a + ", b:" + b + ", c:" + c + "}";
        }
    }

class Circle {
        double x, y, r;
       
        /**
         * Sets the circle coords and the radius
         */
        public Circle(double x1, double y1, double x2, double y2, double x3, double y3) {
            Line ab = new Line(x1, y1, x2, y2);
            Line bc = new Line(x1, y1, x3, y3);
            Line abp = ab.perp((x1 + x2)/2.0, (y1 + y2)/2.0);
            Line bcp = bc.perp((x1 + x3)/2.0, (y1 + y3)/2.0);
            double[] center = abp.intersect(bcp);
            x = center[0];
            y = center[1];
           
           
            double xdiff = x - x1;
            double ydiff = y - y1;
            r = Math.sqrt(xdiff*xdiff + ydiff*ydiff);
           
            xdiff = x - x2;
            ydiff = y - y2;
            r = Math.min(r, Math.sqrt(xdiff*xdiff + ydiff*ydiff));
           
            xdiff = x - x3;
            ydiff = y - y3;
            r = Math.min(r, Math.sqrt(xdiff*xdiff + ydiff*ydiff));
           
        }
    }
