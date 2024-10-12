package pobj.pinboard.editor.tools;

//import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import pobj.pinboard.document.ClipRect;
import pobj.pinboard.editor.EditorInterface;
import pobj.pinboard.editor.commands.CommandAdd;

public class ToolRect implements Tool {
	
	private ClipRect preview ;
	private double x , y ; 


	@Override
	public void press(EditorInterface i, MouseEvent e) {
		x = e.getX() ; 
		y = e.getY() ; 
		preview = new ClipRect( e.getX(), e.getX(), e.getX(), e.getX(), Color.BLACK ) ;
	}
	
	public void drag(EditorInterface i, MouseEvent e) { preview.setGeometry( x, y, e.getX(), e.getY() ) ; }

	@Override
	public void release(EditorInterface i, MouseEvent e) {
		preview.setGeometry( x, y, e.getX(), e.getY() );
		CommandAdd toAdd = new CommandAdd( i, preview ) ;
		toAdd.execute() ;
		i.getUndoStack().addCommand(toAdd);
	}

	@Override
	public void drawFeedback(EditorInterface i, GraphicsContext gc) {
		/*
		 * FUN
		 * Random rand = new Random();
		 * gc.setStroke(Color.color( rand.nextFloat(), rand.nextFloat(), rand.nextFloat() ));
		 */
		gc.setLineWidth(5);
		gc.strokeRect(preview.getLeft(), preview.getTop(), preview.getRight() - preview.getLeft() , preview.getBottom() - preview.getTop() ) ; 
	}

	@Override
	public String getName(EditorInterface editor) { return "Rectangle" ;	}

}
