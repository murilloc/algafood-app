package com.murillo.algafood.domain.repository;

import com.murillo.algafood.domain.model.FormaPagamento;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;

@Repository
public interface FormaPagamentoRepository extends CustomJpaRepository<FormaPagamento, Long> {

    @Query("select max(fp.dataAtualizacao) from FormaPagamento fp")
    OffsetDateTime getDataUltimaAtualizacao();

    @Query("select fp.dataAtualizacao from FormaPagamento fp where fp.id = :formaPagamentoId")
    OffsetDateTime getDataUltimaAtualizacaoById(Long formaPagamentoId);

}
