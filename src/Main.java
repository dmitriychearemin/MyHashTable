public class Main {
    public static void main(String[] args) {

        MyHashTable<String, String> myHashTable = new MyHashTable<>(4);

        myHashTable.put("0","XYZ");
        myHashTable.put("2", "ZYX");
        myHashTable.put("7", "YYX");
        myHashTable.put("1", "GGX");
        myHashTable.put("3", "ppX");

        //System.out.println(myHashTable.HashCode("1")); // 0 у 0 и 2 и 7

        //System.out.println(myHashTable.remove("0"));

       /* System.out.println(myHashTable.get("0"));
        System.out.println(myHashTable.get("2"));
        System.out.println(myHashTable.get("7"));
        System.out.println(myHashTable.get("1"));
        System.out.println(myHashTable.get("3"));*/

        for(var backet: myHashTable){
            System.out.println(backet.key + " " + backet.value + " ");
        }

    }
}