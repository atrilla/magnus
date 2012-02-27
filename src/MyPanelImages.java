/*
 * File    : MyPanelImages.java
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
 * The class <it>MyPanelImages</it> defines the panel where
 * the images will be inserted.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyPanelImages extends Panel {
	
	MyIcon icocanvia,icodescansa,icoamunt,icoavall,icoesquerra,icodreta,icoclic,icodoble,icoarros,icomenu,icostop,icostate,icomodus,icospeech;
	MyMenu mm;
	Label labvoid;
	
	/**
	 * This is the constructor.
	 * @param mym MyMenu: instance of the menu.
	 */

	public MyPanelImages(MyMenu mym) {
		mm = mym;
		
		setLayout(new GridLayout(15,1));
		
		icostop = new MyIcon("icostop.png",mm.GetApplicationSize());
		icoamunt = new MyIcon("Narrow.png",mm.GetApplicationSize());
        icoavall = new MyIcon("Sarrow.png",mm.GetApplicationSize());
        icoesquerra = new MyIcon("Warrow.png",mm.GetApplicationSize());
        icodreta = new MyIcon("Earrow.png",mm.GetApplicationSize());
        icoclic = new MyIcon("icoclic.png",mm.GetApplicationSize());
        icodoble = new MyIcon("icodoble.png",mm.GetApplicationSize());
        icoarros = new MyIcon("icoarros.png",mm.GetApplicationSize());
        icomenu = new MyIcon("icomenu.png",mm.GetApplicationSize());
        icocanvia = new MyIcon("icocanvia.png",mm.GetApplicationSize());
        icodescansa = new MyIcon("icodescansa.png",mm.GetApplicationSize());
        labvoid = new Label(" ");
        icospeech = new MyIcon("comfound.png",mm.GetApplicationSize());
        if (mm.status.equals("listening")) {
        	icostate = new MyIcon("listening.png",mm.GetApplicationSize());
        }
        if (mm.status.equals("processing")) {
        	icostate = new MyIcon("processing.png",mm.GetApplicationSize());
        }
        if (mm.status.equals("sleeping")) {
        	icostate = new MyIcon("sleeping.png",mm.GetApplicationSize());
        }
        
        String aux;
        if (mm.GetModus().equals("mouse")) {
        	aux = "mouse.png";
        } else {
        	aux = "keyboard.png";
        }
        icomodus = new MyIcon(aux,mm.GetApplicationSize());
        AddIcons();
	}
	
	/**
	 * This method changes the status icon according to the state of the program,
	 * which can be either "listening", "waiting" and "sleeping".
	 */

	public void ChangeStatusIcon() {
			remove(icostate);
			if (mm.status.equals("listening")) {
	        	icostate = new MyIcon("listening.png",mm.GetApplicationSize());
	        }
	        if (mm.status.equals("processing")) {
	        	icostate = new MyIcon("processing.png",mm.GetApplicationSize());
	        }
	        if (mm.status.equals("sleeping")) {
	        	icostate = new MyIcon("sleeping.png",mm.GetApplicationSize());
	        }
	        icostate.validate();
	        
	        
	        String apsize = mm.GetApplicationSize();
			int size=22;
			if (apsize.equals("big")) {
	        	size = 32;
	        }
	        if (apsize.equals("medium")) {
	        	size = 22;
	        }
	        if (apsize.equals("small")) {
	        	size = 16;
	        }
	        icostate.size = size;
	        
	        add(icostate);
	        
	        validate();
	}
	
	/**
	 * This method changes the modus icon according to the user's needs.
	 * Magnus can be either acting as a virtual mouse or a virtual keypad.
	 */

	public void ChangeModusIcon() {
		remove(icostate);
		remove(icomodus);
        
        String aux;
        if (mm.GetModus().equals("mouse")) {
        	aux = "mouse.png";
        } else {
        	aux = "keyboard.png";
        }
        icomodus = new MyIcon(aux,mm.GetApplicationSize());
        
        icomodus.validate();
        
        String apsize = mm.GetApplicationSize();
		int size=22;
		if (apsize.equals("big")) {
        	size = 32;
        }
        if (apsize.equals("medium")) {
        	size = 22;
        }
        if (apsize.equals("small")) {
        	size = 16;
        }
        icomodus.size = size;
        
        add(icomodus);
        add(icostate);
        
        validate();
	}
	
	/**
	 * This method is used to add the icons into the panel.
	 */

	public void AddIcons() {
		add(icostop);
		add(icoamunt);
		add(icoavall);
		add(icoesquerra);
		add(icodreta);
        	add(icoclic);
		add(icodoble);
		add(icoarros);
		add(icomenu);
		add(icocanvia);
		add(icodescansa);
		add(labvoid);
		add(icospeech);
		add(icomodus);
		add(icostate);
	}
	
	/**
	 * This methos resizes the icons according to the configuration.
	 */

	public void ResizeIcons() {
		String apsize = mm.GetApplicationSize();
		int size=22;
		if (apsize.equals("big")) {
        	size = 32;
        }
        if (apsize.equals("medium")) {
        	size = 22;
        }
        if (apsize.equals("small")) {
        	size = 16;
        }
		
        icostop.size = size;
		icoamunt.size = size;
        icoavall.size = size;
        icoesquerra.size = size;
        icodreta.size = size;
        icoclic.size = size;
        icodoble.size = size;
        icoarros.size = size;
        icomenu.size = size;
        icocanvia.size = size;
        icodescansa.size = size;
        icospeech.size = size;
        icostate.size = size;
        icomodus.size = size;
	}
	
}
