package pobj.pinboard.main;

import pobj.pinboard.editor.EditorWindow;

public class Main extends javafx.application.Application  {
	
	@SuppressWarnings("unused")
	@Override
	public void start(javafx.stage.Stage stage) throws Exception {
		EditorWindow ed = new EditorWindow(stage) ;
	}

	public static void main(String[] args) {
		launch(args);

	}

}