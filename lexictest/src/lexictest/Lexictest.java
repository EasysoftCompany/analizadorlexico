/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexictest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author g_ric
 */
public class Lexictest {

    /**
     * @param args the command line arguments
     */
    private static final Logger LOG = Logger.getLogger(Lexictest.class.getName()); //Creamos un Logger que nos permita manejar las excepciones y poder visualizarlas en la consola

    public static void main(String[] args) {
        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;
        int contador = 0;
        final JFileChooser fc = new JFileChooser(); //Para facilitar la tarea de escojer el archivo a evaluar creamos un JFileChooser
        int returnVal = fc.showOpenDialog(fc);// Obtenemos la seleccion del usuario (Abrir/cancelar)

        if (returnVal == JFileChooser.APPROVE_OPTION) { //Si selecciono abrir algun archivo entonces...
            archivo = fc.getSelectedFile(); //a archivo se le pasa el valor (Path) que obtuvo el JavaFileChooser
            try {

                fr = new FileReader(archivo);//Leemos el archivo
                br = new BufferedReader(fr);//Creamos un BufferReader con el archivo que leimos

                String linea;
                
                boolean empiezanLenguajes = false; 
                //Existe una linea con caracteres "* * * *" a partir de ahi todos son lenguajes de programacion
                //esta variable nos servira para detectar si ya hemos pasado esa linea

                regex regex = new regex(); //Instancia de la clase regex

                while ((linea = br.readLine()) != null) {
                    
                    if (regex.empiezanLenguajes(linea)) { //Si coincide con la expresion regular de Empiezan leguajes la variable de control se hace true
                        empiezanLenguajes = true;
                    }

                    if (regex.isLanguage(linea)) { //Si coincide con el criterio para ver si es lenguaje de programacion o no

                        if (empiezanLenguajes) { //Si ya hemos pasado la linea * * * * y cumple con el criterio asumimos que es un lenguaje de programacion
                            contador++; // Aumentamos el contador en 1
                            int index = linea.indexOf('-'); //Buscamos en la linea la posicion del caracter '-'
                            char[] leng = linea.toCharArray(); //Convertimos la linea en un arreglo de caracteres
                            for (int i = 0; i < index-1; i++) { //desde el inicio del arreglo de la linea y hasta la posicion donde encontro ' - ' menos uno
                                System.out.print(leng[i]); //Imprime el arreglo
                            }
                            System.out.println("\n"); //despues imprime un salto de linea
                        }
                    }

                }
                JOptionPane.showMessageDialog(null, "Lenguajes de programacion detectados: "+contador);
                System.out.println("CUENTA: " + contador);
            } catch (IOException e) {
                LOG.log(Level.WARNING, e.toString());
            } finally {
                try {
                    if (null != fr) {
                        fr.close();
                    }
                } catch (IOException e2) {
                    LOG.log(Level.WARNING, e2.toString());
                }
            }
        } else {
            LOG.log(Level.WARNING, "No se selecciono archivo, Hasta luego");
        }
    }
}
