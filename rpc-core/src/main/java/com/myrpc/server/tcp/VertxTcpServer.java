package com.myrpc.server.tcp;

import com.myrpc.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class VertxTcpServer implements HttpServer {
    private byte[] handleRequest(byte[] requestData) {
        return "hello, client".getBytes();
    }

    @Override
    public void doStart(int port) {
        Vertx vertx = Vertx.vertx();

        NetServer server = vertx.createNetServer();

        server.connectHandler(socket -> {
            RecordParser parser = RecordParser.newFixed(8);
            parser.setOutput(new Handler<Buffer>() {
                int size = -1;
                Buffer resultBuffer = Buffer.buffer();

                @Override
                public void handle(Buffer buffer) {
                    if (size == -1) {
                        size = buffer.getInt(4);
                        parser.fixedSizeMode(size);
                        resultBuffer.appendBuffer(buffer);
                    }
                    else {
                        resultBuffer.appendBuffer(buffer);
                        System.out.println(resultBuffer.toString());
                        parser.fixedSizeMode(8);
                        size = -1;
                        resultBuffer = Buffer.buffer();
                    }
                }
            });
            socket.handler(parser);
        });

        server.listen(port, result -> {
            if (result.succeeded()) {
//                log.info("TCP server start on port: " + port);
                System.out.println("TCP server start on port: " + port);
            }
            else {
//                log.info("Fail to start TCP server: " + result.cause());
                System.err.println("Fail to start TCP server: " + result.cause());
            }
        });
    }

//    public static void main(String[] args) {
//        new VertxTcpServer().doStart(8888);
//    }
}
