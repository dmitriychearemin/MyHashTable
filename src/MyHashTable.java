import java.security.Key;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashTable <K,V> implements Iterable<Node<K,V>> {

    Node<K,V>[] buckets;
    int Cursize ;
    int MaxSize;
    int LastMaxSize;

    public MyHashTable(int capacity) {
        buckets = new Node[capacity];
        MaxSize = capacity;
        LastMaxSize = capacity;
        Cursize = 0;
    }

    V put(K key, V value){
        if(Cursize/MaxSize >= 0.7){
            Recize();
        }
        int index = HashCode(key);
        Node<K,V> newNode = new Node<>(key,value);
        if(buckets[index] == null){
            buckets[index] = newNode;
            Cursize++;
        }
        else{
            Node<K, V> current = buckets[index];
            while (current.next != null && !current.key.equals(key)) {
                current = current.next;
            }
            if (current.key.equals(key)) {
                current.value = value;
            } else {
                current.next = newNode;
            }
        }
        Cursize++;
        return value;
    }

    V get(K key){
        int index = HashCode(key);

        Node<K,V> curnode = buckets[index];

        while(curnode != null){
            if(curnode.key.equals(key)){

                return curnode.value;
            }
            curnode = curnode.next;
        }
        return null;
        /*Node<K, V> node = null;  Неудачный итератор
        int index = HashCode(key);
        HashTableIterator iterator = new HashTableIterator();
        iterator.currentNode = buckets[index];
        if(buckets[index].key == key){
            return buckets[index].value;
        }
        while (iterator.hasNext()) {
            node = iterator.next();
            if(node.value.equals(key)){
                return node.value;
            }
        }
        return null;*/
    }

    V remove(K key){
        int index = HashCode(key);
        V needValue;
        Node<K,V> curnode = buckets[index];
        Node<K,V> prewnode = buckets[index];
        /*if(buckets[index].key.equals(key)){ // Если нужный ключ лежит в голове списка
            needValue = buckets[index].value;
            if(buckets[index].next != null){
                buckets[index] = buckets[index].next;
            }
            else {
                buckets[index] = null;
            }
            Cursize -=1;
            return needValue;
        }*/
            while(curnode != null){
                if(curnode.key.equals(key)){
                    needValue = curnode.value;
                    if(curnode == buckets[index]){
                        buckets[index] = buckets[index].next;
                        return needValue;
                    }
                    else if(curnode.next != null){
                        prewnode.next = curnode.next;
                        return needValue;
                    }

                    else if(curnode.next == null){
                        prewnode.next = null;
                        return needValue;
                    }

                }
                prewnode = curnode;
                curnode = curnode.next;
            }
        return null;
    }

    @Override
    public Iterator<Node<K, V>> iterator() {
        return new HashTableIterator();
    }

    private class HashTableIterator implements Iterator<Node<K, V>> {
        private int currentBucket = 0;
        private Node<K, V> currentNode = null;

        @Override
        public boolean hasNext() {
            if (currentNode != null && currentNode.next != null) {
                return true; // Есть следующий узел в текущем списке
            }

            while (currentBucket < buckets.length) {
                currentNode = buckets[currentBucket++];
                if (currentNode != null) {
                    return true; // Найден первый узел в следующем бакете
                }
            }

            return false; // больше нет узлов
        }

        @Override
        public Node<K, V> next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the hash table");
            }

            if (currentNode != null) {
                Node<K, V> returnNode = currentNode;
                currentNode = currentNode.next;
                return returnNode; //  текущий узел
            }

            throw new IllegalStateException("Iterator is in an invalid state.");
        }
    }

    int HashCode(K key){
        int HashIndex =0;
        String str = key.toString();
        float p =0.4f, mod = 1;
        float summChars = 0;
        char[] charArray = str.toCharArray();
        for(int i=0; i<charArray.length;i++){
            summChars += (int)charArray[i];
        }
        HashIndex = (int)((p * summChars % mod) * MaxSize);

        return HashIndex;
    }

    public int size(){
        return Cursize;
    }

    public boolean isEmpty(){
        if(buckets.length ==0){
            return true;
        }
        return false;
    }


    void Recize(){
        LastMaxSize = MaxSize;
        if(MaxSize <=50){
            MaxSize *=2;
        }
        else if(MaxSize <= 150){
            MaxSize = MaxSize + (int)(MaxSize*0.3);
        }
        else{
            MaxSize = MaxSize + (int)(MaxSize*0.1);
        }
        Node<K,V>[] newBuckets = new Node[MaxSize];

        for (var bucket : buckets) {
            while (bucket != null) {
                int newIndex = HashCode(bucket.key);
                Node<K,V> nextbucket = bucket.next;
                bucket.next = newBuckets[newIndex];
                newBuckets[newIndex] = bucket;
                bucket = nextbucket;
            }
        }
        buckets = newBuckets;

    }

}
