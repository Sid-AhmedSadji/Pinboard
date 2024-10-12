package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandClear implements Command
{
	private List<Clip> listeAction ; 
	private EditorInterface editor;
	
	public CommandClear(EditorInterface editor, Clip toClear) {
		listeAction = new ArrayList<Clip>();
		listeAction.add( toClear );
		this.editor = editor ;
		
	}
	
	public CommandClear(EditorInterface editor) {
		listeAction = new ArrayList<Clip>();
		listeAction.addAll( editor.getBoard().getContents() );
		this.editor = editor ;
	}

	@Override
	public void execute() {
		editor.getBoard().removeClip( listeAction );
	}

	@Override
	public void undo() {
		editor.getBoard().addClip( listeAction );
	}
	
}
