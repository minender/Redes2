/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;

/**
 *
 * @author federico
 */
public class Nodo {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int puerto=0;
        String conocidos="";
        String biblioteca="";
        String id;
        
        if(args.length==8 && args[0].equals("-p") && args[2].equals("-c") &&
           args[4].equals("-b") && args[6].equals("-i"))
        {
            //verificar si el string es numero y si es positivo
            puerto=(new Integer(args[1])).intValue();
            conocidos=args[3];
            biblioteca=args[5];
            id=args[7];
        }
        else
        {
            String ext="Debe escribir: nodo -p <puerto> -c <conocidos> ";
            ext+="-b <biblioteca> -i <ID del nodo>";
            System.out.println(ext);
            System.exit(1);
        }
        
        ServerSocket server;
        Socket clientSocket;
        BufferedReader in=null;
        PrintWriter out=null;
        String clientRequest;
        
        try
        {
            server=new ServerSocket(puerto);
            clientSocket=server.accept();
            in=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out=new PrintWriter(clientSocket.getOutputStream(),true);
        }
        catch(IOException e)
        {
            System.out.println("Excepcion E/S en el constructor del servidor: " + e);
        }
        
        boolean quit=false;
        String resp="";
        while(!quit)
        {
            try
            {
                clientRequest="";
                
                    clientRequest=in.readLine();
                
                if(clientRequest.startsWith("a "))
                {
                    System.out.println(clientRequest);
                    Par p=Buscar.obtenerVisitados(clientRequest);  
                    resp=Buscar.listaPorAutor(biblioteca,conocidos,p.y,p.x);
                    if(resp.equals("La busqueda no dio resultado"))
                    {
                        out.println(clientRequest);
                    }
                    System.out.println(resp);
                }
                else if(clientRequest.startsWith("t "))
                {
                    resp=Buscar.listaPorTitulo(biblioteca,conocidos,clientRequest.substring(2));
                    System.out.println(resp);
                }
                else
                {
                    System.out.println("Error");
                }
            }
            catch(IOException e)
            {
                System.out.println("Excepcion E/S en server.in.readLine() " + e);
                System.exit(1);
            }
        }
    }
}
