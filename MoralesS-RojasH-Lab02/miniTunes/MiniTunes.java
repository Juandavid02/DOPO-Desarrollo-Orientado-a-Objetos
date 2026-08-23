import java.util.TreeMap;

/** MiniTunes.java
 * 
 * @author ESCUELA 2026-02
 */
    
public class MiniTunes{
    
    private TreeMap<String,Playlist> playlists;
    private boolean ok; 
    
    public MiniTunes(){
        playlists = new TreeMap<>();
        ok = true; 
    }

    private String normalizar(String name){
        if (name == null || name.isEmpty()){
            return null;
        }
        else{
            String nameNormalized = name.trim().replaceAll("\\s+", " ");
            nameNormalized = nameNormalized.toUpperCase();
            return nameNormalized;
        }
    }
        
    //Define a new playlist name
    public void define(String name){
        String nameNormalized = normalizar(name);
        if (nameNormalized != null){
            if (!playlists.containsKey(nameNormalized)){
                playlists.put(nameNormalized, new Playlist());
                ok = true;
            }
            else{
                ok = false;
            }
        }
        else{
            ok = false; 
        }
    }
     
    //Assign a playlist to an existing playlist name
    //a := playlist
    public void assign(String a, String [] [] playlist){
        String nameNormalized = normalizar(a);
        if (nameNormalized != null && playlist != null){
            if (playlists.containsKey(nameNormalized)){
                Playlist playlistAssign = new Playlist(playlist);
                playlists.put(nameNormalized, playlistAssign);
                ok = true;
            }
            else{
                ok = false;
            }
        }    
        else{
            ok =false;
        }
    }

    //Return a playlist's size
    public int size(String a){
        String nameNormalized = normalizar(a);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            ok = true; 
            return playlists.get(nameNormalized).size();
        }
        else{
            ok = false;
            return -1;   
        }
    }
    
    //Returns the playlist names in alphabetical order. comma-separated
    public String toString(){
        StringBuilder result = new StringBuilder();
        for (String name : playlists.keySet()){
            result.append(name+", ");
        }
        if (result.length() > 0){
            result.setLength(result.length() - 2);
        }
        ok = true;
        return result.toString();
    }
    
    // Returns the string representation of a playlist.
    public String toString(String name){
        String nameNormalized = normalizar(name);
        if (nameNormalized != null && playlists.containsKey(nameNormalized)){
            ok = true;
            return playlists.get(nameNormalized).toString();
        }
        else{
            ok = false;
            return null;       
        }
    }    
    
    //Assigns the value of a unary operation to a playlist name
    // a = b op parameters
    //The operator characters are: 'a' (add) , 'd' (delete),'s'(select)
    //For add and delete, the values correspond to the song data. For select, the parameters define the search pattern.
    public void assignUnary(String a, String b, char op, String [] values){
        String nameNormalizedA = normalizar(a);
        String nameNormalizedB = normalizar(b);
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalizedA != null && nameNormalizedB != null && playlists.containsKey(nameNormalizedB)){
            if (opNormalize == 'a'){
                if (playlists.containsKey(nameNormalizedA)){
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).add(values));
                    ok = true;
                }
                else{
                    ok = false;
                }
            }
            else if (opNormalize == 'd'){
                if (playlists.containsKey(nameNormalizedA)){ 
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).delete(values));
                    ok = true;  
                }
                else{
                    ok = false;
                }
            }
            else if (opNormalize == 's'){
                if (playlists.containsKey(nameNormalizedA)){
                    playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).select(values));
                    ok = true;
                }
                else{
                    ok = false;
                }
            }
            else{
                ok = false;
            }
        }
        else{
            ok = false;
        }
    }
      
    
    //Assigns the value of a binary operation to a playlist name
    // a = b op c
    //The operator characters are:  'u' union, 'i' intersection, 'd' difference
    //Songs preserve their original order in the resulting playlist.
    public void assignBinary(String a, String b, char op, String c){
        String nameNormalizedA = normalizar(a);
        String nameNormalizedB = normalizar(b);
        String nameNormalizedC = normalizar(c);
        char opNormalize = Character.toLowerCase(op);
        if (nameNormalizedA != null && nameNormalizedB != null && nameNormalizedC != null && playlists.containsKey(nameNormalizedB) && playlists.containsKey(nameNormalizedC)){
            if (opNormalize == 'u'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).union(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else if (opNormalize == 'i'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).intersection(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else if (opNormalize == 'd'){
                playlists.put(nameNormalizedA, playlists.get(nameNormalizedB).difference(playlists.get(nameNormalizedC)));
                ok = true;
            }
            else {
                ok = false;
            }
        }
        else{
            ok = false;
        }
    }
   
    //If the last operation was successfully completed
    public boolean ok(){
        return ok;
    }
}
    



