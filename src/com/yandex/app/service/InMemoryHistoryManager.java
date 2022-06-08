package com.yandex.app.service;
import com.yandex.app.model.Task;

import java.util.*;

public class InMemoryHistoryManager implements HistoryManager {

    private final Map<Long, Node> customLinkedList = new HashMap<>();
    private Node tail;
    private static class Node {
        Node prev;
        Node next;
        Task data;
        public Node(Node prev, Task data, Node next) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }
    }
    public void linkLast(Task task) {
        Node newNode = new Node(tail, task, null);
        tail = newNode;
        tail.next = newNode;
        tail = newNode;
        customLinkedList.put(task.getId(), newNode);
    }
    @Override
    public void add(Task task) {
        if (customLinkedList.containsKey(task.getId())) {
            removeNode(customLinkedList.get(task.getId()));
        }
        linkLast(task);
    }
    private void removeNode(Node node) {
        Node prevNode = node.prev;
        Node nextNode = node.next;
        if (prevNode != null) {
            prevNode.next = node.next;
        } else {
        }
        if (nextNode != null) {
            nextNode.prev = prevNode;
        } else {
            tail = prevNode;
        }
        node.data = null;
    }
    @Override
    public void remove(long id) {
        removeNode(customLinkedList.remove(id));
        customLinkedList.remove(id);
    }
    @Override
    public List<Task> getHistory() {
        return getList();
    }
    public List<Task> getList() {
        List<Task> linktask = new ArrayList();
        Node node = tail;
        while (node != null) {
            linktask.add((Task) node.data);
            node = node.prev;
        }
        return linktask;
    }
}
