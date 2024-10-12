package pobj.pinboard.editor.commands;

import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandGroup implements Command{
	
	private EditorInterface editor;
	private ClipGroup clipGroup ;
	
	public CommandGroup(EditorInterface editor, Clip toGroup) {
		this.editor = editor ;
		clipGroup = new ClipGroup() ; 
		clipGroup.addClip( toGroup );
		
	}
	
	public CommandGroup(EditorInterface editor, List<Clip> toGroup) {
		this.editor = editor ;
		clipGroup = new ClipGroup() ; 
		for (Clip c : toGroup )
			clipGroup.addClip( c );
	}

	@Override
	public void execute() {
		for (Clip c : clipGroup.getClips() )
			editor.getBoard().removeClip( c );
		editor.getBoard().addClip( clipGroup );
			
	}

	@Override
	public void undo() {
		editor.getBoard().addClip( clipGroup.getClips() );
		editor.getBoard().removeClip( clipGroup );
		
	}

}
