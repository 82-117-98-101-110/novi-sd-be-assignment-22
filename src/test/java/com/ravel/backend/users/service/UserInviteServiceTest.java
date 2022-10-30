package com.ravel.backend.users.service;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.organization.service.OrganizationUserRoleService;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.model.UserInvite;
import com.ravel.backend.users.repository.UserInviteRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(classes = {UserInviteService.class, EnvironmentProperties.class})
@ActiveProfiles({"h2"})
@ExtendWith(SpringExtension.class)
class UserInviteServiceTest {
    @MockBean
    private EmailService emailService;

    @MockBean
    private OrganizationUserRoleService organizationUserRoleService;

    @MockBean
    private UserInviteRepository userInviteRepository;

    @Autowired
    private UserInviteService userInviteService;

    /**
     * Method under test: {@link UserInviteService#userInviteList()}
     */
    @Test
    void testUserInviteList() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.users.service.UserInviteService: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        ArrayList<UserInvite> userInviteList = new ArrayList<>();
        when(this.userInviteRepository.findAll()).thenReturn(userInviteList);

        // Act
        List<UserInvite> actualUserInviteListResult = this.userInviteService.userInviteList();

        // Assert
        assertSame(userInviteList, actualUserInviteListResult);
        assertTrue(actualUserInviteListResult.isEmpty());
        verify(this.userInviteRepository).findAll();
    }

    /**
     * Method under test: {@link UserInviteService#userInviteList()}
     */
    @Test
    void testUserInviteList2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.users.service.UserInviteService: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        when(this.userInviteRepository.findAll()).thenThrow(new NotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.userInviteService.userInviteList());
        verify(this.userInviteRepository).findAll();
    }

    /**
     * Method under test: {@link UserInviteService#newUserInviteForOrganization(User, UUID, String)}
     */
    @Test
    void testNewUserInviteForOrganization() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.users.service.UserInviteService: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UserInvite userInvite = new UserInvite();
        userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setEmail("jane.doe@example.org");
        userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setId(123L);
        userInvite.setOrganizationUUID(UUID.randomUUID());
        userInvite.setToken("ABC123");
        userInvite.setUserUUID(UUID.randomUUID());
        when(this.userInviteRepository.save((UserInvite) any())).thenReturn(userInvite);
        doNothing().when(this.emailService)
                .inviteNewUserToOrganizationEmail((String) any(), (String) any(), (String) any());

        User user = new User();
        user.setActive((Boolean) true);
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setAvatarUrl("https://example.org/example");
        user.setAvatarUrlFullBody("https://example.org/example");
        user.setCreated_at(null);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setNotLocked((Boolean) true);
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setReceivers(new HashSet<>());
        user.setRole("Role");
        user.setSenders(new HashSet<>());
        user.setUpdatedAt(null);
        user.setUserUUID(UUID.randomUUID());

        // Act
        this.userInviteService.newUserInviteForOrganization(user, UUID.randomUUID(), "Organization Name");

        // Assert
        verify(this.userInviteRepository).save((UserInvite) any());
        verify(this.emailService).inviteNewUserToOrganizationEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link UserInviteService#newUserInviteForOrganization(User, UUID, String)}
     */
    @Test
    void testNewUserInviteForOrganization2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.users.service.UserInviteService: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933. (com.ravel.backend.users.service.UserInviteService$$EnhancerBySpringCGLIB$$447e933 is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UserInvite userInvite = new UserInvite();
        userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setEmail("jane.doe@example.org");
        userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setId(123L);
        userInvite.setOrganizationUUID(UUID.randomUUID());
        userInvite.setToken("ABC123");
        userInvite.setUserUUID(UUID.randomUUID());
        when(this.userInviteRepository.save((UserInvite) any())).thenReturn(userInvite);
        doThrow(new NotFoundException("An error occurred")).when(this.emailService)
                .inviteNewUserToOrganizationEmail((String) any(), (String) any(), (String) any());

        User user = new User();
        user.setActive((Boolean) true);
        user.setActive(true);
        user.setAuthorities(new String[]{"JaneDoe"});
        user.setAvatarUrl("https://example.org/example");
        user.setAvatarUrlFullBody("https://example.org/example");
        user.setCreated_at(null);
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setNotLocked((Boolean) true);
        user.setNotLocked(true);
        user.setPassword("iloveyou");
        user.setProfileImageUrl("https://example.org/example");
        user.setReceivers(new HashSet<>());
        user.setRole("Role");
        user.setSenders(new HashSet<>());
        user.setUpdatedAt(null);
        user.setUserUUID(UUID.randomUUID());

        // Act and Assert
        assertThrows(NotFoundException.class,
                () -> this.userInviteService.newUserInviteForOrganization(user, UUID.randomUUID(), "Organization Name"));
        verify(this.userInviteRepository).save((UserInvite) any());
        verify(this.emailService).inviteNewUserToOrganizationEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link UserInviteService#confirmToken(String)}
     */
    @Test
    void testConfirmToken() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.appAuth.service.AppRoleServiceImpl: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UserInvite userInvite = new UserInvite();
        userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setEmail("jane.doe@example.org");
        userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setId(123L);
        userInvite.setOrganizationUUID(UUID.randomUUID());
        userInvite.setToken("ABC123");
        userInvite.setUserUUID(UUID.randomUUID());
        Optional<UserInvite> ofResult = Optional.of(userInvite);
        when(this.userInviteRepository.findByToken((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.userInviteService.confirmToken("ABC123"));
        verify(this.userInviteRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link UserInviteService#confirmToken(String)}
     */
    @Test
    void testConfirmToken2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.appAuth.service.AppRoleServiceImpl: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UserInvite userInvite = mock(UserInvite.class);
        when(userInvite.getConfirmedAt()).thenReturn(null);
        when(userInvite.getOrganizationUUID()).thenReturn(UUID.randomUUID());
        UUID randomUUIDResult = UUID.randomUUID();
        when(userInvite.getUserUUID()).thenReturn(randomUUIDResult);
        doNothing().when(userInvite).setConfirmedAt((LocalDateTime) any());
        doNothing().when(userInvite).setCreatedAt((LocalDateTime) any());
        doNothing().when(userInvite).setEmail((String) any());
        doNothing().when(userInvite).setExpiresAt((LocalDateTime) any());
        doNothing().when(userInvite).setId((Long) any());
        doNothing().when(userInvite).setOrganizationUUID((UUID) any());
        doNothing().when(userInvite).setToken((String) any());
        doNothing().when(userInvite).setUserUUID((UUID) any());
        userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setEmail("jane.doe@example.org");
        userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setId(123L);
        userInvite.setOrganizationUUID(UUID.randomUUID());
        userInvite.setToken("ABC123");
        userInvite.setUserUUID(UUID.randomUUID());
        Optional<UserInvite> ofResult = Optional.of(userInvite);
        when(this.userInviteRepository.updateConfirmedAt((String) any(), (LocalDateTime) any())).thenReturn(1);
        when(this.userInviteRepository.findByToken((String) any())).thenReturn(ofResult);
        doNothing().when(this.organizationUserRoleService).setNewInvitedUserActive((UUID) any(), (UUID) any());

        // Act and Assert
        assertSame(randomUUIDResult, this.userInviteService.confirmToken("ABC123"));
        verify(this.userInviteRepository).updateConfirmedAt((String) any(), (LocalDateTime) any());
        verify(this.userInviteRepository).findByToken((String) any());
        verify(userInvite).getConfirmedAt();
        verify(userInvite).getOrganizationUUID();
        verify(userInvite, atLeast(1)).getUserUUID();
        verify(userInvite).setConfirmedAt((LocalDateTime) any());
        verify(userInvite).setCreatedAt((LocalDateTime) any());
        verify(userInvite).setEmail((String) any());
        verify(userInvite).setExpiresAt((LocalDateTime) any());
        verify(userInvite).setId((Long) any());
        verify(userInvite).setOrganizationUUID((UUID) any());
        verify(userInvite).setToken((String) any());
        verify(userInvite).setUserUUID((UUID) any());
        verify(this.organizationUserRoleService).setNewInvitedUserActive((UUID) any(), (UUID) any());
    }

    /**
     * Method under test: {@link UserInviteService#confirmToken(String)}
     */
    @Test
    void testConfirmToken3() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R026 Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   org.springframework.aop.framework.AopConfigException: Could not generate CGLIB subclass of class com.ravel.backend.appAuth.service.AppRoleServiceImpl: Common causes of this problem include using a final class or a non-visible class; nested exception is org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:209)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   org.springframework.cglib.core.CodeGenerationException: java.lang.LinkageError-->loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:580)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   java.lang.LinkageError: loader com.diffblue.cover.e.f @6c615a66 attempted duplicate class definition for com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b. (com.ravel.backend.appAuth.service.AppRoleServiceImpl$$EnhancerBySpringCGLIB$$4312602b is in unnamed module of loader com.diffblue.cover.e.f @6c615a66, parent loader 'app')
        //       at java.lang.System$2.defineClass(System.java:2307)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2439)
        //       at java.lang.invoke.MethodHandles$Lookup$ClassDefiner.defineClass(MethodHandles.java:2416)
        //       at java.lang.invoke.MethodHandles$Lookup.defineClass(MethodHandles.java:1843)
        //       at org.springframework.cglib.core.ReflectUtils.defineClass(ReflectUtils.java:577)
        //       at org.springframework.cglib.core.AbstractClassGenerator.generate(AbstractClassGenerator.java:363)
        //       at org.springframework.cglib.proxy.Enhancer.generate(Enhancer.java:585)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:110)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData$3.apply(AbstractClassGenerator.java:108)
        //       at org.springframework.cglib.core.internal.LoadingCache$2.call(LoadingCache.java:54)
        //       at java.util.concurrent.FutureTask.run(FutureTask.java:264)
        //       at org.springframework.cglib.core.internal.LoadingCache.createEntry(LoadingCache.java:61)
        //       at org.springframework.cglib.core.internal.LoadingCache.get(LoadingCache.java:34)
        //       at org.springframework.cglib.core.AbstractClassGenerator$ClassLoaderData.get(AbstractClassGenerator.java:134)
        //       at org.springframework.cglib.core.AbstractClassGenerator.create(AbstractClassGenerator.java:319)
        //       at org.springframework.cglib.proxy.Enhancer.createHelper(Enhancer.java:572)
        //       at org.springframework.cglib.proxy.Enhancer.createClass(Enhancer.java:419)
        //       at org.springframework.aop.framework.ObjenesisCglibAopProxy.createProxyClassAndInstance(ObjenesisCglibAopProxy.java:57)
        //       at org.springframework.aop.framework.CglibAopProxy.getProxy(CglibAopProxy.java:206)
        //       at org.springframework.aop.framework.ProxyFactory.getProxy(ProxyFactory.java:110)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.createProxy(AbstractAutoProxyCreator.java:478)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.wrapIfNecessary(AbstractAutoProxyCreator.java:342)
        //       at org.springframework.aop.framework.autoproxy.AbstractAutoProxyCreator.postProcessAfterInitialization(AbstractAutoProxyCreator.java:291)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsAfterInitialization(AbstractAutowireCapableBeanFactory.java:455)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1808)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:620)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.config.DependencyDescriptor.resolveCandidate(DependencyDescriptor.java:276)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.doResolveDependency(DefaultListableBeanFactory.java:1389)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.resolveDependency(DefaultListableBeanFactory.java:1309)
        //       at org.springframework.beans.factory.support.ConstructorResolver.resolveAutowiredArgument(ConstructorResolver.java:887)
        //       at org.springframework.beans.factory.support.ConstructorResolver.createArgumentArray(ConstructorResolver.java:791)
        //       at org.springframework.beans.factory.support.ConstructorResolver.autowireConstructor(ConstructorResolver.java:229)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.autowireConstructor(AbstractAutowireCapableBeanFactory.java:1372)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBeanInstance(AbstractAutowireCapableBeanFactory.java:1222)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:582)
        //       at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:542)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:335)
        //       at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:234)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:333)
        //       at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:208)
        //       at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:953)
        //       at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:918)
        //       at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:583)
        //       at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:740)
        //       at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:415)
        //       at org.springframework.boot.SpringApplication.run(SpringApplication.java:303)
        //       at org.springframework.boot.test.context.SpringBootContextLoader.loadContext(SpringBootContextLoader.java:136)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContextInternal(DefaultCacheAwareContextLoaderDelegate.java:99)
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:124)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:124)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        when(this.userInviteRepository.updateConfirmedAt((String) any(), (LocalDateTime) any())).thenReturn(1);
        when(this.userInviteRepository.findByToken((String) any())).thenReturn(Optional.empty());
        UserInvite userInvite = mock(UserInvite.class);
        when(userInvite.getConfirmedAt()).thenReturn(LocalDateTime.of(1, 1, 1, 1, 1));
        when(userInvite.getOrganizationUUID()).thenReturn(UUID.randomUUID());
        when(userInvite.getUserUUID()).thenReturn(UUID.randomUUID());
        doNothing().when(userInvite).setConfirmedAt((LocalDateTime) any());
        doNothing().when(userInvite).setCreatedAt((LocalDateTime) any());
        doNothing().when(userInvite).setEmail((String) any());
        doNothing().when(userInvite).setExpiresAt((LocalDateTime) any());
        doNothing().when(userInvite).setId((Long) any());
        doNothing().when(userInvite).setOrganizationUUID((UUID) any());
        doNothing().when(userInvite).setToken((String) any());
        doNothing().when(userInvite).setUserUUID((UUID) any());
        userInvite.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setEmail("jane.doe@example.org");
        userInvite.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        userInvite.setId(123L);
        userInvite.setOrganizationUUID(UUID.randomUUID());
        userInvite.setToken("ABC123");
        userInvite.setUserUUID(UUID.randomUUID());
        doNothing().when(this.organizationUserRoleService).setNewInvitedUserActive((UUID) any(), (UUID) any());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.userInviteService.confirmToken("ABC123"));
        verify(this.userInviteRepository).findByToken((String) any());
        verify(userInvite).setConfirmedAt((LocalDateTime) any());
        verify(userInvite).setCreatedAt((LocalDateTime) any());
        verify(userInvite).setEmail((String) any());
        verify(userInvite).setExpiresAt((LocalDateTime) any());
        verify(userInvite).setId((Long) any());
        verify(userInvite).setOrganizationUUID((UUID) any());
        verify(userInvite).setToken((String) any());
        verify(userInvite).setUserUUID((UUID) any());
    }
}

