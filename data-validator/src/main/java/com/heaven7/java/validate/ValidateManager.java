package com.heaven7.java.validate;

import com.heaven7.java.validate.anno.Validate;
import com.heaven7.java.validate.impl.StringValidator;
import com.heaven7.java.validate.plugin.CommonValidatePlugin;
import com.heaven7.java.visitor.MapPredicateVisitor;
import com.heaven7.java.visitor.collection.KeyValuePair;
import com.heaven7.java.visitor.collection.VisitServices;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ValidateManager {

    private final Map<Class<?>, Validator> mCache = new HashMap<>();
    private final Map<Class, ValidatePlugin> mPlugins = new HashMap<>();
    private final Object mContext;

    public ValidateManager(Object context) {
        this.mContext = context;
    }

    public void registerPlugin(Class<? extends Annotation> clazz, ValidatePlugin plugin){
        mPlugins.put(clazz, plugin);
    }

    public void unregisterPlugin(Class<? extends Annotation> clazz){
        mPlugins.remove(clazz);
    }

    public Object getContext() {
        return mContext;
    }

    public Item validate(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, true);
        return list.isEmpty() ? null : list.get(0);
    }
    public List<Item> validateAll(Object obj){
        List<Item> list = new ArrayList<>();
        getValidateItems(obj, obj.getClass(), list, false);
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
        out:
        while (true){
            ValidatePlugin plugin = resolveAnnotation(cur, arr);
            if(plugin != null){
                Validator validator = getValidator(plugin.getValidatorClass(arr[0]));
                if(!validator.accept(mContext, owner)){
                    out.add(new Item(plugin.getMessage(arr[0]), validator, owner));
                }
                break;
            }
            Field[] fields = cur.getDeclaredFields();
            if(fields != null){
                for (Field f : fields){
                    f.setAccessible(true);
                    plugin = resolveAnnotation(f, arr);
                    if(plugin != null){
                        Validator validator = getValidator(plugin.getValidatorClass(arr[0]));
                        try {
                            Object o = f.get(owner);
                            if(!validator.accept(mContext, o)){
                                out.add(new Item(plugin.getMessage(arr[0]), validator, o));
                                if(breakIfFound){
                                    break out;
                                }
                            }
                        } catch (IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
            cur = cur.getSuperclass();
            if(cur == null || cur == Object.class){
                break;
            }
        }
    }
    private Validator getValidator(Class<?> type){
        Validator validator = mCache.get(type);
        if(validator != null){
            return validator;
        }
        if(Validator.class.isAssignableFrom(type)){
            try {
                validator = (Validator) type.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else {
            validator = new StringValidator();
        }
        mCache.put(type, validator);
        return validator;
    }

    public static class Builder{
        private final ValidateManager vm;

        private Builder(ValidateManager vm) {
            this.vm = vm;
        }
        public Builder plugin(Class<? extends Annotation> clazz, ValidatePlugin plugin){
            vm.registerPlugin(clazz, plugin);
            return this;
        }
        public Builder withDefault(){
            vm.registerPlugin(Validate.class, new CommonValidatePlugin());
            return this;
        }
        public ValidateManager build(){
            return vm;
        }
    }
}
