package cliente;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * TODO: Complementa esta clase para que genere la conexi�n TCP con el servidor
 * para enviar un boleto, recibir la respuesta y finalizar la sesion
 */
public class ClienteTCP {
	
	  private String ip;
	    private int puerto;
	    private Socket socket;
	    private DataOutputStream salida;
	    private DataInputStream entrada;

	/**
	 * Constructor
	 */
	    public ClienteTCP(String ip, int puerto) {
	        this.ip = ip;
	        this.puerto = puerto;
	        try {
	            // Establecer conexión con el servidor
	            this.socket = new Socket(ip, puerto);
	            this.salida = new DataOutputStream(socket.getOutputStream());
	            this.entrada = new DataInputStream(socket.getInputStream());
	            System.out.println("Conexión establecida con el servidor: " + ip + ":" + puerto);
	        } catch (IOException e) {
	            System.err.println("Error al conectar con el servidor: " + e.getMessage());
	        }
	    }

	/**
	 * @param combinacion que se desea enviar
	 * @return respuesta del servidor con la respuesta del boleto
	 */
	    public String comprobarBoleto(int[] combinacion) {
	        String respuesta = "";
	        try {
	            // Enviar el tamaño de la combinación
	            salida.writeInt(combinacion.length);
	            
	            // Enviar cada número de la combinación
	            for (int numero : combinacion) {
	                salida.writeInt(numero);
	            }
	            
	            // Esperar respuesta del servidor
	            respuesta = entrada.readUTF();
	        } catch (IOException e) {
	            System.err.println("Error al enviar o recibir datos: " + e.getMessage());
	        }
	        return respuesta;
	    }

	/**
	 * Sirve para finalizar la la conexi�n de Cliente y Servidor
	 */
	public void finSesion () { 
		try {
        // Cerrar flujos y socket
        if (salida != null) salida.close();
        if (entrada != null) entrada.close();
        if (socket != null) socket.close();
        System.out.println("Conexión cerrada correctamente.");
    } catch (IOException e) {
        System.err.println("Error al cerrar la conexión: " + e.getMessage());
    }

		
	}
	
}
