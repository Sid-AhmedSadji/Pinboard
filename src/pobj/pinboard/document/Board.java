package pobj.pinboard.document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Board implements Clip, Serializable{
	
	private List<Clip> list ;
	
	public Board () {
		list = new ArrayList<Clip>() ; 
	}
	
	public List<Clip> getContents() { return list ; }
	
	public void addClip( Clip clip ) { list.add( clip ) ; }
	
	public void addClip( List<Clip> clip ) { list.addAll( clip ) ; }
	
	public void removeClip( Clip clip ) { list.remove( clip ) ; } ;
	
	public void removeClip( List<Clip> clip ) { list.removeAll( clip ) ; } ;

	@Override
	public void draw(GraphicsContext ctx) {
		ctx.setFill(Color.WHITE);
		ctx.fillRect(0, 0, ctx.getCanvas().getWidth() , ctx.getCanvas().getHeight() ) ;	
		for (Clip c : list )
			c.draw(ctx);
	}

	@Override
	public double getTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getBottom() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getRight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setGeometry(double left, double top, double right, double bottom) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void move(double x, double y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isSelected(double x, double y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColor(Color c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Color getColor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Clip copy() {
		// TODO Auto-generated method stub
		return null;
	}

}
