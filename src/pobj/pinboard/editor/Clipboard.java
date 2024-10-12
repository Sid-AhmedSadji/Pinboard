package pobj.pinboard.editor;

import java.util.ArrayList;
import java.util.List;

import pobj.pinboard.document.Clip;

public class Clipboard {
	
	private static Clipboard singleton = null  ; 
	private List<ClipboardListener> listListener ;
	private List<Clip> liste ; 
	
	
	private Clipboard () {
		liste = new ArrayList<Clip>() ;
		listListener = new ArrayList<ClipboardListener>() ;
	}
	
	public static Clipboard getInstance() {
		if ( singleton == null )
			singleton = new Clipboard() ; 
		return singleton ;
	}
	
	@SuppressWarnings("exports")
	public void copyToClipboard(  List<Clip> clips ) { 
		liste.clear();
		for (Clip c : clips )
			liste.add(c.copy());
		listListener.forEach(l -> l.clipboardChanged() );
	}
	
	@SuppressWarnings("exports")
	public List<Clip> copyFromClipboard() { 
		List<Clip> copy = new ArrayList<Clip>() ;
		for (Clip c : liste )
			copy.add(c.copy());
		return copy ; 
	}
	
	public void clear() { 
		liste.clear() ; 
		listListener.forEach(l -> l.clipboardChanged() );
	}
	
	public boolean isEmpty() { return liste.isEmpty() ; }
	
	public void addListener( ClipboardListener listener ) { listListener.add( listener ) ; }
	
	public void removeListener( ClipboardListener listener ) { listListener.remove( listener ) ; }
	
}
