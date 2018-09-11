package com.hhly.ticket.config;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @desc RabbitMq配置类
 * @author wulong
 * @date 2018年7月26日
 * @company 益彩网络
 * @version v1.0
 */
@Configuration
public class RabbitMqConfig {

	/**
	 * number消费者预消费数
	 */
	@Value("${rabbitmq.prefetchCount}")
	private int prefetchCount;
	
	
	/**
	 * peopleout消费者并发数
	 */
	@Value("${peopleout.rabbitmq.concurrent}")
	private int peopleoutConcurrent;
	
	
	/**
	 * number消费者并发数
	 */
	@Value("${number.rabbitmq.concurrent}")
	private int numberConcurrent;

	/**
	 * 并发设置的线程池线程数
	 */
	@Value("${rabbitmq.threadNum}")
	private int threadNum;

	/**
	 * numberListenter Rabbit监听容器工厂
	 * @param configurer
	 * @param connectionFactory
	 * @return
	 */
	@Bean("numberRabbitmqContainerFactory")
	public SimpleRabbitListenerContainerFactory numberRabbitmqContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setPrefetchCount(prefetchCount);
		factory.setConcurrentConsumers(numberConcurrent);
		// 线程的线程池
		ExecutorService executorService = Executors.newFixedThreadPool(numberConcurrent);
		factory.setTaskExecutor(executorService);
		configurer.configure(factory, connectionFactory);
		return factory;
	}
	
	/**
	 * peopleOutTicketListenter Rabbit监听容器工厂
	 * @param configurer
	 * @param connectionFactory
	 * @return
	 */
	@Bean("peopleoutRabbitmqContainerFactory")
	public SimpleRabbitListenerContainerFactory peopleoutRabbitmqContainerFactory(
			SimpleRabbitListenerContainerFactoryConfigurer configurer, ConnectionFactory connectionFactory) {
		SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
		factory.setPrefetchCount(prefetchCount);
		factory.setConcurrentConsumers(peopleoutConcurrent);
		// 线程的线程池
		ExecutorService executorService = Executors.newFixedThreadPool(peopleoutConcurrent);
		factory.setTaskExecutor(executorService);
		configurer.configure(factory, connectionFactory);
		return factory;
	}
}
