package com.cold.tutorial.soa;

import java.io.*;

/**
 * @author hui.liao
 *         2016/1/18 15:48
 */
public class SerializableDemo {

    public static void main(String[] args) {
        Person person = new Person();
        person.setNum(1);
        person.setName("CGLibProxy");

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(person);

            ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
            ObjectInputStream objectInputStream = new ObjectInputStream(input);
            Person p = (Person) objectInputStream.readObject();
            System.out.println(p);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
