%FUNCIONES PARA EL MANEJO DE LISTAS

concatenar([],L,L).
concatenar([C|R],L,[C|R1]):-  concatenar(R,L,R1).

agregarini(X, L, [X|L]).

eliminarini(L,L1):-
agregarini(_,L1,L).

juntar([C|R],L,F):- eliminarini(L, L1), concatenar([C|R],L1,F).

sumar(X, Y, Z):-Z is X + Y.

agregar(X, [], [X]).
agregar(X, [C|R], [C|R1]):-agregar(X, R, R1).


miembro(X,[X|_]).
miembro(X,[_|R]):-miembro(X,R).