//Each song is described by its title, artist, genre, duration, and rating.
//The title and artist are mandatory. The genre, duration, and rating may be unknown.
//The combination (title, artist) must be unique. Two songs cannot have the same title and artist.
//The duration (minutes) must be between 1 and 9.
//The rating must be between * and *****.

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
public class Playlist {
    
    private List<String[]> songs;

        public Playlist(){
        this.songs = new ArrayList<>();
    }
    
    public Playlist(String[][] songs) {
        this.songs = new ArrayList<>();
        for (String[] song : songs) {
            String[] normalizedSong = normalizar(song);
            if (normalizedSong != null && !check(normalizedSong)) {
                this.songs.add(normalizedSong);
            }
        }
    }

    private Playlist(List<String[]> songsYaValidas) { //IA generativa lo hacemos para que no haya reprocesamiento
        this.songs = songsYaValidas;
    }

    private boolean check(String[] normalizedSong) {
        for (String[] song : this.songs) {
            if (song[0].equals(normalizedSong[0]) && song[1].equals(normalizedSong[1])) {
                return true;
            }
        }
        return false;
    }
    
    private String[] normalizar(String [] song){
        String titleSong = song[0];
        String nameArtist = song[1];
    
        String genre = null;
        String duraction = null;
        String rating = null;
    
        if (song.length > 2){
            genre = song[2];
        }
    
        if (song.length > 3){
            duraction = song[3];
        }
    
        if (song.length > 4){
            rating = song[4];
        }
        if (titleSong != null && !titleSong.isEmpty() && nameArtist != null && !nameArtist.isEmpty()){
            titleSong = titleSong.trim().replaceAll("\\s+", " "); //IA generativa
            titleSong = titleSong.toUpperCase();
            nameArtist = nameArtist.trim().replaceAll("\\s+", " ");
            nameArtist = nameArtist.toUpperCase();
        }
        else{
            return null;
        }
        
        if (genre == null || genre.isEmpty()){
            genre = null;                 
        }
        else {
            genre = genre.trim().replaceAll("\\s+", " ");
            genre = genre.toUpperCase();
        }
        
        if (duraction == null || duraction.isEmpty()){
            duraction = null;
        }
        else {
            duraction = duraction.replace(" ", "");
            if (duraction.length() == 1 &&
                duraction.charAt(0) >= '1' &&
                duraction.charAt(0) <= '9') {}
            else {
                return null;
            }
        }
        
        if (rating == null || rating.isEmpty()){
            rating = null;                 
        }
        else {
            rating = rating.replace(" ", "");
            if (rating.length()<1 || rating.length() > 5){
               return null;
            }
            else{
                for (int j = 0; j < rating.length(); j++) {
                    if (rating.charAt(j) != '*') {
                        return null;
                    }
                }
            }   
        }
        return new String[]{titleSong, nameArtist, genre, duraction, rating};
    }
    
    public Playlist add(String [] song){
        String[] normalizedSong = normalizar(song);
        if (normalizedSong == null || check(normalizedSong)) {
            return this;
        }
        List<String[]> nueva = new ArrayList<>(this.songs);
        nueva.add(normalizedSong);
        return new Playlist(nueva);   
    }
    
    public Playlist delete(String [] song){
        String[] normalizedSong = normalizar(song);
        if (normalizedSong == null) {
            return this;
        }
        int index = -1;
         for (int i = 0; i < this.songs.size(); i++){
            if (this.songs.get(i)[0].equals(normalizedSong[0]) && this.songs.get(i)[1].equals(normalizedSong[1])){
                index = i;
                break;    
            }
        }
        if (index == -1) {
            return this;
        }
        // Creo una LISTA NUEVA (copia) a partir de this.songs, así modificar "nueva" 
        // (agregar o quitar canciones) no afecta a this.songs, manteniendo la playlist original intacta.
        List<String[]> nueva = new ArrayList<>(this.songs); // Diferente campo en memoria es decir no afecta a this.songs
        nueva.remove(index);
        return new Playlist(nueva);
    }
    
