package ru.progwards.java2.lessons.synchro;


import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

// реализация для работы с многопоточностью (задача 3.1)
public class HeapForThreads {
    class Mark {
        private int sizeBlock;
        private Integer startMark;

        public Mark(int sizeBlock) {
            this.sizeBlock = sizeBlock;
            startMark = null;
        }

        void setStartMark (Integer startMark) {
            this.startMark = startMark;
        }

        void setSizeBlock (int sizeBlock) {
            this.sizeBlock = sizeBlock;
        }
    }

    private byte[] bytes;
    ConcurrentSkipListMap<Integer, ConcurrentSkipListSet<Integer>> freeBlock = new ConcurrentSkipListMap<>();      // количество/множество указателей
    ConcurrentSkipListMap<Integer, Mark> markMap = new ConcurrentSkipListMap<>();                    // указатель/размер блока и свободен ли
    HashMap<Integer, LinkedList<Integer>> codeMark = new HashMap<>();    // перекодированные указатели
    TreeMap<Integer, Integer> freeMarks = new TreeMap<>();
    Lock lock = new ReentrantLock();

    HeapForThreads(int maxHeapSize, int threads) {
        ConcurrentSkipListSet<Integer> tempTree;
        int tempInt = maxHeapSize;
        int mark = 0;
        bytes = new byte[maxHeapSize];
        for (int i =0; i<threads+1; i++) {
            if (i==0)
                freeBlock.put(tempInt/2, new ConcurrentSkipListSet<>(Set.of(0)));
            else if (i != threads-1)
                freeBlock.put(tempInt/2, new ConcurrentSkipListSet<>(Set.of(mark-1)));
            else {
                tempTree = freeBlock.putIfAbsent(tempInt, new ConcurrentSkipListSet<>(Set.of(mark-1)));
                if (tempTree != null) {
                    tempTree.add(tempInt);
                    freeBlock.put(tempInt, tempTree);
                }
            }
            tempInt = tempInt - tempInt/2;
            mark+= tempInt;
        }
    }

    public int malloc(int size) {
        Integer markBlock;
        synchronized (this) {
            Integer sizeBlock = freeBlock.ceilingKey(size);                // находим подходящий блок
            if (sizeBlock == null) {
                compact();
                sizeBlock = freeBlock.ceilingKey(size);
                if (sizeBlock == null) {
                    throw new OutOfMemoryException(size);
                }
            }
            markBlock = freeBlock.get(sizeBlock).pollFirst();          // получаем его указатель
            if (freeBlock.get(sizeBlock).isEmpty())                     // если дальше указателей на блок такого размера нет - удаляем
                freeBlock.remove(sizeBlock);
            if (size != sizeBlock)                                        // если блок больше заданного
                addFreeBlock(sizeBlock - size, markBlock + size); // добавляем остаток в свободные блоки
        }
        markMap.put(markBlock, new Mark(size));                     // в указатель записанных
        return markBlock;
    }

    public void free(int ptr) {
        if (codeMark.containsKey(ptr)) {                           // проверка - не менялся ли указатель
            int tempPtr = codeMark.get(ptr).poll();
            if (codeMark.get(ptr).isEmpty())
                codeMark.remove(ptr);
            ptr = tempPtr;
        } else if (!markMap.containsKey(ptr))
            throw new InvalidPointerException(ptr);
        int sizeFree = markMap.get(ptr).sizeBlock;
        markMap.remove(ptr);
        addFreeBlock(sizeFree, ptr);
    }

    public void addFreeBlock (int sizeB, int mark) {
        ConcurrentSkipListSet<Integer> tempSet = new ConcurrentSkipListSet<>();
        tempSet.add(mark);
        synchronized (this) {
            tempSet = freeBlock.putIfAbsent(sizeB, tempSet);
            if (tempSet!= null) {
                tempSet.add(mark);
                freeBlock.put(sizeB, tempSet);
            }
        }
    }

    public void defrag() {
        for (Map.Entry<Integer, ConcurrentSkipListSet<Integer>> entry: freeBlock.entrySet())
            while (!entry.getValue().isEmpty())
                freeMarks.put(entry.getValue().pollFirst(),entry.getKey());
        TreeMap<Integer, Integer> freeMarksTemp = new TreeMap<>();
        int prevM = 0, prevS = 0;
        for (Map.Entry<Integer, Integer> entry: freeMarks.entrySet()){
            if (entry.getKey() == prevS && prevS!=0) {
                freeMarksTemp.put(prevM, freeMarksTemp.get(prevM) + entry.getValue());
                prevS += entry.getValue();
            } else {
                prevM = entry.getKey();
                freeMarksTemp.put(prevM, entry.getValue());
                prevS = entry.getKey()+entry.getValue();
            }
        }
        freeMarks.clear();
        freeBlock.clear();
        for (Map.Entry<Integer, Integer> entry: freeMarksTemp.entrySet())
            addFreeBlock(entry.getValue(),entry.getKey());
    }

    public void compact() {
        int nowMark = 0;
        TreeMap<Integer, Mark> markMapTemp = new TreeMap<>();
        for (Map.Entry<Integer, Mark> entry: markMap.entrySet()){
            markMapTemp.put(nowMark, entry.getValue());
            if (nowMark != entry.getKey() && entry.getValue().startMark == null) {  // если первый раз мняется указатель
                if (!codeMark.containsKey(entry.getKey())) {
                    LinkedList<Integer> sameMark = new LinkedList<>();
                    sameMark.push(nowMark);
                    codeMark.put(entry.getKey(), sameMark);                         // первая перекодировка  - старый/новый
                } else {
                    codeMark.get(entry.getKey()).push(nowMark);
                }
                markMapTemp.get(nowMark).setStartMark(entry.getKey());
            }
            else if (nowMark != entry.getKey()) {
                codeMark.get(entry.getValue().startMark).remove(entry.getKey());     // повторная перекодировка
                codeMark.get(entry.getValue().startMark).push(nowMark);
            }
            nowMark += entry.getValue().sizeBlock;
        }
        markMap.clear();
        markMap.putAll(markMapTemp);
        freeBlock.clear();
        addFreeBlock(bytes.length-nowMark, nowMark);
    }

    public void getBytes(int ptr, byte[] bytes) {
        //System.arraycopy(this.bytes, ptr, bytes, 0, size);
        for (int i = 0; i < markMap.get(ptr).sizeBlock; i++) {
            bytes[i] = this.bytes[i+ptr];
        }
    }

    public void setBytes(int ptr, byte[] bytes) {
        //System.arraycopy(bytes, 0, this.bytes, ptr, size);
        for (int i = 0; i < markMap.get(ptr).sizeBlock; i++) {
            this.bytes[i+ptr] = bytes[i];
        }
    }
}
