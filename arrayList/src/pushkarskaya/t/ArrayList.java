package pushkarskaya.t;


import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class ArrayList<E> implements List<E> {
    private int size;
    private Object[] element;
    private static final Object[] EMPTY_ELEMENT = new Object[0];
    private static final int MAX_ARRAY_SIZE = 2147483639;

    public ArrayList(){
        this.element=EMPTY_ELEMENT;
        this.size=0;
    }

    public ArrayList(int size) {
        if (size > 0) {
            this.element = new Object[size];
            this.size=0;
        } else {
            if (size != 0) {
                throw new IllegalArgumentException("Illegal Capacity: " + size);
            }
            this.element = EMPTY_ELEMENT;
        }

    }

    public ArrayList(Collection<? extends E> c) {
        this.element = c.toArray();
        if ((this.size = this.element.length) != 0) {
            if (this.element.getClass() != Object[].class) {
                this.element = Arrays.copyOf(this.element, this.size, Object[].class);
            }
        } else {
            this.element = EMPTY_ELEMENT;
            this.size=0;
        }

    }

    /**
     * Метод grow(int minCapacity)
     * Возвращает размер больше, чем тот, который был
     */
    private Object[] grow(int minCapacity){
        return this.element = Arrays.copyOf(this.element, this.newCapacity(minCapacity));
    }

    /**
     * Метод newCapacity(int minCapacity)
     * Возвращает размер нового массива, если размер допустим
     */
    private int newCapacity(int minCapacity) {
        int oldCapacity = this.element.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        if (newCapacity - minCapacity <= 0) {
            if (this.element == EMPTY_ELEMENT) {
                return Math.max(10, minCapacity);
            } else if (minCapacity < 0) {
                throw new OutOfMemoryError();
            } else {
                return minCapacity;
            }
        } else {
            return newCapacity - 2147483639 <= 0 ? newCapacity : hugeCapacity(minCapacity);
        }
    }
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) {
            throw new OutOfMemoryError();
        } else {
            return minCapacity > 2147483639 ? 2147483647 : 2147483639;
        }
    }

    /**
     * Метод checkIndex(int index)
     * Выдает ошибку, если индекс ArrayList-а недопустим
     */

    private void checkIndex(int index) {
        if (index > this.size || index < 0) {
            throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
        }
    }

    /**
     * Метод outOfBoundsMsg(int index)
     * Возвращает строку для Exception'а
     */
    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + this.size;
    }

    /**
     * Метод trimToSize()
     * Уменьшает вместимость массива element до size
     */
    public void trimToSize() {
        if (this.size < this.element.length) {
            this.element = this.size == 0 ? EMPTY_ELEMENT : Arrays.copyOf(this.element, this.size);
        }

    }
    /**
     * Метод size()
     * Возвращает колличество элементов в ArrayList-е
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Метод isEmpty()
     * Возвраащет true, если элементов в ArrayList-е нет
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Метод contains(Object o)
     * Возвращает true, если в ArrayList-е есть объект o
     */
    @Override
    public boolean contains(Object o) {
        return this.indexOf(o) >= 0;
    }

    /**
     * Метод iterator()
     * Возвращает итератор ArrayList-а
     */
    @Override
    public Iterator<E> iterator() {
        return new ArrayList.Itr();
    }


    /**
     * Метод toArray()
     * Возвращает массив
     */
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.element, this.size);
    }


    /**
     * Метод toArray(T[] ts)
     * Возвращает массив ts, в котором будут элементы ArrayList-а
     */
    @Override
    public <T> T[] toArray(T[] ts) {
        if (ts.length < this.size) {
            return (T[]) Arrays.copyOf(this.element, this.size, ts.getClass());
        } else {
            System.arraycopy(this.element, 0, ts, 0, this.size);
            if (ts.length > this.size) {
                ts[this.size] = null;
            }

            return ts;
        }
    }

    /**
     * Метод add(E e)
     * Добавляет в конец ArrayList-а элемент e
     */
    @Override
    public boolean add(E e) {
        if (this.size == this.element.length) {
            element = this.grow(this.size+1);
        }
        element[this.size] = e;
        ++this.size;
        return true;
    }

    /**
     * Метод remove(Object o)
     * Удаляет элемент o из ArrayList-а
     */
    @Override
    public boolean remove(Object o) {
        Object[] es = this.element;
        int size = this.size;
        int i = this.indexOf(o);
        if(i==(-1)){
            return false;
        }
        this.fastRemove(es, i);
        return true;
    }


    /**
     * Метод containsAll(Collection<\?> collection)
     * Возвращает true, если объекты коллекции collection есть в ArrayList-е
     */
    @Override
    public boolean containsAll(Collection<?> collection) {
        Object[] a = collection.toArray();
        if(this.size<a.length){
            return false;
        }
        else{
            for(int i=0;i<a.length;i++){
                if(!this.contains(a[i])){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Метод  addAll(Collection<\? extends E> collection)
     * Добавляет элементы коллекции в конец ArrayList-а
     */
    @Override
    public boolean addAll(Collection<? extends E> collection) {
        Object[] a = collection.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        } else {
            Object[] elementData;
            int s;
            if (numNew > (elementData = this.element).length - (s = this.size)) {
                elementData = this.grow(s + numNew);
            }

            System.arraycopy(a, 0, elementData, s, numNew);
            this.size = s + numNew;
            return true;
        }
    }


    /**
     * Вставляет все элементы collection в ArrayList, начиная с позиции i
     */
    @Override
    public boolean addAll(int i, Collection<? extends E> collection) {
        this.checkIndex(i);
        Object[] a = collection.toArray();
        int numNew = a.length;
        if (numNew == 0) {
            return false;
        } else {
            Object[] elementData;
            int s;
            if (numNew > (elementData = this.element).length - (s = this.size)) {
                elementData = this.grow(s + numNew);
            }

            int numMoved = s - i;
            if (numMoved > 0) {
                System.arraycopy(elementData, i, elementData, i + numNew, numMoved);
            }

            System.arraycopy(a, 0, elementData, i, numNew);
            this.size = s + numNew;
            return true;
        }
    }

    /**
     * Метод removeAll(Collection<\?> collection)
     * Удаляет все элементы collection из ArrayList-а
     */
    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.batchRemove(collection, false, 0, this.size);
    }

    /**
     * Метод retainAll(Collection<\?> collection)
     * Удаляет все элементы из ArrayList-а, кроме тех, которые есть в collection
     */
    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.batchRemove(collection, true, 0, this.size);
    }

    /**
     * Метод batchRemove(Collection<\?> c, boolean complement, int from, int end)
     * Удаляет те элементы ArrayList-а, которые либо есть в c(complement==false),
     * либо те, которых нет в c(complement==true)
     */
    boolean batchRemove(Collection<?> c, boolean complement, int from, int end) {
        Objects.requireNonNull(c);
        Object[] es = this.element;
        for(int r = from; r != end; ++r) {
            if (c.contains(es[r]) != complement) {
                int w = r++;

                try {
                    for(; r < end; ++r) {
                        Object e;
                        if (c.contains(e = es[r]) == complement) {
                            es[w++] = e;
                        }
                    }
                } catch (Throwable var12) {
                    System.arraycopy(es, r, es, w, end - r);
                    w += end - r;
                    throw var12;
                } finally {
                    this.shiftTailOverGap(es, w, end);
                }

                return true;
            }
        }

        return false;
    }

    /**
     * Метод shiftTailOverGap(Object[] es, int lo, int hi)
     * Сдвигает элементы ArrayList-а после удаления элементов из collection
     */
    private void shiftTailOverGap(Object[] es, int lo, int hi) {
        System.arraycopy(es, hi, es, lo, this.size - hi);
        int to = this.size;

        for(int i = this.size -= hi - lo; i < to; ++i) {
            es[i] = null;
        }

    }

    /**
     * Метод clear()
     * Делает все элементы ArrayList-а равными null
     */
    @Override
    public void clear() {
        Object[] es = this.element;
        int to = this.size;

        for(int i = this.size = 0; i < to; ++i) {
            es[i] = null;
        }
    }

    /**
     * Метод get(int i)
     * Возвращает элемент на позиции i
     */
    @Override
    public E get(int i) {
        this.checkIndex(i);
        return this.element(i);
    }


    /**
     * Метод set(int i, E e)
     * Заменяет элемент на позиции i на элемент e
     * Возвращает старое значение элемента на позиции i
     */
    @Override
    public E set(int i, E e) {
        Objects.checkIndex(i, this.size);
        E oldValue = this.element(i);
        this.element[i] = element;
        return oldValue;
    }

    /**
     * Метод element(int index)
     * Возвращает элемент на позиции index
     */
    E element(int index) {
        return (E) this.element[index];
    }

    /**
     * Метод add(int i, E e)
     * Вставляет элемент e на позицию i
     */
    @Override
    public void add(int i, E e) {
        this.checkIndex(i);
        int s;
        Object[] elementData;
        if (this.size== this.element.length) {
            elementData = this.grow(this.size+1);
        }
        System.arraycopy(this.element, i, this.element, i + 1, this.size - i);
        this.element[i] = e;
        ++this.size;
    }

    /**
     * Метод remove(int i)
     * Удаляет элемент на позиции i
     */
    @Override
    public E remove(int i) {
        Objects.checkIndex(i, this.size);
        Object[] es = this.element;
        E oldValue = (E) es[i];
        this.fastRemove(es, i);
        return oldValue;
    }

    /**
     * Метод fastRemove(Object[] es, int i)
     * Создает ArrayList меньше на 1, чем был, удаляя элемент на позиции i
     */
    private void fastRemove(Object[] es, int i) {
        int newSize;
        if ((newSize = this.size - 1) > i) {
            System.arraycopy(es, i + 1, es, i, newSize - i);
        }

        es[this.size = newSize] = null;
    }

    /**
     * Метод indexOf(Object o)
     * Возвращает индекс объекта o
     */
    @Override
    public int indexOf(Object o) {
        return this.indexOfRange(o, 0, this.size);
    }

    /**
     * Метод indexOfRange(Object o, int start, int end)
     * Возвращает -1, если элемента нет в ArrayList-е, иначе индекс элемента, найденного с начала списка
     */
    int indexOfRange(Object o, int start, int end) {
        Object[] es = this.element;
        int i;
        if (o == null) {
            for(i = start; i < end; ++i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for(i = start; i < end; ++i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Метод lastIndexOf(Object o)
     * Возвращает индекс элемента o
     */
    @Override
    public int lastIndexOf(Object o) {
        return this.lastIndexOfRange(o, 0, this.size);
    }

    /**
     * Метод lastIndexOfRange(Object o, int start, int end)
     * Возвращает -1, если элемента нет в ArrayList-е, иначе индекс первого попавшегося такого-же элемента с конца
     */
    int lastIndexOfRange(Object o, int start, int end) {
        Object[] es = this.element;
        int i;
        if (o == null) {
            for(i = end - 1; i >= start; --i) {
                if (es[i] == null) {
                    return i;
                }
            }
        } else {
            for(i = end - 1; i >= start; --i) {
                if (o.equals(es[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Метод ListIterator<E> listIterator()
     * Возвращает ListIterator ArrayList-а
     */
    @Override
    public ListIterator<E> listIterator() {
        return new ArrayList.ListItr(0);
    }

    /**
     * Метод listIterator(int i)
     * Возвращает ListIterator ArrayList-а, начиная с позиции i
     */
    @Override
    public ListIterator<E> listIterator(int i) {
        checkIndex(i);
        return new ArrayList.ListItr(i);
    }

    /**
     * Метод subList(int i, int i1)
     * Возвращает SubList("подсписок", который находится между позицией i и i1) ArrayList-а
     */
    @Override
    public List<E> subList(int i, int i1) {
        return new ArrayList.SubList(this, i, i1);
    }
    static <E> E elementAt(Object[] es, int index) {
        return (E) es[index];
    }
    private class Itr implements Iterator<E> {
        int cursor;
        int lastRet = -1;

        Itr() {}

        public boolean hasNext() {
            return this.cursor != ArrayList.this.size;
        }

        public E next() {
            int i = this.cursor;
            if (i >= ArrayList.this.size) {
                throw new NoSuchElementException();
            } else {
                Object[] elementData = ArrayList.this.element;
                if (i >= elementData.length) {
                    throw new ConcurrentModificationException();
                } else {
                    this.cursor = i + 1;
                    return (E) element[this.lastRet = i];
                }
            }
        }

        public void remove() {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            } else {
                try {
                    ArrayList.this.remove(this.lastRet);
                    this.cursor = this.lastRet;
                    this.lastRet = -1;
                } catch (IndexOutOfBoundsException var2) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        public void forEachRemaining(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            int size = ArrayList.this.size;
            int i = this.cursor;
            if (i < size) {
                Object[] es = ArrayList.this.element;
                if (i >= es.length) {
                    throw new ConcurrentModificationException();
                }
                while(i < size) {
                    action.accept(ArrayList.elementAt(es, i));
                    ++i;
                }
                this.cursor = i;
                this.lastRet = i - 1;
            }

        }
    }


    private static class SubList<E> extends AbstractList<E> implements RandomAccess {
        private final ArrayList<E> root;
        private final ArrayList.SubList<E> parent;
        private final int offset;
        private int size;

        public SubList(ArrayList<E> root, int fromIndex, int toIndex) {
            this.root = root;
            this.parent = null;
            this.offset = fromIndex;
            this.size = toIndex - fromIndex;
        }

        private SubList(ArrayList.SubList<E> parent, int fromIndex, int toIndex) {
            this.root = parent.root;
            this.parent = parent;
            this.offset = parent.offset + fromIndex;
            this.size = toIndex - fromIndex;
        }

        public E set(int index, E element) {
            Objects.checkIndex(index, this.size);
            E oldValue = this.root.element(this.offset + index);
            this.root.element[this.offset + index] = element;
            return oldValue;
        }

        public E get(int index) {
            Objects.checkIndex(index, this.size);
            return this.root.element(this.offset + index);
        }

        public int size() {
            return this.size;
        }

        public void add(int index, E element) {
            this.rangeCheckForAdd(index);
            this.root.add(this.offset + index, element);
            this.updateSizeAndModCount(1);
        }

        public E remove(int index) {
            Objects.checkIndex(index, this.size);
            E result = this.root.remove(this.offset + index);
            this.updateSizeAndModCount(-1);
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            this.root.removeRange(this.offset + fromIndex, this.offset + toIndex);
            this.updateSizeAndModCount(fromIndex - toIndex);
        }

        public boolean addAll(Collection<? extends E> c) {
            return this.addAll(this.size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            this.rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize == 0) {
                return false;
            } else {
                this.root.addAll(this.offset + index, c);
                this.updateSizeAndModCount(cSize);
                return true;
            }
        }

        public boolean removeAll(Collection<?> c) {
            return this.batchRemove(c, false);
        }

        public boolean retainAll(Collection<?> c) {
            return this.batchRemove(c, true);
        }

        private boolean batchRemove(Collection<?> c, boolean complement) {
            int oldSize = this.root.size;
            boolean modified = this.root.batchRemove(c, complement, this.offset, this.offset + this.size);
            if (modified) {
                this.updateSizeAndModCount(this.root.size - oldSize);
            }

            return modified;
        }


        public Object[] toArray() {
            return Arrays.copyOfRange(this.root.element, this.offset, this.offset + this.size);
        }

        public <T> T[] toArray(T[] a) {
            if (a.length < this.size) {
                return (T[]) Arrays.copyOfRange(this.root.element, this.offset, this.offset + this.size, a.getClass());
            } else {
                System.arraycopy(this.root.element, this.offset, a, 0, this.size);
                if (a.length > this.size) {
                    a[this.size] = null;
                }

                return a;
            }
        }


        public int indexOf(Object o) {
            int index = this.root.indexOfRange(o, this.offset, this.offset + this.size);
            return index >= 0 ? index - this.offset : -1;
        }

        public int lastIndexOf(Object o) {
            int index = this.root.lastIndexOfRange(o, this.offset, this.offset + this.size);
            return index >= 0 ? index - this.offset : -1;
        }

        public boolean contains(Object o) {
            return this.indexOf(o) >= 0;
        }

        public Iterator<E> iterator() {
            return this.listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            this.rangeCheckForAdd(index);
            return new ListIterator<E>() {
                int cursor = index;
                int lastRet = -1;
                int expectedModCount;


                public boolean hasNext() {
                    return this.cursor != ArrayList.SubList.this.size;
                }

                public E next() {
                    int i = this.cursor;
                    if (i >=ArrayList.SubList.this.size) {
                        throw new NoSuchElementException();
                    } else {
                        Object[] elementData = ArrayList.SubList.this.root.element;
                        if (ArrayList.SubList.this.offset + i >= elementData.length) {
                            throw new ConcurrentModificationException();
                        } else {
                            this.cursor = i + 1;
                            return (E) elementData[SubList.this.offset + (this.lastRet = i)];
                        }
                    }
                }

                public boolean hasPrevious() {
                    return this.cursor != 0;
                }

                public E previous() {
                    int i = this.cursor - 1;
                    if (i < 0) {
                        throw new NoSuchElementException();
                    } else {
                        Object[] elementData = ArrayList.SubList.this.root.element;
                        if (ArrayList.SubList.this.offset + i >= elementData.length) {
                            throw new ConcurrentModificationException();
                        } else {
                            this.cursor = i;
                            return (E) elementData[SubList.this.offset + (this.lastRet = i)];
                        }
                    }
                }

                public void forEachRemaining(Consumer<? super E> action) {
                    Objects.requireNonNull(action);
                    int size = ArrayList.SubList.this.size;
                    int i = this.cursor;
                    if (i < size) {
                        Object[] es = ArrayList.SubList.this.root.element;
                        if (ArrayList.SubList.this.offset + i >= es.length) {
                            throw new ConcurrentModificationException();
                        }

                        while(i < size && ArrayList.SubList.this.modCount == this.expectedModCount) {
                            action.accept(ArrayList.elementAt(es, ArrayList.SubList.this.offset + i));
                            ++i;
                        }

                        this.cursor = i;
                        this.lastRet = i - 1;
                    }

                }

                public int nextIndex() {
                    return this.cursor;
                }

                public int previousIndex() {
                    return this.cursor - 1;
                }

                public void remove() {
                    if (this.lastRet < 0) {
                        throw new IllegalStateException();
                    } else {

                        try {
                            ArrayList.SubList.this.remove(this.lastRet);
                            this.cursor = this.lastRet;
                            this.lastRet = -1;
                        } catch (IndexOutOfBoundsException var2) {
                            throw new ConcurrentModificationException();
                        }
                    }
                }

                public void set(E e) {
                    if (this.lastRet < 0) {
                        throw new IllegalStateException();
                    } else {

                        try {
                            ArrayList.SubList.this.root.set(ArrayList.SubList.this.offset + this.lastRet, e);
                        } catch (IndexOutOfBoundsException var3) {
                            throw new ConcurrentModificationException();
                        }
                    }
                }

                public void add(E e) {
                    try {
                        int i = this.cursor;
                        ArrayList.SubList.this.add(i, e);
                        this.cursor = i + 1;
                        this.lastRet = -1;
                    } catch (IndexOutOfBoundsException var3) {
                        throw new ConcurrentModificationException();
                    }
                }


            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new ArrayList.SubList(this, fromIndex, toIndex);
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > this.size) {
                throw new IndexOutOfBoundsException(this.outOfBoundsMsg(index));
            }
        }

        private String outOfBoundsMsg(int index) {
            return "Index: " + index + ", Size: " + this.size;
        }



        private void updateSizeAndModCount(int sizeChange) {
            ArrayList.SubList slist = this;

            do {
                slist.size += sizeChange;
                slist = slist.parent;
            } while(slist != null);

        }

        public Spliterator<E> spliterator() {
            return new Spliterator<E>() {
                private int index;
                private int fence;
                private int expectedModCount;

                {
                    this.index = ArrayList.SubList.this.offset;
                    this.fence = -1;
                }

                private int getFence() {
                    int hi;
                    if ((hi = this.fence) < 0) {
                        this.expectedModCount = ArrayList.SubList.this.modCount;
                        hi = this.fence = ArrayList.SubList.this.offset + ArrayList.SubList.this.size;
                    }

                    return hi;
                }


                public boolean tryAdvance(Consumer<? super E> action) {
                    Objects.requireNonNull(action);
                    int hi = this.getFence();
                    int i = this.index;
                    if (i < hi) {
                        this.index = i + 1;
                        E e = (E) SubList.this.root.element[i];
                        action.accept(e);
                        return true;
                    } else {
                        return false;
                    }
                }

                public void forEachRemaining(Consumer<? super E> action) {
                    Objects.requireNonNull(action);
                    ArrayList<E> lst = ArrayList.SubList.this.root;
                    Object[] a;
                    if ((a = lst.element) != null) {
                        int hi;
                        int mc;
                        if ((hi = this.fence) < 0) {
                            mc = ArrayList.SubList.this.modCount;
                            hi = ArrayList.SubList.this.offset + ArrayList.SubList.this.size;
                        } else {
                            mc = this.expectedModCount;
                        }

                        int i;
                        if ((i = this.index) >= 0 && (this.index = hi) <= a.length) {
                            while(i < hi) {
                                E e = (E) a[i];
                                action.accept(e);
                                ++i;
                            }

                        }
                    }

                    throw new ConcurrentModificationException();
                }

                @Override
                public Spliterator<E> trySplit() {
                    return null;
                }

                public long estimateSize() {
                    return (long)(this.getFence() - this.index);
                }

                public int characteristics() {
                    return 16464;
                }
            };
        }
    }

    private class ListItr extends ArrayList<E>.Itr implements ListIterator<E> {
        ListItr(int index) {
            super();
            this.cursor = index;
        }

        public boolean hasPrevious() {
            return this.cursor != 0;
        }

        public int nextIndex() {
            return this.cursor;
        }

        public int previousIndex() {
            return this.cursor - 1;
        }

        public E previous() {
            int i = this.cursor - 1;
            if (i < 0) {
                throw new NoSuchElementException();
            } else {
                Object[] elementData = ArrayList.this.element;
                if (i >= elementData.length) {
                    throw new ConcurrentModificationException();
                } else {
                    this.cursor = i;
                    return (E) elementData[this.lastRet = i];
                }
            }
        }

        public void set(E e) {
            if (this.lastRet < 0) {
                throw new IllegalStateException();
            } else {
                try {
                    ArrayList.this.set(this.lastRet, e);
                } catch (IndexOutOfBoundsException var3) {
                    throw new ConcurrentModificationException();
                }
            }
        }

        public void add(E e) {
            try {
                int i = this.cursor;
                ArrayList.this.add(i, e);
                this.cursor = i + 1;
                this.lastRet = -1;
            } catch (IndexOutOfBoundsException var3) {
                throw new ConcurrentModificationException();
            }
        }
    }

    /**
     * Метод removeRange(int fromIndex, int toIndex)
     * Удаляет все элементы между fromIndex и toIndex
     */
    protected void removeRange(int fromIndex, int toIndex) {
        if (fromIndex > toIndex) {
            throw new IndexOutOfBoundsException("from " + fromIndex + " to " + toIndex);
        } else {
            this.shiftTailOverGap(this.element, fromIndex, toIndex);
        }
    }

}

