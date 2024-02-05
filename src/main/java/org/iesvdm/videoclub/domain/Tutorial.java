package org.iesvdm.videoclub.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "tutorial",
        schema = "videoclub_jpa",
        indexes = {@Index(name="index_titulo", columnList = "titulo", unique = false)})
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @Column(name = "descrip", length = 150)
    private String descripcion;

    @Column(name = "publi")
    private boolean publicado;

    @OneToMany
    private List<Comentario> comentarios;

}
