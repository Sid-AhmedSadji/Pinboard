package pobj.pinboard.document;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipRect extends AbstractClip implements Clip{
	
	public ClipRect(double left, double top, double right, double bottom, Color color){
		super( left, top, right, bottom ,color);
	}

	@Override
	public void draw(GraphicsContext ctx) { 
		ctx.setFill(getColor());
		ctx.fillRect(super.getLeft(), super.getTop(), super.getRight() - super.getLeft() , super.getBottom() - super.getTop() ) ; 
	}
	
	@Override
	public Clip copy() { return new ClipRect( super.getLeft(), super.getTop(), super.getRight(), super.getBottom(), Color.valueOf( super.getColor().toString()) ) ; }
	
}
