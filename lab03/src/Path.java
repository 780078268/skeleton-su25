/** A class that represents a path via pursuit curves. */
public class Path {

    // TODO
    Point curr;
    Point next;
    public Path(double x, double y) {
        curr = new Point(x, y);
        next = new Point(x, y);
    }
    public double getCurrX(){
        return curr.getX();
    }
    public double getCurrY(){
        return curr.getY();
    }
    public double getNextX(){
        return next.getX();
    }
    public double getNextY(){
        return next.getY();
    }
    public Point getCurrentPoint(){
        return curr;
    }
    public void setCurrentPoint(Point point){
        curr = point;
    }
    public void iterate(double dx, double dy){
        curr = new Point(next);
        next.setX(next.getX() + dx);
        next.setY(next.getY() + dy);
    }
}
