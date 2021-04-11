package forum.controller.dto;

import forum.modelo.StatusTopico;
import forum.modelo.Topico;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DetalhesDoTopicoDto {

    private long id;
    private String titulo;
    private String mensagem;
    private LocalDateTime dataCriacao;
    private String nomeAutor;
    private StatusTopico status;
    private List<RespostaDto> respostas;

    public DetalhesDoTopicoDto(Topico t) {
        this.id = t.getId();
        this.mensagem = t.getMensagem();
        this.titulo = t.getTitulo();
        this.dataCriacao = t.getDataCriacao();
        this.nomeAutor = t.getAutor().getNome();
        this.status = t.getStatus();
        this.respostas = new ArrayList<>();
        this.respostas.addAll(t.getRespostas().stream().map(RespostaDto::new).collect(Collectors.toList()));
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

    public String getNomeAutor() {
        return nomeAutor;
    }

    public StatusTopico getStatus() {
        return status;
    }

    public List<RespostaDto> getRespostas() {
        return respostas;
    }
}