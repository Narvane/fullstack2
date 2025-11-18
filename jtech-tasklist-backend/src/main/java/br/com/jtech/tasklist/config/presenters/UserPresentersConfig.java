package br.com.jtech.tasklist.config.presenters;

import br.com.jtech.tasklist.adapters.output.presenters.AuthenticateUserPresenter;
import br.com.jtech.tasklist.adapters.output.presenters.RegisterUserPresenter;
import br.com.jtech.tasklist.application.ports.output.UserOutputGateway;
import br.com.jtech.tasklist.config.qualifiers.AuthenticateUser;
import br.com.jtech.tasklist.config.qualifiers.RegisterUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserPresentersConfig {

    @Bean
    @RegisterUser
    public UserOutputGateway registerUserPresenter() {
        return new RegisterUserPresenter();
    }

    @Bean
    @AuthenticateUser
    public UserOutputGateway authenticateUserPresenter() {
        return new AuthenticateUserPresenter();
    }

}

