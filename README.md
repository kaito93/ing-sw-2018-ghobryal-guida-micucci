
# Sagrada - Prova Finale (Ingegneria del Software) - Gruppo 04   
  
## Lista dei membri:  
  
 - *Ghobryal Anton - Matricola: 844941*  
 - *Guida Samuele - Matricola: 806408*  
- *Micucci Andrea - Matricola 811025*  
  
## ***Lista dei requisiti sviluppati***  
  
***Regole:***  
 - *Regole complete:* Abbiamo deciso di implementare tutte le funzionalità supportate dal gioco reale, ovvero la                          versione spiegata nel regolamento del gioco disponibile sul sito ufficiale.  
  
***User Interface:***  
 - *Command Line Interface:* Pur essendo un gioco molto grafico abbiamo deciso di implementare la Command Line Interface mediante l'utilizzo di codici *unicode* per gestire i colori e rendere il gioco più approcciabile anche tramite linea di testo.  
 - *Graphical User Interface:* Dotato di particolari risorse grafiche, questo gioco si appresta molto ad essere un gioco grafico, la gestione avviene mediante l'utilizzo dei componenti predisposti da *JavaFx*.  
  
***Connessione di rete:***  
 - *Socket:* Le comunicazioni tra server e client possono essere gestite tramite questa tipologia di connessione attraverso un protocollo realizzato ad hoc ideato per spedire in rete degli oggetti e non delle stringhe.  
 - *RMI:* Le comunicazioni tra server e client possono essere gestite tramite questa tipologia di connessione attraverso la chiamata di metodi presenti in opportune interfacce rese disponibili nella fase iniziale della partita.  
  
***Funzionalità avanzate:***  
 - *Partite multiple:* E' stata implementata la possibilità di gestire più partite contemporaneamente sullo stesso server attraverso una serie diversa di lobby che gestisce ognuna una singola partita.  
 - *Carte schema dinamiche:* E' stata implementata la possibilità di modificare le carte schema accedendo al file *Maps.Json* e modificando le proprietà delle singole celle. Automaticamente queste modifiche vengono acquisite correttamente e la *UI* stampa a schermo la carta schema modificata.  
  
***Nota bene.1:*** Tutti i file Json sono inclusi nei contenitori *.Jar*, per accedere al file Json si devono dunque scompattare i file *.Jar* oppure clonare questa repository accedendo così al file.  
***Nota bene.2:*** Per integrare i file Json dentro i contenitori .Jar si è dovuto ricorrere agli pathname relativi. Se si desidera provare il gioco direttamente da un IDE, è necessario cambiare gli indirizzi relativi al pathname per non incorrere in errori.  
***Note bene.3:*** Durante la scelta del proprio schema, se uno dei giocatori ha optato per una connessione di tipo RMI, allora tutti i giocatori successivi a questa connessione non potranno scegliere la propria mappa fino a quando il giocatore in questione non ha effettuato la sua scelta. (e.g. 1° Giocatore: Socket, 2° Giocatore: RMI, 3° Giocatore RMI, 4° Giocatore:  Socket. Il giocatore 1 visualizzerà le mappe e potrà sceglierne una così come il giocatore 2  nel medesimo tempo, mentre i giocatori 3 e 4 non visualizzeranno nulla. Appena il giocatore 2 sceglie la sua mappa, il server riceve sia la scelta del primo giocatore sia quella del secondo, e permette di visualizzare le mappe al giocatore 3. Appena il giocatore 3 sceglie la mappa allora viene inviata la richiesta di scelta della mappa anche al giocatore 4)
  
*Pathname relativi per i file .Jar:*  
  
 - Classe Server, metodo Main, riga 266:   
`PathDeserializer path = new PathDeserializer("/PathnameServer.json");`  
- Classe LauncherClient, metodo Main, riga 37:   
 `PathDeserializer path = new PathDeserializer("/PathnameClient.json");`  
  *Pathname relativi per l'IDE:*  
  
 - Classe Server, metodo Main, riga 266:   
`PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/server/json_server/Pathname.json");`  
- Classe LauncherClient, metodo Main, riga 37:   
`PathDeserializer path = new PathDeserializer("src/main/java/it/polimi/se2018/client/json_client/Pathname.json");`  
  
## ***Istruzioni per lanciare l'applicazione***  
  
  
 - ***Lato Server:*** Per lanciare l'applicazione lato server, accedere tramite terminale alla cartella *Deliveries* ed eseguire l'istruzione  `java -jar Server.jar`.   
  
 - ***Lato Client:*** Per lanciare l'applicazione lato client, accedere tramite terminale alla cartella *Deliveries* ed eseguire l'istruzione  `java -jar Client.jar`.   
  
***Nota Bene:***  
Avendo scelto di implementare la CLI avvalendosi di simboli unicode, per visualizzarli correttamente è necessario aver installato sul proprio pc un terminale Unix.   
Per chi possiede un sistema operativo Windows è possibile configurarne uno seguendo [questa](https://github.com/michele-bertoni/W10JavaCLI) semplice guida   
   
## ***UML***  
  
 - ***Gestione delle carte:*** Abbiamo scelto di adottare il pattern *Strategy* per una corretta gestione degli effetti delle carte.  
   
[![Card_Strategy.png](https://s19.postimg.cc/sxr43tctf/Card_Strategy.png)](https://postimg.cc/image/691x48vfj/)  
  
- ***Gestione del caricamento da file Json:*** Abbiamo deciso di avvalerci della libreria *Gson* e del pattern *Observer-Observable* per garantire la corretta lettura dei file in formato Json.  
  
[![Deserializer.png](https://s19.postimg.cc/e1skw6boj/Deserializer.png)](https://postimg.cc/image/4u0cfh4m7/)  
  
- ***Gestione delle connessioni di rete tra server e client:*** Per implementare correttamente entrambe le tipologie di connessioni abbiamo definito una interfaccia per la connessione che viene implementata dalle classi relative alla tipologia di rete (Es. interfaccia: ConnectionClient viene implementata da ConnectionClientSocket e ConnectionClientRMI).  
  
 [  
 ![UML_Network.png](https://s19.postimg.cc/ripjf5wbn/UML_Network.png)](https://postimg.cc/image/9fwgny0gv/)  
   
- ***Gestione del protocollo socket:*** Sono state ideate quattro tipologie di messaggi socket possibili: MessageMV (Medal->View), MessageCV (Controller->View), MessageVC (View->Controller) ed infine MessageSystem( stringhe inviate da Controller->View); tutte le precedenti classi implementano il pattern visitor illustrato nel prossimo punto. Da notare come alcuni oggetti implementano più tipologie di messaggio perchè vengono sfruttati per essere spediti avanti e indietro tra client e server.  
  
[![Uml_Message_Socket.png](https://s19.postimg.cc/8dma5fk8j/Uml_Message_Socket.png)](https://postimg.cc/image/fgu5l1pnz/)  
  
- ***Gestione di invio e ricezione messaggi tramite socket:*** Per una corretta ricezione dei messaggi si è deciso di implementare un listener sul client in modo che riesca a distinguere tra le tre categorie di messaggi. Una volta riconosciuta la tipologia, tramite il pattern visitor illustrato sotto viene chiamato il metodo desiderato.  
[![Visitor_Pattern.png](https://s19.postimg.cc/56rqltunn/Visitor_Pattern.png)](https://postimg.cc/image/cmr07micv/)