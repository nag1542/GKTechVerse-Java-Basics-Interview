package com.gktechverse.corejava.serialization;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.Serial;
import java.io.Serializable;

/**
 * Use case 3: shows InvalidClassException when serialVersionUID mismatches.
 */
public class SerialVersionUidMismatchDemo {
    public static void main(String[] args) {
        String fileName = "user-version-mismatch.ser";

        serializeOldVersion(fileName);

        try (ObjectInputStream input = new MappingObjectInputStream(new FileInputStream(fileName))) {
            Version2User user = (Version2User) input.readObject();
            System.out.println("Unexpected success: " + user);
        } catch (InvalidClassException e) {
            System.out.println("Expected exception occurred: " + e.getClass().getSimpleName());
            System.out.println("Reason: " + e.getMessage());
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void serializeOldVersion(String fileName) {
        Version1User oldUser = new Version1User(201, "Karan");
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
            output.writeObject(oldUser);
            System.out.println("Serialized Version1User with serialVersionUID=1L");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static final class MappingObjectInputStream extends ObjectInputStream {
        private MappingObjectInputStream(FileInputStream inputStream) throws IOException {
            super(inputStream);
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if (desc.getName().equals(Version1User.class.getName())) {
                return Version2User.class;
            }
            return super.resolveClass(desc);
        }
    }

    private static final class Version1User implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private final int id;
        private final String name;

        private Version1User(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    private static final class Version2User implements Serializable {
        @Serial
        private static final long serialVersionUID = 2L;
        private final int id;
        private final String name;

        private Version2User(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "Version2User{" + "id=" + id + ", name='" + name + '\'' + '}';
        }
    }
}
