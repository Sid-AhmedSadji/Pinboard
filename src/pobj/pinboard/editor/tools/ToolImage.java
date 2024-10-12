package pobj.pinboard.editor.tools;

import java.io.File;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import pobj.pinboard.document.ClipImage;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolImage implements Tool {
	
	private ClipImage preview ;
	
	public ToolImage(File img ) { preview = new ClipImage(0,0,img) ; }

	@Override
	public void press(EditorInterface i, MouseEvent e) {
		preview = (ClipImage) preview.copy() ;
		preview.setGeometry(e.getX(), e.getY(), 0, 0);
	}

	@Override
	public void drag(EditorInterface i, MouseEvent e) { preview.setGeometry( e.getX(), e.getY(), 0, 0 ) ; }

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		preview.setGeometry(e.getX(), e.getY(), 0, 0);
		CommandAdd toAdd = new CommandAdd( i, preview ) ;
		toAdd.execute() ;
		i.getUndoStack().addCommand(toAdd);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		// Si on veut un carre gc.strokeRect(preview.getLeft(), preview.getTop(), preview.getRight() - preview.getLeft() , preview.getBottom() - preview.getTop() ) ; 
		preview.draw(gc);
	}

	@Override
	public String getName(EditorInterface editor) { return "Image" ; }

}
