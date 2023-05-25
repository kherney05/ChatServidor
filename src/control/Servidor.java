package control;
import control.ControlClientes;
import java.io.*;
import java.net.*;
public class Servidor{
    private ServerSocket socketServidor;
    private final int PUERTO=9002;
    public Servidor(){
        iniciar();
        aceptarCliente();
    }
    public static void main(String [] cuec){
        new Servidor();
    }
    private void iniciar(){
        try {
        //Crea el socket para la comunicacion
            socketServidor=new ServerSocket(PUERTO);
            System.out.println("EL SERVIDOR FUE MONTADO EN EL PUERTO "+PUERTO);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void aceptarCliente(){
    //Metodo que acepta conexiones de los clientes
        Socket socketCliente=null;
    //al colocar un ciclo infinito permite aceptar un numero
    //indefinido de clientes
        while(true){
            try{
                //Acepta una conexion entrante desde un cliente


                        socketCliente=socketServidor.accept();
                //Iniciamos la clase de control de clientes
                new ControlClientes(socketCliente);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}