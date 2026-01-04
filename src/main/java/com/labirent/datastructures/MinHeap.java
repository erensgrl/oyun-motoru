package com.labirent.datastructures;

import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<T>> {
    private T[] heap;
    private int size;
    private int capacity;

    @SuppressWarnings("unchecked")
    public MinHeap(int initialCapacity){
        this.capacity = initialCapacity;
        this.size = 0;
        this.heap = (T[]) new Comparable[capacity];
    }
    
    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void insert(T item){
        if(size == capacity){
            resize();
        }
        heap[size] = item;
        size++;
        heapifyUp(size-1);
    }

    public T extractMin(){
        if(isEmpty()){
            throw new NoSuchElementException("Heap boş");
        }
        T min = heap[0];
        heap[0] = heap[size-1];
        heap[size-1] = null;
        size--;

        heapifyDown(0);

        return min;
    }

    public T peek(){
        if(isEmpty()){
            throw new NoSuchElementException("Heap boş");
        }
        return heap[0];
    }

    private void heapifyUp(int index){
        int parentIndex = (index -1) / 2;

        while(index > 0 && heap[index].compareTo(heap[parentIndex]) < 0){
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index-1) / 2;         
        }
    }

    private void heapifyDown(int index){
        int smallest = index;
        int leftChild = 2 * index + 1;
        int rightChild = 2 * index + 2;

        if(leftChild < size && heap[leftChild].compareTo(heap[smallest]) < 0){
            smallest = leftChild;
        }
        if(rightChild < size && heap[rightChild].compareTo(heap[smallest]) < 0){
            smallest = rightChild;
        }
        if(smallest != index){
            swap(index, smallest);
            heapifyDown(smallest);
        }
    }

    private void swap(int i, int j){
        T temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    @SuppressWarnings("unchecked")
    private void resize(){
        capacity *= 2;
        T[] newHeap = (T[]) new Comparable[capacity];

        for(int i = 0; i < size; i++){
            newHeap[i] = heap[i];
        }
        heap = newHeap;
    }
}
