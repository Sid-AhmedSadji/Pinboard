package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipEllipse extends AbstractClip implements Clip{
	
	public ClipEllipse (double left, double top, double right, double bottom, Color color){
		super( left, top, right, bottom ,color);
	}

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(super.getColor());
		ctx.fillOval( super.getLeft(), super.getTop(), super.getRight() -  super.getLeft(), super.getBottom() - super.getTop() );
	}

	@Override
	public Clip copy() { return new ClipEllipse( super.getLeft(), super.getTop(), super.getRight(), super.getBottom(), Color.valueOf( super.getColor().toString()) ) ; }
	
	public double pow ( double a ) { return a * a ; } 
	
	@Override
	public boolean isSelected( double x, double y ){ return (( pow (( x -(( super.getLeft() + super.getRight() )/ 2 ))/(( super.getRight() - super.getLeft())/ 2 )))+( pow (( y -(( super.getTop() + super.getBottom() )/ 2 ))/(( super.getBottom() - super.getTop())/ 2 )))) <= 1 ; }

	
}
