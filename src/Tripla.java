/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author federico
 */
public class Tripla 
{
  public String[] campos;
  
  public Tripla(){

  campos = new String[3];

  campos[0] = "";
  campos[1] = "";
  campos[2] = "";
  
  }

  @Override
  public String toString(){

  String exit = campos[0]+" "+campos[1]+" "+campos[2]+"\n";
  return exit;

  }
}
