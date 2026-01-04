package com.labirent.datastructures;

import java.util.NoSuchElementException;

public class Queue<T> {
    private Node<T> head;
    private Node<T> tail;
    private int size;

    public Queue(){
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public void enqueue(T data){
        Node<T> newNode = new Node<>(data);

        if(isEmpty()){
            head = newNode;
            tail = newNode;
        }else{
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    public T dequeue(){
        if(isEmpty()){
            throw new NoSuchElementException("Kuyruk boş");
        }
        T data = head.data;
        head = head.next;
        if(head == null){
            tail = null;
        }
        size--;
        return data;
    }

    public boolean isEmpty(){
        return head == null;
    }

    public int size(){
        return size;
    }
}
