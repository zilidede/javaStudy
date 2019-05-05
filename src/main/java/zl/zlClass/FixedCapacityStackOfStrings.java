package zl.zlClass;

/*
 * @Description: 定容栈
 * @Param:
 * @Author: zl
 * @Date: 2019/3/25 22:42
 */

import java.util.Iterator;

public class FixedCapacityStackOfStrings <Item>implements Iterable<Item>{

    private Item [] FItemist;
    private int N;
    private int iterNum=0;
    private void reSize(int max){
        Item [] tItemList= (Item[]) new Object[max];
        for(int i=0;i<N;i++){
            tItemList[i]=FItemist[i];

        }
        FItemist =tItemList;
    }
    public  FixedCapacityStackOfStrings(int n){
        if (n<1)
            throw new IllegalArgumentException("n 必须大约零");
        FItemist= (Item[]) new Object[n];

    }
    public boolean push(Item s){
        if (N==FItemist.length) reSize(2*FItemist.length);
        FItemist[N++] =s;

        return true;
    }

    public Item pop(){
        N--;
        Item rIm= FItemist[N];
        FItemist[N] = null;
        FItemist[N]=null;
        if(N>0&&N==FItemist.length/4) reSize(FItemist.length/2);
        return  rIm;
    }
    public  boolean isEmpty(){
        if (N<=0)
            return false;
        else
            return true;
    }
    public  int size(){
        return N;
    }

    @Override
    public Iterator<Item> iterator() {
        return new ReverArrayIterator();
    }
    private class ReverArrayIterator implements Iterator<Item>{
        private int i =N;
        @Override
        public boolean hasNext(){
            return i>0;
        }
        @Override
        public Item next(){
            return FItemist[--i];
        }
        @Override
        public void remove(){
            // TODO Auto-generated method stub
        }

    }


    public static void main(String[] args) {
        FixedCapacityStackOfStrings<Double> fcs=  new FixedCapacityStackOfStrings<Double>(3);
        for(int i=0;i<10;i++)
            fcs.push(zlTypeConvern.intToDouble(i)) ;
        System.out.println(fcs.pop());
        for(Double im: fcs){
            System.out.println(im);
        }

    }
}
