/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

/* Method: init() */
/**
 * This method has the responsibility for reading in the data base
 * and initializing the interactors at the bottom of the window.
 */
	public void init() {
		//setFont("TimesNewRoman-20");
		initGUI();
		initDatabase();
		initGraph();
	}

/* Method: actionPerformed(e) */
/**
 * This class is responsible for detecting when the buttons are
 * clicked, so you will have to define a method to respond to
 * button actions.
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.nameField.getText()) {
			NameSurferEntry entry = this.database.findEntry(this.nameField.getText());
			graphEntry(entry);
		}
		if (e.getActionCommand().equals("Graph")) {
			NameSurferEntry entry = this.database.findEntry(this.nameField.getText());
			graphEntry(entry);
		}
		if (e.getActionCommand().equals("Clear")) {
			println("Clear");
			this.graph.clear();
			this.graph.update();
		}
	}

/** handles a single entry, adding it to the database, plotting it to the graph and printing to the console */
	private void graphEntry(NameSurferEntry entry) {
		if (entry != null) {
			this.graph.addEntry(entry);
			println("Graph: " + entry.toString() );
		}
		else {
			entry = new NameSurferEntry(this.nameField.getText() + " 0 0 0 0 0 0 0 0 0 0 0");
			this.graph.addEntry(entry);
			println("Graph: " + entry.toString());
		}
		this.graph.update();
	}

/** initializes the bottom control bar with buttons to graph typed names and clear all previous entries */
	private void initGUI() {
		add(new JLabel("Name: "), SOUTH);
	    
		this.nameField = new JTextField("type here pls", 20);
	    add(this.nameField, SOUTH);
	    this.nameField.addActionListener(this);

	    add(new JButton("Graph"), SOUTH);
	    add(new JButton("Clear"), SOUTH);
	    
	    addActionListeners();
	}
	
/** initializes the database that stores the entries */
	private void initDatabase() {
		this.database = new NameSurferDataBase(NAMES_DATA_FILE);
	}
	
/** initializes the graphics */
	private void initGraph() {
		graph = new NameSurferGraph();
		add(graph);
		this.graph.update();
	}
	
// for testing the NameSurferEntry class using the console the console
/*	private void testNameSurferEntry() {
		String dataSample = "Sam 1 2 3 4 5 6 7 8 9 10 11";
		NameSurferEntry newEntry = new NameSurferEntry(dataSample); 
		println("name: " + newEntry.getName());
		println("rank 1900: " + newEntry.getRank(0));
		println("rank 1910: " + newEntry.getRank(1));
		println("rank 1920: " + newEntry.getRank(2));
		println("rank 1930: " + newEntry.getRank(3));
		println("rank 1940: " + newEntry.getRank(4));
		println("rank 1950: " + newEntry.getRank(5));
		println("rank 1960: " + newEntry.getRank(6));
		println("rank 1970: " + newEntry.getRank(7));
		println("rank 1980: " + newEntry.getRank(8));
		println("rank 1990: " + newEntry.getRank(9));
		println("rank 2000: " + newEntry.getRank(10));
		println("toString representation: " + newEntry.toString());
	}
*/	

	/** private instance variables */
	private JTextField nameField;
	private NameSurferDataBase database;
	private NameSurferGraph graph;
	
}
