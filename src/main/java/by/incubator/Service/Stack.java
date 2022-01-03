package by.incubator.Service;

import java.util.EmptyStackException;

public class Stack<T> {
    int size;
    Object[] myStack;

    public Stack() {
        this.myStack = new Object[10];
        this.size = 0;
    }

    public Stack(int capacity) {
        this.myStack = new Object[capacity];
        this.size = 0;
    }

    private void changeCapacityArray() {
        Object[] newArray = new Object[myStack.length * 3 / 2 + 1];
        System.arraycopy(this.myStack, 0, newArray, 0, size);
        myStack = newArray;
    }

    public T push(T item) {
        if (size == myStack.length) {
            changeCapacityArray();
        }
        myStack[size] = item;
        this.size++;
        return item;
    }

    public synchronized T peek() {
        if (empty())
            throw new EmptyStackException();

        return (T) myStack[size - 1];
    }

    public T pop() {
        if (empty())
            throw new EmptyStackException();

        T t = peek();
        myStack[size - 1] = null;
        size--;
        return t;
    }

    public boolean empty() {
        return size == 0;
    }

}
