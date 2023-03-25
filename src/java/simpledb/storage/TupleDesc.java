package simpledb.storage;

import simpledb.common.Type;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * TupleDesc describes the schema of a tuple.
 */
public class TupleDesc implements Serializable {

    /**
     * 描述列表
     */
    private List<TDItem> descList;

    /**
     * A help class to facilitate organizing the information of each field
     */
    public static class TDItem implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * The type of the field
         */
        public final Type fieldType;

        /**
         * The name of the field
         */
        public final String fieldName;

        public TDItem(Type t, String n) {
            this.fieldName = n;
            this.fieldType = t;
        }

        public String toString() {
            return fieldName + "(" + fieldType + ")";
        }

        public boolean equals(Object o){
            if(o instanceof TDItem){
                TDItem tdItem = (TDItem) o;
                if(tdItem.fieldType.equals(fieldType) && tdItem.fieldName.equals(fieldName)){
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * @return An iterator which iterates over all the field TDItems
     *         that are included in this TupleDesc
     */
    public Iterator<TDItem> iterator() {
        // TODO: some code goes here
        return null;
    }

    private static final long serialVersionUID = 1L;

    /**
     * Create a new TupleDesc with typeAr.length fields with fields of the
     * specified types, with associated named fields.
     *
     * @param typeAr  array specifying the number of and types of fields in this
     *                TupleDesc. It must contain at least one entry.
     * @param fieldAr array specifying the names of the fields. Note that names may
     *                be null.
     */
    public TupleDesc(Type[] typeAr, String[] fieldAr) {
        descList = new ArrayList<>();
        for (int i = 0; i < typeAr.length; i++) {
            TDItem tdItem = new TDItem(typeAr[i],fieldAr[i]);
            descList.add(tdItem);
        }
    }

    /**
     * Constructor. Create a new tuple desc with typeAr.length fields with
     * fields of the specified types, with anonymous (unnamed) fields.
     *
     * @param typeAr array specifying the number of and types of fields in this
     *               TupleDesc. It must contain at least one entry.
     */
    public TupleDesc(Type[] typeAr) {
        // TODO: some code goes here
        descList = new ArrayList<>();
        for (Type type : typeAr) {
            TDItem tdItem = new TDItem(type,"");
            descList.add(tdItem);
        }
    }

    /**
     * @return the number of fields in this TupleDesc
     */
    public int numFields() {

        return descList.size();
    }

    /**
     * Gets the (possibly null) field name of the ith field of this TupleDesc.
     *
     * @param i index of the field name to return. It must be a valid index.
     * @return the name of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public String getFieldName(int i) throws NoSuchElementException {
        // TODO: some code goes here
        return null;
    }

    /**
     * Gets the type of the ith field of this TupleDesc.
     *
     * @param i The index of the field to get the type of. It must be a valid
     *          index.
     * @return the type of the ith field
     * @throws NoSuchElementException if i is not a valid field reference.
     */
    public Type getFieldType(int i) throws NoSuchElementException {
        return descList.get(i).fieldType;
    }

    /**
     * Find the index of the field with a given name.
     *
     * @param name name of the field.
     * @return the index of the field that is first to have the given name.
     * @throws NoSuchElementException if no field with a matching name is found.
     */
    public int indexForFieldName(String name) throws NoSuchElementException {
        if(null == name){
            throw new NoSuchElementException();
        }
        for (int i = 0;i < descList.size(); i++) {
            TDItem tdItem = descList.get(i);
            if(name.equals(tdItem.fieldName)){
                return i;
            }
        }
        throw new NoSuchElementException();
    }

    /**
     * @return The size (in bytes) of tuples corresponding to this TupleDesc.
     *         Note that tuples from a given TupleDesc are of a fixed size.
     */
    public int getSize() {
        int size = 0;
        for (TDItem tdItem : descList) {
            Type fieldType = tdItem.fieldType;
            size += fieldType.getLen();
        }
        return size;
    }

    /**
     * Merge two TupleDescs into one, with td1.numFields + td2.numFields fields,
     * with the first td1.numFields coming from td1 and the remaining from td2.
     *
     * @param td1 The TupleDesc with the first fields of the new TupleDesc
     * @param td2 The TupleDesc with the last fields of the TupleDesc
     * @return the new TupleDesc
     */
    public static TupleDesc merge(TupleDesc td1, TupleDesc td2) {
        List<Type> typeList= new ArrayList<>();
        List<String> fieldList= new ArrayList<>();
        for (TDItem tdItem : td1.descList) {
            typeList.add(tdItem.fieldType);
            fieldList.add(tdItem.fieldName);
        }
        for (TDItem tdItem : td2.descList) {
            typeList.add(tdItem.fieldType);
            fieldList.add(tdItem.fieldName);
        }
        TupleDesc tupleDesc = new TupleDesc(typeList.toArray(new Type[0]), fieldList.toArray(new String[0]));
        tupleDesc.descList = new ArrayList<>();
        for (TDItem tdItem : td1.descList) {
            tupleDesc.descList.add(tdItem);
        }
        for (TDItem tdItem : td2.descList) {
            tupleDesc.descList.add(tdItem);
        }
        return tupleDesc;
    }

    /**
     * Compares the specified object with this TupleDesc for equality. Two
     * TupleDescs are considered equal if they have the same number of items
     * and if the i-th type in this TupleDesc is equal to the i-th type in o
     * for every i.
     *
     * @param o the Object to be compared for equality with this TupleDesc.
     * @return true if the object is equal to this TupleDesc.
     */

    public boolean equals(Object o) {
        if(o instanceof TupleDesc){
            TupleDesc tupleDesc = (TupleDesc) o;
            if(tupleDesc.numFields() == descList.size()){
                for(int i = 0; i < descList.size(); i++){
                    if(!descList.get(i).equals(tupleDesc.descList.get(i))){
                        return false;
                    }
                }
            }else{
                return false;
            }
        }else {
            return false;
        }
        return true;
    }

    public int hashCode() {
        // If you want to use TupleDesc as keys for HashMap, implement this so
        // that equal objects have equals hashCode() results
        throw new UnsupportedOperationException("unimplemented");
    }

    /**
     * Returns a String describing this descriptor. It should be of the form
     * "fieldType[0](fieldName[0]), ..., fieldType[M](fieldName[M])", although
     * the exact format does not matter.
     *
     * @return String describing this descriptor.
     */
    public String toString() {
        // TODO: some code goes here
        return "";
    }
}
