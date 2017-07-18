package com.zerofang.pagerank.interfaces;

import java.util.List;

import com.zerofang.pagerank.entity.Identifiable;

public interface IDBAccessor<T extends Identifiable> {
    /**
     * Add t to the database.
     * Note: the ID property of t will be ignored!!!
     * @param t
     * @return true if successed
     */
    public boolean add(T t);

    /**
     * Update t, which is in the database.
     * Passing a t that not existing in the database will cause this method to fail.
     * @param t
     * @return true if successed
     */
    public boolean update(T t);

    /**
     * Delete one line in the database, which has exactly the same id.
     * Nonexisting t in the database will cause this method to fail.
     * @param t
     * @return true if successed
     */
    public boolean delete(T t);

    /**
     * Search and return all T in the database according to the constrain key.
     * @param key
     * 格式�?属�?�?=�?;属�?�?=�?
     * @return
     * All t that satisfy the constrain in a Set.
     */
    public List<T> select(String key);
}
