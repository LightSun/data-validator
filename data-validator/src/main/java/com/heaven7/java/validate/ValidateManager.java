package com.heaven7.java.validate;

import com.heaven7.java.validate.anno.Validate;
import com.heaven7.java.validate.anno.ValidateDate;
import com.heaven7.java.validate.validator.StringValidator;
import com.heaven7.java.validate.plugin.CommonValidatePlugin;
import com.heaven7.java.validate.plugin.DateValidatePlugin;
import com.heaven7.java.visitor.MapPredicateVisitor;
import com.heaven7.java.visitor.collection.KeyValuePair;
import com.heaven7.java.visitor.collection.VisitServices;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * validate manager
 * @author heaven7
 */
public final class ValidateManager {

    private static final Comparator<Item> COM_ORDER = new Comparator<Item>() {
        @Override
        public int compare(Item o1, Item o2) {
            return Integer.compare(o1.order, o2.order);
        }
    };

    private final Map<Integer, Validator> mCache = new HashMap<>();
    //anno-plugin
    private final Map<Class, ValidatePlugin> mPlugins = new HashMap<>();
    private final Object mContext;
    private ValueReader mReader = ValueReader.DEFAULT;

    /**
     * create validate manager from context
     * @param context the context
     */
    public ValidateManager(Object context) {
        this.mContext = context;
    }
    /**
     * create builder from context
     * @param context the context
     * @return the builder.
     */
    public static Builder newBuilder(Object context){
        return new Builder(context);
    }
    /**
     * set value reader
     * @param mReader the value reader
     */
    public void setValueReader(ValueReader mReader) {
        this.mReader = mReader;
    }

    /**
     * register the plugin
     * @param clazz the annotation class
     * @param plugin the plugin
     */
    public void registerPlugin(Class<? extends Annotation> clazz, ValidatePlugin plugin){
        mPlugins.put(clazz, plugin);
    }

    /**
     * unregister the plugin
     * @param clazz the annotation class
     */
    public void unregisterPlugin(Class<? extends Annotation> clazz){
        mPlugins.remove(clazz);
    }

    /**
     * get the context
     * @return the context.
     */
    public Object getContext() {
        return mContext;
    }

    /**
     * validate the object until failed and return the failed item.
     * @param obj the object to validate
     * @return the item which is validate failed.
     */
    public Item validate(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, true);
        return list.isEmpty() ? null : list.get(0);
    }
    /**
     * validate the object with sort.
     * @param obj the object to validate
     * @return the item which is validate failed.
     */
    public Item validateWithSort(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, false);
        switch (list.size()){
            case 0:
                return null;
            case 1:
                return list.get(0);
            default:
                Collections.sort(list, COM_ORDER);
                return list.get(0);
        }
    }
    /**
     * validate the object with all failed items
     * @param obj the object to validate
     * @return the items which are all validate failed.
     */
    public List<Item> validateAll(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, false);
        return list;
    }

    /**
     * validate the object with all failed sorted items.
     * @param obj the object to validate
     * @return the items which are all validate failed.
     */
    public List<Item> validateAllWithSort(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, false);
        if(list.size() > 1){
            Collections.sort(list, COM_ORDER);
        }
        return list;
    }

    private ValidatePlugin resolveAnnotation(Class cur, final Annotation[] out){
        out[0] = null;
        KeyValuePair<Class, ValidatePlugin> pair = VisitServices.from(mPlugins)
                .visitForQuery(new MapPredicateVisitor<Class, ValidatePlugin>() {
            @Override
            public Boolean visit(KeyValuePair<Class, ValidatePlugin> pair, Object param) {
                Annotation anno = cur.getAnnotation(pair.getKey());
                if(anno != null){
                    out[0] = anno;
                    return true;
                }
                return false;
            }
        });
        return pair != null ? pair.getValue() : null;
    }
    private ValidatePlugin resolveAnnotation(Field f, final Annotation[] out){
        out[0] = null;
        KeyValuePair<Class, ValidatePlugin> pair = VisitServices.from(mPlugins)
                .visitForQuery(new MapPredicateVisitor<Class, ValidatePlugin>() {
                    @Override
                    public Boolean visit(KeyValuePair<Class, ValidatePlugin> pair, Object param) {
                        Annotation anno = f.getAnnotation(pair.getKey());
                        if(anno != null){
                            out[0] = anno;
                            return true;
                        }
                        return false;
                    }
                });
        return pair != null ? pair.getValue() : null;
    }

    private void getValidateItems(Object owner, Class cur, List<Item> out, boolean breakIfFound){
        Annotation[] arr = new Annotation[1];
        Annotation anno;
        out:
        while (true){
            ValidatePlugin plugin = resolveAnnotation(cur, arr);
            anno = arr[0];
            if(plugin != null){
                Validator validator = getValidator(plugin, anno);
                if(!validator.accept(mContext, owner, anno)){
                    out.add(new Item(plugin.getMessage(anno), validator, owner, plugin.getOrder(anno)));
                }
                if(breakIfFound){
                    break;
                }
            }else {
                Field[] fields = cur.getDeclaredFields();
                if(fields != null){
                    for (Field f : fields){
                        f.setAccessible(true);
                        plugin = resolveAnnotation(f, arr);
                        anno = arr[0];
                        if(plugin != null){
                            Validator validator = getValidator(plugin, anno);
                            try {
                                Object o = mReader.read(mContext, owner, f);
                                if(!validator.accept(mContext, o, anno)){
                                    out.add(new Item(plugin.getMessage(anno), validator, o, plugin.getOrder(anno)));
                                    if(breakIfFound){
                                        break out;
                                    }
                                }
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            }
            cur = cur.getSuperclass();
            if(cur == null || cur.getName().startsWith("android.") || cur.getName().startsWith("java.") || cur == Object.class){
                break;
            }
        }
    }
    private Validator getValidator(ValidatePlugin plugin, Annotation anno){
        Integer key = plugin.getCacheKey(anno);
        Validator validator = null;
        if(key != null){
            validator = mCache.get(key);
        }
        if(validator == null){
            validator = plugin.getValidator(anno);
        }
        if(key != null){
            mCache.put(key, validator);
        }
        return validator;
    }

    public static class Builder{
        private final ValidateManager vm;

        private Builder(Object context) {
            this.vm = new ValidateManager(context);
        }
        public Builder plugin(Class<? extends Annotation> clazz, ValidatePlugin plugin){
            vm.registerPlugin(clazz, plugin);
            return this;
        }
        public Builder withDefaultPlugins(){
            vm.registerPlugin(Validate.class, new CommonValidatePlugin());
            vm.registerPlugin(ValidateDate.class, new DateValidatePlugin());
            return this;
        }
        public Builder valueReader(ValueReader reader){
            vm.setValueReader(reader);
            return this;
        }
        public ValidateManager build(){
            return vm;
        }
    }
}
