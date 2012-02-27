//
// File    : deesser_frame.sci
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
 

function [newDeessedSp,newatten]=deesser_frame(deessedSp,frame,prevFrame,atten,Hzss,gcss,fsamp)

	// Processes the input frame to perform the de-essing.

	// Low Pass Filtering
	y=dsimul(Hzss,frame');

	// Energy
	e1=rms_level(frame);
	e2=rms_level(y);

	// Ratio
	ratio=e1./e2;

	// Attenuation estimation
	att=att_estimator(ratio,1.5,3,-0.67,2,0);
	newatten=att;

	if att==atten then
		attwave=att.*ones(1,length(frame));
	else
		aux1=atten.*ones(1,length(frame));
		aux2=att.*ones(1,length(frame));
		u=[aux1,aux2];

		prevattwave=dsimul(gcss,u);
		for i=1:length(frame)
			attwave(i)=prevattwave(i+length(frame));
		end
	end

	// De-essing through a LPF based on the main shelving filter proposed in Chanda and Park
	alpha_num=alpha_calc(2000,fsamp);
	A=-attwave(1).*alpha_num-attwave(1)-alpha_num+1;
	B=attwave(1)+attwave(1).*alpha_num+1-alpha_num;
	newDeessedSp(1)=(B./2).*frame(1)+(A./2).*prevFrame(length(prevFrame))+alpha_num.*deessedSp(length(deessedSp));
	for i=2:length(frame)
		A=-attwave(i).*alpha_num-attwave(i)-alpha_num+1;
		B=attwave(i)+attwave(i).*alpha_num+1-alpha_num;
		newDeessedSp(i)=(B./2).*frame(i)+(A./2).*frame(i-1)+alpha_num.*newDeessedSp(i-1);
	end

endfunction
