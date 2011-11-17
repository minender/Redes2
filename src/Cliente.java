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
public class Cliente 
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        int puerto=0;
        String nodo="";
        String directorio="";
        
        if(args.length >= 4 && args[0].equals("-p") && args[2].equals("-n"))
        {
            puerto=(new Integer(args[1])).intValue();
            nodo=args[3];
            if(args.length != 4)
            {
                if(args.length == 6 && args[4].equals("-d"))
                {
                    directorio=args[5];
                }
                else
                {
                    String ext="Debe escribir: cliente -p <puerto> -n ";
                    ext+="<nodo> [-d <directorio de descargas>]";
                    System.out.println(ext);
                    System.exit(1);
                }
            }
        }
        else
        {
            String ext="Debe escribir: cliente -p <puerto> -n ";
            ext+="<nodo> [-d <directorio de descargas>]";
            System.out.println(ext);
            System.exit(1);
        }
        
        BufferedReader bf=null;
        try
        {
            Socket cliente=new Socket(nodo,puerto);
            BufferedReader in=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
            PrintWriter out=new PrintWriter(cliente.getOutputStream(),true);
                    
            bf=new BufferedReader(new InputStreamReader(System.in));
            while(true)
            {   
                String a=bf.readLine();
                if (a.length()>0 && a.charAt(0)=='C')
                {
                    if(a.length() >= 6 && a.charAt(3)=='a')
                    {
                        out.println("a 0 "+a.substring(5));
                    }
                    else if(a.length() >= 6 && a.charAt(3)=='t')
                    {
                        out.println("t "+a.substring(5));
                    }
                    else
                    {
                        if(a.length()==1)
                            System.out.println(Buscar.listaTodo());
                        else
                            System.out.println("Error de Sintaxis");
                    }
                }
                else if(a.length()==2 && a.charAt(0) =='D')
                { 
                    Buscar.descargar((new Integer(args[1])).intValue());
                }
                else if(a.length()==1 && a.charAt(0)=='A')
                {
                    System.out.println(Buscar.alcanzables());
                }
                else if(a.length()==1 && a.charAt(0)=='Q')
                {
                    cliente.close();
                    System.exit(1);
                }
                else
                {
                    System.out.println("Error de sintaxis");
                }
                String s="";
                while(in.ready())
                    s+=in.readLine()+"\n";
               // String s=in.;
                System.out.println(s);
            }
        }
        catch(IOException e)
        {
            System.out.println("Error de lectura en consola:"+e);
        }
    }
}
