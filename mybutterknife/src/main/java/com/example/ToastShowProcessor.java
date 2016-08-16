package com.example;


import com.google.auto.common.SuperficialValidation;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;

import java.io.IOException;
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
import javax.lang.model.element.Modifier;
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
//@SupportedAnnotationTypes({ "com.example.annotation.classAnnotation.MyToastShowAnnotation" })
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ToastShowProcessor extends AbstractProcessor {
    private static final String STRING_TYPE = "java.lang.String";
    public static final String subClassName = "$$yuyang";
    private static final ClassName VIEW_BINDER = ClassName.get("com.example", "ViewBinder");
    private static final ClassName LOG = ClassName.get("android.util", "Log");
    private static final ClassName TOAST = ClassName.get("android.widget", "Toast");

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
        // 处理MyToastShowAnnotation
        for (Element element : env.getElementsAnnotatedWith(MyToastShowAnnotation.class)) {
            //颜值元素有效性
            if (!_verifyElement(element, MyToastShowAnnotation.class)){
                return false;
            }

            //注解中的方法返回值
            String message = element.getAnnotation(MyToastShowAnnotation.class).message();

            TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
            // 获取元素的完全限定名称：com.butterknife.MainActivity
            String targetType = enclosingElement.getQualifiedName().toString();
            // 获取元素所在包名：com.butterknife
            String classPackage = elementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
            // 获取要生成的Class的名称：MainActivity$$yuyang
            int packageLen = classPackage.length() + 1;
            String className = targetType.substring(packageLen).replace('.', '$') + subClassName;
            // 生成Class的完全限定名称：com.butterknife.MainActivity$$yuyang
            String classFqcn = classPackage + "." + className;

            System.out.println("编译时注解－－className:"+className+"/n"+"classPackage:"+classPackage);
            // 构建一个类
            TypeSpec.Builder resultClassBuilder = TypeSpec.classBuilder(className)
                    .addModifiers(Modifier.PUBLIC)
                    .addTypeVariable(TypeVariableName.get("T", ClassName.bestGuess(targetType)));

            // 实现 ViewBinder 接口
            resultClassBuilder.addSuperinterface(ParameterizedTypeName.get(VIEW_BINDER, TypeVariableName.get("T")));

            //　定义一个方法，其实就是实现 ViewBinder 的 doSomething 方法
            MethodSpec.Builder method1 = MethodSpec.methodBuilder("doSomething")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(TypeVariableName.get("T"), "target", Modifier.FINAL);
            method1.addStatement("$L.e(\"－－－编译时注解－－－\", \"$L\")", LOG, message);

            //　定义一个方法，其实就是实现 ViewBinder 的 doSomething 方法
            MethodSpec.Builder method2 = MethodSpec.methodBuilder("showToast")
                    .addAnnotation(Override.class)
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(TypeVariableName.get("T"), "target", Modifier.FINAL);
            method2.addStatement("$L.makeText(target, \"$L\", 1).show()", TOAST, message);

            //添加方法
            resultClassBuilder.addMethod(method1.build());
            resultClassBuilder.addMethod(method2.build());

            // 构建Java文件
            JavaFile javaFile = JavaFile.builder(classPackage, resultClassBuilder.build())
                    .addFileComment("Generated code from Butter Knife. Do not modify!")
                    .build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                _error(enclosingElement, "Unable to write view binder for type %s: %s", enclosingElement,
                        e.getMessage());
            }
        }
        return true;
    }


    //android平台建议使用重写这两个方法，而不是用上面的注解方法
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> set = new HashSet<>();
        set.add(MyToastShowAnnotation.class.getCanonicalName());
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
        if (!SuperficialValidation.validateElement(element)) {
            return false;
        }
        System.out.println("查找错误点－－－－－－－－－－－－－－－－－－－1");
        // 获取最里层的外围元素
        TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
        System.out.println("查找错误点－－－－－－－－－－－－－－－－－－－2");

        // 检测使用该注解的元素类型是否正确
        if (annotationClass == MyToastShowAnnotation.class) {
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

    /**
     * 输出错误信息
     * @param element
     * @param message
     * @param args
     */
    private void _error(Element element, String message, Object... args) {
        if (args.length > 0) {
            message = String.format(message, args);
        }
        processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, message, element);
    }
}
