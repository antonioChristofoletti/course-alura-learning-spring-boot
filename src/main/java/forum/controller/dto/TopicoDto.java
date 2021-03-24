package forum.controller.dto;

import forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class TopicoDto {

    private long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico t) {
        this.mensagem = t.getMensagem();
        this.titulo = t.getTitulo();
        this.dataCriacao = t.getDataCriacao();
    }

    public static List<TopicoDto> convert(List<Topico> list) {
        return list.stream().map(TopicoDto::new).collect(Collectors.toList());
    }

    public long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
}