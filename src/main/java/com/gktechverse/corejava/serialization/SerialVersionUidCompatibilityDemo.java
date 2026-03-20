package com.gktechverse.corejava.serialization;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Use case 4: Advantage of serialVersionUID.
 * Demonstrates adding a new field while still reading old saved stream.
 */
public class SerialVersionUidCompatibilityDemo {
    public static void main(String[] args) {
        String fileName = "user-old-stream.ser";

        createOldStreamWithPreviousClassVersion(fileName);
        User upgradedUser = readOldStreamUsingCurrentClass(fileName);

        System.out.println("Read old stream using new class definition successfully.");
        System.out.println("id    = " + upgradedUser.getId());
        System.out.println("name  = " + upgradedUser.getName());
        System.out.println("email = " + upgradedUser.getEmail() + " (new field defaulted because old stream had no value)");
        System.out.println("Why it works: same serialVersionUID (=1L) keeps versions compatible.");
    }

    private static void createOldStreamWithPreviousClassVersion(String fileName) {
        try {
            Path tempRoot = Files.createTempDirectory("legacy-user");
            Path packageDir = tempRoot.resolve("com/gktechverse/corejava/serialization");
            Files.createDirectories(packageDir);

            Path sourceFile = packageDir.resolve("User.java");
            Files.writeString(sourceFile, legacyUserSource(), StandardCharsets.UTF_8);

            JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
            if (compiler == null) {
                throw new IllegalStateException("JDK compiler not available in runtime.");
            }
            int result = compiler.run(null, null, null, "-d", tempRoot.toString(), sourceFile.toString());
            if (result != 0) {
                throw new IllegalStateException("Failed to compile old User version.");
            }

            URL[] urls = {tempRoot.toUri().toURL()};
            try (URLClassLoader classLoader = new URLClassLoader(urls, null);
                 ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(fileName))) {
                Class<?> oldUserClass = classLoader.loadClass("com.gktechverse.corejava.serialization.User");
                Constructor<?> constructor = oldUserClass.getDeclaredConstructor(int.class, String.class);
                Object oldUserObject = constructor.newInstance(401, "Rohan");
                output.writeObject(oldUserObject);
                System.out.println("Old stream created with previous User version (id + name only).");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create old stream", e);
        }
    }

    private static User readOldStreamUsingCurrentClass(String fileName) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(fileName))) {
            return (User) input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static String legacyUserSource() {
        return "package com.gktechverse.corejava.serialization;\n" +
                "import java.io.Serial;\n" +
                "import java.io.Serializable;\n" +
                "public class User implements Serializable {\n" +
                "    @Serial private static final long serialVersionUID = 1L;\n" +
                "    private final int id;\n" +
                "    private final String name;\n" +
                "    public User(int id, String name) { this.id = id; this.name = name; }\n" +
                "}\n";
    }
}
