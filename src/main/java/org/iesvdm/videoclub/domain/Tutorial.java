package org.iesvdm.videoclub.domain;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Esto es un constructor en cadena de Lombok
@Entity
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(
        name = "tutorial",
        schema = "videoclub_jpa",
        indexes = {@Index(name="index_titulo", columnList = "titulo", unique = false)})
public class Tutorial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include // Solo utiliza ID para el equals
    private long id;

    @Column(name = "titulo", length = 50, nullable = false)
    private String titulo;

    @Column(name = "descrip", length = 150)
    private String descripcion;

    @Column(name = "publi")
    private boolean publicado;

    @Column(nullable = false)
    private Date fechaPublicacion;
                                    // LAZY as default value // CASCADE ALL
    @OneToMany(mappedBy = "tutorial", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Comentario> comentarios;
    // Usamos SET para evitar comentarios duplicados


    // HELPER AÑADIR:
    public Tutorial addComentario(Comentario comentario) {
        comentario.setTutorial(this);
        this.comentarios.add(comentario);
        return this;
        // Se devuelve a sí mismo ara poder hacer el encadenamiento de métodos.
    }

    // HELPER BORRAR:
    public Tutorial removeComentario(Comentario comentario) {
        this.comentarios.remove(comentario);
        comentario.setTutorial(null); // Seteo Null para desvincularlo en el otro lado (relación bidireccional)
        return this;
        // Se devuelve a sí mismo para poder hacer el encadenamiento de métodos.
    }

}
