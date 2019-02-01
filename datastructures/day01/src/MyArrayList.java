public class MyArrayList {
    private Cow[] elems;
    private int size;

    // Runtime: O(1)
    public MyArrayList() {
        this.size = 0;
        this.elems = new Cow[10];
    }

    // Runtime: O(1)
    public MyArrayList(int capacity) {
        this.size = 0;
        this.elems = new Cow[capacity];
    }

    // Runtime: O(1)
    public void add(Cow c) {
        this.resize(1);
        if (this.elems[this.size] == null) {
            this.elems[this.size] = c;
            this.size++;
        }
    }

    // Runtime: O(1)
    public int size() {
        return this.size;
    }

    // Runtime: O(n)
    public Cow remove(int index) {
        if (this.elems[index] != null) {
            Cow c = this.elems[index];
            while (index < this.size-1) {
                this.elems[index] = this.elems[index+1];
                index++;
            }
            this.elems[index] = null;
            this.size--;
            this.resize(-1);
            return c;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Runtime: O(1)
    public Cow get(int index) {
        if (this.elems[index] != null) {
            return this.elems[index];
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Runtime: O(n)
    public void add(int index, Cow c) {
        if (index <= this.size()) {
            Cow[] oldElems;
            oldElems = new Cow[this.elems.length];
            System.arraycopy(this.elems, 0, oldElems, 0, this.elems.length);
            this.resize(1);
            this.size++;

            for (int i = index; i <= oldElems.length; i++) {
                if (i == index) {
                    this.elems[i] = c;
                } else if (oldElems[i - 1] != null) {
                    this.elems[i] = oldElems[i - 1];
                } else {
                    break;
                }
            }
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    // Runtime: O(1)
    public void resize(int change) {
        Cow[] newElems;
        if (this.size()+1 > this.elems.length && change == 1) {
            newElems = new Cow[this.elems.length * 2];
        } else if ((float) this.size() < (float) this.elems.length/4 && change == -1) {
            newElems = new Cow[Math.max(this.elems.length/2, 2)];
        } else {
            return;
        }

        System.arraycopy(this.elems, 0, newElems, 0, this.size());
        this.elems = newElems;
    }

    // Runtime: O(n)
    public void list() {
        System.out.println("[[\n");
        for (Cow c : this.elems) {
            if (c != null) {
                System.out.println(c.name);
            } else {
                System.out.println("null");
            }
        }
        System.out.println("\n]]");
    }
}