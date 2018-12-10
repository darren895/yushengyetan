package com.da.jubensha.aop;

import com.da.jubensha.repository.SequenceRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.data.mongodb.core.mapping.event.BeforeSaveEvent;
import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import java.lang.reflect.Field;

@Component
public class IdAop extends AbstractMongoEventListener {

    @Autowired
    private SequenceRepository sequenceRepository;

    @Override
    public void onBeforeConvert(BeforeConvertEvent event) {
        Object param = event.getSource();
        Field[] fields = param.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(GeneratedValue.class)){
                field.setAccessible(true);
                try {
                    Object value = field.get(param);
                    if (value == null || value.equals(0)){
                        Long id = sequenceRepository.getId(param.getClass().getName());
                        if(value instanceof Integer){
                            field.set(param, id.intValue());
                        }else if(value instanceof Long){
                            field.set(param, id);
                        }
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
