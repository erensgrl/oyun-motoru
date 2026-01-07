package com.labirent.datastructures;

import java.util.ArrayList;
import java.util.List;

public class AVLTree<T extends Comparable<T>> {
    private class Node {
        T data;
        Node left, right;
        int height;

        Node(T data){
            this.data = data;
            this.height = 1;
        }
    }
    
    private Node root;

    public void insert(T data){
        root = insert(root, data);
    }

    public boolean search(T data){
        return search(root, data);
    }

    public void printInOrder(){
        inOrder(root);
        System.out.println();
    }

    private Node insert(Node node, T data){
        if(node == null){
            return new Node(data);
        }

        if(data.compareTo(node.data) < 0){
            node.left = insert(node.left, data);
        } else if(data.compareTo(node.data) > 0){
            node.right = insert(node.right, data);
        } else {
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balance = getBalance(node);

        if(balance > 1 && data.compareTo(node.left.data) < 0){
            return rightRotate(node);
        }

        if(balance < -1 && data.compareTo(node.right.data) > 0){
            return leftRotate(node);
        }

        if(balance > 1 && data.compareTo(node.left.data) > 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        if(balance < -1 && data.compareTo(node.right.data) < 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private boolean search(Node node, T data){
        if(node == null) return false;

        int cmp = data.compareTo(node.data);
        if(cmp < 0) return search(node.left, data);
        else if(cmp > 0) return search(node.right, data);
        else return true;
    }

    private void inOrder(Node node){
        if(node != null){
            inOrder(node.left);
            System.out.print(node.data + " ");
            inOrder(node.right);
        }
    }

    public List<T> getSortedList(){
        List<T> list = new ArrayList<>();
        inOrderToList(root, list);
        return list;
    }

    private void inOrderToList(Node node, List<T> list){
        if(node != null){
            inOrderToList(node.left, list);
            list.add(node.data);
            inOrderToList(node.right, list);
        }
    }

    private int height(Node n){
        if(n == null) return 0;
        return n.height;
    }

    private int getBalance(Node n){
        if(n == null) return 0;
        return height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y){
        Node x = y.left;
        Node T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));

        return x;
    }

    private Node leftRotate(Node x){
        Node y = x.right;
        Node T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));

        return y;
    }
}
