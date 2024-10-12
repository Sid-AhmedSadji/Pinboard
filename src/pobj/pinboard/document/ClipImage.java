package pobj.pinboard.document;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.paint.Color;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class ClipImage extends AbstractClip implements Clip {
	private transient Image img ; 
	private String filePath ;
	
	public ClipImage( double left, double top, File filename ) {
		super(0,0,0,0,Color.BLACK);
		try {
			img = new Image( new FileInputStream( filename.getAbsolutePath() ));
			filePath = filename.getAbsolutePath() ;
			super.setGeometry( left, top, left + img.getWidth(), top + img.getHeight() );
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ClipImage( double left, double top, Image img, String filePath) {
		super(0,0,0,0,Color.BLACK);
		super.setGeometry( left, top, left + img.getWidth(), top + img.getHeight() );
		this.img=img ;
		this.filePath = filePath ;

	}
	
	@Override 
	public void setGeometry(double left, double top, double right, double bottom) {
		super.setGeometry(left, top, left + img.getWidth(), top + img.getHeight() );
	}
	
	@Override
	public void draw(GraphicsContext ctx) { 
		try {
			if (img == null )
				img = new Image( new FileInputStream( filePath ));
			ctx.drawImage( img, super.getLeft(), super.getTop() ); 
		}catch(Exception e ) {
			
		}
	}

	@Override
	public Clip copy() { return new ClipImage( super.getLeft(), super.getTop(), img, filePath ); }

}
