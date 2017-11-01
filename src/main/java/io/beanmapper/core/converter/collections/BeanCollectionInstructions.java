package io.beanmapper.core.converter.collections;

import io.beanmapper.annotations.BeanCollectionUsage;
import io.beanmapper.core.BeanField;

public class BeanCollectionInstructions {

    private Class<?> collectionElementType;

    private BeanCollectionUsage beanCollectionUsage;

    private Class<?> preferredCollectionClass;

    private Boolean flushAfterClear;

    public Class<?> getCollectionElementType() {
        return collectionElementType;
    }

    public void setCollectionElementType(Class<?> collectionElementType) {
        this.collectionElementType = collectionElementType;
    }

    public BeanCollectionUsage getBeanCollectionUsage() {
        return beanCollectionUsage;
    }

    public void setBeanCollectionUsage(BeanCollectionUsage beanCollectionUsage) {
        this.beanCollectionUsage = beanCollectionUsage;
    }

    public Class<?> getPreferredCollectionClass() {
        return preferredCollectionClass;
    }

    public void setPreferredCollectionClass(Class<?> preferredCollectionClass) {
        this.preferredCollectionClass =
                preferredCollectionClass == null || preferredCollectionClass.equals(void.class) ?
                null :
                        preferredCollectionClass;
    }

    public Boolean getFlushAfterClear() {
        return flushAfterClear;
    }

    public void setFlushAfterClear(Boolean flushAfterClear) {
        this.flushAfterClear = flushAfterClear;
    }

    public static BeanCollectionInstructions merge(
            BeanField sourceBeanField,
            BeanField targetBeanField) {

        BeanCollectionInstructions source = sourceBeanField == null ? null : sourceBeanField.getCollectionInstructions();
        BeanCollectionInstructions target = targetBeanField == null ? null : targetBeanField.getCollectionInstructions();

        if (source == null && target == null) {
            return null;
        }
        if (source == null) {
            return determineFinalValues(target, null);
        }
        if (target == null) {
            return determineFinalValues(source, null);
        }

        return determineFinalValues(target, source);
    }

    private static BeanCollectionInstructions determineFinalValues(
            BeanCollectionInstructions target,
            BeanCollectionInstructions source) {
        BeanCollectionInstructions merged = new BeanCollectionInstructions();
        merged.setFlushAfterClear(chooseValue(
                target.getFlushAfterClear(),
                source == null ? null : source.getFlushAfterClear(),
                true));
        merged.setBeanCollectionUsage(chooseValue(
                target.getBeanCollectionUsage(),
                source == null ? null : source.getBeanCollectionUsage(),
                BeanCollectionUsage.CLEAR));
        merged.setCollectionElementType(chooseValue(
                target.getCollectionElementType(),
                source == null ? null : source.getCollectionElementType(),
                null));
        merged.setPreferredCollectionClass(chooseValue(
                target.getPreferredCollectionClass(),
                source == null ? null : source.getPreferredCollectionClass(),
                null));
        return merged;
    }

    private static <C> C chooseValue(C target, C source, C defaultValue) {
        if (target != null) {
            return target;
        }
        if (source != null) {
            return source;
        }
        return defaultValue;
    }

}
