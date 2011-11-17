/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.util.*;
import java.net.*;
import nanoxml.*;

public class Buscar 
{
    public static LinkedList parsear(String biblioteca)
    {
        XMLElement xml = new XMLElement();
        FileReader reader=null;
        try
        {
            reader = new FileReader(biblioteca);
            xml.parseFromReader(reader);
        }
        catch(Exception e)
        {
            
        }
          //  System.out.println(xml);

        XMLElement trackLists = new XMLElement();
        XMLElement tracks = new XMLElement();
        XMLElement atrib = new XMLElement();
        String varAux ="";
        LinkedList listaAtributos = new LinkedList();
        //listaAtributos.add(elemento)   agrega al final de la lista)
        //listaAtributos.get(ENTERO)
        //listaAtributos.remove(object a)
	Enumeration enu = xml.enumerateChildren();
        trackLists = (XMLElement) enu.nextElement();
        //System.out.print(trackLists.getName() + " \n");
	//System.out.print(trackLists.getContent() + " \n");

	enu = trackLists.enumerateChildren();
	while (enu.hasMoreElements()) {
	tracks = (XMLElement) enu.nextElement();
	Tripla T = new Tripla();
	//System.out.print(" \n" + " \n" + tracks.getName() + " \n");
			
	Enumeration enu2 = tracks.enumerateChildren();
	while (enu2.hasMoreElements()) {
		atrib = (XMLElement) enu2.nextElement();
			
		if (atrib.getName().equals("location")){
		//System.out.print(atrib.getName() + " \n");
                    varAux = atrib.getContent();
                    T.campos[0] = atrib.getContent();
                    //System.out.print(varAux + " \n");
		}
  
		if (atrib.getName().equals("title")){
		//System.out.print(atrib.getName() + " \n");
                    varAux = atrib.getContent();
                    T.campos[1] = atrib.getContent();
                    //System.out.print(varAux + " \n");
		}

		if (atrib.getName().equals("creator")){
		//System.out.print(atrib.getName() + " \n");
                    varAux = atrib.getContent();
                    T.campos[2] = atrib.getContent();
                    //System.out.print(varAux + " \n");
		}



		//System.out.print(atrib.getContent() + " \n");
		}
        listaAtributos.add(T);
		//System.out.print(tracks.getContent() + " \n");
	}
        return listaAtributos;
    }
    
    public static boolean fueVisitado(String linea,String visitados)
    {
        String[] s=visitados.split(" ");
        int k=(new Integer(s[0])).intValue();
        //formato "30 fe fe fe "
        int i=1;
        while(i<=k && !s[i].equals(linea))
        {
            i++;
        }
        if(i>k)
            return false;
        else
            return true;
    }
    
    public static String agregarVisitado(String visitados,String linea)
    {
        visitados=visitados+linea+" ";
        String[] s=visitados.split(" ");
        int i=(new Integer(s[0])).intValue()+1;
        visitados=i+" ";
        for(int j=1;j<s.length;j++)
        {
            visitados+=s[j]+" ";
        }
        
        return visitados;
    }
    
    public static Par obtenerVisitados(String res)
    {
        String[] s=res.split(" ");
        String visitados=s[1]+" ";
        int k=(new Integer(s[1])).intValue();
        int i=2;
        while(i<=k+1)
        {
            visitados=visitados+s[i]+" ";
            i++;
        }
        String s2="";
        for(int j=i;j<s.length;j++)
        {
            s2+=s[j];
        }
        Par p=new Par(visitados,s2);
        return p;
    }
    
    public static String listaPorAutor(String biblioteca, String autor)
    {
        LinkedList l=parsear(biblioteca);

        LinkedList listaDeArtista = new LinkedList();
        Iterator iterador2;
        Tripla Taux2 = new Tripla();
        String artistaCancion = "";
        iterador2 = l.iterator();
        while (iterador2.hasNext())
        {
            Taux2 = (Tripla)iterador2.next();
            artistaCancion = Taux2.campos[2];
  
            if (artistaCancion.equals(autor)){
                listaDeArtista.add(Taux2);
	   }
        }
      
        if(!listaDeArtista.isEmpty())
            return listaDeArtista.toString();
        else
            return "La busqueda no dio resultado";
    }
    
