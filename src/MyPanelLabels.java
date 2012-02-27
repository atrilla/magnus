/*
 * File    : MyPanelLabels.java
 * Created : 20-Feb-2008
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
 * The class <it>MyPanelLabels</it> defines the labels that will be
 * inserted into the panel.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyPanelLabels extends Panel {
	
	Label labamunt,labavall,labdreta,labesquerra,labclic,labdoble,labarros,labmenu,labstop,labstatus,labmodus,labvoid,labcanvia,labdescansa;
	MyMenu mm;
	
	/**
	 * This is the cosntructor.
	 * @param mm MyMenu: instance of the menu.
	 */

	public MyPanelLabels(MyMenu mm) {
		this.mm = mm;
		setLayout(new GridLayout(15,1));
		
		labstop = new Label("sí!");
		labamunt = new Label("amunt");
		labavall = new Label("avall");
		labesquerra = new Label("esquerra");
		labdreta = new Label("dreta");
		labclic = new Label("clic");
		labdoble = new Label("doble");
		labarros = new Label("arrossega");
		labmenu = new Label("menú");
		labcanvia = new Label("canvia");
		labdescansa = new Label("descansa");
		labvoid = new Label(" ");
		// here goes labspeech
		labstatus = new Label("status");
		labmodus = new Label("modus");
		AddLabels();
	}
	
	/**
	 * Method to add the labels.
	 */

	public void AddLabels() {
		add(labstop);
		add(labamunt);
		add(labavall);
		add(labesquerra);
		add(labdreta);
		add(labclic);
		add(labdoble);
		add(labarros);
		add(labmenu);
		add(labcanvia);
		add(labdescansa);
		add(labvoid);
		add(mm.labspeech);
		add(labmodus);
		add(labstatus);
	}
	
	/**
	 * Method to set the font size.
	 * @param fs int: the size in question.
	 */

	public void SetFontSize(int fs) {
		labstop.setFont(new Font("SansSerif",Font.BOLD,fs));
		labamunt.setFont(new Font("SansSerif",Font.BOLD,fs));
		labavall.setFont(new Font("SansSerif",Font.BOLD,fs));
		labesquerra.setFont(new Font("SansSerif",Font.BOLD,fs));
		labdreta.setFont(new Font("SansSerif",Font.BOLD,fs));
		labclic.setFont(new Font("SansSerif",Font.BOLD,fs));
		labdoble.setFont(new Font("SansSerif",Font.BOLD,fs));
		labarros.setFont(new Font("SansSerif",Font.BOLD,fs));
		labmenu.setFont(new Font("SansSerif",Font.BOLD,fs));
		labcanvia.setFont(new Font("SansSerif",Font.BOLD,fs));
		labdescansa.setFont(new Font("SansSerif",Font.BOLD,fs));
		mm.labspeech.setFont(new Font("SansSerif",Font.BOLD,fs));
		labstatus.setFont(new Font("SansSerif",Font.PLAIN,fs));
		labmodus.setFont(new Font("SansSerif",Font.PLAIN,fs));
	}
	
	/**
	 * This method sets the font color.
	 * @param col Color: the color in question.
	 */

	public void SetFontColor(Color col) {
		labstop.setForeground(col);
		labamunt.setForeground(col);
		labavall.setForeground(col);
		labesquerra.setForeground(col);
		labdreta.setForeground(col);
		labclic.setForeground(col);
		labdoble.setForeground(col);
		labarros.setForeground(col);
		labmenu.setForeground(col);
		labcanvia.setForeground(col);
		labdescansa.setForeground(col);
		mm.labspeech.setForeground(col);
		labstatus.setForeground(col);
		labmodus.setForeground(col);
		
	}
	
	/**
	 * This method sets the background color of the labels.
	 * @param col Color: the color in question.
	 */

	public void SetBackgroundLabels(Color col) {
		labstop.setBackground(col);
		labamunt.setBackground(col);
		labavall.setBackground(col);
		labesquerra.setBackground(col);
		labdreta.setBackground(col);
		labclic.setBackground(col);
		labdoble.setBackground(col);
		labarros.setBackground(col);
		labmenu.setBackground(col);
		labcanvia.setBackground(col);
		labdescansa.setBackground(col);
		mm.labspeech.setBackground(col);
		labstatus.setBackground(col);
		labmodus.setBackground(col);
	}
	
}
