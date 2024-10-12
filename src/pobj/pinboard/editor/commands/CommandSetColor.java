package pobj.pinboard.editor.commands;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.paint.Color;
import pobj.pinboard.document.Clip;
import pobj.pinboard.editor.EditorInterface;

public class CommandSetColor implements Command{
	private List<Clip> listeAction ; 
	private Color [] oldColor ;
	private Color newColor ;
	
	public CommandSetColor(EditorInterface editor, Color color) {
		listeAction = new ArrayList<Clip>( editor.getSelection().getContents() );
		oldColor = new Color[ listeAction.size()] ;
		for ( int i = 0 ; i < listeAction.size(); i++)
			oldColor[i] = listeAction.get(i).getColor() ;
		newColor = color ;
	}

	@Override
	public void execute() {
		listeAction.forEach( c -> c.setColor( newColor ) );
		
	}

	@Override
	public void undo() {
		for ( int i = 0 ; i < listeAction.size(); i++)
			listeAction.get(i).setColor( oldColor[i]);
	}
}
