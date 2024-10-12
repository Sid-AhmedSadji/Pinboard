package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.editor.commands.Command;

public class CommandStack {
	private List<Command> undo = new ArrayList<Command>();
	private List<Command> redo = new ArrayList<Command>();
	private List<CommandStackListener> listListener = new ArrayList<CommandStackListener>();;
	
	public void addCommand(Command cmd) {
		undo.add( cmd );
		redo.clear();
		listListener.forEach(l -> l.CommandStackChanged() );
	}
	
	public void undo() {
		undo.get( undo.size() - 1 ).undo();
		redo.add( undo.get( undo.size() - 1 ) );
		undo.remove( undo.size() - 1 );
		listListener.forEach(l -> l.CommandStackChanged() );
	}
	
	public void redo()
	{
		redo.get( redo.size() - 1 ).execute();
		undo.add( redo.get( redo.size() - 1 ) );
		redo.remove( redo.size() - 1 );
		listListener.forEach(l -> l.CommandStackChanged() );
	}
	
	public boolean isUndoEmpty() { return undo.size() == 0 ; }
	
	public boolean isRedoEmpty() { return redo.size() == 0 ; }
	
	public void addListener( CommandStackListener  listener ) { listListener.add( listener ) ; }

	public void removeListener( CommandStackListener  listener ) { listListener.remove( listener ) ; }

}
