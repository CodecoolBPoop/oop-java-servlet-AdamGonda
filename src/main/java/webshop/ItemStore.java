package webshop;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemStore {
    private List<Item> items = new ArrayList<>();

    public void add(Item item){
        items.add(item);
    }

    public void addAll(List<Item> items){
        this.items.addAll(items);
    }

    public void remove(int id){
        items = items.stream()
                .filter(item -> item.getId() != id)
                .collect(Collectors.toList());
    }

    public List<Item> getItems() {
        return items;
    }

    public void printContent(){
        System.out.println(items);
    }
}
