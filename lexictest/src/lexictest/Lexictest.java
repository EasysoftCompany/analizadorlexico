package lexictest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
    private static String allLanguages = "";
    public static void main(String[] args) {
        
        //Mostramos un menaje de bienvenida y una descripcion de lo que hace el programa
        JOptionPane.showMessageDialog(null, "Realizare un analisis lexico por linea a un archivo que elija bajo la expresion regular que se interpreta como:\n\n\t Cualquier cosa, segudo de un espacio en blanco, seguido de un guion, seguido de otro espacio en blanco, seguido de lo que sea \n\n\n\t Para comenzar seleccione un archivo! ");
        
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
                            printArrayUntilIndex(linea.toCharArray(), linea.indexOf('-')); 
                            /*
                            Recibe 2 parametros, el primero es un arreglo de caracteres y el segundo es la posicion del arreglo hasta el que debemos imprimir
                            Para esto hacemos uso de linea.toCharArray() que transforma la cadena de texto en un arreglo de caracteres
                            y de linea.lastIndexOf() que nos regresa un entero con la posicion del caracter buscado
                            
                            Entonces imprimiremos el arreglo hasta el indice del caracter
                            
                            P Ej.
                            
                            UNIDAD PROFESIONAL - INTERDISCIPLINARIA...
                            
                            Solo imprimiria: 
                                UNIDAD PROFESIONAL
                            
                            */
                        }
                    }

                }
                JOptionPane.showMessageDialog(null, "Lenguajes de programacion detectados: " + contador);
                System.out.println("CUENTA: " + contador);
                outputStreamLog(); //llamamos a la funcion outputStreamLog() que nos creara un archivo de texto en el escritorio con los lenguajes que detecto
                JOptionPane.showMessageDialog(null, "He creado un archivo en el Escritorio donde podras visualizar los lenguajes que he detectado (Recomiendo usar algun editor de textos como Atom, SublimeText, Etc)");
                
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

    public static void printArrayUntilIndex(char[] arreglo, int index) {

       StringBuilder sb = new StringBuilder();
        for (int i = 0; i < index - 1; i++) { //desde el inicio del arreglo de la linea y hasta la posicion donde encontro ' - ' menos uno
            System.out.print(arreglo[i]); //Imprime el arreglo
            sb.append(arreglo[i]);
        };
        addToOutstream(sb.toString());
        System.out.println("\n"); //despues imprime un salto de linea
    }
    public static void addToOutstream(String lenguaje){ //añade el lenguaje de programacion a la variable estatica allLanguajes
        allLanguages += lenguaje; 
        allLanguages += "\n\n";
    }
    public static void outputStreamLog(){
        FileWriter fichero = null;
        try {
             fichero = new FileWriter(javax.swing.filechooser.FileSystemView.getFileSystemView().getHomeDirectory()+"\\lang.txt",true);
             PrintWriter pw = new PrintWriter(fichero);  
             pw.println(allLanguages);
        } catch (IOException e) {
            LOG.log(Level.SEVERE, e.toString());
        }finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (IOException e2) {
              LOG.log(Level.SEVERE, e2.toString());
           }
    }}
}
