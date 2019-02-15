package com.cloudnative.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.logging.Logger;

public class GRPCClient {
    private static final Logger logger = Logger.getLogger(GRPCServer.class.getName());
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8888)
                .usePlaintext(true)
                .build();
        ProductServiceGrpc.ProductServiceBlockingStub blckStub=ProductServiceGrpc.newBlockingStub(channel);
        ProductResponse response=blckStub.getProduct(ProductRequest.newBuilder().setId(111).build());
        logger.info(response.getName());

        response=blckStub.getProduct(ProductRequest.newBuilder().setId(2).build());
        logger.info(response.getName());

    }

}
