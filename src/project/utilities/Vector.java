package project.utilities;

public class Vector {

    private double x;
    private double y;
    private double theta;
    private double magnitude;

    /**
     * Creates a Vector object.
     * 
     * @param x
     * @param y
     */
    public Vector(double x, double y)
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


    public double getX() {
        return x;
    }
    public double getY() {
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

    public boolean equals(Vector other) {
        return this.x == other.getX() && this.y == other.getY();
    }
    public String toString() {
        return "<" + x + ", " + y + ">, magnitude: " + magnitude + ", theta: " + theta;
    }
}