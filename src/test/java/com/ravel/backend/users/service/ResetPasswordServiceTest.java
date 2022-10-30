package com.ravel.backend.users.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.ravel.backend.email.EmailService;
import com.ravel.backend.shared.EnvironmentProperties;
import com.ravel.backend.shared.exception.NotFoundException;
import com.ravel.backend.users.model.ResetPassword;
import com.ravel.backend.users.model.User;
import com.ravel.backend.users.repository.ResetPasswordRepository;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ActiveProfiles({"h2"})
@ExtendWith(SpringExtension.class)
class ResetPasswordServiceTest {
    @MockBean
    private EmailService emailService;

    @MockBean
    private ResetPasswordRepository resetPasswordRepository;

    @Autowired
    private ResetPasswordService resetPasswordService;

    /**
     * Method under test: {@link ResetPasswordService#newResetPasswordEmail(User)}
     */
    @Test
    void testNewResetPasswordEmail() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.mail.MailSendException: Mail server connection failed; nested exception is com.diffblue.cover.sandbox.execution.ForbiddenByPolicyException: to access the network. Failed messages: com.diffblue.cover.sandbox.execution.ForbiddenByPolicyException: to access the network
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:448)
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:361)
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:356)
        //       at com.ravel.backend.email.EmailServiceImpl.resetPasswordEmail(EmailServiceImpl.java:119)
        //       at com.ravel.backend.users.service.ResetPasswordService.newResetPasswordEmail(ResetPasswordService.java:41)
        //       at com.ravel.backend.users.service.ResetPasswordService$$FastClassBySpringCGLIB$$3e6e1771.invoke(<generated>)
        //       at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:783)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123)
        //       at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:388)
        //       at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)
        //       at com.ravel.backend.users.service.ResetPasswordService$$EnhancerBySpringCGLIB$$9302ee5d.newResetPasswordEmail(<generated>)
        //   In order to prevent newResetPasswordEmail(User)
        //   from throwing MailSendException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   newResetPasswordEmail(User).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setEmail("jane.doe@example.org");
        resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setId(123L);
        resetPassword.setToken("ABC123");
        resetPassword.setUserUUID(UUID.randomUUID());
        when(this.resetPasswordRepository.save((ResetPassword) any())).thenReturn(resetPassword);
        doNothing().when(this.emailService).resetPasswordEmail((String) any(), (String) any(), (String) any());

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
        this.resetPasswordService.newResetPasswordEmail(user);

        // Assert
        verify(this.resetPasswordRepository).save((ResetPassword) any());
        verify(this.emailService).resetPasswordEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#newResetPasswordEmail(User)}
     */
    @Test
    void testNewResetPasswordEmail2() {
        //   Diffblue Cover was unable to write a Spring test,
        //   so wrote a non-Spring test instead.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   org.springframework.mail.MailSendException: Mail server connection failed; nested exception is com.diffblue.cover.sandbox.execution.ForbiddenByPolicyException: to access the network. Failed messages: com.diffblue.cover.sandbox.execution.ForbiddenByPolicyException: to access the network
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.doSend(JavaMailSenderImpl.java:448)
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:361)
        //       at org.springframework.mail.javamail.JavaMailSenderImpl.send(JavaMailSenderImpl.java:356)
        //       at com.ravel.backend.email.EmailServiceImpl.resetPasswordEmail(EmailServiceImpl.java:119)
        //       at com.ravel.backend.users.service.ResetPasswordService.newResetPasswordEmail(ResetPasswordService.java:41)
        //       at com.ravel.backend.users.service.ResetPasswordService$$FastClassBySpringCGLIB$$3e6e1771.invoke(<generated>)
        //       at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:783)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.transaction.interceptor.TransactionInterceptor$1.proceedWithInvocation(TransactionInterceptor.java:123)
        //       at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:388)
        //       at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:119)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)
        //       at com.ravel.backend.users.service.ResetPasswordService$$EnhancerBySpringCGLIB$$9302ee5d.newResetPasswordEmail(<generated>)
        //   In order to prevent newResetPasswordEmail(User)
        //   from throwing MailSendException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   newResetPasswordEmail(User).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setEmail("jane.doe@example.org");
        resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setId(123L);
        resetPassword.setToken("ABC123");
        resetPassword.setUserUUID(UUID.randomUUID());
        when(this.resetPasswordRepository.save((ResetPassword) any())).thenReturn(resetPassword);
        doThrow(new NotFoundException("An error occurred")).when(this.emailService)
                .resetPasswordEmail((String) any(), (String) any(), (String) any());

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
        assertThrows(NotFoundException.class, () -> this.resetPasswordService.newResetPasswordEmail(user));
        verify(this.resetPasswordRepository).save((ResetPassword) any());
        verify(this.emailService).resetPasswordEmail((String) any(), (String) any(), (String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    void testConfirmToken() {
        // Arrange
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setConfirmedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setEmail("jane.doe@example.org");
        resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setId(123L);
        resetPassword.setToken("ABC123");
        resetPassword.setUserUUID(UUID.randomUUID());
        Optional<ResetPassword> ofResult = Optional.of(resetPassword);
        when(this.resetPasswordRepository.findByToken((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.resetPasswordService.confirmToken("ABC123"));
        verify(this.resetPasswordRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    void testConfirmToken2() {
        // Arrange
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setConfirmedAt(null);
        resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setEmail("jane.doe@example.org");
        resetPassword.setExpiresAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setId(123L);
        resetPassword.setToken("ABC123");
        resetPassword.setUserUUID(UUID.randomUUID());
        Optional<ResetPassword> ofResult = Optional.of(resetPassword);
        when(this.resetPasswordRepository.findByToken((String) any())).thenReturn(ofResult);

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.resetPasswordService.confirmToken("ABC123"));
        verify(this.resetPasswordRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConfirmToken3() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.util.Optional.orElseThrow(java.util.function.Supplier)" because the return value of "com.ravel.backend.users.repository.ResetPasswordRepository.findByToken(String)" is null
        //       at com.ravel.backend.users.service.ResetPasswordService.confirmToken(ResetPasswordService.java:52)
        //       at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:789)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)
        //       at com.ravel.backend.users.service.ResetPasswordService$$EnhancerBySpringCGLIB$$9302ee5d.confirmToken(<generated>)
        //   In order to prevent confirmToken(String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   confirmToken(String).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        when(this.resetPasswordRepository.findByToken((String) any())).thenReturn(null);

        // Act
        this.resetPasswordService.confirmToken("ABC123");
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    void testConfirmToken4() {
        // Arrange
        when(this.resetPasswordRepository.findByToken((String) any())).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.resetPasswordService.confirmToken("ABC123"));
        verify(this.resetPasswordRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    void testConfirmToken5() {
        // Arrange
        when(this.resetPasswordRepository.findByToken((String) any()))
                .thenThrow(new NotFoundException("An error occurred"));

        // Act and Assert
        assertThrows(NotFoundException.class, () -> this.resetPasswordService.confirmToken("ABC123"));
        verify(this.resetPasswordRepository).findByToken((String) any());
    }

    /**
     * Method under test: {@link ResetPasswordService#confirmToken(String)}
     */
    @Test
    @Disabled("TODO: Complete this test")
    void testConfirmToken6() {
        // TODO: Complete this test.
        //   Reason: R013 No inputs found that don't throw a trivial exception.
        //   Diffblue Cover tried to run the arrange/act section, but the method under
        //   test threw
        //   java.lang.NullPointerException: Cannot invoke "java.time.LocalDateTime.isBefore(java.time.chrono.ChronoLocalDateTime)" because "expiredAt" is null
        //       at com.ravel.backend.users.service.ResetPasswordService.confirmToken(ResetPasswordService.java:57)
        //       at org.springframework.aop.support.AopUtils.invokeJoinpointUsingReflection(AopUtils.java:344)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.invokeJoinpoint(ReflectiveMethodInvocation.java:198)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:789)
        //       at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
        //       at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:753)
        //       at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:698)
        //       at com.ravel.backend.users.service.ResetPasswordService$$EnhancerBySpringCGLIB$$9302ee5d.confirmToken(<generated>)
        //   In order to prevent confirmToken(String)
        //   from throwing NullPointerException, add constructors or factory
        //   methods that make it easier to construct fully initialized objects used in
        //   confirmToken(String).
        //   See https://diff.blue/R013 to resolve this issue.

        // Arrange
        ResetPassword resetPassword = new ResetPassword();
        resetPassword.setConfirmedAt(null);
        resetPassword.setCreatedAt(LocalDateTime.of(1, 1, 1, 1, 1));
        resetPassword.setEmail("jane.doe@example.org");
        resetPassword.setExpiresAt(null);
        resetPassword.setId(123L);
        resetPassword.setToken("ABC123");
        resetPassword.setUserUUID(UUID.randomUUID());
        Optional<ResetPassword> ofResult = Optional.of(resetPassword);
        when(this.resetPasswordRepository.findByToken((String) any())).thenReturn(ofResult);

        // Act
        this.resetPasswordService.confirmToken("ABC123");
    }
}

