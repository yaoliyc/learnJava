package com.wangwenjun.cocurrent.jut.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.concurrent.ThreadLocalRandom.current;

/**
 * Created on 2023/8/17.
 *
 * @author DESKTOP-7BFIUE4
 */
public class CountDownLatchExample1 {

    public static void main(String[] args) throws InterruptedException {
        final int[] products = getProductsByCategoryId();
        List<ProductPrice> list = Arrays.stream(products)
                .mapToObj(ProductPrice::new)
                .collect(Collectors.toList());
        CountDownLatch countDownLatch = new CountDownLatch(products.length);
        list.forEach(pp->{
            new Thread(()->{
                System.out.println(pp.getProdID()+"-> start calculate price" );
                try {
                    TimeUnit.SECONDS.sleep(current().nextInt(10));
                    if (pp.prodID%2 ==0){
                        pp.setPrice(pp.prodID*0.9D);
                    } else {
                        pp.setPrice(pp.prodID*0.71D);
                    }
                    System.out.println(pp.getProdID()+"->  price calculate completed" );
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    countDownLatch.countDown();
                }

            }).start();
        });
        countDownLatch.await();
        System.out.println("all of price caculate finished.");
        list.forEach(System.out::println);
    }

    private static int[] getProductsByCategoryId() {
        return IntStream.rangeClosed(1, 10).toArray();
    }

    private static class ProductPrice {
        private final int prodID;
        private double price;

        public ProductPrice(int prodID) {
            this(prodID, -1);
        }

        public ProductPrice(int prodID, double price) {
            this.prodID = prodID;
            this.price = price;
        }

        public int getProdID() {
            return prodID;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        @Override
        public String toString() {
            return "ProductPrice{" +
                    "prodID=" + prodID +
                    ", price=" + price +
                    '}';
        }
    }
}
