package com.myproject.bankwithkafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.myproject.bankwithkafka.dto.PaymentTransactionRequestDto;
import com.myproject.bankwithkafka.service.PaymentTransactionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentTransactionListener {
    private final PaymentTransactionService paymentTransactionService;
    @KafkaListener(topics = "payment-transactions-topic", groupId = "payment-transaction-group", containerFactory = "kafkaListenerContainerFactory")
    public void processPaymentTransaction(
            @Payload PaymentTransactionRequestDto request,
            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset,
            Acknowledgment acknowledgment) {
        log.info("Получено сообщение из топика: {}, partition: {}, offset: {}, payload: {}", topic, partition, offset, request);
        try {
            var result = paymentTransactionService.initiateTransaction(request);
            log.info("Транзакция успешно обработана: {}", result);
            acknowledgment.acknowledge();

        } catch (Exception e) {
            log.error("Ошибка при обработке транзакции: {}", e.getMessage(), e);
            acknowledgment.acknowledge();
        }
    }
}