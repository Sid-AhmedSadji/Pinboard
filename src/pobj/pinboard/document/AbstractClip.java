package pobj.pinboard.document;

import java.io.Serializable;

import javafx.scene.paint.Color;



public abstract class AbstractClip implements Clip, Serializable {
	private double top ; 
	private double left ; 
	private double right ; 
	private double bottom ; 
	private SerializableColor color ;
	
	public AbstractClip(double left, double top, double right, double bottom, Color color){
		this.left = left ; 
		this.top = top ; 
		this.right = right ; 
		this.bottom = bottom ;
		this.color = new SerializableColor( color );
	}
	
	public double getTop() { return top ; }

	public double getLeft() { return left ; }

	public double getBottom() { return bottom ; }

	public double getRight() { return right ; }
	
	public void setColor(Color c) { if( c != null ) color.setColor(c) ; }

	public Color getColor() { return color.getColor() ; }
	
	public void move(double x, double y) {
		left += x ;
		right += x ;
		top += y ;
		bottom += y ;
	}
	
	public void setGeometry(double left, double top, double right, double bottom) {
		this.left = left ; 
		this.top = top ; 
		this.right = right ; 
		this.bottom = bottom ;
		if ( left > right ) {
			this.left = right ; 
			this.right = left ;
		}
		if (top > bottom ) {
			this.top = bottom ; 
			this.bottom = top ; 
		}
	}
	
	public boolean isSelected(double x, double y) { return left <= x && x <= right && top <= y && y <= bottom ; }

}
