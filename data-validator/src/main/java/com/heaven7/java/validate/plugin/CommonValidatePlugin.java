package com.heaven7.java.validate.plugin;

import com.heaven7.java.validate.Validator;
import com.heaven7.java.validate.anno.Validate;
import com.heaven7.java.validate.validator.StringValidator;

import java.lang.annotation.Annotation;

public class CommonValidatePlugin extends AbstractValidatePlugin {
    @Override
    public String getMessage(Annotation anno) {
        Validate validate = (Validate) anno;
        return validate.value();
    }

    @Override
    public Validator getValidator(Annotation anno) {
        Validate validate = (Validate) anno;
        if(Validator.class == validate.validator()){
            return new StringValidator();
        }
        try {
            return validate.validator().newInstance();
        } catch (InstantiationException e) {
           throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Integer getCacheKey(Annotation anno) {
        Validate validate = (Validate) anno;
        if(Validator.class == validate.validator()){
           return StringValidator.class.getName().hashCode();
        }
        return validate.validator().getName().hashCode();
    }

}
