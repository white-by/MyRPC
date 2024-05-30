package com.myrpc.protocol;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtocolMessage<T> {
    private Header header;

    private T body;

    @Data
    public static class Header{
        //魔数
        private byte magic;
        private byte version;
        private byte serializer;
        //消息类型
        private byte type;
        private byte status;
        private long requestId;
        private int bodyLength;
    }
}
