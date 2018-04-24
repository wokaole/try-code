package com.cold.jdk;

import org.junit.Test;

import java.util.*;


/**
 * @author hui.liao
 *         2015/12/28 19:54
 */
public class CollectionTest {

    @Test
    public void testArrayList() {
        ArrayList<Object> list = new ArrayList<>();
        for (int i=0; i<12; i++) {
            list.add(i);
        }

        list.remove(3);
        list.remove(Integer.valueOf(4));
        Iterator<Object> iterator = list.iterator();
        iterator.hasNext();
        list.contains(Integer.valueOf(5));


        LinkedList<Object> linkedList = new LinkedList<>();
        for (int i=0; i<6; i++) {
            linkedList.add(i);
        }

        linkedList.remove(2);
        linkedList.remove(Integer.valueOf(3));
        ListIterator<Object> listIterator = linkedList.listIterator();
        listIterator.hasNext();
        linkedList.contains(Integer.valueOf(4));


        HashSet<Object> hashSet = new HashSet<>();

        hashSet.add(1);
        hashSet.add(1);
        hashSet.add(2);
        hashSet.remove(1);
        hashSet.contains(2);


    }

}
