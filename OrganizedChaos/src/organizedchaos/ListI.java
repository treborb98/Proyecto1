/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package organizedchaos;

/**
 * Lista de Inventario
 * @author Robert
 */
public class ListI{ //Lista para inventarios
    protected class Nodo{
        Inventory info;
        Nodo sig;
        
        public Nodo(Inventory info){
            this.info = info;
            this.sig = null;
        }
    }
    Nodo headI = null;
    Nodo tailI = null;
    int size = 0;
    
    public boolean empty(){
        return headI == null;
    }
    
    /**
     * Muestra la informacion de los nodos en la lista.
     * @return outString String con el inventario
     */
    
    public String[] showNodesWithoutRepetition(){ //Lista para tener todo acceso al inventario disponible
        Nodo track = headI;
        String outString = "";
        if (track == null) {
            System.out.println("Lista vacia");
            return null;
        }
        while (track != null){
            outString += track.info.name+"\n";
            track = track.sig;
        }
        
        String[] inventario = outString.split("\n"); 
        
        return inventario;
    }
    
    public String showNodes(){ 
        Nodo track = headI;
        String outString = "";
        if (track == null) {
            System.out.println("Lista vacia");
            return null;
        }
        while (track != null){
            outString += track.info.name + ": " + track.info.quantity + "\n";
            track = track.sig;
        }
        return outString;
    }
    
    /**
     * agrega al final de la lista
     * @param info 
     */
    public void addLast(Inventory info){ 
        
        Nodo nuevo = new Nodo(info);
        if (headI == null){
            headI = tailI = nuevo;
            size ++;
        } else {
            tailI.sig = nuevo;
            tailI = nuevo;
            size ++;
        }
    }
    
    /**
     * busca el Item indicado
     * @param nombre
     * @return el Item que busca o null
     */
    public Inventory buscarItem(String nombre){
        Nodo track = headI;
        while (track != null){
            if (nombre.equalsIgnoreCase(track.info.name)){
                return track.info;
            }
            track = track.sig;
        }
        return null;
    }
    
    public void eliminarItem(Inventory item){
        Nodo track = headI.sig;
        Nodo pasado = headI;
        if (headI.info == item){
            headI = headI.sig;
            size --;
            return;
        }
        while (track != null){
            if(track.info == item){
                pasado.sig = track.sig;
                return;
            }
            pasado = track;
            track = track.sig;
            size --;
        }
    }
    
    public void descontar(String item, int cantidad){
        Nodo track = headI;
        while (track != null){
            if (item.equalsIgnoreCase(track.info.name)){
                if(track.info.quantity > cantidad){
                    track.info.quantity -= cantidad;
                    return;
                } else if (track.info.quantity == cantidad){
                    track.info.quantity -= cantidad;
                    this.eliminarItem(track.info);
                    return;
                } else {
                    // solo se tienen x cantidad del item, en este caso pedimos a otro almacen
                }
            }
            track = track.sig;
        }
    }
    
    public void descontar(Inventory producto){
        String item = producto.name;
        int cantidad = producto.quantity;
        Nodo track = headI;
        while (track != null){
            if (item.equalsIgnoreCase(track.info.name)){
                if(track.info.quantity > cantidad){
                    track.info.quantity -= cantidad;
                    return;
                } else if (track.info.quantity == cantidad){
                    track.info.quantity -= cantidad;
                    this.eliminarItem(track.info);
                    return;
                } else {
                    // solo se tienen x cantidad del item, en este caso pedimos a otro almacen
                }
            }
            track = track.sig;
        }
    }
    
    public void agregarItem(Inventory item){
        Nodo track = headI;
        while (track != null){
            if (item.name.equalsIgnoreCase(track.info.name)){
                track.info.quantity += item.quantity;
                return;
            }
            track = track.sig;
        }
        this.addLast(item);
    }
    
    public void agregarItem(String nombre, int cantidad){
        Inventory newItem = new Inventory(nombre, cantidad);
        Nodo track = headI;
        while (track != null){
            if (nombre.equalsIgnoreCase(track.info.name)){
                track.info.quantity += cantidad;
                return;
            }
            track = track.sig;
        }
        this.addLast(newItem);
    }
    
    public void juntar(ListI segunda){
        Nodo track = segunda.headI;
        
        while (track != null){
            this.agregarItem(track.info);
            track = track.sig;
        }
    }
    
    public String guardarArchivo(){
        String archivo = "";
        Nodo track = headI;
        while (track != null){
            if (track.sig != null){
                archivo += track.info.name + "," + track.info.quantity + "\n";
                
            } else {
                archivo += track.info.name + "," + track.info.quantity;
            }
            track = track.sig;
        }
        archivo += ";\n";
        return archivo;
    }
    
    public Nodo getNode (int index){
        Nodo track = headI;
        int counter = 0;
        while (index != counter){
            track = track.sig;
            counter ++;
        }
        return track;
    }
        
    public void group(){
        Nodo guide;
        Nodo tracker;
        for (int i = 0; i < size; i++) {
            guide = this.getNode(i);
            for (int j = i+1; j < size; j ++) {
                tracker = this.getNode(j);
                if (guide.info.name.equalsIgnoreCase(tracker.info.name)){
                    guide.info.quantity += tracker.info.quantity;
                    this.eliminarItem(tracker.info);
                }
            }
        }
    }    
    
    public Boolean checkquantity(String name, int number){
        if (this.buscarItem(name).quantity < number){
            return false;
        }
        else{
            return true;
        } 
    }
    
}
