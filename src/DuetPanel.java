/*
 * File    : DuetPanel.java
 * Created : 12-Gen-2008
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

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.MediaTracker;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * The class <it>DuetPanel</it> defines the structure of the elements in the Action Panel.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 * 
 */
 
public class DuetPanel extends Panel implements ItemListener,MouseListener {
	
	ConfigScreen confscr;
	Vector colors,sizes,speeds;
	Label param;
	Choice choice;
	String defopt;
	
	
    /**
     * This is the constructor for the <it>DuetPanel</it>.
     * @param cs ConfigScreen: reference of the bigger structure.
     */

	public DuetPanel(ConfigScreen cs) {
		confscr = cs;
		
		defopt = "bgcol";
		
		setLayout(new GridLayout(1,2));
		setForeground(new Color(255,255,255));
		setBackground(new Color(0,174,0));
		
		//Vector information
		
		String green = new String("verd");
		String red = new String("vermell");
		String blue = new String("blau");
		String yellow = new String("groc");
		String orangeLinkat = new String("taronja Linkat");
		String cyan = new String("cian");
		String pink = new String("rosa");
		String white = new String("blanc");
		String black = new String("negre");
		String big = new String("gran");
		String medium = new String("mitjà");
		String small = new String("petit");
		String fast = new String("ràpid");
		String moderate = new String("moderat");
		String slow = new String("lent");
		String none = new String("cap");
		
		colors = new Vector();
		colors.add(green);
		colors.add(red);
		colors.add(blue);
		colors.add(yellow);
		colors.add(orangeLinkat);
		colors.add(cyan);
		colors.add(pink);
		colors.add(white);
		colors.add(black);
		
		sizes = new Vector();
		sizes.add(big);
		sizes.add(medium);
		sizes.add(small);
		
		speeds = new Vector();
		speeds.add(fast);
		speeds.add(moderate);
		speeds.add(slow);
		speeds.add(none);
	}
	
    /**
     * This method configures the background color of the menu.
     */

	public void ConfigBackgroundColor() {
		param = new Label("   Color del fons:");
		param.setFont(new Font("SansSerif",Font.CENTER_BASELINE,14));
		param.setName("bgcol");
		param.addMouseListener(this);
		param.setBackground(new Color(0,174,0));
		param.setForeground(new Color(255,255,255));
		add(param);
		choice = new Choice();
		choice.setBackground(new Color(255,255,255));
		choice.setForeground(new Color(0,0,0));
		String nowopt = confscr.mm.GetBackgroundColor();
		String trans = TranslateColor(nowopt,"en","ca");			//Translate from English to Catalan
		this.setBackground(new Color(255,255,255));
		this.setForeground(new Color(0,0,0));
		choice.add(trans);
		for (Enumeration enumera=colors.elements();enumera.hasMoreElements();) {
			Object ob=enumera.nextElement();
			String str = (String)ob;
			if (str.equals(trans)==false) choice.add(str);
		}
		choice.setName("bgcol");
		choice.addItemListener(this);
		add(choice);
	}
	
    /**
     * This method configures the foreground color of the menu (the font color).
     */

	public void ConfigForegroundColor() {
		param = new Label("   Color del text:");
		param.setFont(new Font("SansSerif",Font.CENTER_BASELINE,14));
		param.setName("textcol");
		param.setBackground(new Color(0,174,0));
		param.setForeground(new Color(255,255,255));
		param.addMouseListener(this);
		add(param);
		choice = new Choice();
		choice.setBackground(new Color(255,255,255));
		choice.setForeground(new Color(0,0,0));
		String nowopt = confscr.mm.GetFontColor();
		String trans = TranslateColor(nowopt,"en","ca");			//Translate from English to Catalan
		this.setBackground(new Color(255,255,255));
		this.setForeground(new Color(0,0,0));
		choice.add(trans);
		for (Enumeration enumera=colors.elements();enumera.hasMoreElements();) {
			Object ob=enumera.nextElement();
			String str = (String)ob;
			if (str.equals(trans)==false) choice.add(str);
		}
		choice.setName("textcol");
		choice.addItemListener(this);
		add(choice);
	}
	
    /**
     * This method configures the size of the menu.
     */

	public void ConfigApplicationSize() {
		param = new Label("   Mida de l'aplicació:");
		param.setFont(new Font("SansSerif",Font.CENTER_BASELINE,14));
		param.setName("appsize");
		param.setBackground(new Color(0,174,0));
		param.setForeground(new Color(255,255,255));
		param.addMouseListener(this);
		add(param);
		choice = new Choice();
		choice.setBackground(new Color(255,255,255));
		choice.setForeground(new Color(0,0,0));
		String nowopt = confscr.mm.GetApplicationSize();
		String trans = TranslateAppSize(nowopt,"en","ca");			//Translate from English to Catalan
		this.setBackground(new Color(255,255,255));
		this.setForeground(new Color(0,0,0));
		choice.add(trans);
		for (Enumeration enumera=sizes.elements();enumera.hasMoreElements();) {
			Object ob=enumera.nextElement();
			String str = (String)ob;
			if (str.equals(trans)==false) choice.add(str);
		}
		choice.setName("appsize");
		choice.addItemListener(this);
		add(choice);
	}
	
    /**
     * This method configures the acceleration of the mouse cursor.
     */

