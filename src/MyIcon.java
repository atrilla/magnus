/*
 * File    : MyIcon.java
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
 * The class <it>MyIcon</it> defines the icons.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyIcon extends Label {

	String targetstr;
	int size;
	
    /**
     * This is the icon constructor.
     * @param str String: the name of the icon.
     * @param apsize String: the size of the icon.
     */

    public MyIcon(String str,String apsize) { //constructor
        super();
        targetstr = str;
        if (apsize=="big") {
        	size = 32;
        }
        if (apsize=="medium") {
        	size = 22;
        }
        if (apsize=="small") {
        	size = 16;
        }
        setSize(size, size);
    }

    /**
     * This is the main method to pain the icons.
     * @param g Graphics: the graphical environment.
     */

    public void paint (Graphics g) {
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        URL url=getClass().getResource(targetstr);
        Image image=toolkit.getImage(url);
        MediaTracker mediaTracker=new MediaTracker(this);
        mediaTracker.addImage(image,0);
            try {
                mediaTracker.waitForAll();
                    if(mediaTracker.checkAll()) {
                        g.drawImage(image,0,0,size,size,this);
                    }
            } catch (InterruptedException e) {
                System.out.println("No puc amb la imatge!");
            }
    }

}
