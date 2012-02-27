//
// File    : enhancement.sci
// Created : 23-May-2008
// By      : atrilla
// 
// Magnus - Computer mouse pointer controller through voice commands
// 
// Copyright (C) 2007 Alexandre Trilla &
// Departament d'Educacio de la Generalitat de Catalunya &
// Universitat Ramon Llull La Salle Enginyeria de Telecomunicacions
// 
// 
// This file is part of Magnus.
// 
// Magnus is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
// 
// Magnus is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details (see the COPYING file).
// 
// You should have received a copy of the GNU General Public License
// along with Magnus.  If not, see <http://www.gnu.org/licenses/>.
 

function enhancement(input_speech_file)

	// Given the noise polluted input speech file it performs the 
	// speech enhancement process to improve the spoken speech.
	//
	// The input_speech_file parameter corresponds to the name of an audio
	// file (*.wav). The generated output file will preserve the base name
	// of the input file name and the word '_enhanced' will be
	// appended to it.


	// Inclusion of the necessary functions
	getf('deesser_frame.sci');
	getf('shelving_filter.sci');
	getf('att_estimator.sci');
	getf('rms_level.sci');
	getf('alpha_calc.sci');
	getf('gain_controller.sci');
	getf('diff_fcincr.sci');

	// Loading of the input speech file
	[filein,fsamp,bits]=wavread(input_speech_file);
	printf('\nSpeech Enhancement Study for Magnus\n');
	printf('-----------------------------------\n');
	printf('File name: %s\n',input_speech_file);
	printf('Sampling frequency: %d\n',fsamp);
	printf('Bits per sample: %d\n\n',bits);

	// Elements

	// LPF Butterworth fc=2KHz 2nd order --> de-esser
	[poles,gain]=zpbutt(2,2.*%pi.*2000);
	Hs=syslin('c',gain./real(poly(poles,'s')));
	Hzss=cls2dls(tf2ss(Hs),1./fsamp);

	// LPF Butterworth fc=6KHz 2nd order --> excess boost reduction
	[poles_eb,gain_eb]=zpbutt(2,2.*%pi.*6000);
	Hs_eb=syslin('c',gain_eb./real(poly(poles_eb,'s')));
	Hzss_eb=cls2dls(tf2ss(Hs_eb),1./fsamp);

	// Gain controller --> de-esser
	gcss=gain_controller(6e-3,10e-3,fsamp);

	// Variables for initialization

	// For fsamp=16KHz the frame size equals 30ms
	frameSize=480;

	prevFrameDeesser=zeros(1,frameSize);

	deessed_sp=zeros(1,frameSize);

	resultEnhsp=0;

	atten=1;

	// Initial HP shelving filter
	fcut=3000;
	Gzero_hpsf=0.7;
	Gpi_hpsf=1.7;
	shf=shelving_filter(fcut,fsamp,Gzero_hpsf,Gpi_hpsf);
	shfss=tf2ss(shf);
	fcVect=fcut.*ones(1,frameSize);

	framePrev=zeros(1,frameSize);
	prevHpsfFiltFrame=zeros(1,frameSize);

	printf('Processing ');


	// Process --> speech enhancement

	// Set the length of the input signal a multiple of the analysis frame size
	// by padding it with zeros.
	multi=floor(length(filein)./frameSize);
	if multi*frameSize == length(filein) then
	else
		numdif=(multi+1).*frameSize - length(filein);
		filein=[filein,zeros(1,numdif)];
	end

	// Variables for the boost reducer
	prevRBS=zeros(1,frameSize);
	prevHpsfFF=zeros(1,frameSize);
	// The values have been obtain from Scilab for a 2nd order Butterworth filter
	// and fc=6KHz
	Abr=1.421e9;
	Bbr=53314.595;
	alphabr=4.*fsamp.^2 - 2.*Bbr.*fsamp + Abr;
	betabr=-8.*fsamp.^2 + 2.*Abr;
	gammabr=4.*fsamp.^2 + 2.*Bbr.*fsamp + Abr;

	for i=0:multi

		for j=(frameSize.*i+1):frameSize.*(i+1)
			frame(j-frameSize.*i)=filein(j);
		end
		

		// High Pass Shelving Filter Process
		// ----------------------------------------------------------------

		// Input frame level
		frameLevel=rms_level(frame);

		// Filtered frame level
		filtFrame=dsimul(shfss,frame');
		filtFrameLevel=rms_level(filtFrame);

		// APF parameter estimation
		differ=frameLevel - filtFrameLevel;

		// Vector of cut-off frequencies
		fcVect=diff_fcincr(differ,fcVect,fsamp)

		// Map: fc --> alpha
		for k=1:length(fcVect)
			alp(k)=alpha_calc(fcVect(k),fsamp);
		end

		// Processing through the HP shelving filter in the discrete time domain
		A=-Gpi_hpsf.*alp(1)-Gpi_hpsf-Gzero_hpsf.*alp(1)+Gzero_hpsf;
		B=Gpi_hpsf+Gpi_hpsf.*alp(1)+Gzero_hpsf-Gzero_hpsf.*alp(1);
		hpshFiltFrame(1)=(B./2).*frame(1)+(A./2).*framePrev(length(framePrev))+alp(1).*prevHpsfFiltFrame(length(prevHpsfFiltFrame));
		for m=2:length(frame)
			A=-Gpi_hpsf.*alp(m)-Gpi_hpsf-Gzero_hpsf.*alp(m)+Gzero_hpsf;
			B=Gpi_hpsf+Gpi_hpsf.*alp(m)+Gzero_hpsf-Gzero_hpsf.*alp(m);
			hpshFiltFrame(m)=(B./2).*frame(m)+(A./2).*frame(m-1)+alp(m).*hpshFiltFrame(m-1);
		end

		// Refresh items for the next iteration
		shf=shelving_filter(fcVect(length(fcVect)),fsamp,Gzero_hpsf,Gpi_hpsf);
		shfss=tf2ss(shf);
		framePrev=frame;
		prevHpsfFiltFrame=hpshFiltFrame;


		// Low Pass Shelving Filter to reduce the excess boost of HF components
		// -------------------------------------------------------------------

//		reducedBoostSpeech=dsimul(Hzss_eb,hpshFiltFrame');

		reducedBoostSpeech(1)=(Abr./gammabr).*hpshFiltFrame(1) + (2.*Abr./gammabr).*prevHpsfFF(length(prevHpsfFF)) + (Abr./gammabr).*prevHpsfFF(length(prevHpsfFF)-1) - (betabr./gammabr).*prevRBS(length(prevRBS)) - (alphabr./gammabr).*prevRBS(length(prevRBS)-1);
		reducedBoostSpeech(2)=(Abr./gammabr).*hpshFiltFrame(2) + (2.*Abr./gammabr).*hpshFiltFrame(1) + (Abr./gammabr).*prevHpsfFF(length(prevHpsfFF)) - (betabr./gammabr).*reducedBoostSpeech(1) - (alphabr./gammabr).*prevRBS(length(prevRBS));
		// Process of reducing the excessive boost
		for n=3:length(frame)
			reducedBoostSpeech(n)=(Abr./gammabr).*hpshFiltFrame(n) + (2.*Abr./gammabr).*hpshFiltFrame(n-1) + (Abr./gammabr).*hpshFiltFrame(n-2) - (betabr./gammabr).*reducedBoostSpeech(n-1) - (alphabr./gammabr).*reducedBoostSpeech(n-2);
		end

		// Refresh
		prevHpsfFF=hpshFiltFrame;
		prevRBS=reducedBoostSpeech;


		// De-esser module
		// -------------------------------------------------------------------

		frameDeesser=reducedBoostSpeech;

		// De-esser process
		[deessed_sp,atten]=deesser_frame(deessed_sp,frameDeesser,prevFrameDeesser,atten,Hzss,gcss,fsamp);
	
		// Refresh for the next iteration		
		prevFrameDeesser=frameDeesser;


		// Result write-back
		resultEnhsp=[resultEnhsp,deessed_sp'];
		if i==multi then
			printf('.\n\n');
		else
			printf('.');
		end

	end


	// Keep the sizes equal
	filein=[0,filein];

	// Create the name of the resulting audio file
	v = strsplit(input_speech_file,length(input_speech_file)-4);
	strenhanced=v(1)+'_enhanced.wav';

	// Write the resulting audio file
	wavwrite(resultEnhsp,fsamp,strenhanced);

	printf('\nPlease check the file -->%s<-- to hear the speech enhancement results!\n\n',strenhanced);

	// Plot the results
	subplot(2,1,1);
	thexaxis=1:length(filein);
	plot2d(thexaxis,[filein' resultEnhsp'],[2 5],leg="Input speech@Enhanced speech");
	xtitle('Signal plot comparison','Samples','Amplitude');

	subplot(2,1,2);
	thexaxis=1:length(filein);
	plot2d(thexaxis,[abs(fft(resultEnhsp')) abs(fft(filein'))],[5 2],leg="FFT Enhanced speech@FFT Input speech");
	xtitle('FFT Signal plot comparison','Samples','Amplitude');

endfunction
