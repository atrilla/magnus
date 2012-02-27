/*
 * File    : MyMenu.java
 * Created : 04-oct-2007
 * By      : atrilla
 *
 * MAGNUS - Computer mouse pointer controller through voice commands
 *
 * Copyright (C) 2007 Alexandre Trilla &
 * Departament d'Educacio de la Generalitat de Catalunya &
 * Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
 *
 *
 * This file is part of MAGNUS.
 *
 * MAGNUS is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * MAGNUS is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the COPYING file).
 *
 * You should have received a copy of the GNU General Public License
 * along with MAGNUS.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.awt.Toolkit;
import java.io.*;

import javax.swing.JProgressBar;

import java.util.prefs.*;

import edu.cmu.sphinx.frontend.util.Microphone;

import java.util.Timer;
import java.util.TimerTask;

/**
 * The class <it>MyMenu</it> defines the frame where all
 * the menu elements will be placed.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyMenu extends Frame implements ActionListener,MouseListener {
	
	String appsize,cursize,acceleration,bgcol,fontcol,modus,status;
	ConfigScreen cs;
	MouseControl mc;
	boolean exceptionalconfigmove,closingconf,somespeech,setmic;
	Button config;
	MyPanelLabels panellabs;
	MyPanelImages panelimgs;
	int counter;
	Label labspeech;
	Microphone mic;
	VUMeterThread vumth;
	JProgressBar jpb;

    /**
     * This is the main method for launching the <it>MyMenu</it>.
     * @param mcntrl MouseControl: This is the instance of the mouse control.
     * @throws IOException
     */

    public MyMenu(MouseControl mcntrl) throws IOException {
    	mc = mcntrl;
    	// Globals
    	
    	exceptionalconfigmove = false;
    	closingconf=false;
    	somespeech = false;
    	status = "processing";
    	labspeech = new Label();
    	// setmic will be set true when the microphone initialization is launched
    	setmic = false;
    	
    	// Retrieve personal preferences
    	Preferences prefs = Preferences.userNodeForPackage(MyMenu.class);
    	
    	// Get the value of the preference;
        // default value is returned if the preference does not exist
        String defaultValue1 = "green";
        bgcol = prefs.get("magnus_pref_backgroundColor", defaultValue1);
        String defaultValue2 = "white";
        fontcol = prefs.get("magnus_pref_fontColor", defaultValue2);
        String defaultValue3 = "medium";
        appsize = prefs.get("magnus_pref_applicationSize", defaultValue3);
        String defaultValue4 = "moderate";
        acceleration = prefs.get("magnus_pref_acceleration", defaultValue4);
        String defaultValue5 = "mouse";
        modus = prefs.get("magnus_pref_modus", defaultValue5);
        
    	cs = new ConfigScreen(this);
    	config = new Button("configuració");
    	panellabs = new MyPanelLabels(this);
    	panelimgs = new MyPanelImages(this);
    	jpb = new JProgressBar(JProgressBar.VERTICAL);
    	setLayout(new BorderLayout());
    	setTitle("MAGNUS Menú d'Acció");
    	Toolkit toolkit=Toolkit.getDefaultToolkit();
        URL url=getClass().getResource("icon.png");
        Image image=toolkit.getImage(url);
        setIconImage(image);
    	setResizable(false);
    	
    	SetMyMenu();
    	mc.SetFactor(acceleration);
    	
    	add(panellabs,BorderLayout.WEST);
    	add(panelimgs,BorderLayout.CENTER);
    	add(jpb,BorderLayout.EAST);
    	
    	config.addActionListener(this);
		add(config,BorderLayout.SOUTH);
		
		config.addMouseListener(this);
		
		counter = 0;
		
		TimerTask timerTask = new TimerTask() {
	         public void run() {
	             if (somespeech==true) {
	            	 // Something said
	            	 counter = 0;
	            	 somespeech = false;
	            	 status = "processing";
	            	 panelimgs.ChangeStatusIcon();
	            	 panelimgs.validate();
	            	 validate();
	             } else {
	            	 // Nothing said
	            	 counter++;
	            	 if (counter==60) {			// if 30 seconds idle goes to sleep
	            	     status = "sleeping";
	            	     panelimgs.ChangeStatusIcon();
	            	     panelimgs.validate();
	            	     validate();
	            	 }
	            	 if (counter==10) {
	            	     status = "listening";
	            	     panelimgs.ChangeStatusIcon();
	            	     panelimgs.validate();
	            	     validate();
	            	 }
	             }
	         }
	     }; 
		
		Timer magtimer = new Timer();
		magtimer.scheduleAtFixedRate(timerTask, 0, 500);		// 0.5 seconds
		
		addWindowListener(new WindowAdapter() {

            /**
	     * This method contemplates the window closing event.
	     * It saves the user's preferences before closing.
	     * @param e WindowEvent: this is the window event.
	     */

            public void windowClosing(WindowEvent e) {
            	System.out.println("Desallotjant els recursos...");
            	
            	// Save personal preferences
            	// Retrieve the user preference node for the package com.mycompany
                Preferences prefs = Preferences.userNodeForPackage(MyMenu.class);
            	
                prefs.put("magnus_pref_backgroundColor", bgcol);
                prefs.put("magnus_pref_fontColor", fontcol);
                prefs.put("magnus_pref_applicationSize", appsize);
                prefs.put("magnus_pref_acceleration", acceleration);
                prefs.put("magnus_pref_modus", modus);
                
            	System.out.println("Gràcies per utilitzar MAGNUS! Fins aviat!");
                dispose();
                System.exit(0);
            }
        });
    }
    
    /**
     * This process initializes the microphone.
     * @param candidate Microphone: this unit represents microphone.
     */

    public void SetMicrophone(Microphone candidate) {
    	mic = candidate;
    	setmic = true;
    	vumth = new VUMeterThread(mic,this);
    	int aux = mic.getAudioFormat().getSampleSizeInBits();
    	jpb.setMaximum((int)Math.pow(2, aux-1));
    	jpb.setMinimum(0);
    	vumth.start();
    }
    
    /**
     * This method sets the text recognized.
     * @param str String: the text that has been recognized.
     */

    public void SetSpeechRecognized(String str) {
    	labspeech.setText(str);
    	labspeech.validate();
    	validate();
    }
    
    /**
     * This method sets the modus of operation.
     * @param str String: the modus in question.
     */

    public void SetModus(String str) {
    	modus = str;
    	
    	panelimgs.ChangeModusIcon();
	    panelimgs.validate();
	    validate();
    }
    
    /**
     * This function retrieves the modus of operation.
     * @return The present modus.
     */

    public String GetModus() {
    	return modus;
    }
    
    /**
     * This method sets the menu of the application.
     */

    public void SetMyMenu() {
    	
    	int fonts=16;
    	
    	if (appsize.equals("big")) { //200, 490
    		setSize(200,580);
    	} else {
    		if (appsize.equals("medium")) { //165, 350
    			setSize(165, 430);
    			fonts = 12;
    		} else {
    			setSize(120, 320);//120, 265
    			fonts = 8;
    		}
    	}
    	
    	validate();
    	
    	config.setFont(new Font("SansSerif",Font.BOLD,fonts));
    	config.validate();
    	
    	if (bgcol.equals("green")) {
    		setBackground(new Color(0,174,0));
    		panellabs.SetBackgroundLabels(new Color(0,174,0));
    		panellabs.setBackground(new Color(0,174,0));
    		config.setBackground(new Color(0,128,0));
    		jpb.setBackground(new Color(0,154,0));
    	}
    	if (bgcol.equals("red")) {
    		setBackground(new Color(255,0,0));
    		panellabs.SetBackgroundLabels(new Color(255,0,0));
    		panellabs.setBackground(new Color(255,0,0));
    		config.setBackground(new Color(128,0,0));
    		jpb.setBackground(new Color(213,0,0));
    	}
    	if (bgcol.equals("blue")) {
    		setBackground(new Color(0,0,255));
    		panellabs.SetBackgroundLabels(new Color(0,0,255));
    		panellabs.setBackground(new Color(0,0,255));
    		config.setBackground(new Color(0,0,128));
    		jpb.setBackground(new Color(0,0,196));
    	}
    	if (bgcol.equals("yellow")) {
    		setBackground(new Color(255,255,0));
    		panellabs.SetBackgroundLabels(new Color(255,255,0));
    		panellabs.setBackground(new Color(255,255,0));
    		config.setBackground(new Color(128,128,0));
    		jpb.setBackground(new Color(202,199,0));
    	}
    	if (bgcol.equals("orangeLinkat")) {
    		setBackground(new Color(255,153,0));
    		panellabs.SetBackgroundLabels(new Color(255,153,0));
    		panellabs.setBackground(new Color(255,153,0));
    		config.setBackground(new Color(185,111,0));
    		jpb.setBackground(new Color(235,144,0));
    	}
    	if (bgcol.equals("cyan")) {
    		setBackground(new Color(0,255,255));
    		panellabs.SetBackgroundLabels(new Color(0,255,255));
    		panellabs.setBackground(new Color(0,255,255));
    		config.setBackground(new Color(0,128,128));
    		jpb.setBackground(new Color(0,200,202));
    	}
    	if (bgcol.equals("pink")) {
    		setBackground(new Color(255,0,255));
    		panellabs.SetBackgroundLabels(new Color(255,0,255));
    		panellabs.setBackground(new Color(255,0,255));
    		config.setBackground(new Color(128,0,128));
    		jpb.setBackground(new Color(208,0,210));
    	}
    	if (bgcol.equals("white")) {
    		setBackground(new Color(255,255,255));
    		panellabs.SetBackgroundLabels(new Color(255,255,255));
    		panellabs.setBackground(new Color(255,255,255));
    		config.setBackground(new Color(153,153,153));
    		jpb.setBackground(new Color(153,153,153));
    	}
    	if (bgcol.equals("black")) {
    		setBackground(new Color(0,0,0));
    		panellabs.SetBackgroundLabels(new Color(0,0,0));
    		panellabs.setBackground(new Color(0,0,0));
    		config.setBackground(new Color(153,153,153));
    		jpb.setBackground(new Color(153,153,153));
    	}
    	
    	validate();
    	config.validate();
    	panellabs.validate();
    	
    	panellabs.SetFontSize(fonts);
    	panellabs.validate();
    	
    	Color color = new Color(255,255,255);
		if (fontcol.equals("green")) {
    		color = new Color(0,255,0);
    	}
    	if (fontcol.equals("red")) {
    		color = new Color(255,0,0);
    	}
    	if (fontcol.equals("blue")) {
    		color = new Color(0,0,255);
    	}
    	if (fontcol.equals("yellow")) {
    		color = new Color(255,255,0);
    	}
    	if (fontcol.equals("orangeLinkat")) {
    		color = new Color(255,153,0);
    	}
    	if (fontcol.equals("cyan")) {
    		color = new Color(0,255,255);
    	}
    	if (fontcol.equals("pink")) {
    		color = new Color(255,0,255);
    	}
    	if (fontcol.equals("white")) {
    		color = new Color(255,255,255);
    	}
    	if (fontcol.equals("black")) {
    		color = new Color(0,0,0);
    	}
    	
    	config.setForeground(color);
    	config.validate();
    	
    	panellabs.SetFontColor(color);
    	panellabs.validate();
        
    	remove(panelimgs);
        panelimgs.ResizeIcons();
        panelimgs.validate();
        add(panelimgs,BorderLayout.CENTER);
        
        validate();
    }
    
    /**
     * This method sets a variable that indicates that some speech has been uttered.
     */

    public void SetSomeSpeech() {
    	somespeech = true;
    }
    
    /**
     * This method is run when user has closed the configuration menu.
     */

    public void SetConfigClosing() {
    	closingconf = true;
    }
    
    /**
     * This function indicates that the user has closed the configuration menu.
     * @return TRUE if the configuration closing action has happened.
     */

    public boolean ConfigClosing() {
    	boolean tmp = closingconf;
    	closingconf = false;
    	
    	return tmp;
    }
    
    /**
     * This function indicates whether there must be an exception config movement or not.
     * @return TRUE if the movement must be done in order to set the mouse pointer in its original positin.
     */

    public boolean ConfigMove() {
    	boolean tmp = exceptionalconfigmove;
    	exceptionalconfigmove = false;
    	
    	return tmp;
    }
    
    /**
     * This method is called when an action is performed.
     * @param e ActionEvent: the action event.
     */

    public void actionPerformed(ActionEvent e) {
    	cs.setVisible(true);
    	exceptionalconfigmove = true;
    }
    
    /**
     * This method sets the background color.
     * @param col String: the color in question.
     */

    public void SetBackgroundColor(String col) {
    	bgcol = col;
    }
    
    /**
     * This methos sets the font color.
     * @param col String: the color in question.
     */

    public void SetFontColor(String col) {
    	fontcol = col;
    }
    
    /**
     * This method sets the application size.
     * @param str String: the application size in question.
     */

    public void SetApplicationSize(String str) {
    	appsize = str;
    }
    
    /**
     * This method sets the acceleration of the pointer.
     * @param str String: the acceleration in question.
     */

    public void SetAcceleration(String str) {
    	acceleration = str;
    	mc.SetFactor(str);
    }
    
    /**
     * This function retrieves the background color.
     * @return The present bacjground color.
     */

    public String GetBackgroundColor() {
    	return bgcol;
    }
    
    /**
     * This function retrieves the font color.
     * @return The present font color.
     */

    public String GetFontColor() {
    	return fontcol;
    }
    
    /**
     * This function retrieves the application size.
     * @return The present application size.
     */

    public String GetApplicationSize() {
    	return appsize;
    }
    
     
    /**
     * This function retrieves the acceleration.
     * @return The present acceleration.
     */

    public String GetAcceleration() {
    	return acceleration;
    }
    
    /**
     * This method is launched every time a mouse button is clicked.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mouseClicked(MouseEvent e) {
    }

    /**
     * This method is launched every time a mouse button is pressed.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mousePressed(MouseEvent e) {
    }

    /**
     * This method is launched every time a mouse button is released.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mouseReleased(MouseEvent e) {
    }

    /**
     * This method is launched every time the mouse cursor enters a determined zone.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mouseEntered(MouseEvent e) {
    	Cursor handCursor = new Cursor(Cursor.HAND_CURSOR);
		setCursor(handCursor);
    }

    /**
     * This method is launched every time the mouse cursor exits a determined zone.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mouseExited(MouseEvent e) {
    	Cursor handCursor = new Cursor(Cursor.DEFAULT_CURSOR);
		setCursor(handCursor);
    }
   
}
