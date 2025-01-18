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
    public static final String SAVE_SUCCESS_NOTIFICATION = "Данные сохранены.";
    public static final String ERROR_SAVING_DATA_MESSAGE = "Ошибка сохранения данных: ";
    public static final String SUCCESSFUL_DATA_LOAD = "Данные загружены.";
    public static final String ERROR_LOADING_DATA_MESSAGE = "Ошибка загрузки данных: ";

    @Override
    public void saveWallet(Wallet wallet, String filename) {
        try (Writer writer = new FileWriter(filename)) {
            gson.toJson(wallet, writer);
            System.out.println(SAVE_SUCCESS_NOTIFICATION);
        } catch (IOException e) {
            System.err.println(ERROR_SAVING_DATA_MESSAGE + e.getMessage());
        }
    }

    @Override
    public Wallet loadWallet(String filename) {
        try (Reader reader = Files.newBufferedReader(Paths.get(filename))) {
            Type type = new TypeToken<Wallet>() {
            }.getType();
            Wallet wallet = gson.fromJson(reader, type);
            System.out.println(SUCCESSFUL_DATA_LOAD);
            return wallet;
        } catch (IOException e) {
            System.err.println(ERROR_LOADING_DATA_MESSAGE + e.getMessage());
            return null;
        }
    }
}
