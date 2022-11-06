package controller;

public class Node<T> {
    T data;
    Node<T> next;
    Node<T> previous;

    public Node(Node<T> prev, T data, Node<T> next) {
        this.data = data;
        this.next = next;
        this.previous = prev;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> prev) {
        this.previous = prev;
    }

}