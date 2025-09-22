package com.anjos_bolos.anjos_bolos_api.core.adapters;

public interface StorageGateway {

    String upload(byte[] bytes, String key);

    void delete(String key);
}
