# Backgammon-Java-Processing
## Backgammon

Backgammon ist ein klassisches Brettspiel für zwei Personen. Ziel ist es, die 15 eigenen Steine vom Brett zu entfernen. Sie müssen Ihre Steine vom Feld des Gegners auf Ihr
eigenes Feld ziehen. Wenn alle Steine in Ihrem eigenen Feld sind, können Sie Ihre Steine mit Hilfe der Zahlen, die Sie durch Würfeln erhalten, entfernen.

Dies ist eine einfache Implementierung des klassischen Brettspiels Backgammon. Das Spiel wurde mit Java erstellt, und die Benutzeroberfläche wurde mit der in Java integrierten GUIBibliothek erstellt. Das Projekt ist in drei Hauptpakete unterteilt: Model, View und Controller. 

Das Modellpaket enthält die Hauptlogik des Spiels, einschließlich der Spielmechanik, der Regeln und des Spielzustands. Die Backgammon-Klasse in diesem Paket ist die
Hauptklasse, die die gesamte Spiellogik handhabt.

Das View-Paket enthält die Klassen, die für die Erstellung der Benutzeroberfläche und die
Anzeige des Spielzustands für den Benutzer verantwortlich sind. Die IView-Schnittstelle in
diesem Paket definiert die Methoden, die die View-Klassen implementieren müssen.

Das Controller-Paket enthält die Klassen, die die Benutzereingaben verarbeiten und
zwischen dem Modell und dem View kommunizieren. Die Klasse BackgammonController in
diesem Paket ist die Hauptklasse, die die Benutzereingaben verarbeitet und den Spielstatus
aktualisiert.
## Voraussetzung

- Java Version: Java 17
- Processing Version 4
- ControlP5

## How to run
Um das Spiel zu starten, führen Sie einfach die Main-Methode der View-Klasse im ViewPaket aus. Dadurch wird das Spielfenster gestartet, in dem Sie das Spiel spielen können.

## Wie man spielt
Das Spiel beginnt damit, dass beide Spieler jeweils 15 Steine haben. Das Spiel wird mit zwei
Würfeln gespielt. Würfle mit den Würfeln, um die Anzahl der Schritte zu ermitteln, die jeder
Stein ziehen kann. Entsprechend der gewürfelten Schrittzahl kann der Stein von einem Teil
des Spielfelds zu einem anderen bewegt werden. Ziel des Spiels ist es, alle Steine in das
Heimatgebiet des Spielers zu bringen. Der erste Spieler, der alle Steine in das eigenes
Heimatgebiet bewegt hat, gewinnt.

Wenn das Spiel gestartet wird, gibt es zwei Schaltflächen zu sehen:
Roll Button: um das Spiel zu spielen, müssen Sie auf diesen Button klicken, um die Würfel
zu bekommen. Dieser Button zeigt Ihnen zwei weitere Buttons, die die Würfel sind.

![image](https://user-images.githubusercontent.com/99660768/222146676-c04656b4-4155-4d79-b479-4b4322b35e06.png)

(Roll Button)

Give Up-Button: Wenn Sie auf diesen Button klicken, endet das Spiel und die andere Partei
hat das Spiel gewonnen.

![image](https://user-images.githubusercontent.com/99660768/222147456-b4248b82-69e8-4130-8981-ea470c1a1b9e.png)

(Give UP Button)

Nach dem Drücken des Roll-Buttons werden 2 zufällig generierte Zahlen angezeigt, die Sie
drücken können. Wenn Sie sie drücken, werden dir die gültigen Felder angezeigt, die Sie
benutzen können, um die Spielsteine auf dem Spielfeld zu bewegen. Bevor Sie die zweite
Zahl verwenden, müssen Sie den Chip bewegen und können dann die zweite Zahl
verwenden.

![image](https://user-images.githubusercontent.com/99660768/222148820-0169ebe2-beda-4c71-9647-82a2758222da.png)


(Dice Buttons)

![image](https://user-images.githubusercontent.com/99660768/222148998-0e48bc88-6eb1-431a-ab01-a1c5cf2023ad.png)

(Gültigen Felder)

Wenn alle 15 auf dem Spielfeld sind, können Sie erneut würfeln und ihre Chips
herausnehmen, je nachdem, welche Zahl Sie gewürfelt haben. Wenn alle 15 Chips nicht
mehr auf dem Spielfeld liegen, haben Sie gewonnen. Wenn ein Chip aus einer Partition
alleine ist, kann er angegriffen werden. Wenn Ihr Chip von der anderen Partition angegriffen
wird, wird er nach einem erneuten Wurf auf seinen ursprünglichen Platz zurückgesetzt.

## Test model mit Jshell

- To create a new instance of model:
```java
var game = new Backgammon()
```
- To get the board data:
```java 
game.getBoard();
```
- To save the dice and roll:
``` java 
int[] dice = game.rollDice();
``` 
- Test the moveStones() method
```java
game.moveStones(1,11, 4);
```
- Test the gameOver() method
```j ava
boolean gameOver = game.gameOver(game.getStonesCollected()); 
```

![image](https://user-images.githubusercontent.com/99660768/222153489-cb556fe6-d523-441f-af22-1a762dad74cf.png)

## Hinweis
Das Spiel ist noch nicht fertig und kann nicht gespielt werden.

Einige Funktionen sind noch nicht komplett fertig.

Das Spiel ist im Moment nur für zwei Spieler geeignet.

Um das Spiel ausführen zu können, muss Java auf deinem Rechner installiert sein. Stellen
Sie sicher, dass Sie die neueste Version von Java installiert haben, um die Kompatibilität mit
dem Spiel zu gewährleisten.

Außerdem kann das Spiel zusätzliche Bibliotheken oder Frameworks erfordern, die nicht in
diesem Projekt enthalten sind. Stellen Sie sicher, dass diese installiert sind, bevor Sie das
Spiel starten.

Bitte beachten Sie, dass sich das Spiel noch in der Entwicklung befindet und noch Bugs
oder Fehler enthalten kann. Der Entwickler arbeitet aktiv daran, diese Probleme zu beheben
und dem Spiel neue Funktionen hinzuzufügen.

Ich freue mich über Ihr Interesse an diesem Projekt und wünsche Ihnen viel Spaß mit dem
Spiel!
