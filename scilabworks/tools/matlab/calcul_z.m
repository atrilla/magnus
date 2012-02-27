function calcul_z(n,d,T)
% calcula l'equivalent d'una funcio analogica (n,d) a un digital (nd,dd) 
% incloent el conversor D/A
% T es el periode mostreig

[A,B,C,D]=tf2ss(n,d)
[F,G]=c2d(A,B,T)
[nd,dd]=ss2tf(F,G,C,D)

nd

dd

roots(nd)

roots(dd)
