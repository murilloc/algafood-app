package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.input.FormaPagamentoInputModel;
import com.murillo.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FormaPagamentoInputModelDisassembler {

    @Autowired
    private ModelMapper modelMapper;


    public FormaPagamento toDomainObject(FormaPagamentoInputModel formaPagamentoInput) {
        return modelMapper.map(formaPagamentoInput, FormaPagamento.class);
    }


    public void copyToDomainObject(FormaPagamentoInputModel formaPagamentoInput, FormaPagamento formaPagamento) {
        modelMapper.map(formaPagamentoInput, formaPagamento);
    }
}
