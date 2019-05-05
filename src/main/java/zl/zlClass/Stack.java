package zl.zlClass;

/*
 * @Description: 链表实现栈
 * @Param:
 * @Author: zl
 * @Date: 2019/3/26 15:54
 */

public class Stack <Item>{
    private  class Node{
        Item item;
        Node next;
    }
    private Node first;
    private int N;
    public  boolean  isEmpty(){
        if (first==null) return true;
        else
            return false;
    }
    public int size(){
        return N;
    }
    public boolean push(Item im){
            Node oldfirst =first;
            first = new Node();
            first.item =im;
            first.next = oldfirst;
            N++;
            return true;
    }
    public Item pop(){
        N--;
        Item im = first.item;
        first = first.next;
        return im;
    }

    public static void main(String[] args) {
        Stack <String> sk= new Stack<String>();
        zlTypeConvern tc = new zlTypeConvern();
        for(int i=0;i<100;i++){
            sk.push( tc.IntToStr(i));
        }
        System.out.printf("size%d, pop%s", sk.size(),sk.pop());
    }
}
