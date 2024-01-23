package edu.augie.finalProgram.ahona;
public class ArrayList<E> {
    private static final int INITIAL_CAPACITY = 16;
    private Object[] data;
    private int size;

    // Constructor initializes the ArrayList with initial capacity
    public ArrayList() {
        data = new Object[INITIAL_CAPACITY]; // Initialize the data array with the initial capacity
    }

    // Method to add an element to the ArrayList
    public void add(E element) {
        if (size == data.length) { // Check if the current size equals the capacity
            // If capacity is reached, create a new array with double the capacity
            Object[] newData = new Object[data.length * 2];
            // Copy elements from the old array to the new array
            System.arraycopy(data, 0, newData, 0, data.length);
            // Update the reference of the data array to the new array with increased capacity
            data = newData;
        }
        // Add the new element to the ArrayList and increment the size
        data[size] = element;
        size++;
    }

    // Method to get an element from the ArrayList at a specific index
    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= size) { // Check if the index is within valid bounds
            throw new IndexOutOfBoundsException();
        }
        return (E) data[index]; // Return the element at the specified index with an unchecked cast
    }

    // Method to get the size of the ArrayList
    public int size() {
        return size; // Return the current size of the ArrayList
    }

    // Method to convert the ArrayList to a 2D String array
    public String[][] toArray(String[][] array) {
        if (array.length < size || array[0].length < 1) {
            throw new IllegalArgumentException();
        }
        // Copy elements from the ArrayList to the 2D String array
        System.arraycopy(data, 0, array, 0, size);
        return array; // Return the 2D String array
    }
}
