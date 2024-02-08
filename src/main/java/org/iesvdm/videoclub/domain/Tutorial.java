package org.iesvdm.videoclub.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Esto es un constructor en cadena de Lombok
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
                                    // LAZY es el valor default
    @OneToMany(mappedBy = "tutorial", fetch = FetchType.LAZY)
    private List<Comentario> comentarios = new ArrayList<>();


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
