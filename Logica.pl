:-style_check(-singleton).
:-dynamic
	rpath/2.
iniciar():-pregunta1().

:-[arcos].
:-[lugares].
:-[gramatica].
:-[manejo_listas].


%...SERIE DE PREGUNTAS Y RESPUESTAS...
recibir_mensaje(M):-write('Usuario: '),read(Entrada), atomic_list_concat(M, ' ', Entrada), phrase(o(A),M).

pregunta1():-write('DrWaze: Bienvenido a WazeLog la mejor logica de llegar a su destino. \nPor Favor indiqueme donde se encuentra. \n'),
			 recibir_mensaje(M), respuesta1(M).

respuesta1(M):-lugares(X), miembro(X,M), pregunta2(X);
			   write('DrWaze: No puedo entenderle, por favor indiqueme de manera correcta donde se encuentra.\n'), recibir_mensaje(M), respuesta1(M).

pregunta2(O):-write('DrWaze: Muy bien ¿Cual es su destino?\n'),
			  recibir_mensaje(M), respuesta2(M,O).

respuesta2(M,O):-lugares(X), miembro(X,M), avisitar(O,X,[]);
			     write('DrWaze: No puedo entenderle, por favor indiqueme de manera correcta su destino.\n'), recibir_mensaje(M), respuesta2(M,O).

pregunta3(O,D):-write('DrWaze: ¿Tiene algun destino intermedio?\n'),
			    recibir_mensaje(M), respuesta3(O,D,M).

respuesta3(O,D,M):-miembro(si,M),lugares(X),miembro(X,M),avisitar(O,_,D);
				   miembro(si,M),lugares(X), not(miembro(X,M)), preguntarI(I), avisitar(O,I,D);
				   miembro(no,M), imprimir(O,D,[],0);
                                   write('DrWaze: No puedo entenderle, intente de nuevo.\n'), pregunta3(O,D).

preguntarI(I):-write('DrWaze: ¿Cual es el destino intermedio?\n'), recibir_mensaje(M), lugares(I), miembro(I,M);
			  write('DrWaze: No puedo entenderle, intente de nuevo.\n'), preguntarI(I).

avisitar(O,I,T):-  agregarini(I,T,K), pregunta3(O,K).

imprimir(O,[C|L],[],0):-  ruta(O,C,R1,T1),imprimir(C,L,R1,T1).
imprimir(O,[C|L],R,T):- ruta(O,C,R1,T1), sumar(T,T1,T2),juntar(R,R1,R2),imprimir(C,L,R2,T2).
imprimir(O,[C|_],R,T):-ruta(O,C,R1,T1), sumar(T,T1,T2),juntar(R,R1,R2), writef('su ruta es %w y recorrera: %w km \n',[R2, T2]).
imprimir(O,[C|_],[],0):-ruta(O,C,R2,T2), writef('su ruta es %w y recorrera: %w km \n',[R2, T2]).



camino(Ini,Fin,Dist) :- arco(Ini,Fin,Dist).


mascorto([H|Path], Dist) :-
	rpath([H|T], D), !, Dist < D,
	retract(rpath([H|_],_)),
        assert(rpath([H|Path], Dist)).
mascorto(Path, Dist) :-
         assert(rpath(Path,Dist)).



atravesar(Inicio, Camino, Dist) :-
	camino(Inicio, T, D),
	not(memberchk(T, Camino)),
	mascorto([T,Inicio|Camino], Dist+D),
	atravesar(T,[Inicio|Camino],Dist+D).

atravesar(Inicio) :-
	retractall(rpath(_,_)),
	atravesar(Inicio,[],0).
atravesar(_).




ruta(Inicio, Fin, Camino,Distancia) :-
	atravesar(Inicio),
	rpath([Fin|R], Dist)->
        reverse([Fin|R], Camino),
        Distancia is round(Dist);

	writef('No conozco la ruta de %w a %w\n', [Inicio, Fin]).

