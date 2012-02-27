/*
 * File    : DeEsser.java
 * Created : 10-Sep-2008
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

import edu.cmu.sphinx.frontend.BaseDataProcessor;
import edu.cmu.sphinx.frontend.Data;
import edu.cmu.sphinx.frontend.DataEndSignal;
import edu.cmu.sphinx.frontend.DataProcessingException;
import edu.cmu.sphinx.frontend.DoubleData;
import edu.cmu.sphinx.util.props.PropertyException;
import edu.cmu.sphinx.util.props.PropertySheet;
import edu.cmu.sphinx.util.props.PropertyType;
import edu.cmu.sphinx.util.props.Registry;

import java.lang.Math;

/**
 * De-esser module.
 */
public class DeEsser extends BaseDataProcessor {

    public static final String PROP_DE_TH1 = "threshold1";
    public static final double PROP_DE_TH1_DEFAULT = 1.5;

    public static final String PROP_DE_TH2 = "threshold2";
    public static final double PROP_DE_TH2_DEFAULT = 3;

    public static final String PROP_DE_SLOPE = "slope";
    public static final double PROP_DE_SLOPE_DEFAULT = -0.67;

    public static final String PROP_DE_CNT = "constant";
    public static final double PROP_DE_CNT_DEFAULT = 2;

    public static final String PROP_DE_FINGAIN = "fingain";
    public static final double PROP_DE_FINGAIN_DEFAULT = 0;

    private double xPrior=0;
    private double yPrior=0;

    private double th1Value, th2Value, slopeValue, cntValue, fingainValue;
    private double newAtt, oldAtt=1;


    public void register(String name, Registry registry)
            throws PropertyException {
        super.register(name, registry);
        registry.register(PROP_DE_TH1, PropertyType.DOUBLE);
        registry.register(PROP_DE_TH2, PropertyType.DOUBLE);
        registry.register(PROP_DE_SLOPE, PropertyType.DOUBLE);
        registry.register(PROP_DE_CNT, PropertyType.DOUBLE);
        registry.register(PROP_DE_FINGAIN, PropertyType.DOUBLE);
    }


    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
	th1Value = ps.getDouble(PROP_DE_TH1,PROP_DE_TH1_DEFAULT);
	th2Value = ps.getDouble(PROP_DE_TH2,PROP_DE_TH2_DEFAULT);
	slopeValue = ps.getDouble(PROP_DE_SLOPE,PROP_DE_SLOPE_DEFAULT);
	cntValue = ps.getDouble(PROP_DE_CNT,PROP_DE_CNT_DEFAULT);
	fingainValue = ps.getDouble(PROP_DE_FINGAIN,PROP_DE_FINGAIN_DEFAULT);
    }

    /**
     * Returns the next Data object being processed by this module, or
     * if it is a Signal, it is returned without modification.
     * 
     * @return the next available Data object, returns null if no Data object
     *         is available
     * 
     * @throws DataProcessingException
     *                 if there is a processing error
     */

    public Data getData() throws DataProcessingException {
        Data input = getPredecessor().getData();
        getTimer().start();
        if (input != null) {
            if (input instanceof DoubleData) {
                applyDeEsser(((DoubleData) input).getValues());
            }
        }
        getTimer().stop();
        return input;
    }

    /**
     * Applies the de-essing process to the given Audio.
     * 
     * @param in double[]: audio data
     */

    private void applyDeEsser(double[] in) {
	    // Input frame level
	    double frameLevel = applyRMS(in);
	    
	    // apply filter Butterworth 2KHz 2n order
	    double[] num = new double[3];
	    double[] den = new double[3];
	    num[0] = 0.3423570;
	    num[1] = 0.6847139;
	    num[2] = 0.3423570;
	    den[0] = 0.1780545;
	    den[1] = 0.1913733;
	    den[2] = 1;
	    GenericFilter filtIN = new GenericFilter(num,den);
	    double[] filtFrame = filtIN.applyFilter(in);

	    // Filtered frame level
	    double filtFrameLevel = applyRMS(filtFrame);

	    // Ratio
	    double ratio = frameLevel/filtFrameLevel;

	    // Attenuation estimation
	    newAtt = 1;
	    if (ratio < th1Value) {
	    } else if (ratio < th2Value) {
	    	newAtt = slopeValue*ratio + cntValue;
	    } else {
	    	newAtt = fingainValue;
	    }

	    double[] attWave = new double[in.length];
	    if (newAtt == oldAtt) {
	    	for (int i=0;i<in.length; i++) {
			attWave[i] = newAtt;
		}
	    } else {
		double[] u = new double[2*in.length];
		for (int i=0; i<in.length; i++) {
			u[i] = oldAtt;
		}
		for (int i=in.length; i<(2*in.length); i++) {
			u[i] = newAtt;
		}

		// Controller for the att adaptation
	    	num[0] = 0.0000594;
	    	num[1] = 0.0001189;
	    	num[2] = 0.0000594;
	    	den[0] = 0.9793827;
	    	den[1] = -1.9791449;
	    	den[2] = 1;
		
		GenericFilter gcDE = new GenericFilter(num,den);
		double[] prevAttWave = gcDE.applyFilter(u);
		for (int i=0; i<in.length; i++) {
			attWave[i] = prevAttWave[i+in.length];
		}
	    }

	    // De-essing process through a LPF based on the main shelving filter
	    // proposed in [Chanda and Park, 2007]

	    double A, B;
	    double cwc = Math.cos(2*Math.PI*2/16);
	    double alp = (2-Math.sqrt(4-4*cwc*cwc))/(2*cwc);
	    for (int i=0; i<in.length; i++) {
	    	A = -attWave[i]*alp-attWave[i]-alp+1;
	    	B = attWave[i]+attWave[i]*alp+1-alp;
		yPrior = (B/2)*in[i] + (A/2)*xPrior + alp*yPrior;
		xPrior = in[i];
		in[i] = yPrior;
	    }
    }


    /**
     * Calculates the Root Mean Square.
     *
     * @param frame double[]: Input signal.
     */

    private double applyRMS(double[] frame) {
    	double[] frame2 = new double[frame.length];
	double sf2;
	double msf2;

	for (int i=0; i<frame.length; i++) {
		frame2[i] = frame[i]*frame[i];
	}

	sf2 = 0;
	for (int i=0; i<frame.length; i++) {
		sf2 = sf2 + frame2[i];
	}

	msf2 = sf2/frame.length;

	return Math.sqrt(msf2);
    }

}
