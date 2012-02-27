//
// File    : att_estimator.sci
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
 

function att=att_estimator(ratio,th1,th2,slope,cnt,fingain)

	// Estimates the linear attenuation for the high
	// spectre of the signal. Performs a
	// hard-knee attenuation according to
	// the input ratio, the thresholds,
	// the attenuation slope and constant and
	// the final gain.

	if ratio < th1 then
		att = 1;
	elseif ratio < th2 then
		att = slope.*ratio + cnt;
	else
		att = fingain;
	end

endfunction
