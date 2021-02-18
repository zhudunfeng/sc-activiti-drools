# sc-activiti-drools
activiti整合drools案例

主要使用的技术：spring+springboot2.0+activiti6.0+drools7.0+mybatis_plus4
<br />
本项目使用了两个案例：（1）请假案例（2）打折案例
<br />
注意事项：
<br />
activiti配置,为其(ActivitiConfig)加入规则文件的部署实现类
```java
   //启用自定义的规则部署器
    List<Deployer> deployers = new ArrayList<>();
    deployers.add(new RulesDeployer());
    configuration.setCustomPostDeployers(deployers);
```
Drools配置类(DroolsConfig)
```java
    package com.sc.config;
    import org.kie.api.KieBase;
    import org.kie.api.KieServices;
    import org.kie.api.builder.KieBuilder;
    import org.kie.api.builder.KieFileSystem;
    import org.kie.api.builder.KieRepository;
    import org.kie.api.runtime.KieContainer;
    import org.kie.api.runtime.KieSession;
    import org.kie.api.runtime.KieSessionConfiguration;
    import org.kie.internal.io.ResourceFactory;
    import org.kie.spring.KModuleBeanFactoryPostProcessor;
    import org.kie.spring.annotations.KModuleAnnotationPostProcessor;
    import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
    import org.springframework.core.io.support.ResourcePatternResolver;
    import org.springframework.core.io.Resource;
    import java.io.IOException;
    /**
     * 规则引擎配置类
     */
    @Configuration
    public class DroolsConfig {
        //指定规则文件存放的目录
        private static final String RULES_PATH = "rules/";
        private final KieServices kieServices = KieServices.Factory.get();
        @Bean
        @ConditionalOnMissingBean
        public KieFileSystem kieFileSystem() throws IOException {
            KieFileSystem kieFileSystem = kieServices.newKieFileSystem();
            ResourcePatternResolver resourcePatternResolver = 
                new PathMatchingResourcePatternResolver();
            Resource[] files = 
                resourcePatternResolver.getResources("classpath*:" + RULES_PATH + "*.*");
            String path = null;
            for (Resource file : files) {
                path = RULES_PATH + file.getFilename();
                kieFileSystem.write(ResourceFactory.newClassPathResource(path, "UTF-8"));
            }
            return kieFileSystem;
        }
        @Bean
        @ConditionalOnMissingBean
        public KieContainer kieContainer() throws IOException {
            KieRepository kieRepository = kieServices.getRepository();
            kieRepository.addKieModule(kieRepository::getDefaultReleaseId);
            KieBuilder kieBuilder = kieServices.newKieBuilder(kieFileSystem());
            kieBuilder.buildAll();
            return kieServices.newKieContainer(kieRepository.getDefaultReleaseId());
        }
        @Bean
        @ConditionalOnMissingBean
        public KieBase kieBase() throws IOException {
            //System.setProperty("drools.dateformat","yyyy-MM-dd HH:mm");
            return kieContainer().getKieBase();
        }
    
        @Bean
        @ConditionalOnMissingBean
        public KModuleBeanFactoryPostProcessor kiePostProcessor() {
            return new KModuleBeanFactoryPostProcessor();
        }
    
    
    }
```
<br />
ActivitiAndDroolsTest主要是请假案例
<br />
SaleDemoTest主要是打折案例
<br />
DroolsDemoTest主要是Drools原生api的使用
<br />

[activiti的businessTask与drools相关的技术博客](https://www.cnblogs.com/dengjiahai/p/7051288.html)
<br />
[推荐的学习笔记网站](https://happysnail.cn/#/./docs/Drools)


