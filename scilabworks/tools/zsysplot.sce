function zsysplot(fz)

// zsysplot(fz)
//
// This function plots the response of a system described in the Z domain
// through the representation of the logarithmic gain response and the
// phase response in degrees.
//
//	fn = 0   (continuous component)
//	fn = 0.5 (Nyquist's frequency)
//	fn = 1   (sampling rate)

omega=exp(2.*%pi.*((0:999)./1000).*%i);
Fz=horner(fz,omega);
[db,phi]=dbphi(Fz);
resp_db(1:500)=db(1:500);
resp_phi(1:500)=phi(1:500);
subplot(2,1,1);
t=(0:0.001:0.499);
plot2d(t,resp_db);
a=get('current_axes');
p=a.children.children(1);
set(p,'thickness',2);
xtitle('','Normalized frequency [rad]','Magnitude [dB]');
subplot(2,1,2);
plot2d(t,resp_phi);
a=get("current_axes");
p=a.children.children(1);
set(p,'thickness',2);
xtitle('','Normalized frequency [rad]','Phase [degree]');

endfunction
