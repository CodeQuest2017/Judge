// Copyright (c) 2017 Donovan Glover. All rights reserved.
import java.util.*;

public class JsonArrayList<E> extends ArrayList<E> {
    private static final long serialVersionUID = 7296729673955326372L;
    public String toString() {
        Iterator<E> iterator = iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        
        while(iterator.hasNext()) {
            E el = iterator.next();
            sb.append('\t');
            sb.append(el == this ? "(this Collection)" : el);
            sb.append(",\n");
        }
        
        return sb.append("];").toString();
    }
}
