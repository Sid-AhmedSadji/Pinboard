package pobj.pinboard.editor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pobj.pinboard.document.Board;
import pobj.pinboard.editor.commands.CommandGroup;
import pobj.pinboard.editor.commands.CommandUngroup;
import pobj.pinboard.editor.tools.* ;

public class EditorWindow implements EditorInterface, ClipboardListener, CommandStackListener {
	
	private Board board ; 
	private Tool tool = new ToolRect() ;
	private Selection select = new Selection() ;
	private Menu edit ;
	private Color color = Color.BLACK ;
	private CommandStack pile = new CommandStack() ;

	
	public EditorWindow (Stage stage) {
		//S'ajoute au clipboard 
		Clipboard.getInstance().addListener(this);
		pile.addListener(this);
		
		board = new Board() ;
		
		
		//FileChooser
		FileChooser fileChooser = new FileChooser() ;
		fileChooser.setTitle("Open Ressource File");
		fileChooser.getExtensionFilters().addAll(
				new ExtensionFilter("Image Files","*.png","*.jpg","*.gif"),
				new ExtensionFilter("All Files","*.*")
		);
		
				
		Label label = new Label("Filled rectangle tool");
		
		Canvas canvas = setCanvas();
				
		ToolBar boutons = setBoutons ( label, fileChooser, stage );

		ToolBar colorBotton = setColorBar( canvas ); 
		
		Menu file  = setFile( stage, fileChooser, canvas ) ; 
				
		Menu menuTool = setMenuTool( label, fileChooser, stage) ;
		
		edit = setEdit( canvas );

		MenuBar menu = new MenuBar(file, edit, menuTool);

		VBox vbox = new VBox();	
		vbox.getChildren().addAll( menu, boutons, colorBotton, canvas, label );
		
		stage.setScene(new Scene(vbox));
		stage.show();
	}
	
	private Canvas setCanvas( ) {
		
		Canvas canvas = new Canvas( 800, 600 );
		
		canvas.setOnMousePressed( e-> {
			tool.press( this, e );
		});
		canvas.setOnMouseDragged( e-> {
			tool.drag(this, e);
			draw(canvas.getGraphicsContext2D());
			tool.drawFeedback(this, canvas.getGraphicsContext2D());
		});
		canvas.setOnMouseReleased( e-> {
			tool.release( this, e );
			board.getContents().get( board.getContents().size() - 1 ).setColor(color);
			draw(canvas.getGraphicsContext2D());
		});
		
		return canvas ;
	}
	
	private ToolBar setBoutons( Label label, FileChooser fileChooser, Stage stage ) {
		
		ToolBar boutons = new ToolBar();
		
		Button box = new Button("Box") ;
		Button ellipse = new Button("Ellipse");
		Button image = new Button("Image");
		Button select = new Button("Selecte");
		
		
		boutons.getItems().addAll( box, ellipse, image, select );
		
		box.setOnAction( e-> {
			tool = new ToolRect();
			label.textProperty().set("Filled rectangle tool");
		});
		ellipse.setOnAction( e-> {
			tool = new ToolEllipse();
			label.textProperty().set("Filled ellipse tool");
		});
		image.setOnAction( e-> {
			tool = new ToolImage( fileChooser.showOpenDialog(stage) );
			label.textProperty().set("Image tool");
		});
		select.setOnAction(e->{
			tool = new ToolSelection();
			label.textProperty().set("Selection tool");
		});
		
		return boutons ; 
		
	}
	
	private ToolBar setColorBar( Canvas canvas) {
		
		ToolBar colorBotton = new ToolBar();
		
		Button buttonColor1 = new Button("", new ColorPicker(Color.CYAN) );
		Button buttonColor2 = new Button("", new ColorPicker() );
		
		colorBotton.getItems().addAll( buttonColor1, buttonColor2 );
		
		buttonColor1.setOnAction( e-> {
			color= (Color)((ColorPicker)buttonColor1.getGraphic()).getValue();
			if (tool instanceof ToolSelection)
				this.select.getContents().forEach( c -> c.setColor( color ) );
			draw(canvas.getGraphicsContext2D());
		});
		buttonColor2.setOnAction( e-> {
			color= (Color)((ColorPicker)buttonColor2.getGraphic()).getValue();
			if (tool instanceof ToolSelection)
				this.select.getContents().forEach( c -> c.setColor( color ) );
			draw(canvas.getGraphicsContext2D());
		});
		
		return colorBotton ; 

	}
	
	private Menu setFile( Stage stage, FileChooser fileChooser, Canvas canvas ) {
		Menu file = new Menu("File") ;
		
		MenuItem newWindow = new MenuItem("New");
		MenuItem closeWindow = new MenuItem("Close");
		MenuItem open = new MenuItem("Open");
		MenuItem save = new MenuItem("Save");
				
		newWindow.setOnAction( e -> new EditorWindow( new Stage() ));
		closeWindow.setOnAction( e -> stage.close() );
		open.setOnAction( e->{
			try {
				ObjectInputStream toOpen = new ObjectInputStream( new FileInputStream( fileChooser.showOpenDialog(stage) ));
				board = (Board) toOpen.readObject();
				toOpen.close();
				draw( canvas.getGraphicsContext2D() );
				
			} catch (Exception e1) {
				e1.printStackTrace();
			} 

		});
		save.setOnAction( e -> {
			try {
				ObjectOutputStream toOpen = new ObjectOutputStream( new FileOutputStream( fileChooser.showSaveDialog(stage) ));
				toOpen.writeObject( board );
				toOpen.close();
			}catch (IOException e1) {
				e1.printStackTrace();
			} 
		});
				
		file.getItems().addAll( newWindow, closeWindow, open, save );
		
		return file ;
	}
	
