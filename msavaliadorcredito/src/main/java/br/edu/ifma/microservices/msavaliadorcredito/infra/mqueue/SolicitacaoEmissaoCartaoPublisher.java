package br.edu.ifma.microservices.msavaliadorcredito.infra.mqueue;

import br.edu.ifma.microservices.msavaliadorcredito.domain.model.DadosSolicitacaoEmissaoCartao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SolicitacaoEmissaoCartaoPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final Queue queueEmissaoCartoes;


    public void solicitarCartao(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
            var json = convertToJson(dados);
            rabbitTemplate.convertAndSend(queueEmissaoCartoes.getName(), json);
    }

    private String convertToJson(DadosSolicitacaoEmissaoCartao dados) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        var json = mapper.writeValueAsString(dados);
        return json;
    }
}
