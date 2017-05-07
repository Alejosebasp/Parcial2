/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Parcial2;
import java.math.*;
import java.util.*;
import java.io.*;

/**
 * 
 * @author Alejosebasp
 */

public class Parcial2 {
  /**
   * Complete la clase Proceso segun la descripcion del problema y 
   * para que pueda ser usada con el MonticuloMinimo.
   *
   * Recuerde que Proceso debe implementar la interfaz Comparable.
   */
  static class Proceso implements Comparable<Proceso>{
    
        private int id;
        private int im;
        private int posicion;

        public Proceso(int id, int im, int posicion) {
            this.id = id;
            this.im = im;
            this.posicion = posicion;
        }
        
        public void setPosicion(int posicion){
            this.posicion = posicion;
        }
        
        public int getposicion(){
            return posicion;
        }
        
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIm() {
            return im;
        }

        public void setIm(int im) {
            this.im = im;
        }

        @Override
        public int compareTo(Proceso proceso) {
            if  (this.im != proceso.im) {
                return this.im - proceso.im;
            } else {
                return proceso.id - this.id;
            }
        }
  }

  static class MonticuloMinimo {
    List<Proceso> datos;
    ArrayList<Proceso> proces = new ArrayList<>();

    public MonticuloMinimo() {
      datos = new ArrayList<Proceso>();
    }

    public MonticuloMinimo(Proceso[] datos) {

      this.datos = new ArrayList<Proceso>(datos.length);
      
      for(int i = 0; i < datos.length; i++)
        this.datos.add(datos[i]);
      for (int i = padre(datos.length-1); i >= 0; i--)
        desplazarAbajo(i);
      
      for (Proceso p : datos) {
            proces.add(p);
        }
      proces = sort(proces);
    }

    public int padre(int u) {
      return (u-1)/2;
    }

    public int izquierdo(int p) {
      return 2*p+1;
    }

    public int derecho(int p) {
      return 2*p+2;
    }
    
    public void desplazarAbajo(int p) {

      int izq = izquierdo(p);
      int der = derecho(p);
      int max = p;

      if (izq < datos.size() && datos.get(izq).compareTo(datos.get(max)) > 0)
        max = izq;
      if (der < datos.size() && datos.get(der).compareTo(datos.get(max)) > 0)
        max = der;
      if (p != max) {
        Collections.swap(datos, max, p);
        datos.get(max).setPosicion(max);
        datos.get(p).setPosicion(p);
        desplazarAbajo(max);
      }
      if (datos.size() == 1) {
            datos.get(p).posicion = 0;
        }
    }
    
    private void desplazarArriba(int u) {
        if (u != 0) {
            int p = padre(u);
      
            if (p >= 0 && datos.get(u).compareTo(datos.get(p)) > 0) {
              Collections.swap(datos, u, p);
              datos.get(p).setPosicion(p);
              datos.get(u).setPosicion(u);
              desplazarArriba(p);
            }
        }      
    }
    
    public Proceso consultar() {
      
      if (datos.isEmpty())
        return null;
      return datos.get(0);
    }
  
    public void insertar(Proceso v) {
      
      datos.add(v);
      desplazarArriba(datos.size()-1);
    }
    
    public Proceso extraer() {
      
      if (datos.isEmpty())
        return null;
      Proceso v = datos.get(0);
      datos.set(0, datos.get(datos.size()-1));
      datos.remove(datos.size()-1);
      desplazarAbajo(0);
      return v;
    }
    
    public ArrayList<Proceso> sort(ArrayList<Proceso> procesos){
        Collections.sort(procesos, new Comparator<Proceso>() {
            @Override
            public int compare(Proceso o1, Proceso o2) {
                return o1.getId() - o2.getId();
            }
        });
        return procesos;
    }

    /**
     * La logica de la operacion priorizar debe
     * implementarla aqui.
     */
    public void priorizar(int idN) {
        
        int posicion = buscar(idN);
        
        if (posicion != -1){
            int miN = consultar().getIm();
            datos.get(posicion).setIm(miN +1);
            desplazarArriba(posicion);
        }
    }
    
    public int buscar(int id){
        
        int sup = proces.size()-1;
        int centro, infe = 0;
        
        while (infe <= sup) {            
            centro = (sup + infe)/2;
            if (proces.get(centro).getId() == id) {
                return proces.get(centro).getposicion();
            }
            else if (id < proces.get(centro).getId()) {
                sup = centro - 1;
            }else{
                infe = centro + 1;
            }
        }
        return -1;
    }
  }
  
  public static void main(String args[]) throws IOException {
      
      BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
      String entrada  = bf.readLine();
      
      String[] datos = entrada.split(" ");
      
      int numProces = Integer.parseInt(datos[0]);
      int numOperaciones = Integer.parseInt(datos[1]);
      
      Proceso[] procesos = new Proceso[numProces];
      
      for (int i = 0; i < numProces; i++) {
          
          entrada = bf.readLine();
          datos = entrada.split(" ");
          
          int id = Integer.parseInt(datos[0]);
          int im = Integer.parseInt(datos[1]);
          
          Proceso n = new Proceso(id, im, i);
          procesos[i] = n;
      }
      
      MonticuloMinimo monticuloProcesos = new MonticuloMinimo(procesos);
      
      for (int j = 0; j < numOperaciones; j++) {
          entrada = bf.readLine();
          datos = entrada.split(" ");
          
          String accion;
          int idN = -1;
          
          if (datos.length == 1) {
              accion = datos[0];
          }else{
              accion = datos[0];
              idN = Integer.parseInt(datos[1]);
          }
          
          switch (accion){
              
              case "ejecutar":
                    System.out.println(monticuloProcesos.extraer().getId());
                  break;
              case "priorizar":
                    monticuloProcesos.priorizar(idN);
                break;
          }
      }
  }
}