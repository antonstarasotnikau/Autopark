package by.incubator.Service;

public class Queue<T> {
    int size;
    Object[] myQueue;

    public Queue() {
        this.myQueue = new Object[10];
        this.size = 0;
    }

    public Queue(int capacity) {
        this.myQueue = new Object[capacity];
        this.size = 0;
    }

    public boolean offer(T t) {
        if (size != myQueue.length) {
            myQueue[size] = t;
            size++;
            return true;
        } else if (size == myQueue.length) {
            changeCapacityArray();
            myQueue[size] = t;
            size++;
            return true;
        }
        return false;
    }

    private void changeCapacityArray() {
        Object[] newArray = new Object[myQueue.length * 3 / 2 + 1];
        System.arraycopy(this.myQueue, 0, newArray, 0, size);
        myQueue = newArray;
    }

    public T poll() {
        if (size != 0) {
            T t = (T) myQueue[0];
            System.arraycopy(myQueue, 1, myQueue,0, size - 1);
            size--;
            return t;
        }
        return null;
    }

    public T peek() {
        if (size != 0) {
            T t = (T) myQueue[0];
            return t;
        }
        return null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

}
