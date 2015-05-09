package org.organicdesign.fp.collections;

/**
 * Holds Immutable "modification" methods that return a new ImList (for safety) reflecting the modification while
 * sharing as much data structure with the previous ImList as possible (for performance).
 */
public interface ImList<E> extends UnList<E> {
// Do we want to make an ImIterator thatis truly immutable - a Sequence?
//    /** {@inheritDoc} */
//    @Override default UnIterator<E> iterator() { return listIterator(0); }
//
//    /** {@inheritDoc} */
//    @Override default UnListIterator<E> listIterator() { return listIterator(0); }

// Inherited correctly - there is no ImIterator.
// UnListIterator<E> listIterator(int index) {

// Inherited correctly and need to be implemented by the implementing class
// int size() {
// boolean equals(Object o) {
// int hashCode() {
// E get(int index) {

    /**
     This default implementation is at least O(n) slow.
     Inserts a new item at the specified index, shifting that item and subsequent items up/right.
     @param i the zero-based index to insert at
     @param val the value to insert
     @return a new ImList with the additional item.
     */
    default ImList<E> insert(int i, E val) {
        if (i == size()) { return append(val); }

        if ( (i > size()) || (i < 0) ) {
            throw new IllegalArgumentException("Can't insert outside the possible bounds");
        }

        UnIterator<E> iter = iterator();
        ImList<E> v = PersistentVector.empty();
        int j = 0;
        for (; j < i; j++) {
            v = v.append(iter.next());
        }
        v = v.append(val);
        for (; j < size(); j++) {
            v = v.append(iter.next());
        }
        return v;
    }

    /**
     * Adds items to the end of the ImList.
     * @param e the values to insert
     * @return a new ImList with the additional item at the end.
     */
    ImList<E> append(E e);

// I don't know if this is a good idea or not and I don't want to have to support it if not.
//    /**
//     * Returns the item at this index, but takes any Number as an argument.
//     * @param n the zero-based index to get from the vector.
//     * @return the value at that index.
//     */
//    default E get(Number n) { return get(n.intValue()); }

    /**
     * Returns the item at this index.
     * @param i the zero-based index to get from the vector.
     * @param notFound the value to return if the index is out of bounds.
     * @return the value at that index, or the notFound value.
     */
    default E get(int i, E notFound) {
        if (i >= 0 && i < size())
            return get(i);
        return notFound;
    }

    /**
     Replace the item at the given index.  Note: i.put(i.size(), o) is equivalent to i.append(o).

     @param idx the index where the value should be stored.
     @param e the value to store
     @return a new ImList with the replaced item
     */
    ImList<E> put(int idx, E e);

    // ================================================ STATIC METHODS ================================================
//    static <T> ImList<T> empty() { return PersistentVector.empty(); }

//    /**
//     * Inserts a new item at the specified index.
//     * @param i the zero-based index to insert at (pushes current item and all subsequent items up)
//     * @param val the value to insert
//     * @return a new ImList with the additional item.
//     */
//    static <E> ImList<E> insert(ImList<E> list, int i, E val) {
//        if (i == list.size()) { return list.append(val); }
//
//        if ( (i > list.size()) || (i < 0) ) {
//            throw new IllegalArgumentException("Can't insert outside the possible bounds");
//        }
//
//        UnIterator<E> uli = list.iterator();
//        ImList<E> v = PersistentVector.empty();
//        for (int j = 0; j < i; j++) {
//            v = v.append(uli.next());
//        }
//        v = v.append(val);
//        for (int j = i; j < list.size(); j++) {
//            v = v.append(uli.next());
//        }
//        return v;
//    }
//
//    /**
//     * Adds items to the end of the ImList.
//     * @param es the values to insert
//     * @return a new ImList with the additional items at the end.
//     */
//    static <E> ImList<E> append(ImList<E> l, E e) {
//        return l.insert(l.size() - 1, e);
//    }
//
//    /**
//     * Adds items to the end of the ImList.
//     * @param es the values to insert
//     * @return a new ImList with the additional items at the end.
//     */
//    static <E> ImList<E> appendSkipNull(ImList<E> l, E e) {
//        if (e == null) { return l; }
//        return l.append(e);
//    }
//
//    /**
//     * Inserts items at the beginning of the ImList.
//     * @param es the values to insert
//     * @return a new ImList beginning with the additional items.
//     */
//    static <E> ImList<E> prepend(ImList<E> l, E e) {
//        return l.insert(0, e);
//    }
//
//    /**
//     * Inserts items at the beginning of the ImList.
//     * @param es the values to insert
//     * @return a new ImList beginning with the additional items.
//     */
//    static <E> ImList<E> prependSkipNull(ImList<E> l, E e) {
//        if (e == null) { return l; }
//        return l.insert(0, e);
//    }


}
