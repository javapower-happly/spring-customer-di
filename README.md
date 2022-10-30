 这个自定义的依赖注入框架,是我根据spring的扩展
  功能是: 对一个接口类型的属性,可以注入多个值或者一个值,当通过依赖注入完成后,调用接口类型的方法时,则会默认会调用所有实现这个接口类的方法或者调用指定的某个实现类的方法
  
spring完成收集注解和对注解的值进行依赖注入是又两个PostProcessor来完成,分别为InstantiationAwareBeanPostProcessor,MergedBeanDefinitionPostProcessor接口，
我们知道Autowired注解是通过AutowiredAnnotationBeanPostProcessor这个注解来完成解析和依赖注入的,那我们依葫芦画瓢,将该类的所有代码全部复制过来,新建一个新的自定义di的注解类,
将构造方法的Autowired注解更换成自己定义的注解
   public DiAnnotationBeanPostProcessor() {
        this.diAnnotationTypes.add(DiAnnotation.class); //自定义注解信息
        try {
        this.diAnnotationTypes.add((Class<? extends Annotation>)
        ClassUtils.forName("javax.inject.Inject", DiAnnotationBeanPostProcessor.class.getClassLoader()));
        logger.trace("JSR-330 'javax.inject.Inject' annotation found and supported for autowiring");
        } catch (ClassNotFoundException ex) {
        // JSR-330 API not available - simply skip.
        }
    }
    依赖注入的时候的字段值,设置成自己写的代理类
   @Override
    protected void inject(Object bean, @Nullable String beanName, @Nullable PropertyValues pvs) throws Throwable {
        Field field = (Field) this.member;
        Object value = null;
        if (this.cached) {
            value = resolvedCachedArgument(beanName, this.cachedFieldValue);
        }
        if (field.getType().isInterface()) {
            value = DiProxy.proxyInstance(field, context); //自定义代理实现
        }
        if (value != null) {
            ReflectionUtils.makeAccessible(field);
            field.set(bean, value);
        }
    }
   以上依赖注入的地方,就两行代码,你说简单不简单

