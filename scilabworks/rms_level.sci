//
// File    : rms_level.sci
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


function thelev=rms_level(frame)
	
	// Computes the RMS of the frame.
	// This parameter is sometimes referred to as
	// the effective level.

	// Square of the frame
	frame2 = frame.^2;

	// Sum
	sf2 = sum(frame2);

	// Mean
	msf2 = (1./length(frame)).*sf2;

	// Square root
	thelev = sqrt(msf2);

endfunction
