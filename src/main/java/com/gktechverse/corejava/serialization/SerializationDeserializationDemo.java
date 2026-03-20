package com.gktechverse.corejava.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Use case 1: Basic serialization + deserialization.
 * Saves User object into user.ser and reads it back.
 */
public class SerializationDeserializationDemo {
    public static void main(String[] args) {
        String fileName = "user.ser";
        User user = new User(101, "Aarav", "secret-123", "aarav@gktechverse.com");

        System.out.println("Before serialization: " + user);

        serialize(fileName, user);
        User readUser = deserialize(fileName);

        System.out.println("After deserialization: " + readUser);
        System.out.println("Note: transient field password will be null after deserialization.");
    }

    private static void serialize(String fileName, User user) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(user);
            System.out.println("Serialized User into file: " + fileName);
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize user", e);
        }
    }

    private static User deserialize(String fileName) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            return (User) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to deserialize user", e);
        }
    }
}
