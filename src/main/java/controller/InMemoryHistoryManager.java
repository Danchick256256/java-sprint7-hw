package controller;

import model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {
    CustomLinkedList<Task> customLinkedList = new CustomLinkedList<>();
    public List<Task> getHistory(){
        return customLinkedList.getTasks();
    }

    public void add(Task task){
        customLinkedList.linkLast(task);
    }

    public void remove(int id){
        customLinkedList.remove(id);
    }

    public static class CustomLinkedList<T>{
        private final HashMap<Integer, Node<Task>> nodeHistory = new HashMap<>();
        private Node<Task> first;
        private Node<Task> last;
        private int size = 0;

        public void linkLast(Task task) {
            final Node<Task> l = last;
            final Node<Task> newNode = new Node<>(l, task, null);
            last = newNode;

            if (l == null) {
                first = newNode;
            } else {
                l.setNext(newNode);
            }

            if(nodeHistory.containsKey(task.getId())) { // removing duplicate
                removeNode(nodeHistory.get(task.getId()));
            }

            nodeHistory.put(task.getId(), last);

            size++;
        }

        public ArrayList<Task> getTasks(){
            ArrayList<Task> historyList = new ArrayList<>();

            Node<Task> node = first;
            while (!(node == null)) {
                historyList.add(node.getData()); // add task
                node = node.getNext();
            }

            Collections.reverse(historyList);
            return historyList;
        }

        public void remove(int id){
            nodeHistory.remove(id);
        }

        public void removeNode(Node<Task> node){
            Node<Task> prevNode = node.getPrevious();
            Node<Task> nextNode = node.getNext();
            if (prevNode != null) {
                prevNode.setNext(nextNode);
            }
            if (nextNode != null) {
                nextNode.setPrevious(prevNode);
            }
            nodeHistory.remove(node.getData().getId()); // get task id
            if (first == node) {
                first = nextNode;
            } else if (last == node) {
                last = nextNode;
            }
        }

        @Override
        public String toString() {
            return "CustomLinkedList{" +
                    "first=" + first +
                    ", last=" + last +
                    ", size=" + size +
                    '}';
        }
    }
}
