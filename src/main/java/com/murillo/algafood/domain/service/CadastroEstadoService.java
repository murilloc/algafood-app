package com.murillo.algafood.domain.service;

import com.murillo.algafood.domain.exception.EntidadeEmUsoException;
import com.murillo.algafood.domain.exception.EstadoNaoEncontradoException;
import com.murillo.algafood.domain.model.Estado;
import com.murillo.algafood.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CadastroEstadoService {

    public static final String MSG_ESTADO_EM_USO = "Não é possível excluir estado id %d pois está sendo usado no momento";
    @Autowired
    EstadoRepository estadoRepository;

    @Transactional
    public Estado salvar(Estado estado) {

        return estadoRepository.save(estado);
    }

    @Transactional
    public void excluir(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
            estadoRepository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(estadoId);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO,estadoId));
        }
    }

    public Estado buscarOuFalhar(Long estadoId) {
        return estadoRepository.findById(estadoId)
                .orElseThrow(() -> new EstadoNaoEncontradoException(estadoId));
    }
}
