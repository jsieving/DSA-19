package your_code;

public class MyLinkedList {

    private Node head;
    private Node tail;
    private int size;

    private class Node {
        Chicken val;
        Node prev;
        Node next;

        private Node(Chicken d, Node prev, Node next) {
            this.val = d;
            this.prev = prev;
            this.next = next;
        }

        private Node(Chicken d) {
            this.val = d;
            prev = null;
            next = null;
        }

        private void printNode() {
            String s1 = "     ";
            String s2 = this.val.name;
            String s3 = "     ";
            if (this.prev != null) {
                s1 = this.prev.val.name;
            }
            if (this.next != null) {
                s3 = this.next.val.name;
            }
            System.out.printf("<%s<\t\t%s\t\t>%s>\n", s1, s2, s3);
        }

        private void printNode(String label) {
            String s1 = "     ";
            String s2 = this.val.name;
            String s3 = "     ";
            if (this.prev != null) {
                s1 = this.prev.val.name;
            }
            if (this.next != null) {
                s3 = this.next.val.name;
            }
            System.out.printf("<%s<\t\t%s\t\t>%s> // %s\n", s1, s2, s3, label);
        }
    }

    public MyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(Chicken c) {
        addLast(c);
    }

    public Chicken pop() {
        return removeLast();
    }

    public void addLast(Chicken c) {
        Node newNode;
        if (this.size == 0) {
            newNode = new Node(c);
            this.head = newNode;
        } else {
            newNode = new Node(c, this.tail, null);
            this.tail.next = newNode;
        }
        this.tail = newNode;
        this.size++;
    }

    public void addFirst(Chicken c) {
        Node newNode;
        if (this.size == 0) {
            newNode = new Node(c);
            this.tail = newNode;
        } else {
            newNode = new Node(c, null, this.head);
            this.head.prev = newNode;
        }
        this.head = newNode;
        this.size++;
    }

    public Chicken get(int index) {
        if (0 <= index && index < this.size) {
            Node curr;
            if (index < this.size / 2) {
                curr = this.head;
                for (int i = 0; i < index; i++) {
                    curr = curr.next;
                }
            } else {
                curr = this.tail;
                for (int i = this.size-1; i > index; i--) {
                    curr = curr.prev;
                }
            }
            return curr.val;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public Node getNode(int index) {
        if (0 <= index && index < this.size) {
            Node curr;
            if (index < this.size / 2) {
                curr = this.head;
                for (int i = 0; i < index; i++) {
                    curr = curr.next;
                }
            } else {
                curr = this.tail;
                for (int i = this.size-1; i > index; i--) {
                    curr = curr.prev;
                }
            }
            return curr;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public Chicken remove(int index) {
        Node n = this.getNode(index);
        if (n == this.head) {
            this.head = this.head.next;
        }
        if (n == this.tail) {
            this.tail = this.tail.prev;
        }
        if (0 < index && index < this.size-1) {
            n.prev.next = n.next;
            n.next.prev = n.prev;
        }
        this.size--;
        return n.val;
    }

    public Chicken removeFirst() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            Chicken c = this.head.val;
            this.head = null;
            this.tail = null;
            this.size = 0;
            return c;
        } else {
            Chicken c = this.head.val;
            this.head.next.prev = null;
            this.head = this.head.next;
            this.size--;
            return c;
        }
    }

    public Chicken removeLast() {
        if (this.size == 0) {
            return null;
        } else if (this.size == 1) {
            Chicken c = this.head.val;
            this.head = null;
            this.tail = null;
            this.size = 0;
            return c;
        } else {
            Chicken c = this.tail.val;
            this.tail.prev.next = null;
            this.tail = this.tail.prev;
            this.size--;
            return c;
        }
    }

    public void list() {
        Node n = this.head;
        while (n != null) {
            n.printNode();
            n = n.next;
        }
        System.out.println();
    }
}