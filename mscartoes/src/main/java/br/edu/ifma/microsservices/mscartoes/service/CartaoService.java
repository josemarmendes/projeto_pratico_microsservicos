package br.edu.ifma.microsservices.mscartoes.service;

import br.edu.ifma.microsservices.mscartoes.domain.Cartao;
import br.edu.ifma.microsservices.mscartoes.infra.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartaoService {

    private final CartaoRepository repository;

    @Transactional
    public Cartao save(Cartao cartao){
        return repository.save(cartao);
    }

    public List<Cartao> getCartoesRendaMenorIgual(Long renda){
        var rendaInformada = BigDecimal.valueOf(renda);
        return repository.findByRendaLessThanEqual(rendaInformada);
    }

}
