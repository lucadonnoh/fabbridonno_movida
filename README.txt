===================================================
              PROGETTO MOVIDA
===================================================

Progetto del corso Algoritmi e Strutture Dati
Anno 2019/2020, Unibo.

Realizzato dal gruppo: fabbridonno
> Juri Fabbri	0000915308
> Luca Donno	0000924603

Algoritmi e strutture dati assegnati:
> Algoritmi di ordinamento: InsertionSort, QuickSort
> Implementazioni dizionario: ListaConcatenata, HashIndirizzamentoAperto

---------------------------------------------------
	      Istruzioni per compilare/eseguire
---------------------------------------------------

Per eseguire il codice importare la classe MovidaCore
contenuta in fabbridonno_movida\src\movida\fabbridonno.
Il costruttore non prende parametri aggiuntivi.
È già presente una classe test con metodi di prova in
fabbridonno_movida\src\movida\fabbridonno\Test.

---------------------------------------------------
		            Funzionamento
---------------------------------------------------

La classe MovidaCore è la classe entry-point dell'
applicazione MOVIDA, che permette di importare una
knowledge-base a tema cinema da un file testuale
che deve rispettare un certo formato.
Si possono quindi ricavare informazioni sui film,
direttori ed attori, ed effettuare ricerche su di
essi in base a diversi criteri.

MovidaCore contiene le strutture dati che contengono
le informazioni sui film. Si è deciso di usare
5 istanze di dizionario, ognuna con una chiave diversa
per rendere le query più veloci a discapito di poca
memoria e tempo di caricamento iniziale in più.

DizionarioFilm è l'interfaccia che verrà implementata
dalle strutture dati. Son contenuti i metodi che
verranno usati all'interno di MovidaCore.

La classe Record contiene una lista di elementi e la loro
corrispondente chiave. Viene salvato più di un elemento
nei dizionari che hanno come chiave un direttore o un attore,
in quanto un direttore può fare più film e un attore può
partecipare a più film.

La sottoclasse ListRecord aggiunge un puntatore ad un altro
ListRecord per poter implementare una lista.

ListaNonOrdinata implementa una lista concatenata singola
di ListRecord.
TabellaHashAperta implementa una hashtable con indirizzamento
aperto e ispezione quadratica di Record.

Graph implementa tramite HashMap un grafo non orientato dove
ad ogni nodo vengono corrisposti i suoi archi uscenti.
Per rendere il grafo non orientato, ad ogni arco uscente
corrisponde un arco entrante.
Salva le collaborazioni tra attori.

Per l'implementazione del metodo maximizeCollaborationsInTheTeamOf()
è stato utilizzato l'algoritmo di Prim adattato per un maximum spanning tree,
che sfrutta una coda a massima priorità.

Entry estende la classe AbstractMap.SimpleEntry per permettere
l'override dei metodi equals e hashcode.