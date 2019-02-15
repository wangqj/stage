package com.cloudnative.protobuf;

import com.google.protobuf.InvalidProtocolBufferException;

public class TestProtobuf {
    public static void main(String[] args){
        TestProtobuf testProtobuf =new TestProtobuf();
        byte[] buf=testProtobuf.toByte();
        try {
            ProductProtos.Product product = testProtobuf.toProduct(buf);
            System.out.println(product);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }
    //序列化
    private byte[] toByte(){
        ProductProtos.Product.Builder productBuilder=ProductProtos.Product.newBuilder();
        productBuilder.setId(11);
        productBuilder.setName("milk");
        productBuilder.setPrice("4.12");
        ProductProtos.Product product =productBuilder.build();
        byte[] buf = product.toByteArray();
        System.out.println(buf.length);
        return buf;
    }
    //反序列化
    private ProductProtos.Product toProduct(byte[] buf) throws InvalidProtocolBufferException {
        return ProductProtos.Product.parseFrom(buf);
    }
}

