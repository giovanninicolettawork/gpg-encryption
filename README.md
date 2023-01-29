# File Date

Questo software consente di criptare tramite  ***GNU Privacy Guard*** un file utilizzando una chiave pubblica a propria disposizione. 
Una volta terminato il processo, il file potrà essere decriptato solamente da chi è in possesso della chiave privata associata alla chiave pubblica utilizzata in fase di criptazione.
Una volta avviato il software verrà richiesto di inserire in console:
- il path del file da criptare
- il path della chiave pubblica

# Specifiche

Il codice è stato scritto in *Java (11)* utilizzando *Maven*. Nel file *pom.xml* è definita la modalità di build. La chiave pubblica verrà aggiunta al keyring prima dell'avvio della criptazione e verrà rimossa alla fine del processo.

## Installazione e utilizzo
È possibile generare il file jar tramite il comando *Maven Install*. Verrà generato, nella cartella target, il file *gpgEncryption.jar* che potrà essere eseguito da terminale solo in ambiente Linux (con l'unico requisito che sia installato Java).
Per eseguire il jar lanciare sul terminale il comando

    java -jar gpgEncryption.jar
Il file criptato avrà lo stesso nome di quello originale (che non viene cancellato durante il processo) con l'aggiunta dell'estensione "*.gpg*"
### Comandi utili
Per la generazione di una coppia di chiavi pubblica-privata:

    gpg --full-generate-key
Per esportare la chiave pubblica in un file:

    gpg --export  -a YOUR_KEY_NAME_HERE > public.key
Per visualizzare la lista delle chiavi private nel keyring:

    gpg --list-secret-keys
Per esportare la chiave privata in un file:

    gpg --export-secret-keys YOUR_KEY_ID_HERE > private.key
Per eliminare dal keyring una chiave privata:

    gpg --delete-secret-keys YOUR_KEY_ID_HERE
Per importare la chiave privata nel keyring

    gpg --import private.key
Per decriptare un file  tramite chiave privata (utile per testare il funzionamento del software)

    gpg --output filename --decrypt filename.gpg

## Stato del progetto
Si tratta di un software implementato al solo scopo di acquisire confidenza con *Java*
