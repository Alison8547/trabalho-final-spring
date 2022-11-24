package br.com.dbc.dbcmovies.service;


import br.com.dbc.dbcmovies.dto.LocadoraDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutorService {


    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;


    @Value(value = "${kafka.topic}")
    private String topico;


    @Value(value = "${kafka.partition}")
    private Integer particao;


    public void sendTo(LocadoraDto locadora) throws JsonProcessingException {

        String mensagemStr = objectMapper.writeValueAsString(locadora);

        MessageBuilder<String> stringMessageBuilder = MessageBuilder.withPayload(mensagemStr)
                .setHeader(KafkaHeaders.TOPIC, topico)
                .setHeader(KafkaHeaders.MESSAGE_KEY, UUID.randomUUID().toString())
                .setHeader(KafkaHeaders.PARTITION_ID, particao);

        Message<String> message = stringMessageBuilder.build();

        ListenableFuture<SendResult<String, String>> enviadoParaTopico = kafkaTemplate.send(message);
        enviadoParaTopico.addCallback(new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult result) {
                log.info("Foi enviada com sucesso: id: {} | NomePessoa: {} | Preço: {} | NomeItem: {} | Disponibilidade: {} ", locadora.getIdLocadora(), locadora.getNomePessoa(), locadora.getPreco(), locadora.getNomeItem(), locadora.isDisponibilidade());
            }

            @Override
            public void onFailure(Throwable ex) {
                log.error(" Erro ao enviar: {}", locadora, ex);
            }
        });
    }

}
