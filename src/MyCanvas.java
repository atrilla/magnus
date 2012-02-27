/*
 * File    : MyCanvas.java
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
import java.net.*;

/**
 * The class <it>MyCanvas</it> defines the canvas where
 * the start project banner is displayed. 
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class MyCanvas extends Canvas {

    /**
     * This is the constructor.
     */

    public MyCanvas() { //constructor
        super();
    }

    /**
     * This is the main method to paint the canvas.
     * @param g Graphics: the graphical environment.
     */

    public void paint (Graphics g) {
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        URL url=getClass().getResource("magnus_header.png");
        Image image=toolkit.getImage(url);
        MediaTracker mediaTracker=new MediaTracker(this);
        mediaTracker.addImage(image,0);
            try {
                mediaTracker.waitForAll();
                    if(mediaTracker.checkAll()) {
                        g.drawImage(image,0,0,this);
                    }
            } catch (InterruptedException e) {
                System.out.println("No puc amb la imatge!");
            }
    }

}
