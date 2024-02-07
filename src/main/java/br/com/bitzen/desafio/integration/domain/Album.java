package br.com.bitzen.desafio.integration.domain;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private int year;
    private String imageCover;

    @ManyToOne
    @JoinColumn(name = "artistaId")
    private Artista artista;

    @OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
    private List<Musica> musicas;

}