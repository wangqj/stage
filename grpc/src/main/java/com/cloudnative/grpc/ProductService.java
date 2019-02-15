package com.cloudnative.grpc;

import io.grpc.stub.StreamObserver;

import java.util.logging.Logger;

public class ProductService extends ProductServiceGrpc.ProductServiceImplBase{
    private static final Logger logger = Logger.getLogger(GRPCServer.class.getName());
    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        logger.info("接收到客户端的信息:"+request.getId());
        ProductResponse responsed;
        if (111==request.getId()){
            responsed=ProductResponse.newBuilder().setId(111).setName("dddd").build();
        }else {
            responsed=ProductResponse.newBuilder().setId(0).setName("---").build();
        }

        responseObserver.onNext(responsed);
        responseObserver.onCompleted();
    }
}