	public void ConfigAcceleration() {
		param = new Label("   Acceleració:");
		param.setFont(new Font("SansSerif",Font.CENTER_BASELINE,14));
		param.setName("acc");
		param.setBackground(new Color(0,174,0));
		param.setForeground(new Color(255,255,255));
		param.addMouseListener(this);
		add(param);
		choice = new Choice();
		choice.setBackground(new Color(255,255,255));
		choice.setForeground(new Color(0,0,0));
		String nowopt = confscr.mm.GetAcceleration();
		String trans = TranslateAcceleration(nowopt,"en","ca");			//Translate from English to Catalan
		this.setBackground(new Color(255,255,255));
		this.setForeground(new Color(0,0,0));
		choice.add(trans);
		for (Enumeration enumera=speeds.elements();enumera.hasMoreElements();) {
			Object ob=enumera.nextElement();
			String str = (String)ob;
			if (str.equals(trans)==false) choice.add(str);
		}
		choice.setName("acc");
		choice.addItemListener(this);
		add(choice);
	}
	
    /**
     * This function translates a color from one language into another one.
     * @param wiq String: the word (color) in question to be translated.
     * @param start String: the origin language.
     * @param end String: the destination language.
     * @return The translation requested.
     */

	public String TranslateColor(String wiq,String start,String end) {	// wiq = word in question
		String result="";
		
		if (start=="en"&&end=="ca") {
			if (wiq.equals("green")) result = "verd";
			if (wiq.equals("red")) result = "vermell";
			if (wiq.equals("blue")) result = "blau";
			if (wiq.equals("yellow")) result = "groc";
			if (wiq.equals("orangeLinkat")) result = "taronja Linkat";
			if (wiq.equals("cyan")) result = "cian";
			if (wiq.equals("pink")) result = "rosa";
			if (wiq.equals("white")) result = "blanc";
			if (wiq.equals("black")) result = "negre";
		}
		
		if (start=="ca"&&end=="en") {
			if (wiq.equals("verd")) result = "green";
			if (wiq.equals("vermell")) result = "red";
			if (wiq.equals("blau")) result = "blue";
			if (wiq.equals("groc")) result = "yellow";
			if (wiq.equals("taronja Linkat")) result = "orangeLinkat";
			if (wiq.equals("cian")) result = "cyan";
			if (wiq.equals("rosa")) result = "pink";
			if (wiq.equals("blanc")) result = "white";
			if (wiq.equals("negre")) result = "black";
		}
		
		return result;
	}

    /**
     * This function translates the size of the application from one language into another one.
     * @param wiq String: the word (color) in question to be translated.
     * @param start String: the origin language.
     * @param end String: the destination language.
     * @return The translation requested.
     */

	public String TranslateAppSize(String wiq,String start,String end) {
		String result="";
		
		if (start=="en"&&end=="ca") {
			if (wiq.equals("big")) result = "gran";
			if (wiq.equals("medium")) result = "mitjà";
			if (wiq.equals("small")) result = "petit";
		}
		
		if (start=="ca"&&end=="en") {
			if (wiq.equals("gran")) result = "big";
			if (wiq.equals("mitjà")) result = "medium";
			if (wiq.equals("petit")) result = "small";
		}
		return result;
	}

    /**
     * This function translates the acceleration of the mouse pointer from one language into another one.
     * @param wiq String: the word (color) in question to be translated.
     * @param start String: the origin language.
     * @param end String: the destination language.
     * @return The translation requested.
     */

	public String TranslateAcceleration(String wiq,String start,String end) {
		String result="";
		
		if (start=="en"&&end=="ca") {
			if (wiq.equals("fast")) result = "ràpid";
			if (wiq.equals("moderate")) result = "moderat";
			if (wiq.equals("slow")) result = "lent";
			if (wiq.equals("none")) result = "cap";
		}
		
		if (start=="ca"&&end=="en") {
			if (wiq.equals("ràpid")) result = "fast";
			if (wiq.equals("moderat")) result = "moderate";
			if (wiq.equals("lent")) result = "slow";
			if (wiq.equals("cap")) result = "none";
		}
		return result;
	}
	
    /**
     * This method is launched every time the state of a configuration item is changed.
     * @param e ItemEvent: catches the event related to any item.
     */

	public void itemStateChanged(ItemEvent e) {
		Object choiceObj = e.getSource();
		if (choiceObj instanceof Choice) {
			Choice myChoice = (Choice)choiceObj;
			if (myChoice.getName().equals("bgcol")) {
				String colornew = myChoice.getSelectedItem();
				colornew = TranslateColor(colornew,"ca","en");
				confscr.mm.SetBackgroundColor(colornew);
				confscr.mm.SetMyMenu();
			}
			if (myChoice.getName().equals("textcol")) {
				String colornew = myChoice.getSelectedItem();
				colornew = TranslateColor(colornew,"ca","en");
				confscr.mm.SetFontColor(colornew);
				confscr.mm.SetMyMenu();
			}
			if (myChoice.getName().equals("appsize")) {
				String sizenew = myChoice.getSelectedItem();
				sizenew = TranslateAppSize(sizenew,"ca","en");
				confscr.mm.SetApplicationSize(sizenew);
				confscr.mm.SetMyMenu();
			}
			if (myChoice.getName().equals("acc")) {
				String accnew = myChoice.getSelectedItem();
				accnew = TranslateAcceleration(accnew,"ca","en");
				confscr.mm.SetAcceleration(accnew);
			}
		}
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
    	param.setForeground(new Color(0,255,0));
    }

    /**
     * This method is launched every time the mouse cursor exits a determined zone.
     * @param e MouseEvent: catches the event related to the mouse peripheral.
     */

    public void mouseExited(MouseEvent e) {
    	param.setForeground(new Color(255,255,255));

    }
	
}
