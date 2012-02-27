/*
 * File    : MyScreen.java
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
import java.net.*;

/**
 * The class <it>MyScreen</it> defines the frame where
 * the start project banner is displayed as well as the
 * initialization messages.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyScreen extends Frame {
	
	TextArea text;

    /**
     * This is the screen constructor.
     */

    public MyScreen() { //constructor
  	        ScrollPane scrollPane=new ScrollPane(ScrollPane.SCROLLBARS_NEVER);
  	        scrollPane.setSize(800, 185);
  	        scrollPane.setBackground(new Color(0,128,0));
  	        Canvas canvas=new MyCanvas();
  	        canvas.setSize(800, 183);
  	        scrollPane.add(canvas);
  	        add(scrollPane,BorderLayout.NORTH);
  	        text = new TextArea();
  	        text.setBackground(new Color(255,255,255));
  	        text.setFont(new Font("SansSerif",Font.BOLD,12));
  	        add(text,BorderLayout.CENTER);
  	        Toolkit toolkit=Toolkit.getDefaultToolkit();
  	        URL url=getClass().getResource("icon.png");
  	        Image image=toolkit.getImage(url);
  	        setIconImage(image);
  	        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
            	System.out.println("Desallotjant els recursos...");
            	text.append("Desallotjant els recursos...\n");
            	System.out.println("Gràcies per utilitzar MAGNUS! Fins aviat!");
            	text.append("Gràcies per utilitzar MAGNUS! Fins aviat!\n\n");
                dispose();
                System.exit(0);
            }
        });
    }
    
    /**
     * This method inserts a text into the start visual console.
     * @param msg String: the text in question to be insterted.
     */

    public void AddTextMessage(String msg) {
    	text.append(msg);
    }
    
}
