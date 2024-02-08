package org.iesvdm.videoclub.domain;

import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder // Esto es un constructor en cadena de Lombok
@Entity
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String texto;

    @ManyToOne
    @JoinColumn(name = "tutorial_id_fk", nullable = false, foreignKey = @ForeignKey(name = "FK_TUTO"))
    @ToString.Exclude // Con esto se evita la recursividad
    Tutorial tutorial;
}
