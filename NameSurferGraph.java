/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas
	implements NameSurferConstants, ComponentListener {

	/**
	* Creates a new NameSurferGraph object that displays the data.
	*/
	public NameSurferGraph() {
		addComponentListener(this);
		this.initColors();
	}
	
	/**
	* Clears the list of name surfer entries stored inside this class.
	*/
	public void clear() {
		if (!this.namesToGraph.isEmpty()) {
			this.namesToGraph.clear();
		}
		this.numGraphed = 0;
	}
	
	/* Method: addEntry(entry) */
	/**
	* Adds a new NameSurferEntry to the list of entries on the display.
	* Note that this method does not actually draw the graph, but
	* simply stores the entry; the graph is drawn by calling update.
	*/
	public void addEntry(NameSurferEntry entry) {
		this.namesToGraph.add(entry);
	}
	
	
	/**
	* Updates the display image by deleting all the graphical objects
	* from the canvas and then reassembling the display according to
	* the list of entries. Your application must call update after
	* calling either clear or addEntry; update is also called whenever
	* the size of the canvas changes.
	*/
	public void update() {
		this.removeAll();
		this.numGraphed = 0;
		this.drawBackground();
		if (!this.namesToGraph.isEmpty()) {
			this.graphAllNames();
		}
		//	 You fill this in //
	}

	/** draws the grid lines for the graph with labels for each decade */
	private void drawBackground() {
		
		// vertical grid lines across graph for each decade with labels
		for (int i = 0; i < NDECADES; i++) {
			double xDoub = 0 + ( i * this.getWidth() * (1.0/11) );
			int x = (int)xDoub;
			this.drawLine(x, 0, x, this.getHeight(), Color.BLACK);
			this.drawLabel(x, this.getHeight() - GRAPH_MARGIN_SIZE/2, Integer.toString(convertDecade(i)), FONT);
		}
	
		// horizontal border lines on top and bottom
		double y = this.getHeight() - GRAPH_MARGIN_SIZE;
		this.drawLine(0, (int)y, this.getWidth(), (int)y, Color.BLACK);
		y = 0 + GRAPH_MARGIN_SIZE;
		this.drawLine(0, (int)y, this.getWidth(), (int)y, Color.BLACK);
	}
	
	/** graphs all the names entered so far (stored by NameSurferGraph) */
	private void graphAllNames() {
		Iterator<NameSurferEntry> iter = this.namesToGraph.iterator();
		while (iter.hasNext()) {
			this.graphName(iter.next());
		}
	}
	
	/** graphs a single entry */
	private void graphName(NameSurferEntry entry) {
		for (int i = 0; i < NDECADES - 1; i++) {
			// x coordinates depend on decade
			double x1Doub = 0 + ( i * this.getWidth() * (1.0/11) );
			double x2Doub = 0 + ( (i+1) * this.getWidth() * (1.0/11) );
			int x1 = (int)x1Doub;
			int x2 = (int)x2Doub;
			
			// y coordinates depend on rank & decade
			int y1;
			int y2;
			if (entry.getRank(i) == 0) {
				y1 = this.getHeight() - GRAPH_MARGIN_SIZE-1;
			}
			else {
				double y1Doub = GRAPH_MARGIN_SIZE-1 + 
						( entry.getRank(i) / 1000.0 ) * (this.getHeight() - GRAPH_MARGIN_SIZE*2);
				y1 = (int)y1Doub;
			}
			if (entry.getRank(i+1) == 0) {
				y2 = this.getHeight() - GRAPH_MARGIN_SIZE-1;
			}
			else {
				double y2Doub = GRAPH_MARGIN_SIZE-1 + 
						( entry.getRank(i+1) / 1000.0 ) * (this.getHeight() - GRAPH_MARGIN_SIZE*2);
				
				y2 = (int)y2Doub;
			}
			
			// actual drawing
			this.drawLine(x1, y1, x2, y2, this.colors[this.numGraphed%6]);
			this.drawLabel(x1 + 5, y1 - 5, this.entryNameAndDate(entry, i), FONT);
			if (i == NDECADES - 2) {
				this.drawLabel(x2 + 5, y2 - 5, this.entryNameAndDate(entry, i + 1), FONT);
			}
		}
		this.numGraphed++;
	}
	
	/** sets the colors to be used in rotation for the successive slopes drawn for each name entry*/
	private void initColors() {
		this.colors[0] = Color.BLACK;
		this.colors[1] = Color.BLUE;
		this.colors[2] = Color.GREEN;
		this.colors[3] = Color.ORANGE;
		this.colors[4] = Color.RED;
		this.colors[5] = Color.YELLOW;
	}
	
	/** draws a single line from (x1,y1) to (x2.y2). yay! */
	private void drawLine(int x1, int y1, int x2, int y2, Color color) {
		GLine line = new GLine(x1, y1, x2, y2);
		line.setColor(color);
		add(line);
	}
	
	/** draws a single label */
	private void drawLabel(int x, int y, String str, String font) {
		GLabel label = new GLabel(str, x, y);
		label.setFont(font);
		add(label);
	}
	
	/** converts a decade from it's abstract representation (0-10) into a literal form 
	 * (i.e. 0 = 1900, 1 = 1910 ...  10 = 2000) */
	private int convertDecade(int dec) {
		if (dec > 10) {
			return 2000;
		}
		else if (dec < 0) {
			return 1900;
		}
		else {
			return 1900 + (dec * 10);
		}
	}
	
/** creates a string with a name and rank for a corresponding entry at a given decade 
 * (e.g. "Sam 201") to be used as labels for the plotted points */
	private String entryNameAndDate(NameSurferEntry entry, int dec) {
		return entry.getName() + " " + entry.getRank(dec);
	}
	
	/* private instance variables */
	private ArrayList<NameSurferEntry> namesToGraph = new ArrayList<NameSurferEntry>();
	private Color[] colors = new Color[6];
	private int numGraphed = 0;
	
	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) { }
	public void componentMoved(ComponentEvent e) { }
	public void componentResized(ComponentEvent e) { update(); }
	public void componentShown(ComponentEvent e) { }
}