	private Menu setMenuTool(Label label, FileChooser fileChooser, Stage stage) {
		Menu menuTool = new Menu("Tools") ;
		MenuItem menuItemBox = new MenuItem("Box") ;
		MenuItem menuItemEllipse = new MenuItem("Ellipse");
		MenuItem menuItemImage = new MenuItem("Image");
		MenuItem menuItemSelection = new MenuItem("Selection");
		
		menuTool.getItems().addAll( menuItemBox, menuItemEllipse, menuItemImage, menuItemSelection );
		
		menuItemBox.setOnAction( e-> {
			tool = new ToolRect();
			label.textProperty().set("Filled rectangle tool");
		});
		menuItemEllipse.setOnAction( e-> {
			tool = new ToolEllipse();
			label.textProperty().set("Filled ellipse tool"); 
		});
		menuItemImage.setOnAction( e -> {
			tool = new ToolImage( fileChooser.showOpenDialog(stage) );
			label.textProperty().set("Image tool");
		});
		menuItemSelection.setOnAction( e -> {
			tool = new ToolSelection();
			label.textProperty().set("Selection tool");
		});
		
		return menuTool ;
	}
	
	private Menu setEdit( Canvas canvas ) {
		Menu edit = new Menu("Edit") ;
		MenuItem menuItemCopy = new MenuItem("Copy") ;
		MenuItem menuItemPaste = new MenuItem("Paste");
		MenuItem menuItemDelete = new MenuItem("Delete");
		MenuItem menuItemClear = new MenuItem("Clear");
		MenuItem menuItemGroup = new MenuItem("Group");
		MenuItem menuItemUnGroup = new MenuItem("UnGroup");
		MenuItem menuItemUndo = new MenuItem("Undo");
		MenuItem menuItemRedo = new MenuItem("Redo");
		
		menuItemCopy.setOnAction( e-> Clipboard.getInstance().copyToClipboard( this.select.getContents() ));
		menuItemPaste.setOnAction( e-> board.addClip( Clipboard.getInstance().copyFromClipboard() ));
		menuItemDelete.setOnAction( e-> {
			board.removeClip( this.select.getContents() );
			draw(canvas.getGraphicsContext2D());
		});
		menuItemClear.setOnAction( e-> { 
			board.removeClip(board.getContents());
			draw(canvas.getGraphicsContext2D());
		});
		//menuItemClear.setOnAction( e-> Clipboard.getInstance().clear() );
		menuItemGroup.setOnAction( e-> { 
			CommandGroup commandGroup = new CommandGroup( this, this.select.getContents() ) ;
			commandGroup.execute() ;
			pile.addCommand( commandGroup );
			draw(canvas.getGraphicsContext2D());
		});
		menuItemUnGroup.setOnAction( e-> { 
			CommandUngroup commandUngroup = new CommandUngroup( this, this.select.getContents() ) ;
			commandUngroup.execute() ;
			pile.addCommand( commandUngroup );
			draw( canvas.getGraphicsContext2D() );
		});
		menuItemUndo.setOnAction( e-> {
			pile.undo();
			draw( canvas.getGraphicsContext2D() );
		});
		menuItemRedo.setOnAction( e-> {
			pile.redo() ;
			draw( canvas.getGraphicsContext2D() );
		});
		
		if ( Clipboard.getInstance().isEmpty() )
			menuItemUndo.setDisable( true ); 
		if ( pile.isRedoEmpty() )
			menuItemPaste.setDisable( true ); 
		if ( pile.isUndoEmpty() )
			menuItemUndo.setDisable( true ); 
		
		edit.getItems().addAll( menuItemCopy, menuItemPaste, menuItemDelete, menuItemClear,menuItemGroup,menuItemUnGroup, menuItemUndo, menuItemRedo );
		
		return edit ;
	}
	
	public void draw(GraphicsContext gc) { board.draw(gc) ; }

	@SuppressWarnings("exports")
	@Override
	public Board getBoard() { return board ; }

	@Override
	public Selection getSelection() { return select ; }

	@Override
	public CommandStack getUndoStack() { return pile ; }

	@Override
	public void clipboardChanged() {
		if ( Clipboard.getInstance().isEmpty() )
			edit.getItems().get( 1 ).setDisable( true );
		else 
			edit.getItems().get( 1 ).setDisable( false );
		
	}

	public Color getColor() { return color ; }

	@Override
	public void CommandStackChanged() {
		if ( pile.isRedoEmpty() )
			edit.getItems().get( 7 ).setDisable( true );
		else
			edit.getItems().get( 7 ).setDisable( false );
		if ( pile.isUndoEmpty() )
			edit.getItems().get( 6 ).setDisable( true );
		else
			edit.getItems().get( 6 ).setDisable( false );
	}
	

}
