/*
 * File    : HPSFilter.java
 * Created : 6-Sep-2008
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
 * A High Pass Shelving Filter based on [Shankar Chanda and Park, 2007].
 */
public class HPSFilter extends BaseDataProcessor {

    public static final String PROP_HPSF_Gz = "gz";
    public static final double PROP_HPSF_Gz_DEFAULT = 0.7;

    public static final String PROP_HPSF_Gp = "gp";
    public static final double PROP_HPSF_Gp_DEFAULT = 1.7;

    private double fc=3000;
    private double gzValue, gpValue;
    private double xPrior=0;
    private double yPrior=0;


    public void register(String name, Registry registry)
            throws PropertyException {
        super.register(name, registry);
        registry.register(PROP_HPSF_Gz, PropertyType.DOUBLE);
        registry.register(PROP_HPSF_Gp, PropertyType.DOUBLE);
    }


    public void newProperties(PropertySheet ps) throws PropertyException {
        super.newProperties(ps);
	gzValue=ps.getDouble(PROP_HPSF_Gz, PROP_HPSF_Gz_DEFAULT);
	gpValue=ps.getDouble(PROP_HPSF_Gp, PROP_HPSF_Gp_DEFAULT);
    }

    /**
     * Returns the next Data object being processed by this HPSF, or
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
                applyHPSF(((DoubleData) input).getValues());
            }
        }
        getTimer().stop();
        return input;
    }

    /**
     * Applies HPSF filter to the given Audio.
     * 
     * @param in double[]: audio data
     */

    private void applyHPSF(double[] in) {
	    // Input frame level
	    double frameLevel = applyRMS(in);
	    
	    // apply filter to fc
	    double alph = mapAlpha(fc);
	    GenericFilter hSH = new GenericFilter(getNum(alph), getDen(alph));
	    double[] filtFrame = hSH.applyFilter(in);

	    // Filtered frame level
	    double filtFrameLevel = applyRMS(filtFrame);

	    // APF parameter estimation
	    double differ = frameLevel - filtFrameLevel;
	    double[] fcVect = new double[in.length];	
	    for (int i=0; i<in.length; i++) {
	    	fcVect[i]=fc;
	    }

	    // Vector of cut-off frequencies
	    fcVect = applyFcIncr(differ,fcVect);

	    // Keep fc for the next iteration
	    fc = fcVect[fcVect.length - 1];

	    // Map: fc --> alpha
	    double[] alp = new double[fcVect.length];
	    for (int k=0; k<fcVect.length; k++) {
	    	alp[k] = mapAlpha(fcVect[k]);
	    }

	    // Processing through the HP shelving filter in the discrete time domain
	    // A GenericFilter is not used because the values of alpha vary in time.
	    double A, B;
	    for (int i=0; i<in.length; i++) {
	    	A = -gpValue*alp[i]-gpValue-gzValue*alp[i]+gzValue;
	    	B = gpValue+gpValue*alp[i]+gzValue-gzValue*alp[i];
		yPrior = (B/2)*in[i] + (A/2)*xPrior + alp[i]*yPrior;
		xPrior = in[i];
		in[i] = yPrior;
	    }
    }


    /**
     * Calculates the Root Mean Square.
     *
     * @param in double[]: Input speech.
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


    /**
     * Applies increment to the fc vector.
     *
     * @param newdiff double: the difference of signal levels.
     * @param presFcVect double[]: the present vector of fc's.
     */

    private double[] applyFcIncr(double newdiff, double[] presFcVect) {
    	// Linear parameters
	double incrFc = -833*newdiff;
	double newFc = presFcVect[presFcVect.length - 1] + incrFc;

	// Fc clipping
	if (newFc > 3900) {
		newFc = 3900;
	}
	if (newFc < 2000) {
		newFc = 2000;
	}

	double[] aux = new double[presFcVect.length];
	for (int i=0; i<presFcVect.length; i++) {
		aux[i] = newFc;
	}
	double[] u = new double[2*presFcVect.length];
	for (int i=0; i<presFcVect.length; i++) {
		u[i] = presFcVect[i];
	}
	for (int i=presFcVect.length; i<(2*presFcVect.length); i++) {
		u[i] = aux[i - presFcVect.length];
	}

	double[] gcNum = new double[3];
	double[] gcDen = new double[3];
	if (incrFc > 0) {
		// tau = tr = 6e-3
		gcNum[0] = 0.0001107;
		gcNum[1] = 0.0002215;
		gcNum[2] = 0.0001107;
		gcDen[0] = 0.9793837;
		gcDen[1] = -1.9789408;
		gcDen[2] = 1;
	} else {
		// tau = 6e-3, tr = 20e-3
		gcNum[0] = 0.0000265;
		gcNum[1] = 0.0000531;
		gcNum[2] = 0.0000265;
		gcDen[0] = 0.9793820;
		gcDen[1] = -1.9792758;
		gcDen[2] = 1;
	}
	GenericFilter fcController = new GenericFilter(gcNum, gcDen);
	double[] theWave = fcController.applyFilter(u);
	double[] resultVect = new double[presFcVect.length];
	for (int i=0; i<presFcVect.length; i++) {
		resultVect[i] = theWave[i+presFcVect.length];
	}

	return resultVect;
    }


    /**
     * Maps the frequency cut to the correspondent alpha value.
     * A sampling frequency of 16KHz is supposed.
     *
     * @param fc double: the cut-off frequency.
     */

    private double mapAlpha(double fc) {
    	double cwc = Math.cos(2*Math.PI*fc/16000);

	return (2-Math.sqrt(4-4*cwc*cwc))/(2*cwc);
    }


    /**
     * Provides the numerator of Hsh(z).
     *
     * @param alpha double: the alpha parameter.
     */

    private double[] getNum(double alpha) {
    	double[] aux = new double[2];

	aux[0] = gzValue*(1-alpha) - gpValue*(1+alpha);
	aux[1] = gzValue*(1-alpha) + gpValue*(1+alpha);

	return aux;
    }


    /**
     * Provides the denominator of Hsh(z).
     *
     * @param alpha double: the alpha parameter.
     */

    private double[] getDen(double alpha) {
    	double[] aux = new double[2];

	aux[0] = -2*alpha;
	aux[1] = 2;

	return aux;
    }

}
