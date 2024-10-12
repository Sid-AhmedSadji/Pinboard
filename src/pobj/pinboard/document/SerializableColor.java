package pobj.pinboard.document;

import java.io.Serializable;
import javafx.scene.paint.Color;

public class SerializableColor implements Serializable {
	private double red ;
	private double green ; 
	private double blue ;
	private double opacity ;
	
	public SerializableColor(double r, double g, double b, double o ) {
		red = r ; 
		green = g ; 
		blue = b ; 
		opacity = o ;
	}
	
	public SerializableColor ( Color c ) {	
		this ( 0, 0, 0, 0) ;
		if ( c != null )
			setColor( c );
	}
	
	public Color getColor() { return new Color( red, green, blue, opacity) ; }
	
	public void setColor( Color c ) { 
		red = c.getRed();
		green = c.getGreen();
		blue = c.getBlue();
		opacity = c.getOpacity();
	}
}
