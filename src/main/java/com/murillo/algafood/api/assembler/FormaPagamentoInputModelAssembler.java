package com.murillo.algafood.api.assembler;

import com.murillo.algafood.api.model.output.FormaPagamentoOutputModel;
import com.murillo.algafood.domain.model.FormaPagamento;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FormaPagamentoInputModelAssembler {

    @Autowired
    private ModelMapper modelMapper;


    public FormaPagamentoOutputModel toOutputModel(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoOutputModel.class);
    }


    public List<FormaPagamentoOutputModel> toOutputModelCollection(List<FormaPagamento> formasPagamento) {
        return formasPagamento.stream()
                .map(this::toOutputModel)
                .collect(Collectors.toList());
    }
}
