package com.example.annotation.classAnnotation;


import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
    SupportedAnnotationTypes 表示这个 Processor 要处理的 Annotation 名字。
    process 函数中参数 annotations 表示待处理的 Annotations，参数 env 表示当前或是之前的运行环境
    process 函数返回值表示这组 annotations 是否被这个 Processor 接受，如果接受后续子的 processor 不会再对这个
        Annotations 进行处理
 */
//@SupportedAnnotationTypes({ "com.example.annotation.classAnnotation.ToastShow" })
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ToastShowProcessor extends AbstractProcessor {
    private static final String STRING_TYPE = "java.lang.String";

    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        //Types是一个用来处理TypeMirror的工具，
        typeUtils = processingEnv.getTypeUtils();
        //Elements是一个用来处理Element的工具
        elementUtils = processingEnv.getElementUtils();
        // Filer是个接口，支持通过注解处理器创建新文件
        filer = processingEnv.getFiler();
        // Messager提供给注解处理器一个报告错误、警告以及提示信息的途径。
        messager = processingEnv.getMessager();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {
        System.out.println("编译时注解－－－－－－－－－－－－－－－－－－－");
        // 保存包含注解元素的目标类，注意是使用注解的外围类，主要用来处理父类继承，例：MainActivity
        Set<TypeElement> erasedTargetNames = new LinkedHashSet<>();
        // TypeElement 使用注解的外围类，BindingClass 对应一个要生成的类
//        Map<TypeElement, BindingClass> targetClassMap = new LinkedHashMap<>();
        /*HashMap<String, String> map = new HashMap<String, String>();
        for (TypeElement te : annotations) {
            for (Element element : env.getElementsAnnotatedWith(te)) {
                ToastShow methodInfo = element.getAnnotation(ToastShow.class);
                map.put(element.getEnclosingElement().toString(), methodInfo.author());
            }
        }*/
        // 处理BindString
        /*for (Element element : env.getElementsAnnotatedWith(ToastShow.class)) {
            //创建方法
            MethodSpec.Builder method = MethodSpec.methodBuilder("bind")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(Object.class, "source");

            // 创建目标类
            TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(method.build())
                    .build();
        }*/
        return true;
    }


    //android平台建议使用重写这两个方法，而不是用上面的注解方法
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(ToastShow.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 验证元素的有效性
     * @param element   注解元素
     * @param annotationClass   注解类
     * @return  有效则返回true，否则false
     */
    private boolean _verifyElement(Element element, Class<? extends Annotation> annotationClass) {
        // 检测元素的有效性
        /*if (!SuperficialValidation.validateElement(element)) {
            return false;
        }*/
        // 获取最里层的外围元素
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();

        // 检测使用该注解的元素类型是否正确
        if (annotationClass == ToastShow.class) {
            if (!STRING_TYPE.equals(element.asType().toString())) {
                _error(messager, element, "@%s field type must be 'String'. (%s.%s)",
                        annotationClass.getSimpleName(), enclosingElement.getQualifiedName(),
                        element.getSimpleName());
                return false;
            }
        }

        // 包含该注解的外围元素种类必须为 Class
        if (enclosingElement.getKind() != ElementKind.CLASS) {
            _error(messager, enclosingElement, "@%s %s may only be contained in classes. (%s.%s)",
                    annotationClass.getSimpleName(), "fields", enclosingElement.getQualifiedName(),
                    element.getSimpleName());
            return false;
        }
        // 判断是否处于错误的包中
        String qualifiedName = enclosingElement.getQualifiedName().toString();
        if (qualifiedName.startsWith("android.")) {
            _error(messager, element, "@%s-annotated class incorrectly in Android framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return false;
        }
        if (qualifiedName.startsWith("java.")) {
            _error(messager, element, "@%s-annotated class incorrectly in Java framework package. (%s)",
                    annotationClass.getSimpleName(), qualifiedName);
            return false;
        }

        return true;
    }

    /**
     * 输出错误信息
     * @param element
     * @param message
     * @param args
     */
    private static void _error(Messager messager, Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        messager.printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
