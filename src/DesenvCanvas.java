/*
 * File    : DesenvCanvas.java
 * Created : 14-Feb-2008
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
 * The class <it>DesenvCanvas</it> defines the canvas where
 * the development banner will be placed.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class DesenvCanvas extends Canvas {

    /**
     * This is the constructor for <it>DesenvCanvas</it>.
     */

    public DesenvCanvas() { //constructor
        super();
    }

    /**
     * This method paints the canvas.
     * @param g Graphics: this is the graphical environment.
     */

    public void paint (Graphics g) {
        Toolkit toolkit=Toolkit.getDefaultToolkit();
        URL url=getClass().getResource("desenvolupament.png");
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
