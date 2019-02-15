package com.cloudnative.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.logging.Logger;

public class GRPCServer{
    private static final Logger logger = Logger.getLogger(GRPCServer.class.getName());

    private final int port;
    private final Server server;

    public GRPCServer(int port){
        this.port=port;
        this.server = ServerBuilder.forPort(port)
                .addService(new ProductService())
                .build();
    }

    /** Start serving requests. */
    public void start() throws IOException {
        this.server.start();
        logger.info("Server started, listening on " + port);
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                // Use stderr here since the logger may has been reset by its JVM shutdown hook.
                logger.info("*** shutting down gRPC server since JVM is shutting down");
                GRPCServer.this.stop();
                logger.info("*** server shut down");
            }
        });
    }

    /** Stop serving requests and shutdown resources. */
    public void stop() {
        if (server != null) {
            server.shutdown();
        }
    }

    /**
     * Await termination on the main thread since the grpc library uses daemon threads.
     */
    private void blockUntilShutdown() throws InterruptedException {
        if (server != null) {
            server.awaitTermination();
        }
    }

    /**
     * Main method.  This comment makes the linter happy.
     */
    public static void main(String[] args) throws Exception {
        GRPCServer server = new GRPCServer(8888);
        server.start();
        server.blockUntilShutdown();
    }


}
