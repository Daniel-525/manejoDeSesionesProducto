package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DetalleCarro {
    private List<ItemCarro> items;

    public DetalleCarro() {
        this.items = new ArrayList<>();
    }

    public void addItemCarro(ItemCarro itemCarro) {
        if(items.contains(itemCarro)) {
            Optional<ItemCarro> optionalItemCarro = items.stream()
                    .filter(i -> i.equals(itemCarro))
                    .findAny();
            if(optionalItemCarro.isPresent()) {
                ItemCarro i = optionalItemCarro.get();
                i.setCantidad(i.getCantidad() + 1);
            }
        } else {
            this.items.add(itemCarro);
        }
    }

    public List<ItemCarro> getItems() {
        return items;
    }

    public double getTotal() {
        return items.stream().mapToDouble(ItemCarro::getSubTotal).sum();
    }

    // Metodo para calcular el total de IVA
    public double getTotalIva() {
        return items.stream().mapToDouble(ItemCarro::getIva).sum();
    }

    // Metodo para calcular el total con el IVA
    public double getTotalConIva() {
        return getTotal() + getTotalIva();
    }
}
