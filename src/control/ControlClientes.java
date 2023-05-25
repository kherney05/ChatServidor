package control;
import java.io.*;
import java.net.*;
import java.util.*;
public class ControlClientes extends Thread {
    private String nombreCliente;
    private static List clientesActivos = new ArrayList();
    private Socket socket;
    private BufferedReader entrada;
    private PrintWriter salida;
    public ControlClientes(Socket socket) throws IOException{
        this.socket = socket;
        salida = new PrintWriter(socket.getOutputStream(), true);
        entrada = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));

        start();}
    public void run(){
        String textoUsuario = "";
        try {
            salida.println("Bienvendio al Java Socket Chat: ");
            nombreCliente = (entrada.readLine()).trim();
            System.out.println("El usuario "+nombreCliente +"Se ha conectado ");
            if ( (nombreCliente.equals("")) || (nombreCliente == null)

            ){

                nombreCliente = "Anonimo";}
            Iterator it = clientesActivos.iterator();
            while (it.hasNext()) {
                if (nombreCliente.equals(( (ControlClientes)
                        it.next()).nombreCliente)){
                    nombreCliente = nombreCliente + socket.getPort();
                    break;}
            }
            agregarAlChat(this);
            salida.println("Su nombre de usuario es: " +

                    nombreCliente);

            while ( (textoUsuario=entrada.readLine()) != null ){
                escribirATodos(nombreCliente+" dice > "+ textoUsuario);
            }
        }
        catch (IOException e2){}
    }
    private static synchronized void agregarAlChat(ControlClientes control){

        clientesActivos.add(control);
    }
    private synchronized void escribirATodos(String textoUsuario){
        Iterator it = clientesActivos.iterator();
        while (it.hasNext()){
            ControlClientes tmp = (ControlClientes) it.next();
            if ( !(tmp.equals(this)) )
                escribirCliente(tmp, textoUsuario);

        }
    }
    private synchronized void escribirCliente(ControlClientes control,

                                              String textoUsuario){
        (control.salida).println(textoUsuario);
    }
}