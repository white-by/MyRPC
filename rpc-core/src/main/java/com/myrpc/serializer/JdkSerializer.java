package com.myrpc.serializer;

import java.io.*;

public class JdkSerializer implements Serializer{
    @Override
    public <T> byte[] mySerialize(T object) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        objectOutputStream.writeObject(object);
        objectOutputStream.close();

        return outputStream.toByteArray();
    }

    @Override
    public <T> T myDeserialize(byte[] bytes, Class<T> type) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            return (T) objectInputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
