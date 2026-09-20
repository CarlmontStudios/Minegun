package project.utilities;

import java.util.Objects;

public class Vector {

    private int x;
    private int y;
    private double theta;
    private double magnitude;

    /**
     * Creates a Vector object.
     * 
     * @param x
     * @param y
     */
    public Vector(int x, int y)
    {
        this.x = x;
        this.y = y;

        this.theta = Math.atan2(y, x);
        this.magnitude = Math.sqrt(x * x + y * y);
    }
    // DOT PRODUCT
    public static double dotProduct(Vector v1, Vector v2)
    {
        double dp = (v1.getX()*v2.getX()) + (v1.getY()*v2.getY());
        // note to self: COMPLETE
        return dp;
    }


    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public double getTheta() {
        return theta;
    }
    public double getMagnitude() {
        return magnitude;
    }

    public Vector add(Vector other) {
        // Sum the weighted components directly
        Vector result = new Vector(0, 0);  // Dummy creation
        result.x = this.x + other.getX();  // Sum weighted x components
        result.y = this.y + other.getY();  // Sum weighted y components
        result.magnitude = Math.sqrt(result.x * result.x + result.y * result.y);  
        result.theta = Math.atan2(result.y, result.x);  // Calculate new angle from summed components
        return result;
    }
    
    /**
     * turns the vector 180 degrees
     */
    public void flip(){
        x *= -1;
        y *= -1;
        theta = Math.atan2(y, x);  // Just recalculate theta, don't call orient()
    }
    @Override 
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vector vector = (Vector) other;
        return x == vector.x && y == vector.y;
    }


    public String toString() {
        return "<" + x + ", " + y + ">";
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}