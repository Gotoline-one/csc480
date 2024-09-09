package csc480.controller;

public abstract class SubController<T> {
    public abstract void clearInfo();
    public abstract void loadInfo(T obj);


}
