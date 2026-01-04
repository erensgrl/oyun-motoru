package com.labirent.datastructures;

import java.util.NoSuchElementException;

public class Stack<T> {
    private Node<T> top;
    private int size;

    public Stack(){
        this.top = null;
        this.size = 0;
    }

    public void push(T data){
        Node<T> newNode = new Node<>(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop(){
        if(isEmpty()){
            throw new NoSuchElementException("Yığın boş");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek(){
        if(isEmpty()){
            throw new NoSuchElementException("Yığın boş");
        }
        return top.data;
    }

    public boolean isEmpty(){
        return top == null;
    }

    public int size(){
        return size;
    }
}
