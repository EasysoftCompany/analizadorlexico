/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexictest;

/**
 *
 * @author g_ric
 */
import java.util.regex.Pattern;
public class regex {
   
public boolean isEmptyLine(String linea){
    return Pattern.matches("", linea);
}    
public boolean isLanguage(String linea){
    return Pattern.matches(".*[\\s]+[-]+[\\s].*", linea);
}

public boolean empiezanLenguajes(String linea){
    return Pattern.matches(".*[*]+[\\s]+[*].*", linea);
}
        
       
    
}
