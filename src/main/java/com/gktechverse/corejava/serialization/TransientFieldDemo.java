package com.gktechverse.corejava.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Use case 2: Demonstrates transient field behavior clearly.
 */
public class TransientFieldDemo {
    public static void main(String[] args) {
        String fileName = "user-transient.ser";
        User before = new User(102, "Diya", "my-login-password", "diya@gktechverse.com");

        serialize(fileName, before);
        User after = deserialize(fileName);

        System.out.println("Original password value : " + before.getPassword());
        System.out.println("Deserialized password   : " + after.getPassword());
        System.out.println("Because password is transient, JVM skips it from object stream.");
    }

    private static void serialize(String fileName, User user) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static User deserialize(String fileName) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            return (User) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
