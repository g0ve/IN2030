# DEL 1: Scanner - patrikg&sindrsg

## Hvordan kjøre scanner:
Først bygg programmet ved bruk av ant. Må være i "in2030-oblig-2019"
```
$ ant
```
Kjør test av scanner med test fil:
```
$ java -jar asp.jar [FILENAME] -testscanner
```
Resultat kan man finne i logfil = [FILENAME].log

## Smarte ting vi gjorde
For at vi skulle slippe å skrive veldig mange if setninger valgte vi å endre på TokenKind.java. Det vi gjorde var å ta i bruk den statiske metoden values(). 
Dette gjorde at vi kunne lage en ny liste som innehold alle verdiene i TokenKind, som vi da kunne få bruke i Scanner. 
I Scanner lagde vi så en metode som tok in en tokenkind, og sjekket om den fantes i listen vår og alle tokenkinds.
Om den fantes returnerte den tokekinden, som vi da brukte til å lage riktig token.
 
Videre følgte vi kompendiet for håndtering av indentering, og brukte fullførte ukesoppgaver vi hadde gjort som hjelp for å lage/finne tokens.
