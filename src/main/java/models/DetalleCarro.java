package models;
/*
 * Autor: Cristian Arias
 * Fecha y version: 12/11/2025 | Version: 1.0
 * Descripcion: Clase que representa el detalle del carro de compras.
 * Contiene una lista de items y permite agregar productos, calcular subtotales y el total general.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleCarro {
    private List<ItemCarro> items;

    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    /*Implementamos un mpetodo para agrregar un producto al carro*/
    public void addItemCarro(ItemCarro itemCarro){
        if(items.contains(itemCarro)){
            Optional<ItemCarro> optionalItemCarro=items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if(optionalItemCarro.isPresent()){
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad()+1);
            }
        }else{
            this.items.add(itemCarro);
        }
    }

    public List<ItemCarro> getItem() {
        return items;
    }


    public double getTotal() {
        double iva = 0.16;
        double subtotal = items.stream().mapToDouble(ItemCarro::getSubtotal).sum();
        return subtotal + (subtotal * iva);
    }

}