/*
 * File    : RecognizerThread.java
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


import java.io.IOException;
import java.net.URL;

import java.io.*;


import edu.cmu.sphinx.frontend.util.Microphone;
import edu.cmu.sphinx.recognizer.Recognizer;
import edu.cmu.sphinx.result.Result;
import edu.cmu.sphinx.util.props.ConfigurationManager;
import edu.cmu.sphinx.util.props.PropertyException;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.*;

import java.util.*;


/**
 * The <I>RecognizerThread</I> class is the thread that initiates and
 * maintains the communication with the speech recognition engine, which
 * is Sphinx-4.
 * @author Alexandre Trilla (atrilla@xtec.cat, st12809@salle.url.edu)
 */

public class RecognizerThread extends Thread {
	String speechresult;
	boolean resultready;
	Recognizer recognizer;
	Microphone microphone;
	File tempgram,tempdict,tempconfig;
	boolean allok;
	
	/**
     	* This is the main method for launching the thread.
     	*/

	public RecognizerThread () {
		this.resultready = false;
		
		try {
			System.out.println("Creant els fitxers temporals...");
			CreateTemps();
			
			System.out.println("Modificant el fitxer temporal de configuració...");
			ModifyTempConfig();
			
			URL url=tempconfig.toURL();
            
            System.out.println("Carregant el reconeixedor...");
            ConfigurationManager cm = new ConfigurationManager(url);
            
            System.out.println("Configurant el reconeixedor...");
            recognizer = (Recognizer) cm.lookup("recognizer");
            
            System.out.println("Configurant el micròfon...");
    	    microphone = (Microphone) cm.lookup("microphone");

    	    /* allocate the resource necessary for the recognizer */
    	    System.out.println("Reservant els recursos necessaris pel reconeixedor...");
            recognizer.allocate();
            
            System.out.println("");
            System.out.println("Operació completada satisfactòriament! ");
            System.out.println("MAGNUS està preparat per reconèixer!");
            System.out.println("");
            
            } catch (IOException e) {
                System.err.println("Problema carregant MAGNUS: " + e);
                e.printStackTrace();
            } catch (PropertyException e) {
                System.err.println("Problema configurant MAGNUS: " + e);
                e.printStackTrace();
            } catch (InstantiationException e) {
                System.err.println("Problema creant MAGNUS: " + e);
                e.printStackTrace();
            }

	}
	

	/**
     	* This is the main method for running the speech recognition engine.
	* It will keep active all the time, analysing the incoming speech and
	* offering the results obtained.
     	*/

	public void run() {
		
		if (microphone.startRecording()) {
			while (true) {
				Result result = recognizer.recognize();
				
				if (result != null) {
					speechresult = result.getBestResultNoFiller();
					resultready = true;
				} else {
					System.out.println("No puc sentir el que has dit.\n");
				}
			}
		} else {
			System.out.println("No és possible iniciar el micròfon!");
			recognizer.deallocate();
			System.exit(1);
		}
	}
	

	/**
     	* This function returns a boolean indicating if a new command has been spoken.
	* It is the pre-condition for the <I>GetSpeechResult</I> function.
	* @return TRUE if a new command has been pronounced. FALSE otherwise.
     	*/

	public boolean NewSpeechResult() {
		boolean ret = resultready;
		
		resultready = false;
		
		return ret;
	}

	/**
     	* This function returns the most recent result recognized, say, the spoken
	* command. It is the interface to get the ASR engine results.
	* It requires a previous check with <I>NewSpeechResult</I> to ensure that
	* a new command has been uttered.
	* @return The most recent result recognized.
     	*/

	public String GetSpeechResult() {
		return speechresult;
	}
	
	/**
     	* This function loads some temporary files into the user's computer
	* in order to launch the speech engine.
     	*/

