package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pobj.pinboard.document.Board;
import pobj.pinboard.document.Clip;

public class Selection {
	private List <Clip> liste = new ArrayList<Clip>() ;
	
	@SuppressWarnings("exports")
	public void select ( Board board, double x, double y ) {
		liste.clear() ;
		for ( Clip c : board.getContents() )
			if ( c.isSelected( x, y ) ) {
				liste.add( c );
				break ; 
			}
		
	}
	
	@SuppressWarnings("exports")
	public void toogleSelect(Board board, double x, double y) {
		for ( Clip c : board.getContents() ) 
			if ( c.isSelected(x, y ) ){
				if ( liste.contains( c ) )
					liste.remove( c );
				else 
					liste.add( c );
			}
	}
	
	public void clear() { liste.clear() ; }
	
	@SuppressWarnings("exports")
	public List<Clip> getContents () { return liste ; }
	
	public void drawFeedBack( GraphicsContext gc ) {
		gc.setStroke(Color.BLUE);
		for ( Clip c : liste ) 
			gc.strokeRect( c.getLeft(), c.getTop(), c.getRight() -  c.getLeft(), c.getBottom() - c.getTop() );
	}
	
	
}
