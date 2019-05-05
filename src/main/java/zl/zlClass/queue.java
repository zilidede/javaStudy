package zl.zlClass;

/*
 * @Description: 链表实现队列
 * @Param:
 * @Author: zl
 * @Date: 2019/3/26 16:12
 */
public class queue <Item>{
    private  class Node{
        Item item;
        Node next;
    }
    private Node first;
    private Node last;
    private int N;
    public boolean isEmpty(){return first==null;}
    public int size(){return N;}
    public void enqueue(Item im){
        Node oldLast=last;
        last = new Node();
        last.item =im;
        last.next=null;
        if (isEmpty())first=last;
        else
            oldLast.next=last;
        N++;
    }
    public Item dequeue(){
        Item rIm=first.item;
        first =first.next;
        if(isEmpty())last=null;
        N--;
        return rIm;
    }

    public static void main(String[] args) {
        queue <String> qu= new queue<String>();
        zlTypeConvern tc = new zlTypeConvern();
        for(int i=0;i<100;i++){
            qu.enqueue( tc.IntToStr(i));
        }
        System.out.printf("size%d, pop%s", qu.size(),qu.dequeue());
    }
}
