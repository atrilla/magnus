/*
 * File    : Magnus.java
 * Created : 04-Oct-2007
 * By      : atrilla
 *
 * Magnus - Computer mouse pointer controller through voice commands
 *
 * Copyright (C) 2007 Alexandre Trilla &
 * Departament d'Educacio de la Generalitat de Catalunya &
 * Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
 *
 *
 * This file is part of Magnus.
 *
 * Magnus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Magnus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details (see the COPYING file).
 *
 * You should have received a copy of the GNU General Public License
 * along with Magnus.  If not, see <http://www.gnu.org/licenses/>.
 *
 */


import java.awt.AWTException;
import java.io.*;
import java.lang.Thread;

/**
 * The <I>Magnus</I> class is the root class for running Magnus.
 * All the rest of the project's classes are instantiated, initially,
 * by this class.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class Magnus {

    /**
     * This is the main method for running Magnus.
     * @throws AWTException
     * @throws IOException
     */
    public static void main(String[] args) throws AWTException,IOException,InterruptedException {
				
	MyScreen mainFrame = new MyScreen();
	mainFrame.setSize(800, 600);
	mainFrame.setResizable(false);
	mainFrame.setTitle("MAGNUS: Mouse Advanced GNU Speech");
	mainFrame.setVisible(true);

	System.out.println("MAGNUS: Mouse Advanced GNU Speech");
	mainFrame.AddTextMessage("MAGNUS: Mouse Advanced GNU Speech\n");
	System.out.println("**********");
	mainFrame.AddTextMessage("*********\n");
	System.out.println("Servei de Tecnologies per l'Aprenentatge i el Coneixement");
	mainFrame.AddTextMessage("Servei de Tecnologies per l'Aprenentatge i el Coneixement\n");
	System.out.println("Departament d'Educació de la Generalitat de Catalunya 2008");
	mainFrame.AddTextMessage("Departament d'Educació de la Generalitat de Catalunya 2008\n");
	System.out.println(" ");
	mainFrame.AddTextMessage("\n");
	System.out.println("Departament de Tecnologies Mèdia");
	mainFrame.AddTextMessage("Departament de Tecnologies Mèdia\n");
	System.out.println("Enginyeria i Arquitectura La Salle, Universitat Ramon Llull 2008");
	mainFrame.AddTextMessage("Enginyeria i Arquitectura La Salle, Universitat Ramon Llull 2008\n");
	System.out.println(" ");
	mainFrame.AddTextMessage(" \n");
	System.out.println("MAGNUS permet controlar el punter del ratolí amb la veu.");
	mainFrame.AddTextMessage("MAGNUS permet controlar el punter del ratolí amb la veu.\n");
	System.out.println("Per obtenir un resultat òptim del programa cal entonar les ");
	mainFrame.AddTextMessage("Per obtenir un resultat òptim del programa cal entonar les \n");
	System.out.println("comandes vocals tot emfasitzant les síl·labes tòniques.");
	mainFrame.AddTextMessage("comandes vocals tot emfasitzant les síl·labes tòniques.\n");
	System.out.println(" ");
	mainFrame.AddTextMessage(" \n");
	mainFrame.AddTextMessage("Carregant el programa...\n");
	    
	RecognizerThread reconeixedor = new RecognizerThread();
	reconeixedor.start();
	  
	mainFrame.AddTextMessage("Operació completada satisfactòriament!\n");
	mainFrame.AddTextMessage("MAGNUS està preparat per reconèixer!\n\n");
	    
	System.out.println(" ");
	mainFrame.AddTextMessage(" \n");
	    
	mainFrame.setVisible(false);
	 
	String resultText="";

	MouseControl rob = new MouseControl();
		
	rob.SetInitPointer();
		
	MyMenu menu = new MyMenu(rob);
	menu.SetMicrophone(reconeixedor.microphone);
		
	if (menu.modus.equals("mouse")) {
		rob.modusmouse = true;
	} else {
		rob.modusmouse = false;
	}
		
	boolean alreadyhere=false,ahqmf=false;	// alreadyhere quiet menu true/false
		
	String staterec,auxtrans;
	boolean sleeping=false;

	int kbdstatuscount = 0;
	boolean kbdstate = false;

	while (true) {
		if (reconeixedor.NewSpeechResult()==true) {
			if (kbdstate == true) {
				kbdstatuscount = 0;
			}
			menu.SetSomeSpeech();
			resultText = reconeixedor.GetSpeechResult();
			rob.NewAction(resultText);
			if (rob.action.equals("mouse")) {
				menu.SetModus("mouse");
				kbdstate = false;
			}
			if (rob.action.equals("keyboard")) {
				menu.SetModus("keyboard");
				kbdstate = true;
				kbdstatuscount = 0;
			}
			if (rob.action.equals("sleep")) {
				if (sleeping == false) {
					sleeping = true;
				} else {
					sleeping = false;
				}
			}
			auxtrans = TranslateAction(rob.action);
			menu.SetSpeechRecognized(auxtrans);
		} else {
			if (kbdstate == true) {
			// Keyboard statud and nothing said
			// If idle for 5 seconds, return to mouse modus
				Thread.sleep(100);
				kbdstatuscount++;
				if (kbdstatuscount > 50) {
					kbdstate = false;
					menu.SetModus("mouse");
					rob.NewAction("mouse");
				}
			}

			if (sleeping==false) {
			rob.DoAction();
			if (rob.QuietPointer()==true) {		// then show the menu
				if (alreadyhere==true) {		// the boolean wacko is to avoid infinite win location
					if (menu.ConfigMove()==true) {
						menu.setLocation(500, 0);
						menu.setVisible(true);
					}
					if (menu.ConfigClosing()==true) {
						menu.setLocation(rob.GetX(),rob.GetY());
						menu.setVisible(true);
						rob.RestorePosition();
					}
				} else {
					menu.setLocation(rob.GetX(),rob.GetY());
					menu.setVisible(true);
					alreadyhere = true;
					ahqmf=false;
				}
			} else {
				if (ahqmf==true) {
					// do nothing
				} else {
					menu.setVisible(false);
					ahqmf = false;
					alreadyhere=false;
				}
			}
			} else {
				Thread.sleep(100);
			}
		}
	}
    }
    
    /**
     * This function translates the action pronounced into the appropriate
     * language the users may use, in order to display it accordingly.
     * This is intended for running Magnus in many different languages.
     * @param str String: contains the action that has just been spoken.
     * @return The translation of the spoken action into the appropriate language.
     */

    static public String TranslateAction(String str) {
    	String ret="";
    	
    	if (str.equals("sleep")) {
    		ret = "descansa";
    	}
    	if (str.equals("stop")) {
    		ret = "si!";
    	}
    	if (str.equals("up")) {
    		ret = "amunt";
    	}
    	if (str.equals("down")) {
    		ret = "avall";
    	}
    	if (str.equals("left")) {
    		ret = "esquerra";
    	}
    	if (str.equals("right")) {
    		ret = "dreta";
    	}
    	if (str.equals("click")) {
    		ret = "clic";
    	}
    	if (str.equals("double")) {
    		ret = "doble";
    	}
    	if (str.equals("drag")) {
    		ret = "arrossega";
    	}
    	if (str.equals("menu")) {
    		ret = "menú";
    	}
    	if (str.equals("keyboard")) {
    		ret = "teclat";
    	}
    	if (str.equals("mouse")) {
    		ret = "ratolí";
    	}
    	if (str.equals("change")) {
    		ret = "canvia";
    	}
    	
    	return ret;
    }

}
