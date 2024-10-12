package pobj.pinboard.document;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ClipGroup extends AbstractClip implements Composite {
	
	private List<Clip> liste = new ArrayList<Clip>() ;
	
	public ClipGroup( ) {
		super(0., 0., 0., 0.,null) ;
	}
	
	private void setRectangleEnglobant() {
		if (liste.size()==0) {
			super.setGeometry(0, 0, 0, 0);
			return ;
		}

		super.setGeometry(liste.get(0).getLeft(), liste.get(0).getTop(), liste.get(0).getRight(), liste.get(0).getBottom());
			
		for (Clip c : liste ) {
			super.setGeometry(
					Math.min( super.getLeft(), c.getLeft() ),
					Math.min( super.getTop(), c.getTop() ),
					Math.max( super.getRight(), c.getRight() ),
					Math.max( super.getBottom(), c.getBottom() )
					);
		}
	}


	@Override
	public void draw( GraphicsContext ctx ) {
		for ( Clip c : liste )
			c.draw( ctx ) ;
	}
	
	@Override
	public Clip copy() {
		ClipGroup cg = new ClipGroup();
		for ( Clip c : liste )
			cg.addClip( c.copy() );
		return cg ;
	}

	@Override
	public List<Clip> getClips() { return liste ; }

	@Override
	public void addClip(Clip toAdd) { 
		liste.add( toAdd ) ;
		setRectangleEnglobant();
		
	}

	@Override
	public void removeClip(Clip toRemove) { 
		liste.remove( toRemove ) ;
		setRectangleEnglobant();

	}
	
	@Override
	public void setColor( Color color) {
		for (Clip c : liste )
			c.setColor(color);
	}
	
	@Override
	public void move(double x, double y) {
		for (Clip c : liste ) 
			c.move(x,y);
		setRectangleEnglobant();
	}
	
	public void viderListe() {
		while (liste.size() > 0 )
			liste.remove(0);
	}

}