    public Playlist select(String [] values){
        String titleSong = null;
        String nameArtist = null;
        String genre = null;
        String duraction = null;
        String rating = null;
        if (values.length > 0){
            titleSong = values[0];
        }
        if (values.length > 1){
            nameArtist = values[1];
        }
        if (values.length > 2){
            genre = values[2];
        }
        if (values.length > 3){
            duraction = values[3];
        }
        if (values.length > 4){
            rating = values[4];
        }
        
        if (titleSong != null && !titleSong.isEmpty()){
            titleSong = titleSong.trim().replaceAll("\\s+", " ");
            titleSong = titleSong.toUpperCase();
        }
        else{
            titleSong = null;
        }
        if (nameArtist != null && !nameArtist.isEmpty()){
            nameArtist = nameArtist.trim().replaceAll("\\s+", " ");
            nameArtist = nameArtist.toUpperCase();
        }
        else{
            nameArtist = null;
        }
        if (genre != null && !genre.isEmpty()){
            genre = genre.trim().replaceAll("\\s+", " ");
            genre = genre.toUpperCase();                
        }
        else {
            genre = null; 
        }
        
        if (duraction == null || duraction.isEmpty()){
            duraction = null;
        }
        else {
            duraction = duraction.replace(" ", "");
            if (duraction.length() == 1 &&
                duraction.charAt(0) >= '1' &&
                duraction.charAt(0) <= '9') {}
            else {
                duraction = null;
            }
        }
            
        if (rating == null || rating.isEmpty()){
            rating = null;                 
        }
        else {
            rating = rating.replace(" ", "");
            if (rating.length()<1 || rating.length() > 5){
               rating = null;
            }
            else{
                for (int j = 0; j < rating.length(); j++) {
                    if (rating.charAt(j) != '*') {
                        rating = null;
                    }
                }
            }   
        }
        List<String[]> selectedList = new ArrayList<>();
        for (String [] song: this.songs){
            if ((titleSong == null ||  song[0].equals(titleSong)) &&
            (nameArtist == null || song[1].equals(nameArtist)) &&
            (genre == null ||  (song[2] != null && song[2].equals(genre))) &&
            (duraction == null || (song[3] != null && song[3].equals(duraction))) &&
            (rating == null || (song[4] != null && song[4].equals(rating)))) {
                selectedList.add(song);
            }
        }  
        return new Playlist(selectedList);
    }
    
    public int size(){
        return this.songs.size();
    }    
    
   
    // Songs are in uppercase with unnecessary spaces removed.
    // Columns are aligned and separated by three spaces.
    //TITLE    ARTIST          GENRE   DURATION   RATING
    //ONE      U2              ROCK           4   *****
    //NUMB     LINKIN PARK     ROCK           3
    //ALIVE    PEARL JAM       ROCK           5   ****
    //CREEP    RADIOHEAD       ROCK               *****
    //DREAMS   FLEETWOOD MAC                  4   ****
    public String toString() {
        int maxCharsTitle = 4;
        int maxCharsArtist = 6;
        int maxCharsGenre = 5;
        int maxCharsDuration = 8;   // ancho de alineacion de "DURATION" (sin el margen)
        int maxCharsRating = 9;
        for (String[] song: songs){
            int currentTitle = song[0].length();
            int currentArtist = song[1].length();
            int currentGenre = song[2] == null ? 0 : song[2].length();
            if (currentTitle > maxCharsTitle){
                maxCharsTitle = currentTitle;
            }
            if (currentArtist > maxCharsArtist){
                maxCharsArtist = currentArtist;
            }
            if (currentGenre > maxCharsGenre){
                maxCharsGenre = currentGenre;
            }
        }
        maxCharsTitle += 3 ;
        maxCharsArtist += 3;
        maxCharsGenre += 3;
        StringBuilder result = new StringBuilder(); //Recomendacion IA generativa ya que String es inmutable
        result.append(String.format("%-" + maxCharsTitle + "s", "TITLE"));
        result.append(String.format("%-" + maxCharsArtist + "s", "ARTIST"));
        result.append(String.format("%-" + maxCharsGenre + "s", "GENRE"));
        result.append(String.format("%-" + maxCharsDuration + "s   ", "DURATION"));
        result.append(String.format("%-" + maxCharsRating + "s\n", "RATING"));
        
        for (String[] song : songs) {
            //IA generativa recomendo operador ternario: condición ? valorSiVerdadero : valorSiFalso
            //Se usa para reemplazar un valor null por un String vacío ("") y evitar errores al trabajar con ese dato.
            String genre = song[2] == null ? "" : song[2]; 
            String duration = song[3] == null ? "" : song[3];
            String rating = song[4] == null ? "" : song[4];
            result.append(String.format("%-" + maxCharsTitle + "s", song[0]));
            result.append(String.format("%-" + maxCharsArtist + "s", song[1]));
            result.append(String.format("%-" + maxCharsGenre + "s", genre));
            result.append(String.format("%" + maxCharsDuration + "s   ", duration));
            result.append(String.format("%-" + maxCharsRating + "s\n", rating));
        }
        return result.toString();
    }
    