	public void CreateTemps() {
		try {
			tempgram = File.createTempFile("magnus", ".gram");
			tempgram.deleteOnExit();
			URL tempgramurl = getClass().getResource("magnus.gram");
			copy(tempgramurl.openStream(),tempgram);
			
			tempdict = File.createTempFile("magnus", ".dict");
			tempdict.deleteOnExit();
			URL tempdicturl = getClass().getResource("magnus.dict");
			copy(tempdicturl.openStream(),tempdict);
			
			tempconfig = File.createTempFile("magnus", ".aux");
			tempconfig.deleteOnExit();
			URL tempconfigurl = getClass().getResource("magnus.config.xml");
			copy(tempconfigurl.openStream(),tempconfig);
		} catch (IOException e) {
			System.err.println("Problema creant fitxers temporals per MAGNUS: " + e);
            e.printStackTrace();
		}
	}
	
	/**
     	* This function copies the content of one file (input stream) into another
	* destination file.
	* @param src InputStream: source of information.
	* @param dst File: destination file.
     	*/

	public void copy(InputStream src, File dst) throws IOException {
        InputStream in = src;
        OutputStream out = new FileOutputStream(dst);
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }
	
	/**
     	* This function deals with an XML text file.
	* @param is InputStream: source of information.
        * @return The document in question.
	* @throws Exception
     	*/

	public Document getXMLDocument(InputStream is) throws Exception {
        SAXBuilder saxBuilder = new SAXBuilder();
        Document doc = saxBuilder.build(is);
        return doc;
    }
	
        /**
        * This function extracts the plain name of a file.
        * @param str String: name of the input file (with its correspondent extension).
        * @return The name of the file (without extension).
        */

	public String PlainFileName(String str) {
		String result="";
		result = str.substring(0, str.length()-5);
		return result;
	}

        /**
        * This method modifies the temporal configuration files according to the configuration
        * of the user's computer.
        */
	
	public void ModifyTempConfig() {
		
		try {
			InputStream is = new FileInputStream(tempconfig);
			Document doc = getXMLDocument(is);
			Element root = doc.detachRootElement();
			tempconfig.delete();
			Element compelem,propelem;
			List listprops;
			List list = root.getChildren("component");
			for (int i=0; i<list.size(); i++) {
				compelem = (Element)list.get(i);
				if (compelem.getAttributeValue("name").equals("jsgfGrammar")) {
					listprops = compelem.getChildren();
					for (int j=0; j<listprops.size(); j++) {
						propelem = (Element)listprops.get(j);
						if (propelem.getAttributeValue("name").equals("grammarLocation")) {
							Attribute locate = propelem.getAttribute("value");
							locate.setValue(tempgram.getParent());
						}
						if (propelem.getAttributeValue("name").equals("grammarName")) {
							Attribute locate = propelem.getAttribute("value");
							String plainfn = PlainFileName(tempgram.getName());
							locate.setValue(plainfn);
						}
					}
				}
				if (compelem.getAttributeValue("name").equals("dictionary")) {
					listprops = compelem.getChildren();
					for (int j=0; j<listprops.size(); j++) {
						propelem = (Element)listprops.get(j);
						if (propelem.getAttributeValue("name").equals("dictionaryPath")) {
							Attribute locate = propelem.getAttribute("value");
							locate.setValue(tempdict.getAbsolutePath());
						}
						if (propelem.getAttributeValue("name").equals("fillerPath")) {
							Attribute locate = propelem.getAttribute("value");
							locate.setValue(tempdict.getAbsolutePath());
						}
					}
				}
			}
			tempconfig = File.createTempFile("magnus", ".config.xml");
			tempconfig.deleteOnExit();
			OutputStream out = new FileOutputStream(tempconfig);
			saveXMLDocument(out, root);
			out.close();
		} catch (IOException e) {
			System.err.println("Problema obrint configuració temporal: " + e);
            e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Problema obtenint configuració temporal: " + e);
            e.printStackTrace();
		}
	}

        /**
        * This function saves the temporal file, which is a XML file, into the temporary folder of the
        * user's computer.
        * @param out OutputStream: stream of data to be saved.
        * rootElement Element: root element of the XML file.
        * @throws Exception
        */

	public void saveXMLDocument(OutputStream out, Element rootElement) throws Exception {   
		   
        Format format = Format.getPrettyFormat();
        format.setIndent(" ");
        format.setEncoding("UTF-8");
        format.setTextMode (Format.TextMode.PRESERVE);
        XMLOutputter outputter = new XMLOutputter(format);

        outputter.output(new Document(rootElement), out);
        out.flush();
	}
}
