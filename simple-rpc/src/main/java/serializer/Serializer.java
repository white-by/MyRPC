package serializer;

import java.io.IOException;

public interface Serializer {
    //序列化
    <T> byte[] mySerialize(T object) throws IOException;

    //反序列化
    <T> T myDeserialize(byte[] bytes, Class<T> type) throws IOException;

}
