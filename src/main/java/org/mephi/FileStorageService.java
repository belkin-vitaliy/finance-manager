package org.mephi;

/**
 * Этот интерфейс определяет методы сохранения и загрузки объектов кошелька в файлы и из них.
 */
public interface FileStorageService {
    void saveWallet(Wallet wallet, String filename);

    /**
     * Загружает кошелек из указанного файла.
     *
     * @param filename имя файла, из которого должен быть загружен кошелек
     * @return экземпляр кошелька, загруженный из файла
     */
    Wallet loadWallet(String filename);
}
