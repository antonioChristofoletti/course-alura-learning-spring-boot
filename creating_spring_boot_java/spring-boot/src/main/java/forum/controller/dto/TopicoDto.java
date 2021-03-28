package forum.controller.dto;

import forum.modelo.Topico;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;

public class TopicoDto {

    private long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;

    public TopicoDto(Topico t) {
        this.id = t.getId();
        this.mensagem = t.getMensagem();
        this.titulo = t.getTitulo();
        this.dataCriacao = t.getDataCriacao();
    }

    public static Page<TopicoDto> convert(Page<Topico> list) {
        return list.map(TopicoDto::new);
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