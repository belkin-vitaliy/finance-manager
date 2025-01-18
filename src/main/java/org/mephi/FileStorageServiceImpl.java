package org.mephi;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileStorageServiceImpl implements FileStorageService {
    private static final Gson gson = new Gson();

    @Override
    public void saveWallet(Wallet wallet, String filename) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(wallet, writer);
            System.out.println("Данные сохранены.");
        } catch (IOException e) {
            System.err.println("Ошибка сохранения данных: " + e.getMessage());
        }
    }

    @Override
    public Wallet loadWallet(String filename) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            Type type = new TypeToken<Wallet>() {
            }.getType();
            Wallet wallet = gson.fromJson(reader, type);
            System.out.println("Данные загружены.");
            return wallet;
        } catch (IOException e) {
            System.err.println("Ошибка загрузки данных: " + e.getMessage());
            return null;
        }
    }
}
