package pobj.pinboard.editor.tools;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandMove;

public class ToolSelection implements Tool {
	private double x, y, xO, yO ;
	
	@Override
	public void press(EditorInterface i, MouseEvent e) { 
		x = e.getX();
		y = e.getY();
		xO = e.getX();
		yO = e.getY();
		if ( e.isShiftDown() )
			i.getSelection().toogleSelect( i.getBoard(), e.getX(), e.getY() ) ;
		else 
			i.getSelection().select( i.getBoard(), e.getX(), e.getY() ) ; 
		if ( e.isAltDown() )
			i.getBoard().removeClip( i.getSelection().getContents() );
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) {
		for ( Clip c : i.getSelection().getContents() )
			c.move( e.getX() - x, e.getY() - y );	
		x = e.getX();
		y = e.getY();
	}

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		for ( Clip c : i.getSelection().getContents() )
			c.move( xO - e.getX(), yO - e.getY() );
		
		CommandMove toMove = new CommandMove( i, i.getSelection().getContents(), e.getX() - xO, e.getY() - yO );
		toMove.execute();
		i.getUndoStack().addCommand(toMove);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getName(EditorInterface editor) {
		// TODO Auto-generated method stub
		return null;
	}

}
