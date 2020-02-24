package edu.ycp.cs201.disks;

/**
 * An instance of the Disk class represents a disk
 * to be placed on the game board.
 */
public class Disk {
	// TODO: add fields.
	// You need to store information about the
	// Disk's x and y coordinate, its radius,
	// and its color.

	private double xLoc,yLoc;
	private double radius;
	private DiskColor color;
	
	
	/**
	 * Constructor.
	 * Store x, y, radius, and Color values in the fields
	 * of the object being initialized.
	 * 
	 * @param x the x coordinate of the new Disk
	 * @param y the y coordinate of the new Disk
	 * @param radius the radius of the new Disk
	 * @param color the color of the new Disk
	 */
	public Disk(double x, double y, double radius, DiskColor color) {
		this.xLoc = x;
		this.yLoc = y;
		this.radius = radius;
		this.color = color;
	}
	
	/**
	 * @return the Disk's x coordinate
	 */
	public double getX() {
		 return xLoc;
	}
	
	/**
	 * @return the Disk's y coordinate
	 */
	public double getY() {
		return yLoc;
	}
	
	/**
	 * @return the Disk's radius
	 */
	public double getRadius() {
		return radius;
	}
	
	/**
	 * @return the Disk's color
	 */
	public DiskColor getColor() {
		return color;
	}
	
	/**
	 * Return true if this Disk overlaps
	 * the Disk given as the parameter, false otherwise.
	 * @param other another Disk
	 * @return true if the two Disks overlap, false if they don't
	 */
	public boolean overlaps(Disk other) {
		double distBetween;
		distBetween=Math.sqrt(((other.xLoc-xLoc)*(other.xLoc-xLoc))+((other.yLoc-yLoc)*(other.yLoc-yLoc)));
		
		if(distBetween<radius+other.radius) {
			return true;
		}
		else {
			return false;
		}
	}

	/**
	 * Return true if this Disk is out of bounds, meaning that
	 * it is not entirely enclosed by rectangle whose width and
	 * height are given by the two parameters. 
	 * Assume that the upper-left hand corner of the rectangle
	 * is at (0,0), that x coordinates increase going to
	 * the right, and that y coordinates increase going down.
	 * 
	 * @param width   the width of a rectangle
	 * @param height  the height of a rectangle
	 * @return false if the Disk fits entirely within the rectangle,
	 *         true if at least part of the Disk lies outside the
	 *         rectangle
	 */
	public boolean isOutOfBounds(double width, double height) {
		
		double leftEdge,rightEdge,topEdge,botEdge;
		
		leftEdge=xLoc-radius;
		rightEdge=xLoc+radius;
		topEdge=yLoc-radius;
		botEdge=yLoc+radius;
		
		if(leftEdge<0) {
			return true;
		}
		else if(rightEdge>width) {
			return true;
		}
		else if(topEdge<0) {
			return true;
		}
		else if(botEdge>height) {
			return true;
		}
		else {
			return false;
		}
	}
}
