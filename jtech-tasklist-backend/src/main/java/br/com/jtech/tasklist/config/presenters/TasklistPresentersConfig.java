package br.com.jtech.tasklist.config.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.*;
import br.com.jtech.tasklist.config.qualifiers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TasklistPresentersConfig {

    @Bean
    @UpdateTasklist
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UpdateTasklistPresenter updateTasklistOutputGateway() {
        return new UpdateTasklistPresenter();
    }

    @Bean
    @CreateTasklist
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CreateTasklistPresenter createTasklistOutputGateway() {
        return new CreateTasklistPresenter();
    }

    @Bean
    @ListTasklist
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ListTasklistPresenter listTasklistOutputGateway() {
        return new ListTasklistPresenter();
    }



}

