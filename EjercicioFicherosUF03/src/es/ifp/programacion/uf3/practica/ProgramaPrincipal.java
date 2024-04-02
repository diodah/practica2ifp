package es.ifp.programacion.uf3.practica;

import java.io.*;
import java.util.Scanner;

/**
 * Ejericio UF03: Sistema de gestion de archivos
 * Una empresa dedicada al desarrollo de software necesita hacer un pequeño programa en Java
 * que se va a ejecutar en un sistema Linux. El programa no tendrá interfaz gráfico, sino que será a
 * través de la consola Linux donde se introducirá la información necesaria para la correcta ejecución
 * del mismo.
 * El programa gestionará información en un fichero de texto donde se van a introducir datos
 * personales. Estos datos son: nombre, apellidos y correo electrónico. Los datos para cada usuario
 * se escribirán en una línea separados entre ellos por el carácter ‘;’
 *
 * @author nacho
 */
	

/*
 * Cambios para demostrar uso de GitHub
 * 
 * */
public class ProgramaPrincipal {
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String opcionMenu;
        do {
            mostrarMenu();
            opcionMenu = sc.nextLine();
            switch (opcionMenu){

                case "A", "a": {
                    creaFichero();
                    break;
                }
                case "B", "b": {
                    escribeFichero();
                    break;
                }
                case "C", "c": {
                    leeFichero();
                    break;
                }
                case "D", "d": {
                    eliminaFichero();
                    break;
                }
            }
        }
        while (!opcionMenu.equals("E"));
            System.out.println("El programa ha finalizado");
    }
    /**
     * Función que nos permite crear ficheros en la ruta que el usuario nos indique, siempre pidiéndole
     * que los cree dentro de la carpeta "usuarios"
     */
    public static void creaFichero() {
        /*Declaramos las variables que usaremos en la función*/
        Scanner sc = new Scanner(System.in);
        String ruta;
        File f;
        File dir = new File("usuarios");
        FileWriter fw = null;
        BufferedWriter bw = null;
        FileReader fr = null;
        BufferedReader br = null;
        boolean result = false;

            if (!dir.exists()) {
                dir.mkdir();
                System.out.println("Se creará la carpeta " + dir);
            }
            /* Con este try-catch, creamos la posibilidad de que el usuario vea el ultimo fichero creado antes de elegir opcion */
            try {
                f = new File("usuarios" + File.separator+ "config.txt");
                result = f.canRead();
                if (result){
                    fr = new FileReader(f);
                    br = new BufferedReader(fr);
                    System.out.println(br.readLine());
                    br.close();
                    fr.close();
                }
            }
            /* De nuevo capturaremos la excepcion que crea el uso del FileReader y BufferedReader */
            catch (IOException e){
                e.printStackTrace();
            }
            /*
             * Pedimos al usuario que introduzca la ruta y la pasamos a través de consola a la variable f
             * que creara el archivo dentro del directorio creado usuarios
             */
            System.out.println("Introduzca la ruta del fichero que desea crear: ");
            ruta = sc.nextLine();
            f = new File("usuarios" + File.separator + ruta);
            /*Aqui damos comienzo a la creacion del fichero indicado en las anteriores lineas
            * Si el fichero no existe, se procede a su creacion, ademas de crear un fichero de comprobacin
            * Que nos sirve para hacer saber al usuario cual ha sido el ultimo fichero creado y su ruta
            * */
            try {
                if (!f.exists()) {
                    result = f.createNewFile();
                    if (result) {
                        System.out.println("Fichero creado correctamente en la ruta " + File.separator + f.getPath());
                        f = new File("usuarios" + File.separator + "config.txt");
                        try {
                            fw = new FileWriter(f);
                            bw = new BufferedWriter(fw);
                            bw.write("Fichero creado por ultima vez: usuarios" + File.separator + ruta);
                            bw.close();
                            fw.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        /*Si el fichero no puede ser creado se imprimira este mensaje*/
                    } else
                        System.out.println("El fichero no ha podido ser creado.");
                /*Si el fichero ya existe imprimira este mensaje*/
                } else
                    System.out.println("El fichero ya existe.");
            /*El catch nos sirve para capturar la traza de error en caso de darse este
             * Y asi poder identificar dentro del codigo que error esta dando, pero indicando al usuario
             * Que el fichero no ha podido ser creado
             * */
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("El fichero no ha podido ser creado.");
            }
    }
    /**
     * Procedimiento que escribe en un fichero indicado
     */
    public static void escribeFichero(){
        /* Declaramos las variables a usar en la funcion */
        Scanner sc = new Scanner(System.in);
        String ruta;
        String nombre;
        String apellidos;
        String correo;
        String opcion;
        File f = null;
        FileWriter fw = null;
        BufferedWriter bw = null;
        boolean result = false;

        /* Le pedimos al usuario el archivo sobre el cual quiere escribir */
        System.out.println("Introduzca la ruta sobre la que quiere escribir: ");
        ruta = sc.nextLine();

        /* Comenzamos indicando la ruta sobre la cual estaran los archivos, dentro de la carpeta usuarios
        * Indicamos que si existe el fichero escriba en una linea los tres datos introducidos separados por ';' */
        try {
            f = new File("usuarios" + File.separator + ruta);
            if (f.exists()){
                /* Recogemos los datos que queremos que introduzca en el archivo y los almacenamos en las tres variables */
                System.out.println("Introduce el nombre del usuario: ");
                nombre = sc.nextLine();
                System.out.println("Introduce los apellidos del usuario: ");
                apellidos = sc.nextLine();
                System.out.println("Introduce el correo del usuario: ");
                correo = sc.nextLine();

                fw = new FileWriter(f);
                bw = new BufferedWriter(fw);
                bw.write(nombre + "; " + apellidos + "; " + correo);
                bw.close();
                fw.close();

                /* Confirmamos al usuario que se ha escrito correctamente */
                System.out.println("Se ha escrito sobre el fichero correctamente.");
            }
            /* En caso de no existir el fichero sobre el que quiere escribir, le preguntamos al usuario si desea crearlo
            * En caso de que quiera crearlo, se procede a crearse y a su misma vez se escriben los datos en el mismo */
            else{
                System.out.println("El fichero no existe, ¿desea crearlo? (S/N)");
                opcion = sc.nextLine();
                if (opcion.equals("S")){
                    result = f.createNewFile();
                    if (result) {
                        System.out.println("Introduce el nombre del usuario: ");
                        nombre = sc.nextLine();
                        System.out.println("Introduce los apellidos del usuario: ");
                        apellidos = sc.nextLine();
                        System.out.println("Introduce el correo del usuario: ");
                        correo = sc.nextLine();
                        fw = new FileWriter(f);
                        bw = new BufferedWriter(fw);
                        bw.write(nombre + "; " + apellidos + "; " + correo);
                        bw.close();
                        fw.close();

                        /*
                         * Incluimos la misma funcionalidad que en la opcion de crearFichero para que indique
                         * la ruta del ultimo fichero creado en nuestro archivo config.txt
                         */
                        try {
                            f = new File("usuarios" + File.separator + "config.txt");
                            fw = new FileWriter(f);
                            bw = new BufferedWriter(fw);
                            bw.write("Fichero creado por ultima vez: usuarios" + File.separator + ruta);
                            bw.close();
                            fw.close();
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                        /* Indicamos de la misma forma la ruta donde se ha creado este fichero y que se ha escrito bien */
                        System.out.println("Fichero creado correctamente en la ruta " + File.separator + ruta);
                        System.out.println("Se ha escrito sobre el fichero correctamente.");
                    }
                }
                else
                    System.out.println("No se escribira en el fichero");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    /**
     * Procedimiento que lee un fichero indicado
     */
    public static void leeFichero(){
        /* Declaramos las variables a usar */
        Scanner sc = new Scanner(System.in);
        String ruta;
        String linea;
        String totalLineas = "";
        FileReader fr = null;
        BufferedReader br = null;
        File f = null;

        /* Pedimos al usuario la ruta del fichero a leer */
        System.out.println("Introduzca la ruta del fichero que desea leer: ");
        ruta = sc.nextLine();

        try {
            /* Indicamos al programa que el fichero esta dentro de la carpeta usuarios */
            f = new File("usuarios" + File.separator + ruta);
            /* Si el fichero existe, se procede a leer e imprimir el contenido de este en consola */
            if (f.exists()) {
                fr = new FileReader(f);
                br = new BufferedReader(fr);
                linea = br.readLine();
                while (linea != null) {
                    totalLineas += "\n" + linea;
                    linea = br.readLine();
                }
                br.close();
                fr.close();
                if (!totalLineas.isEmpty()){
                    System.out.println("El contenido del fichero es el siguiente: ");
                    System.out.println(totalLineas);
                }
                else
                    System.out.println("El fichero esta vacio.");
            }
            /* Si no existiese, no se leeria y se imprimiria este mensaje */
            else
                System.out.println("El fichero no puede leerse ya que no existe");
        }
        /* Recogemos las excepciones posibles mediante la variable e y el PrintStackTrace */
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Procedimiento para eliminar un fichero
     */
    public static void eliminaFichero(){
        /* Declaracion de variables a usar */
        Scanner sc = new Scanner(System.in);
        String ruta;
        String tmp;
        File f;

        /* Pedimos al usuario que introduzca la ruta del fichero que quiere borrar */
        System.out.println("Introduzca la ruta del fichero que desee borrar: ");
        ruta = sc.nextLine();
        f = new File("usuarios" + File.separator + ruta);

        /* Mediante este if, le pedimos al usuario su confirmacion para eliminar el archivo */
        if (f.exists()){
            System.out.println("El fichero va a eliminarse, ¿confirma su eliminacion? S/N");
            tmp = sc.nextLine();
            /* Si nos indica que si, el fichero se elimnara y se notificara al usuario */
            if (tmp.equals("S")){
                f.delete();
                System.out.println("El fichero ha sido borrado con exito.");
            }
            /* Si el usuario no confirma la eliminacion, el fichero no se eliminara */
            else
                System.out.println("El fichero no se ha eliminado.");
        }
        else
            System.out.println("El fichero no existe");
    }
    /**
     * Procedimiento que muestra el menú en la función principal
     */
    public static void mostrarMenu(){
        /* Estas lineas imprimen el menu que el usuario vera en consola cada vez que elija una opcion */
        System.out.println("\n");
        System.out.println("==================================");
        System.out.println("        Gestión de ficheros       ");
        System.out.println("==================================");
        System.out.println("¿Que desea hacer?");
        System.out.println("A) Crear un fichero de texto");
        System.out.println("B) Introducir información en el fichero");
        System.out.println("C) Leer el fichero de texto");
        System.out.println("D) Eliminar el fichero de texto");
        System.out.println("E) Salir del programa");
        System.out.println("Eliga una opción: A, B, C, D o E");
    }
}
