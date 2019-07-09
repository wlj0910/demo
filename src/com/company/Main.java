package com.company;
import java.util.*;

public class Main {
    public static void demoString(){
        String str="Hello world!";
        System.out.println(str.replaceAll("Hello","Hi"));
    }
    public static void demoList(){
        //可重复。
        List<String> list=new ArrayList<String>();
        for(int i=0;i<4;i++){
            list.add(String.valueOf(i));
            list.add(String.valueOf(i));
        }
        System.out.println(list);
        List<String> list1=new ArrayList<String>();
        for(int j=0;j<3;j++){
            list1.add(String.valueOf(j*j));
        }
        list.addAll(list1);
        list.addAll(Arrays.asList(new String[] {"A","B","C"}));
        //list.remove(0);
        list.remove(String.valueOf(0));
        System.out.println(list);
        //Collections.sort(list);
        Collections.sort(list, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //return o1.compareTo(o2);//从小到大排序
                return o2.compareTo(o1);//从大到小排序
            }
        });
        System.out.println(list);
        Collections.reverse(list);
        System.out.println(list);
        for(String str:list){
            System.out.print(str+",  ");
        }
        System.out.println();
        for(int i=0;i<list.size();i++){
            System.out.print(list.get(i)+",  ");
        }
        System.out.println();
        //int[] array=new int[]{1,2,4};
        //int[] array={1,2,3};
        int[] array=new int[4];
        System.out.println(array[2]);
    }
    public static void demoMapTable(){
        //不重复。
        Map<String,String>  map=new HashMap<String,String>();
        for(int i=0;i<4;i++){
            map.put(String.valueOf(i),String.valueOf(i*i));
            map.put(String.valueOf(i),String.valueOf(i*i));
        }
        for(Map.Entry<String,String> entry:map.entrySet()){
            System.out.print(entry.getKey()+","+entry.getValue()+"; ");
        }
        System.out.println();
        System.out.println(map);
        System.out.println(map.values());
        System.out.println(map.keySet());
        System.out.println(map.get("2"));
        System.out.println(map.containsKey("3"));
        map.remove("3");
        System.out.println(map.containsKey("3"));
    }

    public static void demoSet(){
        //不重复。
        Set<String> set =new HashSet<String>();
        for(int i=0;i<3;i++){
            set.add(String.valueOf(i));
            set.add(String.valueOf(i+5));
        }
        set.add(String.valueOf(4));
        System.out.println(set);
        System.out.println(set.size());
        set.remove(String.valueOf(1));
        System.out.println(set);
        System.out.println(set.contains(String.valueOf(1)));
        System.out.println(set.size());
        set.addAll(Arrays.asList(new String[]{"A","B","C"}));
        System.out.println(set);
        for(String value:set){
            System.out.print(value+", ");
        }
    }
    public static void demoException(){
        try{
            int k=2;
            if(k==2){
                throw new Exception("我是故意的！");
            }
            k=k/0;
        }catch(Exception e){
            System.out.println(e.getMessage());
        }finally{
            System.out.println("执行了！");
        }
    }
    public static void demoOOP(){
        Animal animal=new Animal("panda",5);
        animal.say();
        Human human=new Human("Li",5,"China");
        human.say();
    }
    public static void main(String[] args) {
	// write your code here
        //demoString();
        //demoMapTable();
        //demoSet();
        //demoList();
        //demoException();
        demoOOP();
    }
}
