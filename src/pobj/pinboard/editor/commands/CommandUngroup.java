package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;
import pobj.pinboard.document.ClipGroup;
import pobj.pinboard.editor.EditorInterface;

public class CommandUngroup implements Command{
	
	private EditorInterface editor;
	private List<Clip> clipGroup ;
	
	public CommandUngroup(EditorInterface editor, Clip toUngroup) {
		this.editor = editor ;
		clipGroup = new ArrayList<Clip>() ;
		clipGroup.add( toUngroup );
	}
	
	public CommandUngroup (EditorInterface editor, List<Clip> toUngroup) {
		this.editor = editor ;
		clipGroup = new ArrayList<Clip>() ;
		clipGroup.addAll( toUngroup );
	}

	@Override
	public void execute() {
		for (Clip c : clipGroup )
			if ( c instanceof ClipGroup ) {
				ClipGroup tmp = (ClipGroup) c ;
				editor.getBoard().removeClip( tmp );
				editor.getBoard().addClip( tmp.getClips() );
			}
			
	}

	@Override
	public void undo() {
		for (Clip c : clipGroup )
			if ( c instanceof ClipGroup ) {
				ClipGroup tmp = (ClipGroup) c ;
				editor.getBoard().removeClip( tmp.getClips() );
				editor.getBoard().addClip( tmp );
			}
	}
	
}
