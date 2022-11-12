package com.backend.backend;

import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.springframework.test.util.ReflectionTestUtils;

import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.jmx.support.ObjectNameManager.getInstance;

public class EntidadesTest {

    private static final List<String> METHODS_TO_IGNORE = Arrays
            .asList("");

    private static final List<String> CLASSES_TO_IGNORE = Arrays
            .asList("");

    private final Set<Class<? extends Object>> classesTodosOsMetodos;
    private final Set<Class<? extends Enum>> enumerators = new HashSet<>();

    public EntidadesTest() {
        Reflections reflections = new Reflections("com.backend.backend.model", new SubTypesScanner(false));
        classesTodosOsMetodos = reflections.getSubTypesOf(Object.class);

        reflections = new Reflections("com.backend.backend.dto", new SubTypesScanner(false));
        classesTodosOsMetodos.addAll(reflections.getSubTypesOf(Object.class));
    }

    /**
     * Classe para realização dos testes de entidades
     *
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws InvocationTargetException
     * @throws IntrospectionException
     */
    @Test
    public void testarEntidades() throws IllegalAccessException, InstantiationException, InvocationTargetException,
            IntrospectionException, NoSuchMethodException, NoSuchFieldException {
        for (Class classe : classesTodosOsMetodos) {
            if (ignoreClass(classe)) {
                continue;
            }
            Object instancia = getInstance(classe);
            testarTodosConstrutores(classe);
            testarTodosMetodos(classe, instancia);
        }
    }

    private Object getInstance(Class classe)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Constructor constructor = classe.getDeclaredConstructors()[0];
        constructor.setAccessible(Boolean.TRUE);
        Object instancia;
        if (constructor.getParameters().length > 0) {
            Object[] lObject = new Object[constructor.getParameters().length];
            instancia = constructor.newInstance(lObject);
        } else {
            instancia = constructor.newInstance();
        }
        return instancia;
    }

    private boolean ignoreClass(Class classe) {
        if (classe.getDeclaredConstructors().length == 0) {
            return true;
        }
        if (classe.getName()
                .contains("Test")) {
            return true;
        }
        if (classe.getName().endsWith("_")) {
            return true;
        }
        for (String classToIgnore : CLASSES_TO_IGNORE) {
            if (classe.getName()
                    .contains(classToIgnore)) {
                return true;
            }
        }
        return false;
    }

    private void testarTodosConstrutores(Class classe)
            throws IllegalAccessException, InvocationTargetException, InstantiationException {
        for (Constructor constructor : classe.getDeclaredConstructors()) {
            constructor.setAccessible(Boolean.TRUE);
            List parametros = new ArrayList<>();
            for (int i = 0; i < constructor.getParameterCount(); i++) {
                parametros.add(null);
            }
            constructor.newInstance(parametros.toArray());
        }
    }

    private void testarTodosMetodos(Class classe, Object instancia)
            throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, IllegalArgumentException,
            InstantiationException, NoSuchMethodException, SecurityException {
        for (Method method : obterTodosMetodosTestaveis(classe)) {
            List parametros = new ArrayList<>();
            for (Class type : method.getParameterTypes()) {
                if (type.equals(classe)) {
                    setIdField(classe, instancia);
                    parametros.add(instancia);
                } else {
                    if (type.equals(Optional.class)) {
                        parametros.add(Optional.empty());
                        System.out.println();
                    } else {
                        parametros.add(null);
                    }

                }
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                method.setAccessible(true);
                if (isMethodInIgnoreList(method)) {
                    continue;
                }
                if ("hashCode".equals(method.getName())) {
                    return;
                }
                method.invoke(instancia, parametros.toArray());
                if ("equals".equals(method.getName())) {
                    method.invoke(instancia, "");
                    method.invoke(instancia, instancia);
                    method.invoke(instancia, getInstance(classe));

                    equalsWithId(method, instancia, classe, 1L, 1L);
                    equalsWithId(method, instancia, classe, 1L, 2L);
                    equalsWithId(method, instancia, classe, 1L, null);
                    equalsWithId(method, instancia, classe, null, 2L);
                }
            }
        }
    }

    private void equalsWithId(Method method, Object instancia, Class classe, Long p1, Long p2)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
            NoSuchMethodException, SecurityException {
        Object instancia2 = getInstance(classe);
        try {
            ReflectionTestUtils.setField(instancia, "id", p1);
            ReflectionTestUtils.setField(instancia2, "id", p2);
            method.invoke(instancia, instancia2);

        } catch (Exception e) {
        }
    }

    private boolean isMethodInIgnoreList(Method method) {
        for (String ignoredMethod : METHODS_TO_IGNORE) {
            if (method.getName()
                    .contains(ignoredMethod)) {
                return true;
            }
        }

        return false;
    }

    private void setIdField(Class classe, Object instancia) throws IllegalAccessException {
        for (Field field : classe.getDeclaredFields()) {
            if (field.getDeclaredAnnotation(Id.class) != null) {
                field.setAccessible(Boolean.TRUE);
                field.set(instancia, 1L);
            }
        }
    }

    private List<Method> obterTodosMetodosTestaveis(Class pClasse) {
        List<Method> lMetodos = new ArrayList<>();
        lMetodos.addAll(Arrays.asList(pClasse.getDeclaredMethods()));
        if (!Arrays.asList(Object.class, Exception.class, RuntimeException.class)
                .contains(pClasse.getSuperclass())) {
            lMetodos.addAll(obterTodosMetodosTestaveis(pClasse.getSuperclass()));
        }
        return lMetodos.stream()
                .filter(method -> !method.isSynthetic())
                .collect(Collectors.toList());
    }
}
