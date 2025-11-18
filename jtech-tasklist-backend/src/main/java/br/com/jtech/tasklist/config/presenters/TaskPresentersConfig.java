package br.com.jtech.tasklist.config.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.*;
import br.com.jtech.tasklist.config.qualifiers.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TaskPresentersConfig {

    @Bean
    @CreateTask
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CreateTaskPresenter createTaskOutputGateway() {
        return new CreateTaskPresenter();
    }

    @Bean
    @UpdateTask
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public UpdateTaskPresenter updateTaskOutputGateway() {
        return new UpdateTaskPresenter();
    }

    @Bean
    @ListTasks
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public ListTasksPresenter listTasksOutputGateway() {
        return new ListTasksPresenter();
    }

    @Bean
    @CompleteTask
    @Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public CompleteTaskPresenter completeTaskOutputGateway() {
        return new CompleteTaskPresenter();
    }

}