    public boolean equals(Playlist pl){
        if (this.songs.size() != pl.songs.size()){
            return false;
        }
        else {
            for (int i = 0; i<pl.songs.size(); i++){
                //IA generativa: Se usa Arrays.equals() ya que se necesita comparar por contenido
                //Si usaba ArrayList.equals() el equal de String[] no me funcionaba 
                if (!Arrays.equals(this.songs.get(i), pl.songs.get(i))){
                    return false;
                }
            }
        }
        return true;
    }
    
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        // Consultado en la API de Java: instanceof sirve para comprobar si un objeto es una instancia de una clase determinada
        if (o == null || !(o instanceof Playlist)){
            return false;
        }
        return equals((Playlist)o);
    }
    
    public Playlist union(Playlist b){
        Playlist result = new Playlist();
        for (String[] song : this.songs){
            result = result.add(song);
        }
    
        for (String[] songb : b.songs){
            boolean flag = false;
            for (String[] songr : result.songs){
                if (songr[0].equals(songb[0]) && songr[1].equals(songb[1])){
                    flag = true;
                    break;
                }
            }
            if (!flag){
                result = result.add(songb);
            }
        }
        return result;
    }

    public Playlist intersection(Playlist b){
        Playlist result = new Playlist();
        for (String[] songa : this.songs){
            for (String[] songb : b.songs){
                if (songa[0].equals(songb[0]) && songa[1].equals(songb[1])){
                    result = result.add(songa);
                    break;
                }
            }
        }
        return result;
    }
    
    public Playlist difference(Playlist b){
        Playlist result = new Playlist();
        for (String[] song : this.songs){
            result = result.add(song);
        }
        for (String[] songb : b.songs){
            result = result.delete(songb);
        }
        return result;
    }
    
    public Playlist sort(char value){
        List<String[]> result = new ArrayList<>(this.songs);
        // IA generativa: se utiliza nullsLast para manejar valores nulos y reverseOrder para ordenar de mayor a menor.
        if (value == 't'){
            result.sort(Comparator.comparing(fila -> fila[0]));
        }
        else if (value == 'a'){
            result.sort(Comparator.comparing(fila -> fila[1]));
        }
        else if (value == 'g'){
            result.sort(Comparator.comparing(fila -> fila[2], Comparator.nullsLast(String::compareTo)));
        }
        else if (value == 'd'){
        result.sort(Comparator.comparing(fila -> fila[3], Comparator.nullsLast(Comparator.reverseOrder())));
        }
        else if (value == 'r'){result.sort(Comparator.comparing(fila -> fila[4], Comparator.nullsLast(Comparator.reverseOrder())));
        }
        return new Playlist(result);
    }
    
    public int frequency(String value, char op){
        int count = 0;
        if (value != null && !value.isEmpty()){        
            if (op == 't'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();
                for (String[] song : this.songs){
                    if (song[0].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'a'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();                
                 for (String[] song : this.songs){
                    if (song[1].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'g'){
                value = value.trim().replaceAll("\\s+", " ");
                value = value.toUpperCase();
                 for (String[] song : this.songs){
                    if (song[2] != null && song[2].equals(value)){
                        count++;
                    }
                }
            }
            else if (op == 'd'){
                value = value.replace(" ", "");
                if (value.length() == 1 && value.charAt(0) >= '1' && value.charAt(0) <= '9') {
                    for (String[] song : this.songs){
                        if (song[3] != null && song[3].equals(value)){
                            count++;
                        }
                    }
                }
                else{
                    count = -1;
                }
            }
            else if (op == 'r'){
                value = value.replace(" ", "");
                if (!(value.length()<1 || value.length() > 5)){
                    for (int j = 0; j < value.length(); j++) {
                        if (value.charAt(j) != '*') {
                            value = null;
                        }
                    }
                }
                else {
                    count = -1;
                }
                if (value != null){
                    for (String[] song : this.songs){
                        if (song[4] != null && song[4].equals(value)){
                            count++;
                        }
                    }
                }
            }
            else {
                count = -1;
            }
        }
        else{
            count= -1;
        }
        return count;
    }
    
    public int totalDuration(){
        int total = 0;
        for (String[] song : this.songs){
            if (song[3] != null){
                total += Integer.parseInt(song[3]); // Convertir  String a un int
            }
        }
        return total;
    }
}