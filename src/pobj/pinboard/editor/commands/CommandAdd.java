package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandAdd implements Command{
	
	private List<Clip> listeAction ; 
	private EditorInterface editor;
	
	public CommandAdd(EditorInterface editor, Clip toAdd) {
		listeAction = new ArrayList<Clip>();
		listeAction.add( toAdd );
		this.editor = editor ;
		
	}
	
	public CommandAdd(EditorInterface editor, List<Clip> toAdd) {
		listeAction = new ArrayList<Clip>();
		listeAction.addAll( toAdd );
		this.editor = editor ;
	}

	@Override
	public void execute() {
		editor.getBoard().addClip( listeAction );
		
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub
		editor.getBoard().removeClip( listeAction );
	}

}
