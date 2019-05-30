%Definición de la regla para que una oración sea válida.
o(o(SN,SV)) --> sn(SN,_Gen,Num),sv(SV,Num);sn(SN,_Gen,Num); sv(SV, Num); yn(YN), sn(SN,_Gen,Num).

sn(sn(DET,N),Gen,Num) --> det(DET,Gen,Num), n(N,Gen,Num); sal(SAL,Num),n(N,Gen,Num); n(N,Gen, Num).


sv(sv(VT,SN),Num) --> vt(VT,Num), sn(SN,_Gen,_Num).
sv(sv(VI),Num) --> vi(VI,Num).


det(det(X),f,sg) --> [X], {member(X,[la,una,para])}.
det(det(X),f,pl) --> [X], {member(X,[las,unas])}.
det(det(X),m,sg) --> [X], {member(X,[el,un,en,para])}.
det(det(X),m,pl) --> [X], {member(X,[los,unos])}.

sal(sal(X), sg) --> [X] ,{member(X,[hola,buenosdias])}.

yn(yn(X)) --> [X] ,{member(X,[si,no])}.

vi(vi(ladra),sg) --> [ladra].
vi(vi(ladran),pl) --> [ladran].
vi(vi(estoy),sg) --> [estoy].
vt(vt(estoy),sg) --> [estoy].
vt(vt(voy),sg) --> [voy].
vt(vt(vamos),pl) --> [vamos].

n(n(X),m,sg) --> [X], {member(X,[DrWaze,Waze])}.
n(n(X),m,sg) --> [X], {lugares(X)}.

