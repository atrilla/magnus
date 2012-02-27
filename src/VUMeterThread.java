/*
 * File    : VUMeterThread.java
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
 * The class <it>VUMeterThread</it> defines the thread that will execute the
 * vumeter in parallel with the rest of the program execution, enableling a
 * more interactive use since the user will know any time if the system is running.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class VUMeterThread extends Thread {
	Microphone mic;
	MyMenu mm;
	
	/**
	 * This is the constructor of the vumeter.
	 * @param cand Microphone: this a microphone candidate.
	 * @param mym MyMenu: menu instance.
	 */

	public VUMeterThread(Microphone cand,MyMenu mym) {
		mic = cand;
		mm = mym;
	}
	
	/**
	 * This is the main method to run the thread.
	 */

	public void run() {
			while (true) {
				//System.out.println("values: "+mic.GetPresentValue());
				try {
					sleep(25);
				} catch (InterruptedException e) {
	                System.out.println("Sleep interrupted!");
	            }
				mm.jpb.setValue(mic.GetPresentValue());
				if (((mm.jpb.getPercentComplete()*100)>40)&&((mm.jpb.getPercentComplete()*100)<75)) {
					mm.jpb.setForeground(new Color(0,255,0));
				} else {
					mm.jpb.setForeground(new Color(255,0,0));
				}
			}
	}
	
}
