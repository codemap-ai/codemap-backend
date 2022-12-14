package ai.codemap.codemap;

import ai.codemap.codemap.model.KakaoInfo;
import ai.codemap.codemap.repository.*;
import ai.codemap.codemap.service.*;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/* rabbit mq */
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;


@Configuration
public class SpringConfig {
    private final SubmissionRepository submissionRepository;
    private final ContestRepository contestRepository;
    private final LoadCodeRepository loadCodeRepository;
    private final KakaoInfoRespository kakaoInfoRespository;
    @Autowired
    public SpringConfig(ProblemSetRepository problemSetRepository, SubmissionRepository submissionRepository, ContestRepository contestRepository, LoadCodeRepository loadCodeRepository, KakaoInfoRespository kakaoInfoRespository) {
        this.submissionRepository = submissionRepository;
        this.contestRepository = contestRepository;
        this.loadCodeRepository = loadCodeRepository;
        this.kakaoInfoRespository = kakaoInfoRespository;
    }



    @Bean
    public KakaoInfoService kakaoInfoService() { return new KakaoInfoService(kakaoInfoRespository);}
    @Bean
    public SubmissionService repositoryService() {
        return new SubmissionService(submissionRepository);
    }

    @Bean
    public ContestService testService() {
        return new ContestService(contestRepository);
    }

    @Bean
    public LoadCodeService loadCodeService(){
        return new LoadCodeService(loadCodeRepository);
    }

    /* rabbit queue */

    private static final String EXCHANGE_NAME = "judge.exchange";
    private static final String QUEUE_NAME = "judge-queue";
    private static final String ROUTING_KEY = "4242";

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    @Bean
    Binding binding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
