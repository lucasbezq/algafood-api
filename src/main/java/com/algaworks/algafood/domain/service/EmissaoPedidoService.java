package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.FormaPagamentoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.PedidoNaoEncontradaException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CadastroRestauranteService restauranteService;

    @Autowired
    private CadastroFormaPagamentoService formaPagamentoService;

    @Autowired
    private CadastroCidadeService cidadeService;

    @Autowired
    private CadastroProdutoService produtoService;

    @Autowired
    private CadastroUsuarioService usuarioService;

    public Pedido buscarPedido(String codigo) {
        return pedidoRepository.findByCodigo(codigo)
                .orElseThrow(() -> new PedidoNaoEncontradaException(codigo));
    }

    public Pedido emitir(Pedido pedido) {
        validarPedido(pedido);
        validarItens(pedido);

        pedido.setTaxaFrete(pedido.getRestaurante().getTaxaFrete());
        pedido.calcularValorTotal();

        return pedidoRepository.save(pedido);
    }

    public void validarPedido(Pedido pedido) {
        var cidade = cidadeService.buscarCidade(pedido.getEnderecoEntrega().getCidade().getId());
        var cliente = usuarioService.buscarUsuario(pedido.getCliente().getId());
        var restaurante = restauranteService.buscarRestaurante(pedido.getRestaurante().getId());
        var formaPagamento = formaPagamentoService.buscarFormaPagamento(pedido.getFormaPagamento().getId());

        pedido.getEnderecoEntrega().setCidade(cidade);
        pedido.setCliente(cliente);
        pedido.setRestaurante(restaurante);
        pedido.setFormaPagamento(formaPagamento);

        if (restaurante.naoAceitaFormaPagamento(formaPagamento)) {
            throw new FormaPagamentoNaoEncontradoException(formaPagamento.getId(), restaurante.getId());
        }
    }

    public void validarItens(Pedido pedido) {
        pedido.getItens().forEach(item -> {
                    var produto = produtoService.buscarProduto(item.getProduto().getId(), pedido.getRestaurante().getId());
                    item.setPedido(pedido);
                    item.setProduto(produto);
                    item.setPrecoUnitario(produto.getPreco());
                });
    }

}
