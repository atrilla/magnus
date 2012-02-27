/*
 * File    : ConfigPanel.java
 * Created : 13-Feb-2008
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
 * The class <it>ConfigPanel</it> defines the panel where all
 * the configuration elements will be placed.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class ConfigPanel extends Panel implements ActionListener,MouseListener {
	
	DuetPanel labcolfons,labcoltext,labmidaapp,labmidacurs,labacc;
	Button about;
	String defopt;
	ConfigScreen confscr;
	
    /**
     * This is the main method for configuring the <it>ConfigPanel</it>.
     * @param cs ConfigScreen: This parameter contains valuable information about the present
     *                      configuration.
     */

	public ConfigPanel(ConfigScreen cs) {
		confscr = cs;
		
		setLayout(new GridLayout(5,1));
		
		labcolfons = new DuetPanel(cs);
		labcolfons.ConfigBackgroundColor();
		add(labcolfons);
		
		labcoltext = new DuetPanel(cs);
		labcoltext.ConfigForegroundColor();
		add(labcoltext);
		
		labmidaapp = new DuetPanel(cs);
		labmidaapp.ConfigApplicationSize();
		add(labmidaapp);
		
		labacc = new DuetPanel(cs);
		labacc.ConfigAcceleration();
		add(labacc);
		
		about = new Button("informació sobre el MAGNUS");
		about.setBackground(new Color(0,128,0));
		about.setForeground(new Color(255,255,255));
		about.setFont(new Font("SansSerif",Font.BOLD,14));
		about.addActionListener(this);
		about.addMouseListener(this);
		add(about);
	}

    /**
     * This method reacts to the actions performed.
     * @param e ActionEvent: This parameter contains valuable information about the action
     *                      events.
     */

	public void actionPerformed(ActionEvent e) {
		MyScreenAbout msa = new MyScreenAbout();
		msa.setLocation(100, 100);
		msa.setVisible(true);
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
