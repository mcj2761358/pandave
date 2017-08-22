package com.minutch.fox.utils;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by Minutch on 17/8/15.
 */
public class ListUtils {



    public static  <T>List<T> getSubList(List<T> list,int count){
        if(list.size()<=count){
            return list;
        }
        List<T> subList = new ArrayList<T>(count);
        for(int i=0;i<count;i++){
            subList.add(list.get(i));
        }
        return subList;
    }


    public static String listToStr(List<?> list){
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<list.size();i++){
            if(i==list.size()-1){
                sb.append(list.get(i));
            }
            else{
                sb.append(list.get(i)+",");
            }
        }

        return sb.toString();
    }

    /**
     * set转为list
     * @param set
     * @return
     */
    public static <T>List<T> setToList(Set<T> set){
        List<T> list = new ArrayList<T>();
        if(set ==null){
            return list;
        }
        for(T t:set){
            if(t!=null){
                list.add(t);
            }
        }
        return list;
    }

    public static <T>List<T> covertToList(Collection<T> c){
        return covertToList(c, new ConvertInterface<T,T>(){
            @Override
            public T convert(T param) {
                return param;
            }

        });
    }


    public static <R,P>List<R> covertToList(Collection<P> c,ConvertInterface<R,P> convertInterface){
        List<R> list = new ArrayList<R>();
        for(P p :c){
            R r = convertInterface.convert(p);
            if(r!=null){
                list.add(r);
            }
        }
        return list;
    }


    public static <T> boolean  isExist(T value,List<T> list){
        if(list==null){
            return false;
        }
        for(T t : list){
            if(t!=null&&t.equals(value)){
                return true;
            }
        }
        return false;
    }


    public static <R,P> boolean  isExist(R value,List<P> list,ConvertInterface<R,P> convertInterface){
        for(P p : list){
            R r = convertInterface.convert(p);
            if(value!=null&&value.equals(r)){
                return true;
            }
        }
        return false;
    }

    public static <T>T[] toArray(Collection<T> list,Class c){
        if(list == null ){
            return null;
        }
        T[] array = (T[]) Array.newInstance(c, list.size());
        list.toArray(array);
        return array;
    }


    public static <R,P> List<R> convertToList(Collection<P> c,ConvertInterface<R,P> convertInterface){
        List<R> list = new ArrayList<R>();
        if(c==null||c.size()==0||convertInterface==null){
            return list;
        }
        for(P p :c){
            R r = convertInterface.convert(p);
            if(r!=null){
                list.add(r);
            }
        }
        return list;
    }

    public static <K,V> Map<K,V> convertToMapValue(Collection<V> c,ConvertInterface<K,V> convertInterface){
        Map<K,V> map = new LinkedHashMap<K,V>();
        if(c==null||c.size()==0||convertInterface==null){
            return map;
        }
        for(V v :c){
            K k = convertInterface.convert(v);
            if(k!=null){
                map.put(k,v);
            }
        }
        return map;
    }

    public static <V,K> Map<V,K> convertToMapKey(Collection<V> c,ConvertInterface<K,V> convertInterface){
        Map<V,K> map = new LinkedHashMap<V,K>();
        if(c==null||c.size()==0||convertInterface==null){
            return map;
        }
        for(V v :c){
            K k = convertInterface.convert(v);
            if(k!=null){
                map.put(v,k);
            }
        }
        return map;
    }

    public static <T> Map<T,String> convertToHashMap(Collection<T> c){
        return convertToMapKey(c,new ConvertInterface<String,T>(){
            @Override
            public String convert(T param) {
                if(param==null){
                    return null;
                }
                return "1";
            }
        });
    }



    public static boolean isExist(long longValue, long[] list) {
        for(long t : list){
            if(t==longValue){
                return true;
            }
        }
        return false;
    }


    public static <T> boolean equalsSubList(List<T> c1,List<T> c2,int num){
        if(c1==null||c2==null||num==0){
            return false;
        }
        if(c1.size()<num||c2.size()<num){
            return false;
        }
        for(int i=0;i<num;i++){
            T t1 = c1.get(i);
            T t2 = c2.get(i);
            if(t1==null||t2==null){
                return false;
            }
            if(!t1.equals(t2)){
                return false;
            }
        }
        return true;
    }


    /**
     * 判断列表是否为空
     *
     * @param list
     * @return
     */
    public static <V extends Object> boolean isBlank(final List<V> list) {
        return (null == list || list.isEmpty());
    }

    /**
     * 判断列表是否不为空
     *
     * @param list
     * @return
     */
    public static <V extends Object> boolean isNotBlank(final List<V> list) {
        return !isBlank(list);
    }


    public static <T> List<T> getSubList(List<T> list,int index,int size){
        if(ListUtils.isBlank(list)){
            return null;
        }
        int s = list.size()>(index+size)?index+size:list.size();
        return list.subList(index, s);
    }


    public static <T> List<List<T>> getSubDepartList(List<T> list, int maxCountPerDepart){
        if(ListUtils.isBlank(list)){
            return null;
        }
        if(maxCountPerDepart<=0){
            return null;
        }
        List<List<T>> l = new ArrayList<List<T>>();
        int count = list.size()/maxCountPerDepart;
        int mod = list.size()%maxCountPerDepart;
        if(mod>0){
            count++;
        }
        for(int i =0;i<count;i++){
            l.add(getSubList(list,i*maxCountPerDepart,maxCountPerDepart));
        }
        return l;
    }
}
