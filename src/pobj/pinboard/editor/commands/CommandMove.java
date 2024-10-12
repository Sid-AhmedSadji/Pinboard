package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandMove implements Command{
	
	private List<Clip> toMove ;
	double x, y ;
	
	public CommandMove(EditorInterface editor, Clip toMove, double X, double Y ) {
		this.toMove = new ArrayList<Clip>() ;
		this.toMove.add( toMove );
		x = X ;
		y = Y ;
		
	}
	
	public CommandMove(EditorInterface editor, List<Clip> toMove, double X, double Y ) {
		this.toMove = new ArrayList<Clip>() ;
		this.toMove.addAll( toMove );
		x = X ;
		y = Y ;
	}

	@Override
	public void execute() {
		for ( Clip c : toMove )
			c.move(x,y);
	}

	@Override
	public void undo() {
		for ( Clip c : toMove )
			c.move(-x,-y);
	}

}
