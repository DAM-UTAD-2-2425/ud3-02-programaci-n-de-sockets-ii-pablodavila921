package servidor;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * TODO: Complementa esta clase para que acepte conexiones TCP con clientes
 * para recibir un boleto, generar la respuesta y finalizar la sesion
 */
public class ServidorTCP {
	private String [] respuesta;
	private int [] combinacion;
	private int reintegro;
	private int complementario;
	private ServerSocket servidor;
	private Socket cliente;
	private DataInputStream entrada;
	private DataOutputStream salida;

	/**
	 * Constructor
	 */
	public ServidorTCP (int puerto) {
		this.respuesta = new String [9];
		this.respuesta[0] = "Boleto inv�lido - N�meros repetidos";
		this.respuesta[1] = "Boleto inv�lido - n�meros incorretos (1-49)";
		this.respuesta[2] = "6 aciertos";
		this.respuesta[3] = "5 aciertos + complementario";
		this.respuesta[4] = "5 aciertos";
		this.respuesta[5] = "4 aciertos";
		this.respuesta[6] = "3 aciertos";
		this.respuesta[7] = "Reintegro";
		this.respuesta[8] = "Sin premio";
		generarCombinacion();
		imprimirCombinacion();
	}
	

	/**
	 * @return Debe leer la combinacion de numeros que le envia el cliente
	 */
	public int[] leerCombinacion () {
        try {
            // Esperar conexión del cliente
            this.cliente = servidor.accept();
            System.out.println("Cliente conectado desde: " + cliente.getInetAddress());

            this.entrada = new DataInputStream(cliente.getInputStream());
            this.salida = new DataOutputStream(cliente.getOutputStream());

            // Leer la longitud de la combinación
            int longitud = entrada.readInt();
            int[] combinacionCliente = new int[longitud];

            // Leer los números de la combinación
            for (int i = 0; i < longitud; i++) {
                combinacionCliente[i] = entrada.readInt();
            }
            return combinacionCliente;
        } catch (IOException e) {
            System.err.println("Error al leer la combinación: " + e.getMessage());
            return null;
        }
    }
	
	/**
	 * @return Debe devolver una de las posibles respuestas configuradas
	 */
	
	// Comprobacion de los Caracteres del boleto introducido
	public String comprobarBoleto () {
		if (combinacion == null || combinacion.length !=6 )
		
		return respuesta[0];
		return null;
	}

	/**
	 * @param respuesta se debe enviar al ciente
	 */
	
	// metodo que devuelve respuesta al cliente cuando ha introducido, o error.
	public void enviarRespuesta (String respuesta) {
		try {
			if (salida != null) {
				salida.writeUTF(respuesta);
				System.out.println("Respuesta enviada al cliente:" + respuesta);
			}
		} catch (IOException e){
            System.err.println("Error al enviar la respuesta: " + e.getMessage());
        }
		
	}
	
	/**
	 * Cierra el servidor
	 */
	
	// Cierre del servidor y el cliente
	public void finSesion () {
		{
	        try {
	           
				if (entrada != null) entrada.close();
	            if (salida != null) salida.close();
	            if (cliente != null) cliente.close();
	            if (servidor != null) servidor.close();
	            System.out.println("Servidor cerrado correctamente.");
	        } catch (IOException e) {
	            System.err.println("Error al cerrar la conexión: " + e.getMessage());
	        }
	    }
		
	}
	
	/**
	 * Metodo que genera una combinacion. NO MODIFICAR
	 */
	private void generarCombinacion () {
		Set <Integer> numeros = new TreeSet <Integer>();
		Random aleatorio = new Random ();
		while (numeros.size()<6) {
			numeros.add(aleatorio.nextInt(49)+1);
		}
		int i = 0;
		this.combinacion = new int [6];
		for (Integer elto : numeros) {
			this.combinacion[i++]=elto;
		}
		this.reintegro = aleatorio.nextInt(49) + 1;
		this.complementario = aleatorio.nextInt(49) + 1;
	}
	
	/**
	 * Metodo que saca por consola del servidor la combinacion
	 */
	private void imprimirCombinacion () {
		System.out.print("Combinaci�n ganadora: ");
		for (Integer elto : this.combinacion) 
			System.out.print(elto + " ");
		System.out.println("");
		System.out.println("Complementario:       " + this.complementario);
		System.out.println("Reintegro:            " + this.reintegro);
	}

}