    public static String listaPorAutor(String biblioteca,String conocidos,
                                       String autor,String visitados)
    {
        String lista=Buscar.listaPorAutor(biblioteca,autor);
        if(lista.equals("La busqueda no dio resultado"))
        {
            File archivo=null;
            FileReader fr=null;
            BufferedReader br=null;
            
            try
            {
                archivo=new File(conocidos);
                fr=new FileReader (archivo);
                br=new BufferedReader (fr);
                String linea="";
                boolean parar=false;
                
                int i=0;
                while(!parar && (linea=br.readLine())!=null)
                {
                    System.out.print(linea+" ");
                    if(!Buscar.fueVisitado(linea,visitados))
                    {
                        System.out.println("se esta visitando");
                        //Socket cliente=new Socket(linea,8744);
                        Socket cliente=new Socket("localhost",(new Integer(linea)).intValue());
                        BufferedReader in=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                        PrintWriter out=new PrintWriter(cliente.getOutputStream(),true);
                   
                        visitados=Buscar.agregarVisitado(visitados, linea);
                        out.println("a "+visitados+autor);
                        //while(in.ready())
                            lista=in.readLine();//+"\n";
                        
                        Par p=Buscar.obtenerVisitados(lista);
                        visitados=p.x;
                    
                    
                        /*if(!lista.equals("La busqueda no dio resultado"))
                        {
                            parar=true;
                        }*/
                        //cerrar socket recuerdate
                        in.close();
                        out.close();
                        cliente.close();
                        i++;
                    }
                }
            }
            catch(Exception e)
            {
                        
            }
        }
        return lista;
    }
    
    public static String listaPorTitulo(String biblioteca,String titulo)
    {
         LinkedList l=parsear(biblioteca);

        LinkedList listaDeTitulo = new LinkedList();
        Iterator iterador2;
        Tripla Taux2 = new Tripla();
        String tituloCancion = "";
        iterador2 = l.iterator();
        while (iterador2.hasNext())
        {
            Taux2 = (Tripla)iterador2.next();
            tituloCancion = Taux2.campos[1];
  
            if (tituloCancion.equals(titulo)){
                listaDeTitulo.add(Taux2);
	   }
        }
        if(!listaDeTitulo.isEmpty())
            return listaDeTitulo.toString();
        else
            return "La busqueda no dio resultado";
    }
    
    public static String listaPorTitulo(String biblioteca,String conocidos,
                                        String titulo)
    {
        String lista=Buscar.listaPorTitulo(biblioteca,titulo);
        if(lista.equals("La busqueda no dio resultado"))
        {
            File archivo=null;
            FileReader fr=null;
            BufferedReader br=null;
            
            try
            {
                archivo=new File(conocidos);
                fr=new FileReader (archivo);
                br=new BufferedReader (fr);
                String linea="";
                boolean parar=false;
                
                while(!parar && (linea=br.readLine())!=null)
                {
                    //Socket cliente=new Socket(linea,8744);
                    Socket cliente=new Socket("localhost",(new Integer(linea)).intValue());
                    BufferedReader in=new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                    PrintWriter out=new PrintWriter(cliente.getOutputStream(),true);
                    
                    out.println("t "+titulo);
                 //   while(in.ready())
                 //       lista+=in.readLine()+"\n";
                    
                    
                    if(!lista.equals("La busqueda no dio resultado"))
                    {
                        parar=true;
                    }
                }
            }
            catch(Exception e)
            {
                        
            }
        }
        return lista;
    }
    
    public static String listaTodo()
    {
        return " ";
    }
    
    public static void descargar(int num)
    {
    
    }
    
    public static String alcanzables()
    {
        return " ";
    }
}
