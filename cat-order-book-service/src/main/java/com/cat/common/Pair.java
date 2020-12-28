package com.cat.common;

public class Pair<U,V> {
    private final U key;
    private final V value;
    public Pair(U key, V value){
        this.key = key;
        this.value = value;
    }

    public U getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }
}
